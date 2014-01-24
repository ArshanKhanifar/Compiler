package ece351.f;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import ece351.common.ast.Expr;
import ece351.common.ast.NotExpr;
import ece351.common.ast.VarExpr;
import ece351.util.BaseTest351;

public class TestSimplifierNotExpr extends BaseTest351 {

	@Test
	public void testDoubleNegative() {
		final VarExpr x = new VarExpr("x");
		final NotExpr e = new NotExpr(new NotExpr(x));
		assertTrue(e.repOk());
		final Expr result = e.simplify();
		assertTrue(result.repOk());
		assertEquals(x, result);
	}

}
