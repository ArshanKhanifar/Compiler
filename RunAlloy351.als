
abstract sig Boolean {}
one sig true extends Boolean {}
abstract sig Var { v : lone Boolean }
one sig  TRUE,FALSE extends Var {}
fact{
	some TRUE.v
	no FALSE.v
}
fun _equal[a,b:Var]:Var{_xnor[a,b]}
fun _xor[a,b:Var]:Var{ _or[_and[a, _not[b]], _and[_not[a], b]] }
fun _or[a,b:Var]:Var{{v':(a+b) | ((some a.v)or(some b.v))<=>(v' in a+b)}}
fun _and[a,b:Var]:Var{{v':(a+b) | ((some a.v)and(some b.v))<=>(v' in a+b)}}
fun _xnor[a,b:Var]:Var{_not[_xor[a,b]]}
fun _nor[a,b:Var]:Var{_not[_or[a,b]]}
fun _nand[a,b:Var]:Var{_not[_and[a,b]]}
fun _not[a:Var]:Var{{v':Var|v'.v!=a.v}}
one sig _a, _b, _x, _y extends Var {}
pred _Z1[] {some _xnor[ _and[ _x, _y] , _xnor[ _a, _b] ] .v}
pred _Z2[] {some _xnor[ _and[ _x, _y] , _xnor[ _a, _b] ] .v}
assert equivalent {
    _Z1 <=> _Z2
}
check equivalent
