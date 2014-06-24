module Expr where
import Utils
import Transf
import Data.Bits
import Data.List

import Debug.Trace

-- read-proofing types

type Var = String
type Val = String
type Type = String -- type refers to the possible range types, expressed by header names

data VarBinding = Bind Var Expr deriving (Eq, Ord)        
instance Show VarBinding where
-- this pattern displays nicely the values of special headerfields. The function specified in specialHeaders is used
   show (v `Bind` e) = case (getType v, e) of
                            (t@(_:_),(CVal c)) -> v ++ " := " ++ ((getShow t) c)
                            _ -> v ++ " := " ++ show e


-- models those entities which can be bound in the lhs of an expression
class AsExpression a where
    (.=.) :: Var -> a -> VarBinding
    infixr .=.

instance AsExpression String where
    v .=. s = 
        let typ = getType v
            enc = getEnc typ
        in if typ == "" then v `Bind` (CVal $ Transf.toInteger s) else v `Bind` (TypedVal typ (enc s))

instance AsExpression Integer where
    v .=. i = 
        let typ = getType v
        in if typ == "" then v `Bind` (CVal $ i) else v `Bind` (TypedVal typ i)

        
instance AsExpression Expr where
    v .=. e = v `Bind` e
    
instance Show Expr where
    show (CVal v) = show v
    show (TypedVal t v) = (getShow t) v
    show (CVar v) = v
    show (Cup e e') = "" ++ show e ++" U "++ show e' ++"" 
    show (Interval st end) = "["++ show st ++":"++ show end ++"]"
    show (TypedInt t st end) = "["++ (getShow t) st ++":"++ (getShow t) end ++"]" -- "("++t++")["++ (getShow t) st ++":"++ (getShow t) end ++"]" 
    show (Not e) = "Not (" ++ show e ++ ")"
    show Void = "Void"
    show Any = "Any"

{-
   specialHeaders hold a pair of mappings between headerFieldNames and functions to be called to display them nicely.
   E.g. (port,toString)
   -}
idd x = x   
displayTypes :: [(Type, NumericValue -> String)]
displayTypes = [("port",toString),("mac",macToString),("ip",ipToString),("string",toString),("0-100",show),("vlan",show),("IPS",show),("IPD",show),("tcp",show), ("int",show), ("proto",fromProto)]

encodeTypes = [("port",Transf.toInteger),("mac",macToNumericValue),("ip",ipToNumericValue),("string",Transf.toInteger),("0-100",Transf.toInteger),("vlan",Transf.toInteger),("tcp", read),("IPS",Transf.toInteger),("IPD",Transf.toInteger), ("int",Transf.toInteger),("proto",Transf.toProto)]
ranges :: [(Type, Expr)]
ranges = [("ip", TypedInt "ip" (ipToNumericValue "0.0.0.0") (ipToNumericValue "255.255.255.255")),
          ("mac", TypedInt "mac" (macToNumericValue "0000.0000.0000") (macToNumericValue "ffff.ffff.ffff")),
          ("vlan", TypedInt "vlan" 0 4094), 
          ("tcp", (TypedInt "tcp" 0 65536)),
          ("int", (TypedInt "int" 0 100000)),
          ("proto", (TypedInt "proto" 0 3)),
          ("0-100", (TypedInt "0-100" 0 100))]
          
-- takes a header field and returns it's maximal possible value
getMinValue :: Var -> NumericValue
getMinValue h = let TypedInt _ min max = getRange $ getType h in min
getMaxValue :: Var -> NumericValue
getMaxValue h = let TypedInt _ min max = getRange $ getType h in max
          
defaulttype = "int"
          
header2Type = [("macSrc","mac"),("macDst","mac"),("Source-IP","ip"),("Dest-IP","ip"),("V","string"),("port","port"),("vlan","vlan"),("IPS","0-100"),("IPD","0-100"), ("Source-Port","tcp"),("Dest-Port","tcp"),("Proto","proto"), ("proto","proto")]
getType :: Var -> Type
getType v = case filter (\(v',t)-> v == v') header2Type of
                [] -> ""
                ((_,t):_) -> t
                
-- returns the appropriate display functions                
getShow :: Type -> (NumericValue -> String)
getShow t = snd $ head $ filter (\(t',f)->t==t') displayTypes

-- returns the appropriate encoding function
getEnc :: Type -> (String -> NumericValue)
--getEnc t = snd $ head $ filter (\(t',f)->t==t') encodeTypes
getEnc t = snd $ head $ filter (\(t',f)->t==t') encodeTypes

instance Show (a->b) where
    show f = "func"

{- returns the range of a type -}
getRange :: Type -> Expr
getRange t = snd $ head $ filter (\(t',f)->t==t') ranges
                
-- expressions
data Expr = CVal Integer |                                -- value
            TypedVal Type Integer |
            CVar Var |                                -- variable
--            And Expr Expr | Or Expr Expr | 
            Not Expr | -- boolean expressions
            Interval NumericValue NumericValue |      -- interval lower and upper
            TypedInt Type NumericValue NumericValue |    -- adds to Interval, it's type, which is useful for performing complement
            Cup Expr Expr | Cap Expr Expr | Complement Expr |           -- set operations
            Void |                                    -- denotes the emptyset value
            Any 
            deriving (Eq, Ord)


            
-- --------- from Radu [interval stuff] ------------------            

-- doIntersect :: Expr -> Expr -> Bool
-- doIntersect i i' =
    -- let f la ua lb ub = ((la <= lb) && (lb <= ua)) || ((lb <= ua) && (ua <= ub))
    -- in case (i,i') of
        -- ((Interval la ua), (Interval lb ub)) -> f la ua lb ub
        -- ((TypedInt _ la ua), (Interval lb ub)) -> f la ua lb ub
        -- ((Interval la ua), (TypedInt _ lb ub)) -> f la ua lb ub
        -- ((TypedInt _ la ua), (TypedInt _ lb ub)) -> f la ua lb ub

subnetToInterval :: String -> Expr
subnetToInterval s = let [ip,mask] = split '/' s in maskToInterval ip mask
  
ipMaskToInterval :: String -> String -> Expr
ipMaskToInterval baseIp mask =
  let
    ip = ipToNumericValue baseIp
    binaryMask = ipToNumericValue mask--(ipToNumericValue "255.255.255.255") - ((2 ^ (read mask :: Int) - 1) :: NumericValue)
    lower = ip .&. binaryMask
    upper = ip .|. (complement binaryMask)
  in
    TypedInt "ip" lower upper --Interval lower upper
  
-- takes an ip and a mask and builds the corresponding address interval
maskToInterval :: String -> String -> Expr
maskToInterval baseIp mask =
  let
    ip = ipToNumericValue baseIp
    binaryMask = (ipToNumericValue "255.255.255.255") - ((2 ^ (read mask :: Int) - 1) :: NumericValue)
    lower = ip .&. binaryMask
    upper = ip .|. (complement binaryMask)
  in
    TypedInt "ip" lower upper --Interval lower upper

fullMaskToInterval :: String -> Expr
fullMaskToInterval mask = let
    parts = splitOn "/" mask
    net = head parts
    slash = last parts
  in
    maskToInterval net slash        
    
-- [Matei] Unused?
-- builds a binary representation of an address interval
--toBinaryInterval :: Expr -> (String, String)
--toBinaryInterval (Interval lower upper) = (toBinary lower, toBinary upper)


-- Interval including all possible IPv4 addresses
ipSpace = TypedInt "ip" (ipToNumericValue "0.0.0.0") (ipToNumericValue "255.255.255.255")
tcpSpace = (TypedInt "tcp" 0 65536)
--this should be expanded, but currently is ok like this
protoSpace = (TypedInt "int" 1 2)


-- takes a typed or untyped interval and performs intersection
iIntersect :: Expr -> Expr -> Expr 
iIntersect i i' = 
    let inter ty la ua lb ub = 
            let sorted = sort [la, ua, lb, ub]
                norm int@(TypedInt tt s e) = if s == e then TypedVal tt s else int
            in case sorted of
                [x,y,z,t] ->
                    if (ua == lb) && (x == la) && (y == ua) && (z == lb) && (t == ub)
                        then
                            norm $ (TypedInt ty ua lb)
                        else if (x == la) && (y == lb) && (z == ua) && (t == ub)
                            then
                                norm $ (TypedInt ty lb ua)
                            else if (x == lb) && (y == la) && (z == ub) && (t == ua)
                                then
                                    norm $ (TypedInt ty la ub) 
                                else if (x == lb) && (y == la) && (z == ua) && (t == ub)
                                    then
                                        norm $ (TypedInt ty la ua)
                                    else if (x == la) && (y == lb) && (z == ub) && (t == ua)
                                        then
                                            norm $ (TypedInt ty lb ub)
                                        else
                                            Void	
    in case (i,i') of
        ((Interval la ua), (Interval lb ub)) -> inter "" la ua lb ub
        ((TypedInt t la ua), (Interval lb ub)) -> inter t la ua lb ub
        ((Interval la ua), (TypedInt t lb ub)) -> inter t la ua lb ub
        ((TypedInt _ la ua), (TypedInt t lb ub)) -> inter t la ua lb ub

-- checks if the first expression, evaluated as an interval includes the value of the second expression
-- intervalContainsValue :: Expr -> Expr -> Bool
-- intervalContainsValue i@(Interval _ _) (CVal value) = belongs i (fromIntegral value)--(ipToNumericValue value) 
-- intervalContainsValue i@(TypedInt _ _ _) (CVal value) = belongs i (fromIntegral value)--(ipToNumericValue value) 

-- checks if a value is included inside a given interval
from :: NumericValue -> Expr -> Bool
value `from` (Interval lower upper) = (lower <= value) && (value <= upper)
value `from` (TypedInt _ lower upper) = (lower <= value) && (value <= upper)

-- Right now, it's a kill to have cups of intervals, as each of the above operations becomes inherently complicated.
        
iComplement :: Expr -> (Expr,Expr)
iComplement i@(TypedInt t l r) = 
    let i'@(TypedInt t' lmin rmax) = getRange t
    in if l == lmin then if r == rmax then (Void, Void)
                         else if r+1 == rmax then (Void, TypedVal t (r+1)) else (Void, TypedInt t (r+1) rmax)
       else if r == rmax then if l-1 == lmin then (TypedVal t (l-1),Void) else (TypedInt t lmin (l-1),Void)
            else if l-1 == lmin then if r+1 == rmax then (TypedVal t (l-1),TypedVal t (r+1))
                                     else (TypedVal t (l-1),TypedInt t (r+1) rmax)
                 else if r+1 == rmax then (TypedInt t lmin (l-1),TypedVal t (r+1))
                      else (TypedInt t lmin (l-1),TypedInt t (r+1) rmax)
                      
iComplement v@(TypedVal t i) =
    let i'@(TypedInt t' lmin rmax) = getRange t
    in if i == lmin then (Void, TypedInt t' (lmin+1) rmax)
       else if i == rmax then (Void, TypedInt t' lmin (rmax -1))
            else (TypedInt t' lmin (i-1),TypedInt t' (i+1) rmax)
            
iComplement (CVar u) = (Not (CVar u),Void)

--alternative to the above implementation:
-- expr_complement :: Expr -> [Expr]
-- expr_complement e = case iComplement e of
                        -- of (Void,Void) -> [Void]
                        -- of (e,Void) -> [e]
                        -- of (Void,e) -> [e]
                        -- of (e,e') -> [e,e']
        
-- it takes two intervals and removes common parts for instance
-- 				[-------]
--       			  [-------]
-- becomes       [----) (-------]
-- truncCommonPart :: Expr -> Expr -> (Expr, Expr)
-- truncCommonPart a@(Interval la ua) b@(Interval lb ub) =
	-- if (la <= lb)
		-- then
			-- if (ua >= lb)
				-- then
					-- ((Interval la (lb-1)), (Interval lb ub))
				-- else
					-- (a, b)
		-- else
			-- truncCommonPart b a			

-- flatten Cups
-- if an expression consists of a number (tree) of unions of intervals
-- then to remove overlapping portions the first step is to flatten the
-- structure, sort it and then remove overlapping portions

-- since union is comutative and asociative then a flat structure is equivalen
-- e.g. ((A Cup B) Cup ((C Cup D) Cup E) is (A Cup B Cup C Cup D Cup E) 
-- flattenUnion :: Expr -> [Expr]
-- flattenUnion i@(Interval _ _) = [i]

-- flattenUnion (Cup i@(Interval _ _) e) = i : (flattenUnion e)
-- flattenUnion (Cup e i@(Interval _ _)) = flattenUnion (Cup i e)

-- flattenUnion (Cup a b) = (flattenUnion a) ++ (flattenUnion b)

-- once an expression is flattened then we can get rid of the common interval parts
-- truncCommon :: [Expr] -> [Expr]
-- truncCommon exps =
	-- let
		-- sorted = sortWith (\(Interval l _) -> l) (nub exps)
		-- pairs = zip sorted (tail sorted)
		-- (overlapping, non) = partition (\(a, b) -> doIntersect a b) pairs
		-- normalized = map (\(a, b) -> truncCommonPart a b) overlapping
	-- in
		-- if (null overlapping)
			-- then
				-- sorted
			-- else
				-- let
					-- allPairs = non ++ normalized
					-- allElems = (snd . last $ allPairs) : (map fst allPairs)
				-- in
					-- truncCommon allElems

-- the opposite of flatten
-- unionListToTree :: [Expr] -> Expr
-- unionListToTree [e] = e
-- unionListToTree exps =
	-- let
		-- (left, right) = splitAt ((length exps) `div` 2) exps
	-- in
		-- (Cup (unionListToTree left) (unionListToTree right)) 

-- takes a tree of unions and returns a normalized equivalent list
-- normalizeUnionsAsList :: Expr -> [Expr]
-- normalizeUnionsAsList u = truncCommon . flattenUnion $ u

-- takes a tree of unions and returns its normalized variant
-- normalizeUnions :: Expr -> Expr
-- normalizeUnions u@(Cup _ _) = unionListToTree . normalizeUnionsAsList $ u



 {-- Kicking out the function evaluate --}


-- this is an attempt to evaluate expressions to simpler ones
-- evaluate :: Expr -> Expr

-- Cap VALUE VALUE
-- intersection between two values is the value if equal or Void otherwise
-- evaluate (Cap a@(CVal _) b@(CVal _)) =
	-- if a == b
		-- then
			-- a
		-- else
			-- Void

-- Cap INTERVAL VALUE
-- intersection between value and interval is the value if included by the interval or Void otherwise
-- evaluate (Cap i@(Interval _ _) v@(CVal _)) =
	-- if (intervalContainsValue i v)
		-- then
			-- v
		-- else
			-- Void
-- switch argument place
-- evaluate (Cap v@(CVal _) i@(Interval _ _)) = evaluate (Cap i v)


-- Cap INTERVAL INTERVAL
-- evaluate (Cap a@(Interval la ua) b@(Interval lb ub)) =
	-- let
		-- sorted = sort [la, ua, lb, ub]
	-- in
		-- case sorted of
			-- [x,y,z,t] ->
				-- if (ua == lb) && (x == la) && (y == ua) && (z == lb) && (t == ub)
					-- then
						-- (Interval ua lb)
					-- else if (x == la) && (y == lb) && (z == ua) && (t == ub)
						-- then
							-- (Interval lb ua)
						-- else if (x == lb) && (y == la) && (z == ub) && (t == ua)
							-- then
								-- (Interval la ub) 
							-- else if (x == lb) && (y == la) && (z == ua) && (t == ub)
								-- then
									-- a
								-- else if (x == la) && (y == lb) && (z == ub) && (t == ua)
									-- then
										-- b
									-- else
										-- Void	

-- Cap INTERVAL CUPS
-- evaluate (Cap i@(Interval _ _) u@(Cup _ _)) =
	-- let
		-- normalized = normalizeUnionsAsList u
		-- intersects = filter (\e -> e /= Void) $ map (\x -> evaluate (Cap i x)) normalized
	-- in
		-- unionListToTree intersects
-- evaluate (Cap u@(Cup _ _) i@(Interval _ _)) = evaluate (Cap i u)		

-- Cup of cups
-- evaluate u@(Cup _ _) = normalizeUnions u

-- Complement
-- evaluate (Complement e) = (Complement (evaluate e))

-- catch all point if no specific computation can be performed
-- evaluate x = x
    
-- --------- end from Radu --------------
