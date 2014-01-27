package ece351.common.ast;

import ece351.common.visitor.ExprVisitor;


public final class EqualExpr extends CommutativeBinaryExpr{

	public EqualExpr(Expr leftExpr, Expr rightExpr) {
		super(leftExpr, rightExpr);
	}
    
    public Expr accept(final ExprVisitor v){
    	return v.visitEqual(this);
    }

	@Override
	public String operator() {
		return "=";
	}

	@Override
	public BinaryExpr newBinaryExpr(final Expr left, final Expr right) {
		return new EqualExpr(left, right);
	}

}
