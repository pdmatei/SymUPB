module IPFilter where
--import Eval
import Utils
import Rules
import Data.List
--import Data.List.Split
import Data.Maybe
import Flow
import Behaviours
import CompactFlow
import Rules
import NetworkAppliances

import Expr

import Debug.Trace

type HeaderField = String

data Constraint =
	Below HeaderField NumericValue |
	Above HeaderField NumericValue |
	Between HeaderField NumericValue NumericValue |
	Value HeaderField NumericValue |
	Different Constraint |
	No HeaderField NumericValue
	deriving (Show, Eq)

   
{-
--[ok, deprecated]
getOrElse :: String -> Expr -> Flow -> Expr
getOrElse symbolName defaultExpr f =
	let
		existing = get symbolName f
	in
		if (existing == Void)
			then
				defaultExpr
			else
				existing
-}
{-
-- [ok, deprecated]
getIPSrc = getOrElse "Source-IP" ipSpace
getIPDst = getOrElse "Dest-IP" ipSpace
getTCPSrc = getOrElse "Source-Port" tcpSpace
getTCPDst = getOrElse "Dest-Port" tcpSpace
getProto = getOrElse "Proto" protoSpace
-}

--[Matei's updated version]
-- takes a constraint and builds the flow characterizing it
constrain :: Constraint -> Flow
constrain (Below field value) = (field .=. (TypedInt (getType field) (getMinValue field) value)) .>. finit
constrain (Above field value) = (field .=. (TypedInt (getType field) value (getMaxValue field))) .>. finit
constrain (Between field lower upper) = (field .=. (TypedInt (getType field) lower upper)) .>. finit
constrain (Value field value) = (field .=. (TypedVal (getType field) value)) .>. finit
constrain (Different c) = complement $ constrain c


-- [ok, but deprecated]
fieldOfConstraint :: Constraint -> HeaderField
fieldOfConstraint (Below field _) = field
fieldOfConstraint (Above field _) = field
fieldOfConstraint (Between field _ _) = field
fieldOfConstraint (Value field _) = field
fieldOfConstraint (No field _) = field
fieldOfConstraint (Different c) = fieldOfConstraint c


--[ok, updated]
applyConstraint :: Flow -> Constraint -> Flow
applyConstraint f constraint =
	let
		f' = constrain constraint
	in
		f' `cap` f


--[ok]
applyAll :: [Constraint] -> Flow -> Flow
applyAll constraints f = foldl applyConstraint f constraints

{-
--[deprecated]
-- utility function, it simply adds the port to the result of the apply to match its signature
gibbery :: (Flow -> Flow) -> (Flow -> ([Port], Flow))
gibbery bareApply = \x -> ([], (bareApply x)) 
-}

-- [done]
-- implementation of the filter element, it takes a list of lists of constraints and creates a rule for each
-- inner list of constraints
filterElement :: [[Constraint]] -> [Rule]
filterElement constraints =
	let
		matches = repeat matchAny
		applies = map applyAll constraints
	in
		zip matches applies --zip matches $ map gibbery applies


--[in progress]
-- The function that "builds" an IPFilter element
ipFilterElement :: String -> [[Constraint]] -> [Rule]
ipFilterElement id constraintLists =
	let
		outputPortNames = map (\port -> (id ++ "-out-" ++ (show port))) $ map fst $ zip [0..] constraintLists
		bareLinks = multiplexer (id ++ "-in") outputPortNames
		filterRules = filterElement constraintLists
		linksAndFilters = zipWith (\linkRule filterRule -> linkRule `comp` filterRule) bareLinks filterRules
	in
		linksAndFilters



-- [ok]
-- Build filter out of set of tcpdump rules, each tcpdump rule is going to represent an output port
-- This is the pie
ipFilter :: String -> [String] -> [Rule]
ipFilter id rules = let
		constraints = map tcpdumpRuleToConstraints rules
	in
		ipFilterElement id constraints



-- [internal in Radu's implementation, no need to look at this]
-- Parse and translate tcpdump rules
-- QUIRK ATTENTION: This has to start with a non-white space prefix that is unimportant since it's dropped
tcpdumpRuleToConstraints :: String -> [Constraint]
tcpdumpRuleToConstraints rule = let
	tokens = map strip $ splitOn "&&" $ dropWhile (/= ' ') rule
	rules = map ruleFragmentToConstraint tokens
	restricted = filter (/= Nothing) rules
	in
	map (\r -> case r of (Just rr) -> rr) restricted 

-- [internal in Radu's implementation, no need to look at this]
ruleFragmentToConstraint :: String -> Maybe Constraint
ruleFragmentToConstraint ('t':'c':'p':_) = Just (Value "Proto" 1)
ruleFragmentToConstraint ('u':'d':'p':_) = Just (Value "Proto" 2)


ruleFragmentToConstraint fragment = let
		tokens = words fragment
		target = head tokens
		secondary = tokens !! 1
	in case target of
		"src" -> case secondary of
			-- we have a port
			"port" -> case tokens !! 2 of
				ternary -> if (elem '-' ternary)
					-- interval
					then let
							(l, u) = portIntervalToValues ternary
						in
							Just (Between "Source-Port" l u)
					-- value
					else Just (Value "Source-Port" (read ternary))

			ipv -> if elem '/' ipv
					-- mask
					then let
							(Interval l u) = fullMaskToInterval ipv
						in
							Just (Between "Source-IP" l u)
					-- value
					else Just (Value "Source-IP" (ipToNumericValue ipv))
		"dst" -> case secondary of
			-- we have a port
			"port" -> case tokens !! 2 of
				ternary -> if (elem '-' ternary)
					-- interval
					then let
							(l, u) = portIntervalToValues ternary
						in
							Just (Between "Dest-Port" l u)
					-- value
					else Just (Value "Dest-Port" (read ternary))

			ipv -> if elem '/' ipv
					-- mask
					then let
							(Interval l u) = fullMaskToInterval ipv
						in
							Just (Between "Dest-IP" l u)
					-- value
					else Just (Value "Dest-IP" (ipToNumericValue ipv))
		_ -> Nothing				

testConstraints = tcpdumpRuleToConstraints "allow udp && src port 60 && dst 10.0.0.0/16"


-- Test IP Filter