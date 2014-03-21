package ece351.vhdl;

import org.parboiled.Rule;
import org.parboiled.common.ImmutableList;

import ece351.common.ast.AndExpr;
import ece351.common.ast.AssignmentStatement;
import ece351.common.ast.ConstantExpr;
import ece351.common.ast.EqualExpr;
import ece351.common.ast.Expr;
import ece351.common.ast.NAndExpr;
import ece351.common.ast.NOrExpr;
import ece351.common.ast.NotExpr;
import ece351.common.ast.OrExpr;
import ece351.common.ast.VarExpr;
import ece351.common.ast.XNOrExpr;
import ece351.common.ast.XOrExpr;
import ece351.f.ast.FProgram;
import ece351.util.CommandLine;
import ece351.vhdl.ast.Architecture;
import ece351.vhdl.ast.Component;
import ece351.vhdl.ast.DesignUnit;
import ece351.vhdl.ast.Entity;
import ece351.vhdl.ast.IfElseStatement;
import ece351.vhdl.ast.Process;
import ece351.vhdl.ast.Statement;
import ece351.vhdl.ast.VProgram;

//Parboiled requires that this class not be final
public/* final */class VParser extends VBase {

	public static void main(final String arg) {
		main(new String[] { arg });
	}

	public static void main(final String[] args) {
		final CommandLine c = new CommandLine(args);
		VProgram vprog = parse(c.readInputSpec());
		System.out.println(vprog);
	}
	 public static VProgram parse(final String[] args) {
	    	final CommandLine c = new CommandLine(args);
	    	return parse(c.readInputSpec());
	    }
	
	public static VProgram parse(final String arg) {
		return (VProgram) process(VParser.class, arg).resultValue;
	}

	public Rule Program() {
		// TODO: Write a VHDL parser that pushes an instance of VProgram to the
		// top of the stack when it is done parsing
		// For the grammar production Id, ensure that the Id does not match any
		// of the keywords specified
		// in the rule, 'Keyword'
// TODO: 248 lines snipped
		return Sequence(push(ImmutableList.of()),
				Sequence(ZeroOrMore(DesignUnit()), 
				push(new VProgram((ImmutableList<DesignUnit>)pop())), EOI));
//throw new ece351.util.Todo351Exception();
	}
	public Rule DesignUnit() {
		// TODO: 70 lines snipped
				return Sequence(EntityDecl(),
						swap(),
						ArchBody(),
						swap(),
						push(((ImmutableList<DesignUnit>) pop()).append(new DesignUnit((Architecture)pop(), (Entity)pop()))));
		//throw new ece351.util.Todo351Exception();
			}
			public Rule EntityDecl() {
				// TODO: 1 lines snipped
				    	return Sequence(ENTITY(), W1(), Id(), W1(), IS(), W1(), PORT(), W0(),
				    			"(", W1(), IdList(), ":", W1(), IN(), W1(), BIT(), ";", W1(),
				    			IdList(), ":", W1(), OUT(), W1(), BIT(), W1(), ")", ";", 
				    			W1(), END(), W1(), FirstOf(ENTITY(), Sequence(Id(), drop())), ";", W0(), 
				    			push(new Entity((ImmutableList<String>)pop(), (ImmutableList<String>)pop(), (String)pop())));
				//throw new ece351.util.Todo351Exception();
				    }
			
				    public Rule IdList() {
				// TODO: 1 lines snipped
				    	return Sequence(push(ImmutableList.of()), Id(), W0(), swap(), 
				    			push(((ImmutableList<String>)pop()).append((String)pop())), 
				    			ZeroOrMore(",", W0(), Id(), W0(), swap(), 
				    					push(((ImmutableList<String>)pop()).append((String)pop()))));
				//throw new ece351.util.Todo351Exception();
				    }

				    /**
				     * A Name is composed of a sequence of Letters. 
				     * Recall that PEGs incorporate lexing into the parser.
				     */
				    public Rule ArchBody() {
				// TODO: 1 lines snipped
				    	return Sequence(ARCHITECTURE(), W1(), Id(), W1(),
				    			OF(), W1(), Id(), W1(), IS(), W1(), 
				    			push(new Architecture((String)pop(), (String) pop())),
				    			Optional(SIGNAL(), W1(), IdList(),
				    					":", W1(), BIT(), ";", W0(), swap(),
				    					push(((Architecture)pop()).varySignals((ImmutableList<String>) pop()))),
				    					BEGIN(),W1(), ZeroOrMore(CompInst(), swap(), push(((Architecture)pop()).appendComponent((Component)pop()))), 
				    					W0(), FirstOf(ProcessStmts(), SigAssnStmts()), swap(),
				    					push(((Architecture)pop()).varyStatements((ImmutableList<Statement>) pop())),
				    					W0(), END(), W1(), Id(), drop(), ";", W0());			    
				//throw new ece351.util.Todo351Exception();
				    }

				    /**
				     * A BitString is the sequence of values for a pin.
				     */
				    public Rule SigAssnStmts() {
				// TODO: 1 lines snipped
				    	return Sequence(push(ImmutableList.of()),
				    			OneOrMore(SigAssnStmt(),
				    			swap(),
				    			push(((ImmutableList<AssignmentStatement>)pop()).append((AssignmentStatement)pop()))));
				//throw new ece351.util.Todo351Exception();
				    }
				    
				    /**
				     * A BitString is composed of a sequence of Bits. 
				     * Recall that PEGs incorporate lexing into the parser.
				     */
				    public Rule SigAssnStmt() {       
				// TODO: 1 lines snipped
				    	return Sequence(Id(), push(new VarExpr((String)pop())), W1(), "<=", W1(),
				    			push(new AssignmentStatement(pop())),
				    			Expr(),
				    			swap(),
				    			push(((AssignmentStatement)pop()).varyExpr((Expr)pop())),
				    			";", W0());
				//throw new ece351.util.Todo351Exception();
				    }
				    public Rule ProcessStmts() {       
						// TODO: 1 lines snipped
						return Sequence(push(ImmutableList.of()), 
								OneOrMore(ProcessStmt(),
								swap(),
								push(((ImmutableList<Process>)pop()).append((Process)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule ProcessStmt() {       
						// TODO: 1 lines snipped
						return Sequence(PROCESS(), W0(), "(", W0(), IdList(), W0(), ")", W0(),
								BEGIN(), W0(),
								FirstOf(IfElseStmts(), SigAssnStmts()), W0(),
								END(), W1(), PROCESS(), ";", W0(),
								push(new Process((ImmutableList<Statement>) pop(), (ImmutableList<String>) pop())));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule IfElseStmts() {       
						// TODO: 1 lines snipped
						return Sequence(push(ImmutableList.of()),
								OneOrMore(IfElseStmt(),
										swap(),
										push(((ImmutableList<IfElseStatement>)pop()).append((IfElseStatement)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule IfElseStmt() {       
						// TODO: 1 lines snipped
						return Sequence(IF(), W0(), Expr(), W0(), THEN(), W0(), 
								SigAssnStmts(), W0(), ELSE(), 
								W0(), SigAssnStmts(),
								W0(), ENDIF(), W0(), 
								push(new IfElseStatement((ImmutableList<AssignmentStatement>) pop(),
										(ImmutableList<AssignmentStatement>) pop(),
										(Expr) pop())),
								Optional(Id(), drop()),";", W0());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule CompInst() {       
						// TODO: 1 lines snipped
						return Sequence(Id(), W0(), ":", W0(), ENTITY(), W0(), "work.", W0(),
								Id(), W0(), PORT(), W0(), MAP(), W0(),
								"(", W0(), IdList(), W0(), ")", ";", W0(),
								push(new Component((ImmutableList<String>) pop(), (String) pop(), (String) pop())));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule Expr() {       
						// TODO: 1 lines snipped
						return Sequence(XnorTerm(), ZeroOrMore(W0(), XNOR(), W0(), XnorTerm(), swap(), push(new XNOrExpr((Expr)pop(), (Expr)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule XnorTerm() {       
						// TODO: 1 lines snipped
						return Sequence(XorTerm(), ZeroOrMore(W0(), XOR(), W0(), XorTerm(), swap(), push(new XOrExpr((Expr)pop(), (Expr)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule XorTerm() {       
						// TODO: 1 lines snipped
						return Sequence(NorTerm(), ZeroOrMore(W0(), NOR(), W0(), NorTerm(), swap(), push(new NOrExpr((Expr)pop(), (Expr)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule NorTerm() {       
						// TODO: 1 lines snipped
						return Sequence(NandTerm(), ZeroOrMore(W0(), NAND(), W0(), NandTerm(), swap(), push(new NAndExpr((Expr)pop(), (Expr)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule NandTerm() {       
						// TODO: 1 lines snipped
						return Sequence(OrTerm(), ZeroOrMore(W0(), OR(), W0(), OrTerm(), swap(), push(new OrExpr((Expr)pop(), (Expr)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule OrTerm() {       
						// TODO: 1 lines snipped
						return Sequence(AndTerm(), ZeroOrMore(W0(), AND(), W0(), AndTerm(), swap(), push(new AndExpr((Expr)pop(), (Expr)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule AndTerm() {       
						// TODO: 1 lines snipped
						return Sequence(EqTerm(), ZeroOrMore(W0(), "=", W0(), EqTerm(), swap(), push(new EqualExpr((Expr)pop(), (Expr)pop()))));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule EqTerm() {       
						// TODO: 1 lines snipped
						return FirstOf(Sequence(NOT(), W0(), EqTerm(), push(new NotExpr(pop()))), Sequence("(", W0(), Expr(), W0(), ")"), Sequence(Id(), push(new VarExpr((String)pop()))), Constant());
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule Constant() {       
						// TODO: 1 lines snipped
						return Sequence(FirstOf("'0'", "'1'"), match().startsWith("'1'")? push(ConstantExpr.TrueExpr): push(ConstantExpr.FalseExpr));
						//throw new ece351.util.Todo351Exception();
						}
				    public Rule Id() {       
						// TODO: 1 lines snipped
						return Sequence(TestNot(Keyword()), push(""), Char(), push((String)pop() + match()), 
								ZeroOrMore(FirstOf(Char(), Digit(), "_"), push((String)pop() + match()))
								);
						//throw new ece351.util.Todo351Exception();
						}

}
