module Transf where
import Utils
{-- each character is encoded as a sequence of two integers from 00 - 99
--}
strMap = [('0',99),('1',1),('2',2),('3',3),('4',4),('5',5),('6',6),('7',7),('8',8),('9',9),
          ('a',10),('b',11),('c',12),('d',13),('e',14),('f',15),('g',16),('h',17),('i',18),('j',19),
          ('k',20),('l',21),('m',22),('n',23),('o',24),('p',25),('r',26),('s',27),('t',28),('u',29),
          ('v',30),('w',31),('x',32),('y',33),('z',34),
          ('A',40),('B',41),('C',42),('D',43),('E',44),('F',45),('G',46),('H',47),('I',48),('J',49),
          ('K',50),('L',51),('M',52),('N',53),('O',54),('P',55),('R',56),('S',57),('T',58),('U',59),
          ('V',60),('W',61),('X',62),('Y',63),('Z',64),
          ('-',71),('_',72),(':',73),('*',74),('#',75),(' ',76),('^',77),('&',78),('@',79),('!',80),
          ('%',81),('/',82),('\\',83),('+',84),('[',85),(']',86),('(',87),(')',88),('.',89),(',',90),
          ('q',91),('Q',92)
          ]
          
toProto "icmp" = 0
toProto "tcp" = 1
toProto "udp" = 2
toProto "ip" = 3

fromProto 0 = "icmp"
fromProto 1 = "tcp"
fromProto 2 = "udp"
fromProto 3 = "ip"

toProtocolNo :: String -> Integer
toProtocolNo "ftp" = 21
toProtocolNo "www" = 80
toProtocolNo "https" = 443
toProtocolNo "ssh" = 22
toProtocolNo "http" = 80
toProtocolNo x = (read x)::Integer


toInteger :: String -> Integer
toInteger s = 
    let step = 100
        find k table = snd $ head $ filter (\(c,_)->c==k) table
        toInt (h:t) n table = (find h table) * (step^n) + (toInt t (n+1) table)
        toInt [] _ _ = 0
    in toInt s 0 strMap          
    
toString :: Integer -> String
toString n = 
    let step = 100
        find k table = fst $ head $ filter(\(_,i)->k==i) table
        toStr 0 _ = []
        toStr n table = (find (n `mod` step) table):(toStr (n `div` step) table)
    in toStr n strMap
    
    