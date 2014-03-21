package ece351.vhdl;

import org.parboiled.Rule;

import ece351.util.CommandLine;

//Parboiled requires that this class not be final
public/* final */class VRecognizer extends VBase {

	public static void main(final String arg) {
		main(new String[] { arg });
	}

	public static void main(final String[] args) {
		final CommandLine c = new CommandLine(args);
		process(VRecognizer.class, c.readInputSpec());
	}

	public Rule Program() {
		// TODO: 70 lines snipped
				return Sequence(ZeroOrMore(DesignUnit()), EOI);
		//throw new ece351.util.Todo351Exception();
			}
	public Rule DesignUnit() {
		// TODO: 70 lines snipped
				return Sequence(EntityDecl(), ArchBody());
		//throw new ece351.util.Todo351Exception();
			}
			public Rule EntityDecl() {
				// TODO: 1 lines snipped
				    	return Sequence(ENTITY(), W1(), Id(), W1(), IS(), W1(), PORT(), W0(),
				    			"(", W1(), IdList(), ":", W1(), IN(), W1(), BIT(), ";", W1(),
				    			IdList(), ":", W1(), OUT(), W1(), BIT(), W1(), ")", ";", 
				    			W1(), END(), W1(), FirstOf(ENTITY(), Id()), ";", W0());
				//throw new ece351.util.Todo351Exception();
				    }
			
				    public Rule IdList() {
				// TODO: 1 lines snipped
				    	return Sequence(Id(), W0(), ZeroOrMore(",", W0(), Id(), W0()));
				//throw new ece351.util.Todo351Exception();
				    }

				    /**
				     * A Name is composed of a sequence of Letters. 
				     * Recall that PEGs incorporate lexing into the parser.
				     */
				    public Rule ArchBody() {
				// TODO: 1 lines snipped
				    	return Sequence(ARCHITECTURE(), W1(), Id(),W1(),
				    			OF(), W1(), Id(), W1(), IS(), W1(),
				    			Optional(SIGNAL(), W1(), IdList(), 
				    					":", W1(), BIT(), ";", W0()),
				    					BEGIN(),W1(), ZeroOrMore(CompInst()), W0(),
				    					FirstOf(ProcessStmts(), SigAssnStmts()), W0(), 
				    					END(), W1(), Id(), ";", W0());			    
				//throw new ece351.util.Todo351Exception();
				    }

				    /**
				     * A BitString is the sequence of values for a pin.
				     */
				    public Rule SigAssnStmts() {
				// TODO: 1 lines snipped
				    	return OneOrMore(SigAssnStmt());
				//throw new ece351.util.Todo351Exception();
				    }
				    
				    /**
				     * A BitString is composed of a sequence of Bits. 
				     * Recall that PEGs incorporate lexing into the parser.
				     */
				    public Rule SigAssnStmt() {       
				// TODO: 1 lines snipped
				    	return Sequence(Id(), W1(), "<=", W1(), Expr(), ";", W0());
				//throw new ece351.util.Todo351Exception();
				    }
				    public Rule ProcessStmts() {       
						// TODO: 1 lines snipped
						return OneOrMore(ProcessStmt());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule ProcessStmt() {       
						// TODO: 1 lines snipped
						return Sequence(PROCESS(), W0(), "(", W0(),  IdList(), W0(), ")", W0(),
								BEGIN(), W0(),
								FirstOf(IfElseStmts(), SigAssnStmts()), W0(),
								END(), W1(), PROCESS(), ";", W0());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule IfElseStmts() {       
						// TODO: 1 lines snipped
						return OneOrMore(IfElseStmt());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule IfElseStmt() {       
						// TODO: 1 lines snipped
						return Sequence(IF(), W0(), Expr(), W0(), THEN(), W0(), 
								SigAssnStmts(), W0(), ELSE(), 
								W0(), SigAssnStmts(),
								W0(), ENDIF(), W0(), Optional(Id()),";", W0());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule CompInst() {       
						// TODO: 1 lines snipped
						return Sequence(Id(), W0(), ":", W0(), ENTITY(), W0(), "work.", W0(),
								Id(), W0(), PORT(), W0(), MAP(), W0(),
								"(",W0(), IdList(), W0(), ")", ";", W0());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule Expr() {       
						// TODO: 1 lines snipped
						return Sequence(XnorTerm(), ZeroOrMore(W0(), XNOR(), W0(), XnorTerm()));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule XnorTerm() {       
						// TODO: 1 lines snipped
						return Sequence(XorTerm(), ZeroOrMore(W0(), XOR(), W0(), XorTerm()));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule XorTerm() {       
						// TODO: 1 lines snipped
						return Sequence(NorTerm(), ZeroOrMore(W0(), NOR(), W0(), NorTerm()));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule NorTerm() {       
						// TODO: 1 lines snipped
						return Sequence(NandTerm(), ZeroOrMore(W0(), NAND(), W0(), NandTerm()));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule NandTerm() {       
						// TODO: 1 lines snipped
						return Sequence(OrTerm(), ZeroOrMore(W0(), OR(), W0(), OrTerm()));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule OrTerm() {       
						// TODO: 1 lines snipped
						return Sequence(AndTerm(), ZeroOrMore(W0(), AND(), W0(), AndTerm()));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule AndTerm() {       
						// TODO: 1 lines snipped
						return Sequence(EqTerm(), ZeroOrMore(W0(), "=", W0(), EqTerm()));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule EqTerm() {       
						// TODO: 1 lines snipped
						return FirstOf(Sequence(NOT(), W0(), EqTerm()), Sequence("(", W0(),  Expr(), W0(), ")"), 
								Id(), Constant());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule Constant() {       
						// TODO: 1 lines snipped
						return FirstOf("'0'", "'1'");
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule Id() {       
						// TODO: 1 lines snipped
						return OneOrMore(Char(), ZeroOrMore(FirstOf(Char(), Digit(), "_")));
						//throw new ece351.util.Todo351Exception();
						}
				    
}
