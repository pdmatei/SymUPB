module NetworkAppliances where
import Utils
import Flow
import Expr
import CompactFlow
import ChangeHeaderspace
import Behaviours
import Rules
import Applications
import Data.List

-- [ok, I guess]
-- It is a one to many forwarding rule
multiplexer :: Port -> [Port] -> [Rule]
multiplexer input outputs = map (wire_rule input) outputs

--[ok]
toDevice id = noOp id
fromDevice id = noOp id

--[ok]
-- Passthrough device
noOp id = fwd_rule (id ++ "-in") [(id ++ "-out")]

--[ok]
rewriter :: String -> String -> Port -> String -> Port -> Rule
rewriter id ipSource portSource ipDest portDest = 
    let [bips@(_ `Bind` ips), bipd@(_ `Bind` ipd), bpd@(_ `Bind` pd), bps@(_ `Bind` ps)] = map (\(v,n) -> if v == "-" then (n .=. Void) else (n .=. v)) [(ipSource, "Source-IP"),(portSource, "Source-Port"),(ipDest, "Dest-IP"),(portDest, "Dest-Port")]
        m _ = True
        a f = 
            let f1 = if ips == Void then f else inc_assn_counter "Source-IP" $ bips .>. f
                f2 = if ipd == Void then f1 else inc_assn_counter "Dest-IP" $ bipd .>. f1
                f3 = if ps == Void then f2 else inc_assn_counter "Source-Port" $ bps .>. f2
                f4 = if pd == Void then f3 else inc_assn_counter "Dest-Port" $ bpd .>. f3
            in f4
        in (m,a)

ipRewriter id ipSource portSource ipDestination portDestination = let
  wireConnectionInbound = fwd_rule (id ++ "-in") [(id ++ "-out")]
  in wireConnectionInbound `comp` rewriter id ipSource portSource ipDestination portDestination

