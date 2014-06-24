rSwitch2 = gen_switch [("Switch2-Fa0/2","1"),("Switch2-Fa0/3","1"),("Switch2-Fa0/2","5"),("Switch2-Fa0/3","5"),("Switch2-Fa0/24","5"),("Switch2-Fa0/2","6"),("Switch2-Fa0/3","6"),("Switch2-Fa0/2","99"),("Switch2-Fa0/3","99")] [("1","0009.7ce8.5903","Switch2-Fa0/3"),("1","000b.be80.c702","Switch2-Fa0/2"),("5","000c.8540.c106","Switch2-Fa0/3"),("5","00d0.58d1.bbd8","Switch2-Fa0/3"),("5","00d0.bca7.9546","Switch2-Fa0/2"),("5","00d0.ff91.989b","Switch2-Fa0/24"),("5","00e0.a3aa.e980","Switch2-Fa0/2")]
rSwitch0 = gen_switch [("Switch0-Fa0/1","1"),("Switch0-Fa0/2","1"),("Switch0-Fa0/22","5"),("Switch0-Fa0/23","5"),("Switch0-Fa0/1","5"),("Switch0-Fa0/2","5"),("Switch0-Fa0/1","6"),("Switch0-Fa0/2","6"),("Switch0-Fa0/1","99"),("Switch0-Fa0/2","99")] [("1","0009.7ce8.5901","Switch0-Fa0/1"),("1","00e0.8f2e.d002","Switch0-Fa0/2"),("5","000c.8540.c106","Switch0-Fa0/2"),("5","00d0.58d1.bbd8","Switch0-Fa0/2"),("5","00d0.bca7.9546","Switch0-Fa0/23"),("5","00d0.ff91.989b","Switch0-Fa0/2"),("5","00e0.8f2e.d002","Switch0-Fa0/2"),("5","00e0.a3aa.e980","Switch0-Fa0/22"),("6","00e0.8f2e.d002","Switch0-Fa0/2"),("99","00e0.8f2e.d002","Switch0-Fa0/2")]
rSwitch1 = gen_switch [("Switch1-Fa0/3","1"),("Switch1-Fa0/3","5"),("Switch1-Fa0/23","5"),("Switch1-Fa0/24","5"),("Switch1-Fa0/3","6"),("Switch1-Fa0/3","99")] [("1","00e0.8f2e.d003","Switch1-Fa0/3"),("5","000c.8540.c106","Switch1-Fa0/24"),("5","00d0.58d1.bbd8","Switch1-Fa0/23"),("5","00d0.bca7.9546","Switch1-Fa0/3"),("5","00d0.ff91.989b","Switch1-Fa0/3"),("5","00e0.8f2e.d003","Switch1-Fa0/3"),("5","00e0.a3aa.e980","Switch1-Fa0/3"),("6","00e0.8f2e.d003","Switch1-Fa0/3"),("99","00e0.8f2e.d003","Switch1-Fa0/3")]
wr0 = wire_rule "Switch0-Fa0/2:out" "Switch2-Fa0/2:in"
wr1 = wire_rule "Switch2-Fa0/2:out" "Switch0-Fa0/2:in"
wr2 = wire_rule "Switch0-Fa0/1:out" "Switch1-Fa0/1:in"
wr3 = wire_rule "Switch1-Fa0/1:out" "Switch0-Fa0/1:in"
wr4 = wire_rule "Switch1-Fa0/23:out" "Switch2-Fa0/3:in"
wr5 = wire_rule "Switch2-Fa0/3:out" "Switch1-Fa0/23:in"

