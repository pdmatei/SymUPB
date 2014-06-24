module Test_Reachability where

import Flow
import CompactFlow
import Behaviours
import Expr
import Rules
import Applications
import Data.Set (Set)
import qualified Data.Set as Set

{--------------------- TESTS --------------------}
{-
        --------- | ovr IPD = 2 | <--------
       |                                   |
       |                                   | 
       v                          | if IPD != 2 |
  Src --->| filter IPS=IPD |----->| if IPD = 2  | ----> Dest
    
-}

input = ("IPS" .=. (CVar "U")) .>. ("IPD" .=. (CVar "V")) .>. ("port" .=. "P1:in") .>. finit

d1 = inoutPort "P1:in" "P1:out" $ fil_rule $ ("IPS" .=. (CVar "IPD")) .>. finit
w1 = wire_rule "P1:out" "P2:in"

d2 = inoutPort "P2:in" "P2:out" $ fil_rule $ ("IPS" .=. "2") .>. finit
d2' = inoutPort "P2:in" "P3:out" $ fil_rule $ complement $ ("IPS" .=. "2") .>. finit
w2 = wire_rule "P3:out" "P4:in" 

d3 = inoutPort "P4:in" "P4:out" $ ovr_rule [("IPD" .=. "2")]
w3 = wire_rule "P4:out" "P1:in"

rules = [d1,w1,d2,d2',w2,d3,w3]

op = get_operator (Set.insert input Set.empty) rules
reach = lfp_reachability "P2:out" input rules

step1 = apply input d1
step2 = apply step1 w1
step3 = apply step2 d2
step3' = apply step2 d2'
step4 = apply step3' w2
step5 = apply step4 d3

{-- 31.01.2014. reachability now works. we have problems with unification --}