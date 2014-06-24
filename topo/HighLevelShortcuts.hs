module HighLevelShortcuts where
import Utils
import Flow hiding (id)
import CompactFlow
import ChangeHeaderspace
import Rules
import Applications
import Data.List

--most general
generalFlow = Flow 0 [[]]

--initial flow having simple address bindings
tcp_ip_flow sourceTcp sourceIp destTcp destIp =
	let
		sourceTcpAddressBinding = CVal sourceTcp
		sourceIpAddressBinding = CVal sourceIp
		destTcpAddressBinding = CVal destTcp
		destIpAddressBinding = CVal destIp
	in
		Flow 0 [[("Dest-Port" `Bind` destTcpAddressBinding),("Dest-IP" `Bind` destIpAddressBinding),
		("Source-Port" `Bind` sourceTcpAddressBinding), ("Source-IP" `Bind` sourceIpAddressBinding)]]

-- runs reachability multiple times - quite hackish
check sourcePortId destPortId runCount flow rules =
	if runCount == 0
		then
			flow
		else let
				previous = reachability (sourcePortId, flow) [destPortId] rules
				in
					if null previous
						then
							EmptyFlow 
						else
							check sourcePortId destPortId (runCount - 1) (snd . head $ previous) rules

reach sourcePortId destPortId runCount flow rules = reachability (sourcePortId, flow) [destPortId] rules

testReach sourcePortId destPortId runCount flow rules =
	emptyFlow $ check sourcePortId destPortId runCount flow rules

--reachability :: (Port,Flow) -> [Port] -> [Rule] -> [(History,Flow)]

crossReach :: Port -> Port -> Port -> Flow -> [Rule] -> [(History, Flow)]
crossReach src cross dst initFlow rules = let
		srcToCross = reach src cross 1 initFlow rules
		crossToDst = foldl
			(\ rest (h, f) -> let
					results = reach cross dst 1 f rules
					full = map (\ (nh, f) -> (h ++ nh, f)) results
				in
					full ++ rest)
			[]
			srcToCross
	in
		crossToDst

headerConv :: String -> String
headerConv "src" = "Source-IP"		
headerConv "dst" = "Dest-IP"		
headerConv "dst port" = "Dest-Port"
headerConv "src port" = "Source-Port"
headerConv "proto" = "Proto"

headerConvToAssn :: String -> String
headerConvToAssn = cnt_name . headerConv		


invariant :: [String] -> Port -> Port -> Port -> Flow -> [Rule] -> String
invariant fields source porta portb f rules = let
		f1s = map snd $ reach source porta 1 f rules 
		f2s = map snd $ reach source portb 1 f rules
		allPairs = [(x,y) | x <- f1s, y <- f2s]
		allheaders = map headerConv fields
		broken = any (\(f1, f2) -> any id $ map (\header -> ((get header f1) /= (get header f2) && (assn_count header f1) /= (assn_count header f2))) allheaders) allPairs
	in
		if broken then "Null"
		else "Valid" 