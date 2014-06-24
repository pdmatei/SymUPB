module Flow where
import Utils
import CompactFlow
import Expr
import Behaviours
import Transf
import Data.Set (Set)
import Data.List
import qualified Data.Set as Set

type Port = String

-- a Flow is an integer denoting the last variable id and a list of expressions
-- Flow _ [] is the universal Flow (anything), while EmptyFlow is the empty Flow (i.e. emptyset)
data Flow = Flow { id::Integer, compactFlowList :: Set CompactFlow } | EmptyFlow deriving Ord

finit :: Flow
finit = Flow 0 Set.empty

-- builds a flow from
fromLists :: [[VarBinding]] -> Set (Set VarBinding)
fromLists ccl = Set.fromList $ map Set.fromList ccl

showfl (Flow id ccl) = 
    let showcc (x:y:rest) = "(" ++ x ++ ") U " ++ (showcc (y:rest))
        showcc (x:[]) = "(" ++ x ++ ")"
        showcc [] = "Everything"
    in showcc $ Set.toList $ Set.map show ccl   
showfl EmptyFlow = "<>"


instance Show Flow where
--    show =  show . make_tree 
    show = showfl
    
instance Eq Flow where
    (Flow _ ccl) == (Flow _ ccl') = ccl == ccl'
    (Flow _ _) == EmptyFlow = False
    EmptyFlow == (Flow _ _) = False
    EmptyFlow == EmptyFlow = True
   
-- this hack allows me to remove a variable from a flow (uses overlapping instances)
instance ToContainer Var Flow where 
    (Flow i cfl) .\. v = Set.fold (.>.) finit $ Set.map (\cf->cf .\. v) cfl
    EmptyFlow .\. _ = EmptyFlow   

    EmptyFlow .|. _ = EmptyFlow
    (Flow i cfs) .|. v = 
        let f = Flow i $ Set.map (.|.v) cfs in if f == (cfinit .>. finit) then finit else normalize f 

    
-- making a VarBinding into a Flow
instance ToContainer VarBinding Flow where
    make b = (CFlow (Set.insert b Set.empty)) .>. finit 
    b .>. EmptyFlow = EmptyFlow -- this is the void flow. Any operation will conserve it
    -- This is a smart adding. It will override any previous bindings
    b@(v `Bind` Void) .>. f = EmptyFlow -- a void expression makes a flow empty
    b@(v `Bind` _) .>. f = let f'@(Flow i cfl) = f .\. v in if f' == finit then (b .>. cfinit) .>. finit
                                                         else Flow i $ Set.map (b .>.) cfl
    
    EmptyFlow .\. _ = EmptyFlow
    (Flow i cfl) .\. b = let res = Flow i $ Set.map (.\. b) cfl in if res == Flow i (Set.insert cfinit Set.empty) then EmptyFlow else res
    vb .@. f = not (((vb .>. finit) `cap` f) == EmptyFlow)   

-- these enrolments are unstable, because of the unintuitive effect of .>. It does not do unification.
instance ToContainer CompactFlow Flow where
    make cf = Flow 0 $ Set.insert cf Set.empty
    -- this is a dummy insertion. It does not do unification
    -- we do not insert an empty cflow
    EmptyCFlow .>. f = f -- adding a void compact flow does not alter f
    cf@(CFlow _) .>. (Flow i cfl) = Flow i $ Set.insert cf cfl
    cf .>. EmptyFlow = EmptyFlow -- adding something to the void flow, conserves the latter
    EmptyFlow .\. _ = EmptyFlow
--    (Flow i cfl) .\. cf = Flow i $ Set.filter (==cf) cfl
    -- removes each binding, binding by binding
    f .\. (CFlow cf) = Set.fold (\b f'->f'.\.b) f cf

-- this normalization technique may be necessary later on.
-- most probably, it should eliminate partially overlapping compact flows. 
-- We do not deal with this in the implementation, yet.
normalize EmptyFlow = EmptyFlow
normalize (Flow i cfs) = Flow i $ Set.filter (\x->x/=EmptyCFlow && x/=cfinit) cfs    
    
instance ToContainer Flow Flow where
    make f = f
    -- .>. plays the role of flow union
    EmptyFlow .>. f = f
    f .>. EmptyFlow = f
    -- this is a dummy reunion. It does not do unification
    (Flow i cfl) .>. (Flow j cfl') = normalize $ Flow (i+j) (cfl `Set.union` cfl')
    EmptyFlow .\. _ = EmptyFlow
    f .\. EmptyFlow = f
    (Flow i cfl) .\. (Flow j cfl') = normalize $ Flow (i+j) (cfl `Set.difference` cfl')
--    f@(Flow i cfl) .\. (Flow j cfl') = normalize $ Set.fold (\cf f'-> f' .\. cf) f cfl'
    
    
class Unifiable a where
    unify :: a -> Flow

-- to apply unification on a flow, one applies unification on all compact flows    
instance Unifiable Flow where
    unify EmptyFlow = EmptyFlow
    -- unify all compact flows and put them together in the same flow
    unify (Flow i cfs) = Set.fold (.>.) finit $ Set.map unify cfs
    
var_unify :: Var -> Expr -> Expr -> Flow
--v = u and v = e (v = e will be removed)
var_unify v (CVar u) e = (v .=. (CVar u)) .>. (u .=. e) .>. finit 
--v = e and v = u (v = u will be removed)
var_unify v e e'@(CVar u) = var_unify v e' e 
-- v = i and v = i'
var_unify v i@(TypedInt _ _ _) i'@(TypedInt _ _ _) = (v .=. (iIntersect i i')) .>. finit  -- if the intersection is void the result is the empty flow
--var_unify v i@(Interval _ _) i'@(TypedInt _ _ _) = (v .=. (iIntersect i i')) .>. finit
--var_unify v i@(Interval _ _) i'@(Interval _ _) = (v .=. (iIntersect i i')) .>. finit
--var_unify v i@(TypedInt _ _ _) i'@(Interval _ _) = (v .=. (iIntersect i i')) .>. finit
-- v = interval and v = c
var_unify v i@(TypedInt t l u) e@(TypedVal _ c) = if c `from` i then (v.=.(TypedVal t c)) .>. finit else EmptyFlow
var_unify v e@(TypedVal _ c) i@(TypedInt _ l u) = var_unify v i e

--var_unify v i@(Interval l u) e@(CVal c) = if c `from` i then (v.=.e) .>. finit else EmptyFlow
var_unify v e@(CVal c) i@(Interval l u) = var_unify v i e 
--negation & negation
var_unify v (Not e) (Not e') = (v .=. (Not e)) .>. (var_unify v e e')

var_unify v e@(Not (CVar u)) i@(Interval _ _) = 
    let (i',i'') = iComplement i in ((v .=. e) .>. (u .=. i') .>. cfinit) .>. ((v .=. e) .>. (u .=. i'') .>. cfinit) .>. finit
var_unify v e@(Not (CVar u)) i@(TypedInt _ _ _) = 
    let (i',i'') = iComplement i in ((v .=. e) .>. (u .=. i') .>. cfinit) .>. ((v .=. e) .>. (u .=. i'') .>. cfinit) .>. finit

var_unify v i@(Interval _ _) e@(Not (CVar _)) = var_unify v e i
var_unify v i@(TypedInt _ _ _) e@(Not (CVar _)) = var_unify v e i
    
{-
 [Todo] Please add the cases when we have not applied on an interval
-}
--unification fails for "all other cases"
var_unify _ _ _ = EmptyFlow

instance Unifiable CompactFlow where
    unify EmptyCFlow = EmptyFlow
    unify cf@(CFlow vbs) = 
        let vbl = Set.toList vbs
        -- look for two expressions binding the same variable
        in case [((v `Bind` e),(v' `Bind` e')) | (v `Bind` e) <- vbl, (v' `Bind` e') <- vbl, v == v', e /= e'] of
            [] -> cf .>. finit
            -- just take two such expressions
            ((vb@(v `Bind` e),vb'@(v' `Bind` e')):_) -> case var_unify v e e' of
                                                        EmptyFlow -> EmptyFlow
                                                        -- the unification results in a flow
                                                        -- add the compact flow without vb and vb' to the resulted flow, and continue
                                                        -- the unification of the flow itself
                                                        f@(Flow i cfs) -> unify $ Flow i $ Set.map (.>. (cf .\. vb .\. vb')) cfs
                                                        
-- =============================================================
--          Operations to expose to the outside world
-- =============================================================
{-  ----- Intersection ----------
    f1 = {cf1, cf2}
    f2 = {cf3, cf4}
    f1 `cap` f2
    Step 1: combine flows:
    {cf1 .>. cf3, cf2 .>. cf3, cf1 .>. cf4, cf2 .>. cf4}
    Step 2:
    for each cfi from the above:
    fi = unify cfi -- unification yields a flow. Eg. f1, f2, f3, f4
    Step 3:
    result = f1 .>. f2 .>. f3 .>. f4 .>. EmptyFlow
    Step 4:
    apply some normalization technique on the result
-}                                                        
instance Ops Flow where
    cap EmptyFlow _ = EmptyFlow
    cap _ EmptyFlow = EmptyFlow
    cap f@(Flow i cfs) f'@(Flow j cfs') = 
        if cfs == Set.empty then f' else if cfs' == Set.empty then f else
            let merged_cfs = Set.fromList [ cf .>. cf' | cf <- Set.toList cfs, cf' <- Set.toList cfs']
                r = Set.fold (.>.) finit $ Set.map unify merged_cfs
            in if r == finit then EmptyFlow else r

{-
    (*) The reunion of two flows is implemented as:
    f1 .>. f2
    (**) Pushing a new binding is implemented as below:
    (v = e) .>. f
    and should be interpreted as: replace in all compact flows the occurence of a binding (v = _) with (v = e). 
    Note that ((v = e) .>. cfinit) .>. f will not have the same effect. This is just blind insertion of the binding.
    (***) Removing a binding or a variable is implemented by hacking .\.
    f .\. v  and f .\. (v = e)
-}

-- [Todo] subset requires set comparison, i.e. all variables evaluate to the same possible values. Hard.
-- subset :: Flow -> Flow -> Bool
-- f `subset` f' = not $ f `cap` f' == f

-- takes a flow and replaces the port value suffix
makedir :: String -> Flow -> Flow
makedir s EmptyFlow = EmptyFlow
makedir s (Flow i cfs) = 
    Flow i $ Set.map (\(CFlow cf)->CFlow $ Set.map 
    (\b@(v `Bind` e)-> case e of 
        (CVal i) -> if v == "port" then v `Bind` (CVal $ Transf.toInteger ((head $ split ':' $ Transf.toString i)++s)) else b
        (TypedVal t i) -> if v == "port" then v `Bind` (TypedVal t $ Transf.toInteger ((head $ split ':' $ Transf.toString i)++s)) else b
        _ -> b
        ) cf) cfs
makeout f = makedir ":out" f
makein f = makedir ":in" f

-- the complement of a compact flow
cf_complement :: CompactFlow -> Flow
cf_complement EmptyCFlow = EmptyFlow
cf_complement cf@(CFlow vbs) = 
                                        -- if v already exists, bound to something, in cf, leave it as is
    let b@(v `Bind` (CVar v')) `op` f = if v' .@. cf then b .>. f else (v `Bind` (Not $ CVar v')) .>. f
        (v `Bind` e) `op` f = case iComplement e of
                                (Void, Void) -> EmptyFlow
                                (e', Void) -> ( (v .=.e') .>. finit) `cap` f
                                (Void, e') -> ( ( v.=.e') .>. finit) `cap` f
                                (e', e'') -> (((v.=.e') .>. cfinit) .>. ((v.=.e'') .>. cfinit) .>. finit) `cap` f
    in Set.fold op finit vbs 

-- computes the complement of a flow
complement :: Flow -> Flow
complement EmptyFlow = EmptyFlow
-- complement (Flow i cfs) = Set.fold (\cf f-> (cf_complement cf) .>. f) finit cfs 
complement (Flow i cfs) = Set.fold (\cf f-> (cf_complement cf) `cap` f) finit cfs 


-- ==================================================================
--         Syntactic sugars based on primitive flow ops
-- ==================================================================

get :: String -> Flow -> Expr
get s f = let rst = f .|. s in if rst == finit then Void else 
            let Flow _ cfs = rst
                CFlow vbs = head $ Set.toList cfs
                (_ `Bind` e) = head $ Set.toList vbs
                in e


    
-- ==================================================================
--         Normalization implementations
-- ==================================================================

    
    
-- ==================================================================
--         Display a flow as a tree
-- ==================================================================

-- count the occurrences of a binding
(.#.) :: VarBinding -> Flow -> Integer
vb .#. EmptyFlow = 0
vb .#. (Flow _ cfs) = foldl (+) 0 $ map (\(CFlow cf)-> if vb `Set.member` cf then 1 else 0) (Set.toList cfs)

-- transform a flow to a list of all it's bindings
to_vb_list :: Flow -> [VarBinding]
to_vb_list EmptyFlow = []
to_vb_list (Flow _ cfs) = Set.toList $ Set.fold (\(CFlow cf) cf'->Set.union cf cf') Set.empty cfs

-- sorts a list of bindings by the frequency of their occurrences
sort_vb_list :: [VarBinding] -> Flow -> [VarBinding]
sort_vb_list vbl f = sortBy (\vb vb'-> if (vb .#. f) > (vb' .#. f) then LT else GT) vbl

-- returns a subflow which contains a binding
subflow :: VarBinding -> Flow -> Flow
subflow _ EmptyFlow = EmptyFlow
subflow vb (Flow i cfs) = let res = Set.fold (.>.) finit (Set.filter (\(CFlow cf)-> vb `Set.member` cf) cfs) 
                          in if res == finit then EmptyFlow else res .\. vb
subflow _ finit = EmptyFlow

-- returns all bindings of a variable from a flow
get_all_vb :: Var -> Flow -> [VarBinding]
get_all_vb v f = let f'@(Flow _ cfs) = f .|. v in if f' == EmptyFlow then [] else let (CFlow vbs) = Set.fold (.>.) cfinit cfs in Set.toList vbs 

-- builds a tree representation of a flow
--make_tree :: Flow -> FlowTree
make_tree EmptyFlow = Nil
make_tree f@(Flow _ cfs) = 
    let (first@(v `Bind` _):_) = sort_vb_list (to_vb_list f) f
        rest = filter (\x-> not (x==first)) (to_vb_list f) -- $ get_all_vb v f
        t = Branch $ (first,make_tree (subflow first f)):(map (\vb->(vb,make_tree (subflow vb f))) rest)
        in t--subflow first f

-- showTree :: Integer -> FlowTree -> String
showTree _ Nil = ""
                        
showTree x (Branch l) = 
    let tabs = "   ":tabs
        clr = "\n"
        prefix =  foldl (++) "" $ take x tabs                            
        in foldl (++) "" $ map (\(vb,t)-> prefix ++(show vb)++clr++(showTree (x+1) t)) l
        
data FlowTree = Nil | Branch [(VarBinding,FlowTree)] deriving (Ord,Eq)

instance Show FlowTree where
    show = showTree 0


    
---------------------------------------------------------
---   De la Radu 
---------------------------------------------------------

cnt_name v = v ++ "-Assn-Cnt"

assn_count v f = let
    cnt = get (cnt_name v) f
    val = if cnt == Void then 0
                         else case cnt of (TypedVal "int" x) -> x
  in
    val

inc_assn_counter v f = let
    prev_counter = assn_count v f
    cnt = prev_counter + 1
    nf = ((cnt_name v) `Bind` (TypedVal "int" cnt)) .>. f
  in nf
  
  