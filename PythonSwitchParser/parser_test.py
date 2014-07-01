#!/usr/bin/env python
from ciscoconfparse.ciscoconfparse import CiscoConfParse
from SymnetParser import SymnetParser

parse = SymnetParser(CiscoConfParse("configs/Parser_test.txt"))

cmdlist = parse.find_lines("^command")

for cmd in cmdlist:
    print("Command "+cmd)
    cmdo = parse.find_all_children(cmd)
    pcmd = SymnetParser(CiscoConfParse(cmdo))
    cmdl = pcmd.find_lines("^"+SymnetParser.tab+"command")
    #    for c in cmdl:
    #        cc = SymnetParser
    print(cmdl)
