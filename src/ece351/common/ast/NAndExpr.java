package ece351.common.ast;

import ece351.common.visitor.ExprVisitor;
import ece351.vhdl.VConstants;

public final class NAndExpr extends CommutativeBinaryExpr {
	
	public NAndExpr(Expr left, Expr right) {
		super(left,right);
	}
    
    public Expr accept(final ExprVisitor v){
    	return v.visitNAnd(this);
    }
	@Override
	public String operator() {
		return VConstants.NAND;
	}
	@Override
	public BinaryExpr newBinaryExpr(final Expr left, final Expr right) {
		return new NAndExpr(left, right);
	}
}
