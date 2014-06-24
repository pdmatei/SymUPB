#!/usr/bin/env python
from ciscoconfparse.ciscoconfparse import CiscoConfParse
from SymnetParser import SymnetParser
import re
import os

from functools import reduce

# questions:
# 1. what does a switch do, if it cannot establish on which port to forward a frame? Broadcasting is a vulnerability, right?
# 2. Does the trunking protocol permit any VLAN, or only does configured on the switch?
# 3. Native VLANs


class SwitchGenerator:
    tab = "    "
    func_name = "gen_switch"
    
    def __init__(self, id, spanning_tree, mac_tables):
        self.port_vlan = []
        self.mac = []
        self.id = 0

        self.id = id
        # 
        # Step 1: process the output of show spanning-tree
        # parse the file
        parse = SymnetParser(CiscoConfParse(spanning_tree))
        # clean out everything except VLANs and subsequent interfaces, on which FWD is set
        clean_conf_lines = parse.find_lines("^VLAN|^[^ I-].*FWD*")
        # add tabs to interfaces, so that they become children to the appropriate VLAN
        struct_conf_lines = []
        for line in clean_conf_lines:
            if line.startswith("VLAN"):
                struct_conf_lines.append(line)
            else:   
                struct_conf_lines.append(self.tab+line)
        # re-parse the cleaned-out and structured output
        parse = SymnetParser(CiscoConfParse(struct_conf_lines))

        vlanlist = parse.find_lines("^VLAN")
        # for each vlan
        for vlan in vlanlist:
            vlanid = vlan.strip("VLAN0")
            vlan_ch = parse.find_all_children(vlan)
            for child in vlan_ch:
                port = child.lstrip().split(" ")[0]
                # the input port has to have the device id, and the suffix ":in"
                self.port_vlan.append((self.gen_in_port(port),vlanid))
                    
        # Step 2: process the output of show mac-addresses
        parse = SymnetParser(CiscoConfParse(mac_tables))
        # ignore the first part of the output
        clean_conf_lines = parse.find_lines("^.*[0-9]")
        for line in clean_conf_lines:
            #remove the first whitespaces completely, and replace all other multiple whitespaces with a single one
            entry = re.sub('^ +','',re.sub(' +',' ',line)).split(" ")
            # the output port has to have the device id, and the suffix ":out"
            self.mac.append((entry[0],entry[1],self.gen_out_port(entry[3])))
#            if entry[1] in self.mac:
#                self.mac[entry[1]].append((entry[0],entry[3]))
#            else:
#                self.mac[entry[1]]=[(entry[0],entry[3])]
        
    def gen_in_port (self,str):
        return self.id+"-"+str #+":in"
    def gen_out_port (self,str):
        return self.id+"-"+str #+":out"
        
    def gen_string (self,str):
        return "\""+str+"\""
    
    def gen_list (self, f, list):
        #generate a haskell list, and apply the transformation f on each element
        if not list:
            return "[]"
        else:
            return "["+reduce (lambda x, y:x+","+y,map(f,list))+"]"
        
    def gen_tuple (self, f, item):
        # each of the functions f and g transform the pair components 
        return "("+reduce (lambda x, y:x+","+y,map(f,item))+")"
    
    def gen_list_of_tuples(self, src):
        return self.gen_list(lambda t:self.gen_tuple(self.gen_string,t),src)
        
        map (lambda t:self.gen_tuple(self.gen_string,t),src)
                
    def gen_haskell_output(self):
        return "r"+str(self.id)+" = "+self.func_name+" "+self.gen_list_of_tuples(self.port_vlan)+" "+self.gen_list_of_tuples(self.mac)

class TopologyGenerator:

    switch_list = {}
    span_tpl = "Span-"
    mac_tpl = "Mac-"
    extension = ".txt"
    config_dir = "./"
    
    def __init__(self,config_dir):
        # we assume switches are given as pairs of configuration files:
        # Span-switchId and Mac-switchId
        self.config_dir = config_dir
        # we build the list of ids, by selecting those files starting with "Mac-" and trimming the prefix "Mac-" and the extension
        switchIdList = map (lambda file:file[len(self.mac_tpl):len(file)-len(self.extension)],filter(lambda file:file.startswith(self.mac_tpl),os.listdir("./"+config_dir))) 
               
        # we build a list of switch parsers
        for id in switchIdList:
            self.switch_list[id] = SwitchGenerator(id,config_dir+self.span_tpl+id+self.extension,config_dir+self.mac_tpl+id+self.extension)

    def get_haskell_output(self,file):
        f = open(self.config_dir+file,'w')
        for switch in self.switch_list:
            f.write(self.switch_list[switch].gen_haskell_output()+'\n') # python will convert \n to os.linesep
        f.close()
        
#switch = SwitchGenerator("0","configs/Span-1.txt","configs/Mac-1.txt")
#print(reduce(lambda x,y:x+y,(1,2,3,4,5)))
#print(switch.gen_tuple(switch.gen_string,("1","2","3","4")))
#print(switch.gen_list_of_tuples(switch.mac))
#print(switch.gen_list_of_tuples(switch.port_vlan))

#print(switch.gen_haskell_output())

topo = TopologyGenerator("configs/")
topo.get_haskell_output("Topo.hs")


# input: doua fisiere text (span si mac)
# vars la stdout 