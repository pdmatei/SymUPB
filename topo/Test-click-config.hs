import ChangeHeaderspace
import CompactFlow
import Behaviours
import Expr
import Flow
import Utils
import Rules
import Data.Set

import Applications

import Transf
import NetworkAppliances
import IPFilter

f1 = ("port" .=. "1-in") .>. ((("Source-IP" .=. "1.1.1.1") .>. cfinit) .>. (("Dest-IP" .=. "192.168.1.1") .>. cfinit) .>. finit)

f2 = (("port" .=. "2") .>. cfinit) .>. (("mac" .=. "2") .>. cfinit) .>. ("mac" .=. "1") .>. ((("Source-IP" .=. "1.1.1.1") .>. cfinit) .>. (("Dest-IP" .=. "8.8.8.8") .>. cfinit) .>. finit)


r = apply_ports ["p1", "p2"] 

--r19 = ipRewriter "with_queue-rewriter" "-" "-" "141.85.241.246" "-"
rfw = fwd_rule "id-0-out" ["id-1-in"]

fx = ("port" .=. "1-in") .>. ("Source-IP" .=. "1.1.1.1") .>. ("Dest-IP" .=. "9.9.9.9") .>. ("Source-Port" .=. "1") .>. ("Dest-Port" .=. "9") .>. finit
fr = apply fx r19

--r17 = head $ ipFilter "with_queue-filter" ["allow udp && dst 1.1.1.1 && dst port 9"]

rfil = head $ r17

ftest = ("Dest-IP" .=. "192.168.1.1") .>. ("Dest-Port" .=. "80") .>. ("Proto" .=. "2") .>. ("port" .=. "with_queue-filter-out-0") .>. finit


l0 = []
r1 =  wire_rule "with_queue-src-out" "id-0-in"
l1 = r1 : l0
r2 =  wire_rule "id-0-out" "id-1-in"
l2 = r2 : l1
r3 =  wire_rule "id-1-out" "id-2-in"
l3 = r3 : l2
r4 =  wire_rule "id-2-out" "with_queue-filter-in"
l4 = r4 : l3
r5 =  wire_rule "with_queue-filter-out-0" "with_queue-rewriter-in"
l5 = r5 : l4
--l5 = l4
r6 =  wire_rule "with_queue-rewriter-out" "id-3-in"
l6 = r6 : l5
r7 =  wire_rule "id-3-out" "id-4-in"
l7 = r7 : l6
r8 =  wire_rule "id-4-out" "id-5-in"
l8 = r8 : l7
r9 =  wire_rule "id-5-out" "id-6-in"
l9 = r9 : l8
r10 =  wire_rule "id-6-out" "with_queue-dst-in"
l10 = r10 : l9
r11 = toDevice "with_queue-dst"
l11 = r11 : l10
r12 = noOp "id-5"
l12 = r12 : l11
r13 = noOp "id-4"
l13 = r13 : l12
r14 = fromDevice "with_queue-src"
l14 = r14 : l13
r15 = noOp "id-0"
l15 = r15 : l14
r16 = noOp "id-3"
l16 = r16 : l15
r17 = ipFilter "with_queue-filter" ["allow udp && dst 192.168.1.1 && dst port 80"]
l17 = r17 ++ l16
r18 = noOp "id-1"
l18 = r18 : l17
r19 = ipRewriter "with_queue-rewriter" "-" "-" "141.85.241.246" "-"
l19 = r19 : l18
r20 = noOp "id-6"
l20 = r20 : l19
r21 = noOp "id-2"
l21 = r21 : l20

