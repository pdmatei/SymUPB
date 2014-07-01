#!/usr/bin/env python
from ciscoconfparse.ciscoconfparse import CiscoConfParse
from SymnetParser import SymnetParser
import re
import os
import sys

from functools import reduce

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
        
    def gen_in_port (self,str):
        return self.id+"-"+str 
    def gen_out_port (self,str):
        return self.id+"-"+str 
        
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
        #map (lambda t:self.gen_tuple(self.gen_string,t),src)
		
    def gen_span(self):
        return self.gen_list_of_tuples(self.port_vlan)
	
    def gen_mac(self):
        return self.gen_list_of_tuples(self.mac)
                
    def gen_haskell_output(self):
        return "r"+str(self.id)+" = "+self.func_name+" "+self.gen_list_of_tuples(self.port_vlan)+" "+self.gen_list_of_tuples(self.mac)


		
# SwitchGenerator(id,span-file,mac-file)
# id e folosit pentru a garanta unicitatea regulii generate, in Haskell (e.g. r<id> = gen_switch ...)
# span-file si mac-file pot fi cai relative sau absolute        
# switch = SwitchGenerator("0","C:/configs/Span-Switch0.txt","C:/configs/Mac-Switch0.txt")
switch = SwitchGenerator(sys.argv[1],sys.argv[2],sys.argv[3])

# afiseaza regula de switch generata
#print(switch.gen_haskell_output())

# afiseaza parametrul corespunzator fisierului ce contine outputul "show span" in format Haskell
print(switch.gen_span())

# afiseaza parametrul corespunzator fisierului ce contine outputul "show mac" in format Haskell
print(switch.gen_mac())


