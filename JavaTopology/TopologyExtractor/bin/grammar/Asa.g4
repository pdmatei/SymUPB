// Define a grammar called Hello
grammar Asa;

LINE_COMMENT : '//' .*? '\r'? '\n' -> skip ; // Match "//" stuff '\n'
COMMENT : '/*' .*? '*/' -> skip ; // Match "/*" stuff "*/"

config : (accesslist)*? EOF;
accesslist : 'access-list' acname (acstd | acext);
acstd: 'standard' action src;

// standard access list
acext: 'extended' action proto src dst NEWLINE;// SRC DST PORT '\r'? '\n';

acname: TOKEN;
//TOKEN: ('a'..'z'|'A'..'Z'|'-'|'_'|'0'..'9')+;
TOKEN: [a-zA-Z]('a'..'z'|'A'..'Z'|'-'|'_'|'0'..'9')*;

//actype: 'extended' | 'standard';
action: 'permit' | 'deny';
proto: TOKEN;

src: packet;
dst: packet;

packet : (any | host | iprange) port? ;

any: 'any';
host: 'host' ip;
ip: NUMBER '.' NUMBER '.' NUMBER '.' NUMBER;

iprange: ip ip;

port: 'eq' portid;
portid : (TOKEN | NUMBER);

NUMBER: [0-9]+;

NEWLINE: '\r'? '\n' ; // '\n' |  return newlines to parser (is end-statement signal)
WS : [ \t]+ -> skip ; // toss out whitespace
