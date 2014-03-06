
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
one sig _a, _b, _c extends Var {}
pred _x1[] {some _and[ _or[ _b, _c] , _a] .v}
pred _y1[] {some _and[ _a, _b] .v}
pred _z1[] {some _and[ _a, _c] .v}
pred _x2[] {some _and[ _or[ _b, _c] , _a] .v}
pred _y2[] {some _and[ _a, _b] .v}
pred _z2[] {some _and[ _a, _c] .v}
assert equivalent {
    _x1 <=> _x2
    _y1 <=> _y2
    _z1 <=> _z2
}
check equivalent
