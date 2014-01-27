package ece351.f;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import ece351.common.ast.AndExpr;
import ece351.common.ast.AssignmentStatement;
import ece351.common.ast.ConstantExpr;
import ece351.common.ast.EqualExpr;
import ece351.common.ast.Expr;
import ece351.common.ast.NAndExpr;
import ece351.common.ast.NOrExpr;
import ece351.common.ast.NaryAndExpr;
import ece351.common.ast.NaryOrExpr;
import ece351.common.ast.NotExpr;
import ece351.common.ast.OrExpr;
import ece351.common.ast.VarExpr;
import ece351.common.ast.XNOrExpr;
import ece351.common.ast.XOrExpr;
import ece351.common.visitor.PostOrderExprVisitor;
import ece351.f.ast.FProgram;


public final class DetermineInputVars extends PostOrderExprVisitor {
	private final Set<String> inputVars = new LinkedHashSet<String>();
	private DetermineInputVars(final AssignmentStatement f) { traverseExpr(f.expr); }
	public static Set<String> inputVars(final AssignmentStatement f) {
		final DetermineInputVars div = new DetermineInputVars(f);
		return Collections.unmodifiableSet(div.inputVars);
	}
	public static Set<String> inputVars(final FProgram p) {
		final Set<String> vars = new TreeSet<String>();
		for (final AssignmentStatement f : p.formulas) {
			vars.addAll(DetermineInputVars.inputVars(f));
		}
		return Collections.unmodifiableSet(vars);
	}
	@Override public Expr visitConstant(final ConstantExpr e) { return e; }
	@Override public Expr visitVar(final VarExpr e) { inputVars.add(e.identifier); return e; }
	@Override public Expr visitNot(final NotExpr e) { return e; }
	@Override public Expr visitAnd(final AndExpr e) { return e; }
	@Override public Expr visitOr(final OrExpr e) { return e; }
	@Override public Expr visitNaryAnd(final NaryAndExpr e) { return e; }
	@Override public Expr visitNaryOr(final NaryOrExpr e) { return e; }
	@Override public Expr visitXOr(final XOrExpr e) { return e; }
	@Override public Expr visitNAnd(final NAndExpr e) { return e; }
	@Override public Expr visitNOr(final NOrExpr e) { return e; }
	@Override public Expr visitXNOr(final XNOrExpr e) { return e; }
	@Override public Expr visitEqual(final EqualExpr e) { return e; }
}
