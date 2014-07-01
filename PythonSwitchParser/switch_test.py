#!/usr/bin/env python
from ciscoconfparse.ciscoconfparse import CiscoConfParse
from SymnetParser import SymnetParser

parse = SymnetParser(CiscoConfParse("configs/Span.txt"))

# what do I need to know about a switch:
# 1. The list of configured VLANs, and the assigned ports, on "myself" i.e. the current switch
# 2. The list of learned mappings: mac-vlan-outgoing port, which were learned during traffic 
# 3. The ports which are blocked by STP (show span)

# I must model what ARP messaging does

# Returns the list of vlans
#!/usr/bin/env python

# clean out everything except VLANs and subsequent interfaces, on which FWD is set
clean_conf_lines = parse.find_lines("^VLAN|^[^ I-].*FWD*")
# clean out everything except VLANs and subsequent interfaces, on which BLK is set
#clean_conf_lines = parse.find_lines("^VLAN|^[^ I-].*BLK*")

# for each port line in the cleansed config, add a tab, to make them children

tab = "   "

struct_conf_lines = []
for line in clean_conf_lines:
    if line.startswith("VLAN"):
        struct_conf_lines.append(line)
    else:   
        struct_conf_lines.append(tab+line)

#print(struct_conf_lines)

parse = SymnetParser(CiscoConfParse(struct_conf_lines))

vlanlist = parse.find_lines("^VLAN")

for vlan in vlanlist:
    print("VLAN "+vlan)
    vlan_ch = parse.find_all_children(vlan)
    for child in vlan_ch:
        lst = child.lstrip().split(" ")
        print(lst[0])

