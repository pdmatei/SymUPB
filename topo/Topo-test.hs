import Switch_rule
import Rules
import Behaviours
import CompactFlow
import Flow
import Expr
import Applications
import Utils
import Transf

import qualified Data.Set as Set


r0 = gen_switch [("Switch0-Fa0/1","1"),("Switch0-Fa0/2","1"),("Switch0-Fa0/1","5"),("Switch0-Fa0/2","5"),("Switch0-Fa0/22","5"),("Switch0-Fa0/23","5"),("Switch0-Fa0/1","6"),("Switch0-Fa0/2","6"),("Switch0-Fa0/1","99"),("Switch0-Fa0/2","99")] [("1","0009.7ce8.5901","Switch0-Fa0/1"),("1","00e0.8f2e.d002","Switch0-Fa0/2"),("5","0009.7ce8.5901","Switch0-Fa0/1"),("5","00d0.bca7.9546","Switch0-Fa0/23"),("5","00e0.8f2e.d002","Switch0-Fa0/2"),("5","00e0.a3aa.e980","Switch0-Fa0/22"),("6","0009.7ce8.5901","Switch0-Fa0/1"),("6","00e0.8f2e.d002","Switch0-Fa0/2"),("99","0009.7ce8.5901","Switch0-Fa0/1"),("99","00e0.8f2e.d002","Switch0-Fa0/2")]
r1 = gen_switch [("Switch1-Fa0/3","1"),("Switch1-Fa0/3","5"),("Switch1-Fa0/23","5"),("Switch1-Fa0/24","5"),("Switch1-Fa0/3","6"),("Switch1-Fa0/3","99")] [("1","00e0.8f2e.d003","Switch1-Fa0/3"),("5","000b.be80.c701","Switch1-Fa0/1"),("5","00e0.8f2e.d003","Switch1-Fa0/3"),("6","000b.be80.c701","Switch1-Fa0/1"),("6","00e0.8f2e.d003","Switch1-Fa0/3"),("99","000b.be80.c701","Switch1-Fa0/1"),("99","00e0.8f2e.d003","Switch1-Fa0/3")]
r2 = gen_switch [("Switch2-Fa0/2","1"),("Switch2-Fa0/3","1"),("Switch2-Fa0/2","5"),("Switch2-Fa0/3","5"),("Switch2-Fa0/24","5"),("Switch2-Fa0/2","6"),("Switch2-Fa0/3","6"),("Switch2-Fa0/2","99"),("Switch2-Fa0/3","99")] [("1","0009.7ce8.5903","Switch2-Fa0/3"),("1","000b.be80.c702","Switch2-Fa0/2"),("5","0009.7ce8.5903","Switch2-Fa0/3"),("6","0009.7ce8.5903","Switch2-Fa0/3"),("99","0009.7ce8.5903","Switch2-Fa0/3")]
r3 = wire_rule "Switch0-Fa0/1:out" "Switch1-Fa0/1:in"
r4 = wire_rule "Switch1-Fa0/1:out" "Switch0-Fa0/1:in"
r5 = wire_rule "Switch0-Fa0/2:out" "Switch2-Fa0/2:in"
r6 = wire_rule "Switch2-Fa0/2:out" "Switch0-Fa0/2:in"
r7 = wire_rule "Switch0-Fa0/22:out" "PC0-Fa:in"
r8 = wire_rule "PC0-Fa:out" "Switch0-Fa0/22:in"
r9 = wire_rule "Switch0-Fa0/23:out" "PC2-Fa:in"
r10 = wire_rule "PC2-Fa:out" "Switch0-Fa0/23:in"
r11 = wire_rule "Switch1-Fa0/1:out" "Switch0-Fa0/1:in"
r12 = wire_rule "Switch0-Fa0/1:out" "Switch1-Fa0/1:in"
r13 = wire_rule "Switch1-Fa0/3:out" "Switch2-Fa0/3:in"
r14 = wire_rule "Switch2-Fa0/3:out" "Switch1-Fa0/3:in"
r15 = wire_rule "Switch1-Fa0/23:out" "PC3-Fa:in"
r16 = wire_rule "PC3-Fa:out" "Switch1-Fa0/23:in"
r17 = wire_rule "Switch1-Fa0/24:out" "PC1-Fa:in"
r18 = wire_rule "PC1-Fa:out" "Switch1-Fa0/24:in"
r19 = wire_rule "Switch2-Fa0/2:out" "Switch0-Fa0/2:in"
r20 = wire_rule "Switch0-Fa0/2:out" "Switch2-Fa0/2:in"
r21 = wire_rule "Switch2-Fa0/3:out" "Switch1-Fa0/3:in"
r22 = wire_rule "Switch1-Fa0/3:out" "Switch2-Fa0/3:in"
r23 = wire_rule "Switch2-Fa0/24:out" "Server0-Fa:in"
r24 = wire_rule "Server0-Fa:out" "Switch2-Fa0/24:in"
r25 = wire_rule "PC0-Fa:out" "Switch0-Fa0/22:in"
r26 = wire_rule "Switch0-Fa0/22:out" "PC0-Fa:in"
r27 = wire_rule "PC1-Fa:out" "Switch1-Fa0/24:in"
r28 = wire_rule "Switch1-Fa0/24:out" "PC1-Fa:in"
r29 = wire_rule "PC2-Fa:out" "Switch0-Fa0/23:in"
r30 = wire_rule "Switch0-Fa0/23:out" "PC2-Fa:in"
r31 = wire_rule "PC3-Fa:out" "Switch1-Fa0/23:in"
r32 = wire_rule "Switch1-Fa0/23:out" "PC3-Fa:in"
r33 = wire_rule "Server0-Fa:out" "Switch2-Fa0/24:in"
r34 = wire_rule "Switch2-Fa0/24:out" "Server0-Fa:in"
rules = [r0,r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15,r16,r17,r18,r19,r20,r21,r22,r23,r24,r25,r26,r27,r28,r29,r30,r31,r32,r33,r34]

{-
f1 = ("port" .=. "PC0-Fa:out") .>. finit
r = lfp_reachability "PC3-Fa:in" f1 rules

f2 = ("port" .=. "Switch2-Fa0/2:in") .>. finit
r' = lfp_reachability "Switch2-Fa0/3:out" finit rules
-}
ac = acl "deny" "tcp" "192.168.1.0 255.255.255.0" "" "any" ""
--(Dest-IP := [141.85.225.151:255.255.255.255], Source-IP := [0.0.0.0:37.128.224.5]) U 
cf0 = ("Dest-IP" .=. (TypedInt "ip" (ipToNumericValue "141.85.225.151") (ipToNumericValue  "255.255.255.255"))) .>. ("Source-IP" .=. (TypedInt "ip" (ipToNumericValue "0.0.0.0") (ipToNumericValue "37.128.224.5"))) .>. finit

cf1 = ("Dest-IP" .=. (TypedInt "ip" (ipToNumericValue "141.85.225.151") (ipToNumericValue  "255.255.255.255"))) .>. cfinit
cf2 = ("Dest-IP" .=. (TypedInt "ip" (ipToNumericValue "141.85.228.0") (ipToNumericValue  "141.85.228.63"))) .>. cfinit
cf3 = ("Source-IP" .=. (TypedInt "ip" (ipToNumericValue "0.0.0.0") (ipToNumericValue  "37.128.224.5"))) .>. cfinit
cf4 = ("proto" .=. "ip") .>. cfinit
ls = Set.fromList [cf1.>.cf2.>.cf3.>.cf4]
{-
weird = cf0 `capp` f2
ccf1 = head $ Set.toList ls
ccf2 = head $ Set.toList weird

toList c = let CFlow cfs = c in Set.toList cfs
-}

f0 = acl "permit" "ip" "any" "" "host 141.85.225.150" ""
f1 = acl "permit" "icmp" "host 37.128.224.6" "" "any" ""
f2 = acl "permit" "ip" "any" "" "141.85.228.0 255.255.255.192" ""
f3 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq ftp"
f4 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq www"
f5 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq 5000"
f6 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq 5001"
f7 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq https"
f8 = acl "permit" "tcp" "any" "" "host 141.85.225.152" "eq ssh"
f9 = acl "permit" "tcp" "any" "" "host 141.85.225.153" ""

r_incoming = let
 		 f0 = acl "permit" "ip" "any" "" "host 141.85.225.150" ""
		 f1 = acl "permit" "icmp" "host 37.128.224.6" "" "any" ""
		 f2 = acl "permit" "ip" "any" "" "141.85.228.0 255.255.255.192" ""
		 f3 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq ftp"
		 f4 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq www"
		 f5 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq 5000"
		 f6 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq 5001"
		 f7 = acl "permit" "tcp" "any" "" "host 141.85.225.151" "eq https"
		 f8 = acl "permit" "tcp" "any" "" "host 141.85.225.152" "eq ssh"
		 f9 = acl "permit" "tcp" "any" "" "host 141.85.225.153" ""
		in pipeLine (f0:f1:f2:f3:f4:f5:f6:f7:f8:f9:[])--(f0:f1:f2:f3:f4:f5:f6:f7:f8:f9:[])
        
        