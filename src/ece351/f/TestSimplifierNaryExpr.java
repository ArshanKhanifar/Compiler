package ece351.f;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ece351.common.ast.ConstantExpr;
import ece351.common.ast.Expr;
import ece351.common.ast.NaryAndExpr;
import ece351.common.ast.NaryExpr;
import ece351.common.ast.NaryOrExpr;
import ece351.common.ast.VarExpr;
import ece351.util.BaseTest351;

public class TestSimplifierNaryExpr extends BaseTest351 {

	@Test
	public void testOrTrue() {
		final ConstantExpr t = ConstantExpr.TrueExpr;
		final VarExpr x = new VarExpr("x");
		final NaryExpr e = new NaryOrExpr(x, t);
		assertTrue(e.repOk());
		final Expr result = e.simplify();
		assertTrue(result.repOk());
		assertEquals(t, result);
	}

	@Test
	public void testAndTrue() {
		final ConstantExpr t = ConstantExpr.TrueExpr;
		final VarExpr x = new VarExpr("x");
		final NaryExpr e = new NaryAndExpr(x, t);
		assertTrue(e.repOk());
		final Expr result = e.simplify();
		assertTrue(result.repOk());
		assertEquals(x, result);
	}


}
