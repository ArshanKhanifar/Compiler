package ece351.common.visitor;

import org.parboiled.common.ImmutableList;

import ece351.common.ast.BinaryExpr;
import ece351.common.ast.Expr;
import ece351.common.ast.NaryExpr;
import ece351.common.ast.UnaryExpr;

/**
 * This visitor rewrites the AST from the bottom up.
 * Optimized to only create new parent nodes if children have changed.
 */
public abstract class PostOrderExprVisitor extends ExprVisitor {

	@Override
	public final Expr traverseUnaryExpr(UnaryExpr u) {
		// child first
		final Expr child = traverseExpr(u.expr);
		// only rewrite if something has changed
		if (child != u.expr) {
			u = u.newUnaryExpr(child);
		}
		// now parent
		return u.accept(this);
	}
	
	@Override
	public final Expr traverseBinaryExpr(BinaryExpr b) {
		// children first
		final Expr left = traverseExpr(b.left);
		final Expr right = traverseExpr(b.right);
		// only rewrite if something has changed
		if (left != b.left || right != b.right) {
			b = b.newBinaryExpr(left, right);
		}
		// now parent
		return b.accept(this);
	}

	@Override
	public final Expr traverseNaryExpr(NaryExpr e) {
		// children first
		ImmutableList<Expr> children = ImmutableList.of();
		boolean change = false;
		for (final Expr c1 : e.children) {
			final Expr c2 = traverseExpr(c1);
			children = children.append(c2);
			if (c2 != c1) { change = true; }
		}
		// only rewrite if something changed
		if (change) {
			e = e.newNaryExpr(children);
		}
		// now parent
		return e.accept(this);
	}
}
