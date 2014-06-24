module Applications where
import Utils
import ChangeHeaderspace
import Flow
import Behaviours
import Expr
import CompactFlow
import Rules
import Data.List
import Data.Set (Set)
import qualified Data.Set as Set

import Debug.Trace

type Link = (Port,Port)
type History = [Port]

--checks to see if there is any outgoing link from port p given a list of links
hasLink :: Port -> [Link] -> Bool
--TODO: can use fst instead
hasLink p l = elem p $ map (\(x,y)->x) l

--returns the outgoing port of the first link going out from the port p
getLink :: Port -> [Link] -> Port
getLink p l = head $ map (\(x,y)->y) $ filter (\(x,_)->p==x) l


type Operator = Set Flow -> Set Flow
get_operator :: Set Flow -> [Rule] -> Operator
get_operator set_A rules = let op_F set_X = if set_X == Set.empty then set_A 
                               else let pairs = [(f,r) | f <- Set.toList set_X, r <- rules, match f r] -- all pairs of flows and applicable rules
                                        flows = Set.fromList $ map (\(f,r)->apply f r) pairs -- the resulted flows
                                    in flows
                           in op_F

without_mac x = Set.map (\f-> f .\. "macSrc" .\. "macDst" ) x

-- efficient attempt of lfp       
lfp :: Operator -> Set Flow
lfp op = let f x y = let rmh fl = Set.map (\f->f .\. "History") fl 
                         transf = without_mac
                     in trace ("X= "++show (transf x)++"\nY= "++show (transf y)++"\n") $ if (rmh x) == rmh (x `Set.union` y) then x else f (x `Set.union` y) (op y)
         in f Set.empty (op Set.empty)
{--
step 0: f {} {}
step 1: f {} op({})
step 2: f op({}) op2({})
step 3: f op({}) U op2({}) op3({})
......
--}

test_lfp op step = let f x y step = let rmh fl = Set.map (\f->f .\. "History") fl 
                                    in if step == 0 then (x,y) else f (x `Set.union` y) (op y) (step - 1)
                   in f Set.empty Set.empty step

-- takes a port and an operator modeling the network, and returns the flow which is accessible at that port.
-- lfp_reachability :: String -> Flow -> [Rule] -> Flow
-- lfp_reachability p f rules = Set.fold (.>.) finit $ 
                             -- Set.filter (\f ->not $ (f `cap` (("port" .=. p) .>. finit)) == EmptyFlow) $ 
                             -- lfp $ get_operator (Set.insert f Set.empty) rules     

-- lfp_reachability p f rules = (("port" .=. p) .>. finit) `cap` (Set.fold (.>.) finit $ 
--                             lfp $ get_operator (Set.insert f Set.empty) rules)   

lfp_reachability :: String -> Flow -> [Rule] -> Flow
lfp_reachability p f rules = (("port" .=. p) .>. finit) `cap` (Set.fold (.>.) finit $ 
                            lfp $ get_operator (Set.insert f Set.empty) rules)   
                             
get_flow :: [(History,Flow)] -> Flow
get_flow lst = foldl (.>.) EmptyFlow $ map snd lst

-- improvement attempt      
-- find_loops lf_list = 
    -- let stripf flow = remove "History" flow
        -- getval var f = let (CVal v) = get var f in v
        -- find_loop lf = [(f1,f2) | f1 <- lf, f2 <- lf, f1 /= f2, 
                                  -- stripf f2 `isSubset` stripf f1,    -- a more general traffic passes
                                  -- isPrefixOf (getval "History" f1) (getval "History" f2) --the history of f1 is included in that of f2
                                  -- ] -- the last port of f2 is the same as the current port of f1                                         
        -- in find_loop $ Set.toList lf_list

        
-- the operator must perform history tracking for loop detection to work. Also, the operator must be built with the list of all initial ports
-- careful with the variable generation
-- reflexive loops will not be found
--lfp_loop_detection :: Operator -> [(Flow,Flow)] 
-- lfp_loop_detection rules ports = 
    -- let initial_flows = Set.fromList $ 
                        -- map (\p->force_push_list [("Port" `Bind` (CVal p)), ("History" `Bind` (CVal p)), ("Flow-id" `Bind` (CVal $ "Injected at "++p))] topFlow) ports 
        -- op = get_operator initial_flows $ map (\r->track_history `comp` r) rules
        -- lf = lfp op
        -- in find_loops lf