module Utils where
import Data.List
import Data.Word
import Numeric
import Data.Char
import Data.Bits

import Data.Set (Set)
import qualified Data.Set as Set

import Debug.Trace

--import qualified Data.Text as T
--strip = T.unpack . T.strip . T.pack
strip s = let rem [] = []
              rem l@(h:t) = if h == ' ' then rem t else l
          in rem $ reverse $ rem $ reverse s

--type NumericValue = Word32
type NumericValue = Integer

fold :: (a -> a -> a) -> [a] -> a
-- no basis case allowed. [] will crash
fold op (h:t) = foldl op h t

minVal :: NumericValue
minVal = 0

maxVal :: NumericValue
maxVal = (complement 0)

identity x = x

splitByPredicate :: (Char -> Bool) -> String -> [String]
splitByPredicate p s = let (prefix, sufix) = break p s
                        in case sufix of 
                            "" -> prefix:[]
                            (_:suf) -> prefix:(splitByPredicate p suf) 


split ch string = splitByPredicate (==ch) string

ipToNumericValue :: String -> NumericValue
ipToNumericValue ip =
	let
		tokens = split '.' ip
		ints = map (\token -> read token :: NumericValue) tokens
		shiftValue = 2 ^ 8
	in
		foldl (\total ipSegmnent -> total * shiftValue + ipSegmnent) (0 :: NumericValue) ints
        
{-
countBits :: NumericValue -> Integer
countBits 0 = 0
countBits x = let x' = x `mod` 10
              in 
-}      
portIntervalToValues :: String -> (NumericValue, NumericValue)
portIntervalToValues i = 
    let
        parts = splitOn "-" i
    in (read $ head parts, read $ last parts)

toBinary x = showIntAtBase (2 :: NumericValue) intToDigit x ""

ipToString ip = 
    let ip32 = (fromIntegral ip) :: Word32
        mask = foldl setBit 0 [0,1,2,3,4,5,6,7] 
        [b1,b2,b3,b4] = map (\i->shiftR ip32 i .&. mask) [0,8,16,24] 
    in (show b4) ++"."++(show b3)++"."++(show b2)++"."++(show b1)

macToNumericValue :: String -> NumericValue
macToNumericValue mac = 
    let str = foldl (++) "" $ split '.' mac
        toInt hex = let toIntTail (h:t) n = (16^n) * (hconv h) + toIntTail t (n-1)
                        toIntTail [] _ = 0
                  in toIntTail hex ((length hex)-1)
    in toInt str
    
macToString :: NumericValue -> String
macToString mac = 
    let toStr 0 _ = []
        toStr h i =  let pref l = if i `mod` 4 == 0 then '.':l else l
                     in (dconv (h `mod` 16)): (pref $ toStr (h `div` 16) (i+1))
        res = reverse $ toStr mac 1
    in if (length res) < 14 then res else tail res


hconv '0' = 0
hconv '1' = 1
hconv '2' = 2
hconv '3' = 3
hconv '4' = 4
hconv '5' = 5
hconv '6' = 6
hconv '7' = 7
hconv '8' = 8
hconv '9' = 9
hconv 'a' = 10
hconv 'A' = 10
hconv 'b' = 11
hconv 'B' = 11
hconv 'c' = 12
hconv 'C' = 12
hconv 'd' = 13
hconv 'D' = 13
hconv 'e' = 14
hconv 'E' = 14
hconv 'f' = 15
hconv 'F' = 15    

dconv 0 = '0'
dconv 1 = '1'
dconv 2 = '2'
dconv 3 = '3'
dconv 4 = '4'
dconv 5 = '5'
dconv 6 = '6'
dconv 7 = '7'
dconv 8 = '8'
dconv 9 = '9'
dconv 10 = 'a'
dconv 11 = 'b'
dconv 12 = 'c'
dconv 13 = 'd'
dconv 14 = 'e'
dconv 15 = 'f'

{-
string2bool '0' = False
string2bool '1' = True
bool2string True = '1'
bool2string False = '0'

string_op :: ([Bool]->Bool) -> String -> String -> String
string_op op x y = let op_char c1 c2 = bool2string $ op [string2bool c1, string2bool c2]
                        in zipWith op_char x y

string_and = string_op and
string_or = string_op or

string_not x = map (bool2string.not.string2bool) x
-}

--splitOn :: Eq a => [a] -> [a] -> [[a]]
splitOn d s = 
    let 
        find_d bf crt _ [] = let r = bf ++ crt in if r == [] then [] else [r]
        find_d bf crt [] t = bf:(find_d [] [] d t)
        find_d bf crt (h:t) l'@(h':t') = if h == h' then find_d bf (crt++[h]) t t' else let chunk = crt ++ [h'] in find_d (bf++[(head chunk)]) [] d ((tail chunk) ++ t')
    in find_d [] [] d s

sortWith :: (Ord b) => (a -> b) -> [a] -> [a]
sortWith _ [] = []
sortWith f (h:t) = let less = filter (\e->(f h) < (f e)) t
                       great = filter (\e->(f h) > (f e)) t
                   in (sortWith f less) ++ [h] ++ (sortWith f great)
                   