package ece351.f.parboiled;

import org.parboiled.Rule;
import org.parboiled.common.ImmutableList;

import ece351.common.ast.AndExpr;
import ece351.common.ast.AssignmentStatement;
import ece351.common.ast.ConstantExpr;
import ece351.common.ast.Expr;
import ece351.common.ast.NotExpr;
import ece351.common.ast.OrExpr;
import ece351.common.ast.VarExpr;
import ece351.f.ast.FProgram;
import ece351.util.CommandLine;
import ece351.vhdl.VConstants;
import ece351.w.ast.WProgram;
import ece351.w.ast.Waveform;

// Parboiled requires that this class not be final
public /*final*/ class FParboiledParser extends FBase implements VConstants {

	
	public static void main(final String[] args) {
    	final CommandLine c = new CommandLine(args);
    	final String input = c.readInputSpec();
    	final FProgram fprogram = parse(input);
    	assert fprogram.repOk();
    	final String output = fprogram.toString();
    	
    	// if we strip spaces and parens input and output should be the same
    	if (strip(input).equals(strip(output))) {
    		// success: return quietly
    		return;
    	} else {
    		// failure: make a noise
    		System.err.println("parsed value not equal to input:");
    		System.err.println("    " + strip(input));
    		System.err.println("    " + strip(output));
    		System.exit(1);
    	}
    }
	
	private static String strip(final String s) {
		return s.replaceAll("\\s", "").replaceAll("\\(", "").replaceAll("\\)", "");
	}
	
	public static FProgram parse(final String inputText) {
		final FProgram result = (FProgram) process(FParboiledParser.class, inputText).resultValue;
		assert result.repOk();
		return result;
	}

	@Override
	public Rule Program() {
		// STUB: return NOTHING; // TODO: replace this stub
		// For the grammar production Id, ensure that the Id does not match any of the keywords specified
		// in the rule, 'Keyword'
// TODO: 39 lines snipped
		return Sequence(
				push(ImmutableList.of()),
				Sequence(ZeroOrMore(Formula()),
						push(new FProgram((ImmutableList<AssignmentStatement>)pop())),
								EOI));
		
//throw new ece351.util.Todo351Exception();
	}
			public Rule Formula() {
				// TODO: 1 lines snipped
				    	return Sequence(Var(), 
				    			W1(), 
				    			"<=", 
				    			W1(), 
				    			push(new AssignmentStatement(pop())),
				    			Expr(),
				    			swap(),
				    			push(((AssignmentStatement)pop()).varyExpr((Expr)pop())),
				    			swap(),
				    			";", 
				    			W0(),
				    			push(((ImmutableList<AssignmentStatement>)pop()).append((AssignmentStatement)pop())));
				//throw new ece351.util.Todo351Exception();
				    }

				    /**
				     * The first token in each statement is the name of the waveform 
				     * that statement represents.
				     */
				    public Rule Expr() {
				// TODO: 1 lines snipped
				    	return Sequence(Term(), ZeroOrMore(W0(), OR(), W0(), Term(), swap(), push(new OrExpr((Expr)pop(), (Expr)pop()))));
				//throw new ece351.util.Todo351Exception();
				    }

				    /**
				     * A Name is composed of a sequence of Letters. 
				     * Recall that PEGs incorporate lexing into the parser.
				     */
				    public Rule Term() {
				// TODO: 1 lines snipped
				    	return Sequence(Factor(), ZeroOrMore(W0(), AND(), W0(), Factor(), swap(), push(new AndExpr((Expr)pop(), (Expr)pop()))));
				//throw new ece351.util.Todo351Exception();
				    }

				    /**
				     * A BitString is the sequence of values for a pin.
				     */
				    public Rule Factor() {
				// TODO: 1 lines snipped
				    	return FirstOf(Sequence(W0(), NOT(), W0(), Factor(), push(new NotExpr(pop()))), Sequence(W0(),"(",W0(),Expr(),W0(),")",W0()), 
				    			Var(), Constant());
				//throw new ece351.util.Todo351Exception();
				    }
				    
				    /**
				     * A BitString is composed of a sequence of Bits. 
				     * Recall that PEGs incorporate lexing into the parser.
				     */
				    public Rule Constant() {       
				// TODO: 1 lines snipped
				    	return Sequence(W0(), "'", FirstOf("0","1"), match().startsWith("1")? push(ConstantExpr.TrueExpr): push(ConstantExpr.FalseExpr), "'", W0());
				//throw new ece351.util.Todo351Exception();
				    }
				    public Rule Var() {       
						// TODO: 1 lines snipped
						return Sequence(TestNot(Keyword()), OneOrMore(FirstOf(Char(),Digit(),Ch('_'))), push(new VarExpr(match())));
						//throw new ece351.util.Todo351Exception();
						}
}
