// Define a grammar called Hello
grammar Asa;

LINE_COMMENT : '//' .*? NEWLINE -> skip ; // Match "//" stuff '\n'
COMMENT : '/*' .*? '*/' -> skip ; // Match "/*" stuff "*/"

config : (configitem)*? EOF;
configitem: accesslist | interf | global | nat | statick;

global : 'global' '(' global_int ')' NUMBER global_ip NEWLINE;
global_int: TOKEN;
global_ip: ip | 'interface';

nat: 'nat' '(' nat_int ')' NUMBER nat_ip nat_mask nat_destination? NEWLINE;
nat_int: TOKEN;
nat_ip: ip;
nat_mask: ip;
nat_destination: TOKEN;

statick: 'static' '(' static_src static_dst ')' static_mapped_ip static_real_ip 'netmask' static_netmask NEWLINE;
static_src: TOKEN;
static_dst: TOKEN;
static_mapped_ip: ip;
static_real_ip: ip;
static_netmask: ip;

/* Interface parse area */
interf: 'interface' interf_id NEWLINE interfaceSpec '!' NEWLINE;

interf_id : TOKEN;
interfaceSpec: ((nameif | securitylevel | description? | ip_interface | vlan?) NEWLINE)+;

nameif : has_nameif | 'no nameif';
has_nameif: 'nameif' TOKEN;
securitylevel: has_securitylevel | 'no security-level';
has_securitylevel: 'security-level' NUMBER;

description: 'description' (TOKEN)+;
ip_interface: has_ip_address | 'no ip address';
has_ip_address: 'ip address' ip ip;
vlan: 'vlan' NUMBER;

/* access list parse area */
accesslist : 'access-list' acname (acstd | acext) NEWLINE;
acstd: 'standard' action src;

// standard access list
acext: 'extended' action proto src dst;// SRC DST PORT '\r'? '\n';

acname: TOKEN;
//TOKEN: ('a'..'z'|'A'..'Z'|'-'|'_'|'/'|'0'..'9')+;
TOKEN: [a-zA-Z]('a'..'z'|'A'..'Z'|'-'|'_'|'/'|'.'|'='|'>'|'<'|'0'..'9')*;

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

NEWLINE: '\r'? '\n'; // '\n' |  return newlines to parser (is end-statement signal)
WS : [ \t]+ -> skip ; // toss out whitespace

NL: '\r'? '\n' -> skip;