module Rules where
import ChangeHeaderspace
import CompactFlow
import Behaviours
import Expr
import Flow
import Utils
import Data.List
import Data.Set (Set)
import qualified Data.Set as Set
import Transf

import Debug.Trace



-- a rule consists of two functions;
-- the first function takes a Flow and a port and decides in the rule is applicable under the given Flow, on the given port
-- the second function takes a Flow and produces a pair of outgoing ports and modified Flows, reflecting the rule application
type Match = Flow -> Bool
type Apply = Flow -> Flow
type Rule = (Match, Apply)

-- ---------------------------------------------
--           Helper match functions
-- ---------------------------------------------

-- builds a match function which checks if the flow is on the current port
match_port :: Port -> Match
match_port p = \f -> ("port" .=. p) .@. f

-- ---------------------------------------------
--           Helper apply functions
-- ---------------------------------------------

apply_port :: Port -> Apply
apply_port p =
   \f -> ("port" .=. p) .>. f
   
apply_ports :: [Port] -> Apply
apply_ports l = 
   \f -> fold (.>.) $ map (\p -> ("port" .=. p) .>. (f .\. "port")) l

-- ---------------------------------------------
--           Helper rules
-- ---------------------------------------------
matchAny :: Flow -> Bool
matchAny _ = True
   
--[ok]
match :: Flow -> Rule -> Bool
match f (m, _) = m f

--[ok]
apply :: Flow -> Rule -> Flow
apply f (_, app) = app f

--[ok]
comp :: Rule -> Rule -> Rule
comp (m,a) (m',a') = 
    let mfin f = (m f) && (m' (a f))
        afin = a . a'
    in (mfin, afin)

--[todo]
-- filtering rule
fil_rule :: Flow -> Rule
fil_rule flow = 
    let m f = True
        a f = f `cap` flow                        -- apply all filters
    in (m,a)

--[todo]
-- {-- NOTE: this is a very crude overriding, since it does not reconcile other bindings in the flow --}
-- override rule
ovr_rule :: [VarBinding] -> Rule
ovr_rule ovrlist = 
    let m f = True
        a f = foldr (.>.) f ovrlist                   -- apply all overwriting
    in (m,a)

-- models types which can characterize the output of a flow. Can be either Port or [Port]
class Wire a where
    wire_rule :: Port -> a -> Rule
    
instance Wire Port where
    wire_rule src dst = (match_port src,apply_port dst)
instance Wire [Port] where
    wire_rule src dst_list = (match_port src,apply_ports dst_list)
    
--[ok]
--fwd_rule (id ++ "-in", "in") [(id ++ "-out", "out")]
fwd_rule :: Port -> [Port] -> Rule
fwd_rule = wire_rule

    
-- this rule will maintain the flow history as a "history" variable, inside the flow itself
-- track_history :: Rule
-- track_history = let m f = True
                    -- a f = let (CVal p) = get "Port" f
                          -- in if contains "History" f then let (CVal h) = get "History" f
                                                          -- in force_push ("History" `Bind` (CVal $ h++":"++p)) f
                                                     -- else force_push ("History" `Bind` (CVal p)) f
                    -- in (m,a)
                    
--------------------------------------------------------
-- helper functions for generating certain kinds of rules 
--------------------------------------------------------

-- takes a rule and fixes it on a given port
inputOnPort :: Rule -> Port -> Rule
inputOnPort r p = let m f = ("port" .=. p) .@. f 
                      a f = f
                  in (m,a) `comp` r
-- takes a rule and outputs the result on a given port
outputOnPort :: Rule -> Port -> Rule
outputOnPort r p = let m _ = True
                       a f = ("port" .=. p) .>. f
                   in r `comp` (m,a)

-- takes a rule and input-output ports
inoutPort :: Port -> Port -> Rule -> Rule
inoutPort pin pout r = outputOnPort (inputOnPort r pin) pout                  
{-

   ASA Cuisine
   
   -}

{-
    A pipeline receives a list [f1, ..., fn] of flow conditions and builds the pipeline rule, which takes input f, and outputs:
   f n f1 U ((f \ f1) n f2) U ... U (f \ f1 \ ... \ fn-1) n fn    
   
   But here, fx \ fy is not clever difference. It only removes bindings.
   Hence, we implement fx \ fy as fx n (~ fy)
   f n f1   U
   f n ~f1 n f2   U
   f n ~f1 n ~f2 n f3 
   ...
   f n ~ ...  n ~fn-1 n fn
-}             
pipeLine :: [Flow] -> Rule
pipeLine fl = let 
                fx `dif` fy = fx `cap` (complement fy)
                diff f fx (fh:t) = if fx == fh then f `cap` fh else diff (f `dif` fh) fx t 
                terms f = (map (\fx ->diff f fx fl) fl) -- ++ [(foldl dif f fl)]
                reunion f = foldl (.>.) finit (terms f)
              in (matchAny, reunion)

fx `dif` fy = fx `cap` (complement fy)              
diff f fx (fh:t) = if fx == fh then f `cap` fh else diff (f `dif` fh) fx t 
                              
pip fl = let 
                fx `dif` fy = fx `cap` (complement fy)
                diff f fx (fh:t) = if fx == fh then f `cap` fh else diff (f `dif` fh) fx t 
                terms f = (map (\fx ->diff f fx fl) fl) -- ++ [(foldl dif f fl)]
                reunion f = foldl (.>.) finit (terms f)
              in terms--(matchAny, reunion)

              
pipe_test = 
    let flowList = map (\vb -> vb .>. finit) ["macSrc" .=. "A", "macDst" .=. "B"]
    in pipeLine flowList
    
--- access list rules rules     
--- acl "permit" "tcp" "192.168.1.1 255.255.255.0" "eq https" "any" ""
--    
--     permit/deny  l4 protocol    source ip   source l4 port   dest ip    dest l4 port   condition
acl :: String ->    String ->      String ->   String ->        String ->  String       -> Flow
acl s_pd s_proto s_l3_src s_l4_src s_l3_dst s_l4_dst = 
    let proto = ("proto" .=. s_proto) .>. finit
        func field "any" = finit
        func field ('h':'o':'s':'t':' ':r) = (field .=. r) .>. finit
        func field x = let (ip:mask:_) = split ' ' x
                       in (field .=. (ipMaskToInterval ip mask)) .>. finit
        gunc field "" = finit
        gunc field ('e':'q':' ':r) = (field .=. (toProtocolNo r)) .>. finit
        l3_src = func "Source-IP" s_l3_src
        l4_src = gunc "Source-Port" s_l4_src 
        l3_dst = func "Dest-IP" s_l3_dst
        l4_dst = gunc "Dest-Port" s_l4_dst
    in access_list s_pd proto l3_src l4_src l3_dst l4_dst
    
--             permit/deny  l4 protocol    source ip   source l4 port   dest ip    dest l4 port   condition
access_list :: String ->    Flow ->        Flow ->     Flow ->          Flow ->    Flow ->        Flow
access_list pd proto l3_src l4_src l3_dst l4_dst = 
    let l = [proto, l3_src, l4_src, l3_dst, l4_dst]
        f = foldl cap finit l 
    in if pd == "permit" then f else complement f

