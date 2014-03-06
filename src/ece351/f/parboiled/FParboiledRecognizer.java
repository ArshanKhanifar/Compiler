package ece351.f.parboiled;

import org.parboiled.Rule;
import org.parboiled.common.ImmutableList;

import ece351.util.CommandLine;
import ece351.vhdl.VConstants;
import ece351.w.ast.WProgram;
import ece351.w.ast.Waveform;

//Parboiled requires that this class not be final
public /*final*/ class FParboiledRecognizer extends FBase implements VConstants {

	
	public static void main(final String... args) {
		final CommandLine c = new CommandLine(args);
    	process(FParboiledRecognizer.class, c.readInputSpec());
    }

	@Override

public Rule Program() {
	// TODO: 70 lines snipped
			return Sequence(ZeroOrMore(Formula()), EOI);
	//throw new ece351.util.Todo351Exception();
		}
		public Rule Formula() {
			// TODO: 1 lines snipped
			    	return Sequence(Var(), W1(), "<=", W1(), Expr(), ";", W0());
			//throw new ece351.util.Todo351Exception();
			    }

			    /**
			     * The first token in each statement is the name of the waveform 
			     * that statement represents.
			     */
			    public Rule Expr() {
			// TODO: 1 lines snipped
			    	return Sequence(Term(), ZeroOrMore(W0(), OR(), W0(), Term()));
			//throw new ece351.util.Todo351Exception();
			    }

			    /**
			     * A Name is composed of a sequence of Letters. 
			     * Recall that PEGs incorporate lexing into the parser.
			     */
			    public Rule Term() {
			// TODO: 1 lines snipped
			    	return Sequence(Factor(), ZeroOrMore(W0(), AND(), W0(), Factor()));			    
			//throw new ece351.util.Todo351Exception();
			    }

			    /**
			     * A BitString is the sequence of values for a pin.
			     */
			    public Rule Factor() {
			// TODO: 1 lines snipped
			    	return FirstOf(Sequence(W0(), NOT(), W0(), Factor()), Sequence(W0(),"(",W0(),Expr(),W0(),")",W0()), 
			    			Var(), Constant());
			//throw new ece351.util.Todo351Exception();
			    }
			    
			    /**
			     * A BitString is composed of a sequence of Bits. 
			     * Recall that PEGs incorporate lexing into the parser.
			     */
			    public Rule Constant() {       
			// TODO: 1 lines snipped
			    	return OneOrMore(W0(), FirstOf("'0'","'1'"), W0());
			//throw new ece351.util.Todo351Exception();
			    }
			    public Rule Var() {       
					// TODO: 1 lines snipped
					return Sequence(TestNot(Keyword()), OneOrMore(Char()));
					//throw new ece351.util.Todo351Exception();
					}
}

