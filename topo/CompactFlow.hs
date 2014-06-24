module CompactFlow where
import Utils
--import Interval
import Behaviours
import Expr
--import Data.List
--import Data.Bits
import Data.Set (Set)
import qualified Data.Set as Set

data CompactFlow = CFlow (Set VarBinding) | EmptyCFlow deriving (Eq, Ord)

cfinit :: CompactFlow
cfinit = CFlow Set.empty

-- The class ToCompactFlow models entities which can be converted to a compactflow
class ToCompactFlow a where
    makecf :: a -> CompactFlow
    
-- a property of type a. Elements of type a can be inserted in CompactFlows 
-- the partial implementation relies on the function makecf which is defined in the class ToCompactFlow
instance (ToCompactFlow a) => ToContainer a CompactFlow where
    make = makecf
    x .>. EmptyCFlow = EmptyCFlow -- adding something to the empty cflow conserves the latter
    x .>. (CFlow cfl) = let (CFlow cfl') = makecf x in CFlow $ cfl `Set.union` cfl'
    EmptyCFlow .\. _ = EmptyCFlow
    (CFlow cfl) .\. x = let (CFlow cfl') = makecf x in CFlow $ cfl `Set.difference` cfl'
    -- x .@. EmptyCFlow = False
    -- x .@. (CFlow cfs) = let (CFlow cfs') = makecf x in (cfs' `Set.intersection` cfs) == cfs'
    
-- manual enrollment
instance ToContainer VarBinding CompactFlow where
    make (_ `Bind` Void) = EmptyCFlow
    make b = CFlow $ Set.insert b Set.empty
    (_ `Bind` Void) .>. _ = EmptyCFlow
    vb .>. (CFlow cfl) = CFlow $ Set.insert vb cfl
    vb .>. EmptyCFlow = EmptyCFlow -- adding something to the empty cflow conserves it
    EmptyCFlow .\. _ = EmptyCFlow
    (CFlow cfl) .\. vb = CFlow $ Set.filter (\vb'->not $ vb'==vb) cfl 
    EmptyCFlow .|. _ = EmptyCFlow
    (CFlow vbs) .|. b = CFlow $ Set.filter (==b) vbs 
    
    -- dummy membership. No evaluation of the expression is performed
    -- vb .@. EmptyCFlow = False
    -- vb .@. (CFlow cfs) = vb `Set.member` cfs 
    
-- instance ToCompactFlow VarBinding where
--    adding a variable bound to void yields the empty cflow
    -- makecf (_ `Bind` Void) = EmptyCFlow
    -- makecf b = CFlow $ Set.insert b Set.empty
-- instance ToCompactFlow [VarBinding] where
    -- makecf l = CFlow $ Set.fromList l
instance ToCompactFlow CompactFlow where
    makecf cf = cf

-- this is a hack to use .\. for deleting variables no matter their binding from a compactflow
-- it requires overlapping instances
instance ToContainer Var CompactFlow where 
    (CFlow cfl) .\. v = CFlow $ Set.filter (\(x `Bind` _)-> x /= v) cfl
    EmptyCFlow .\. _ = EmptyCFlow
    
    EmptyCFlow .|. _ = EmptyCFlow
    (CFlow cfs) .|. v = CFlow $ Set.filter (\(x `Bind` _)-> x == v) cfs
    
    v .@. EmptyCFlow = False
    v .@. (CFlow cfs) = Set.member True $ Set.map (\(v' `Bind` _)-> v == v') cfs
    
-- nicely displays a compact flow
instance Show CompactFlow where
    show (CFlow cf) = 
        let bl = Set.toList cf 
            showcc (a:b:l) = (show a) ++ ", " ++ (showcc (b:l))
            showcc (a:[]) = show a
            showcc [] = "CFEverything"
        in showcc bl
    show EmptyCFlow = "{}"

   