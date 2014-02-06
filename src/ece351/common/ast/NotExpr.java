package ece351.common.ast;

import ece351.common.visitor.ExprVisitor;
import ece351.vhdl.VConstants;

public final class NotExpr extends UnaryExpr{
	public NotExpr(Expr argument) {
		super(argument);
	}

	public NotExpr(Object pop) {
		this( (Expr)pop );
	}

	public NotExpr() { this(null); }
	
	@Override
    protected final Expr simplifyOnce() {		
    	// simplify our child
    	final Expr localexpr = expr.simplify();
    			// !true = false
    			// !false = true
    		// !!x = x
    		// nothing changed
    		// something changed
// TODO: 19 lines snipped
    	if(localexpr.getClass().equals(NotExpr.class))
    		return ((NotExpr)localexpr).expr;
    	else if(localexpr.equals(ConstantExpr.TrueExpr))
    		return ConstantExpr.FalseExpr;
    	else if(localexpr.equals(ConstantExpr.FalseExpr))
    		return ConstantExpr.TrueExpr;
    	else
    		return new NotExpr(localexpr);
//throw new ece351.util.Todo351Exception();
    }
	
    public Expr accept(final ExprVisitor v){
    	return v.visitNot(this);
    }
	
	@Override
	public String operator() {
		return VConstants.NOT;
	}
	@Override
	public UnaryExpr newUnaryExpr(final Expr expr) {
		return new NotExpr(expr);
	}

}
