module Switch_rule where

import Flow
import CompactFlow
import Rules
import Behaviours
import Transf
import Expr
import Applications
import Data.Set (Set)
import Utils
import qualified Data.Set as Set

type Vlan = String
type Mac = String


--gen_switch :: [(Port,Vlan)] -> [(Mac,Vlan,Port)] -> Rule
gen_switch port_vlan mac = 
    let inports = Flow 0 $ Set.map (\(p,_) -> ("port" .=. (p++":in")) .>. cfinit) $ Set.fromList port_vlan
--        invlan = Flow 0 $ Set.map (\(p,v) -> ("port" .=. (p++":in")) .>. ("vlan" .=. v) .>. cfinit) $ Set.fromList port_vlan
        outvlan =Flow 0 $ Set.map (\(p,v) -> ("port" .=. (p++":out")) .>. ("vlan" .=. v) .>. cfinit) $ Set.fromList port_vlan
        tbl = Flow 0 $ Set.map (\(v,m,p) -> ("port" .=. (p++":out")) .>. ("vlan" .=. v) .>. ("macDst" .=. m) .>. cfinit) $ Set.fromList mac
--        the match function
        m f = not $ (f `cap` inports) == EmptyFlow
              -- get the port of f, in case it had one. Otherwise, fix all opened switch ports as input ports
        a f = let f_just_port = (f .|. "port") `cap` inports
                  -- in case f had an input port, we remove from the mac table all entries containing the input port(s);
                  mac_tbl = if (f .|. "port") == finit then tbl else tbl .\. (tbl `cap` (makeout f_just_port))
                  -- we do the same for the broadcast table
                  bc_tbl = if (f .|. "port") == finit then outvlan else outvlan .\. (outvlan `cap` (makeout f_just_port))
                  -- fix a vlan in case it was not present, remove the port, and put the flow on all ports from the same vlan
                  f_with_vlan = ((f `cap` (makein outvlan)) .\. "port") `cap` bc_tbl 
                  -- find the output port to which to send the packet
                  f_with_mac = f_with_vlan `cap` mac_tbl 
                  -- identify the cases when the destination address is not known. We include here the broadcast address.
                  --f_without_mac = f_with_vlan `cap` (complement $ mac_tbl .|. "macDst") 
                  f_without_mac = (f_with_vlan `cap` (makeout inports)) `cap` (complement $ tbl .|. "macDst") 
                  in normalize (f_with_mac .>. f_without_mac)
    in (m,a)

{-
macDst := e0.a3aa.e980 Switch0-Fa0/22  OK
macDst := 9.7ce8.5901 Switch0-Fa0/1 OK
macDst := 9.7ce8.5903 Switch2-Fa0/3 
macDst := b.be80.c702 Switch2-Fa0/2 OK
macDst := d0.bca7.9546 Switch2-Fa0/2 Switch0-Fa0/23  OK
macDst := e0.8f2e.d003 Switch1-Fa0/3 Switch1-Fa0/3   OK
-}
{-
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


op = get_operator (Set.insert (("port" .=. "Switch0-Fa0/22:in") .>. finit) Set.empty) rules
sim i = let (l1,l2) = test_lfp op i in (Set.toList $ Set.map make_tree l1,Set.toList $ Set.map make_tree l2)

-}