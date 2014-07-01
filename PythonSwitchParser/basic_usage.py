#!/usr/bin/env python
from ciscoconfparse.ciscoconfparse import CiscoConfParse

parse = CiscoConfParse("configs/Switch0_running-config.txt")

# Return a list of all ATM interfaces and subinterfaces
atm_intfs = parse.find_lines("^interface\sATM")

ups = parse.find_parents_w_child("^interface","up")

# Return a list of all interfaces with a certain QOS policy
qos_intfs = parse.find_parents_w_child( "^interf", "service-policy" )

# Return a list of all active interfaces (i.e. not shutdown)
active_intfs = parse.find_parents_wo_child( "^interf", "shutdown" )

print(ups)
