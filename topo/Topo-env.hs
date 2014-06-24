import Switch_rule
import Rules
import Behaviours
import CompactFlow
import Flow
import Expr
import Applications
{-
macDst := e0.a3aa.e980 Switch0-Fa0/22  OK
macDst := 9.7ce8.5901 Switch0-Fa0/1 OK
macDst := 9.7ce8.5903 Switch2-Fa0/3 
macDst := b.be80.c702 Switch2-Fa0/2 OK
macDst := d0.bca7.9546 Switch2-Fa0/2 Switch0-Fa0/23  OK
macDst := e0.8f2e.d003 Switch1-Fa0/3 Switch1-Fa0/3   OK
-}
rSwitch2 = gen_switch [("Switch2-Fa0/2","1"),("Switch2-Fa0/3","1"),("Switch2-Fa0/2","5"),("Switch2-Fa0/3","5"),("Switch2-Fa0/24","5"),("Switch2-Fa0/2","6"),("Switch2-Fa0/3","6"),("Switch2-Fa0/2","99"),("Switch2-Fa0/3","99")] [("1","0009.7ce8.5903","Switch2-Fa0/3"),("1","000b.be80.c702","Switch2-Fa0/2"),("5","00d0.58d1.bbd8","Switch2-Fa0/3"),("5","00d0.bca7.9546","Switch2-Fa0/2")]
rSwitch0 = gen_switch [("Switch0-Fa0/1","1"),("Switch0-Fa0/2","1"),("Switch0-Fa0/1","5"),("Switch0-Fa0/2","5"),("Switch0-Fa0/22","5"),("Switch0-Fa0/23","5"),("Switch0-Fa0/1","6"),("Switch0-Fa0/2","6"),("Switch0-Fa0/1","99"),("Switch0-Fa0/2","99")] [("1","0009.7ce8.5901","Switch0-Fa0/1"),("1","00e0.8f2e.d002","Switch0-Fa0/2"),("5","00d0.58d1.bbd8","Switch0-Fa0/2"),("5","00d0.bca7.9546","Switch0-Fa0/23"),("5","00e0.8f2e.d002","Switch0-Fa0/2"),("6","00e0.8f2e.d002","Switch0-Fa0/2"),("99","00e0.8f2e.d002","Switch0-Fa0/2"),("5","00e0.a3aa.e980","Switch0-Fa0/22")]
rSwitch1 = gen_switch [("Switch1-Fa0/3","1"),("Switch1-Fa0/3","5"),("Switch1-Fa0/23","5"),("Switch1-Fa0/24","5"),("Switch1-Fa0/3","6"),("Switch1-Fa0/3","99")] [("1","00e0.8f2e.d003","Switch1-Fa0/3"),("5","00d0.58d1.bbd8","Switch1-Fa0/23"),("5","00d0.bca7.9546","Switch1-Fa0/3"),("5","00e0.8f2e.d003","Switch1-Fa0/3"),("6","00e0.8f2e.d003","Switch1-Fa0/3"),("99","00e0.8f2e.d003","Switch1-Fa0/3")]
wr0 = wire_rule "Switch0-Fa0/1:out" "Switch1-Fa0/1:in"
wr1 = wire_rule "Switch1-Fa0/1:out" "Switch0-Fa0/1:in"

wr2 = wire_rule "Switch0-Fa0/2:out" "Switch2-Fa0/2:in"
wr3 = wire_rule "Switch2-Fa0/2:out" "Switch0-Fa0/2:in"

wr4 = wire_rule "Switch2-Fa0/3:out" "Switch1-Fa0/3:in"
wr5 = wire_rule "Switch1-Fa0/3:out" "Switch2-Fa0/3:in"

rules = [rSwitch2,rSwitch1,rSwitch0,wr0,wr1,wr2,wr3,wr4,wr5]

--input = ("macSrc" .=. (CVar "A")) .>. ("macDst" .=. "00D0.58D1.BBD8") .>. ("port" .=. "Switch0-Fa0/22:in") .>. finit
input = ("macSrc" .=. (CVar "A")) .>. ("macDst" .=. (CVar "B")) .>. ("port" .=. "Switch0-Fa0/22:in") .>. finit
input2 = ("macSrc" .=. (CVar "A")) .>. ("macDst" .=. "00D0.58D1.BBD8") .>. ("port" .=. "Switch0-Fa0/22:in") .>. finit

pos = "Switch2-Fa0/2:in"
sw1 = "Switch1-Fa0/23:out"

-- specified and existing mac destination address
reach = lfp_reachability pos input rules
-- arbitrary mac address
reach2 = lfp_reachability pos input2 rules
-- reachability with an arbitrary input packet
reach3 = lfp_reachability pos finit rules
-- reachability over several hops
reach4 = lfp_reachability sw1 (("port" .=. "Switch0-Fa0/22:in") .>. finit) rules
-- reachability for port 24
reach5 = lfp_reachability "Switch1-Fa0/24:out" (("port" .=. "Switch1-Fa0/3:in") .>. finit) rules

{-
op = get_operator (Set.insert (("port" .=. "Switch0-Fa0/22:in") .>. finit) Set.empty) rules
sim i = let (l1,l2) = test_lfp op i in (Set.toList $ Set.map make_tree l1,Set.toList $ Set.map make_tree l2)
-}
