package ece351.vhdl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.parboiled.common.ImmutableList;

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
import ece351.util.CommandLine;
import ece351.vhdl.ast.Architecture;
import ece351.vhdl.ast.DesignUnit;
import ece351.vhdl.ast.IfElseStatement;
import ece351.vhdl.ast.Process;
import ece351.vhdl.ast.Statement;
import ece351.vhdl.ast.VProgram;

/**
 * Process splitter.
 */
public final class Splitter extends PostOrderExprVisitor {
	private final Set<String> usedVarsInExpr = new LinkedHashSet<String>();

	public static void main(String[] args) {
		System.out.println(split(args));
	}
	
	public static VProgram split(final String[] args) {
		return split(new CommandLine(args));
	}
	
	public static VProgram split(final CommandLine c) {
		final VProgram program = DeSugarer.desugar(c);
        return split(program);
	}
	
	public static VProgram split(final VProgram program) {
		VProgram p = Elaborator.elaborate(program);
		final Splitter s = new Splitter();
		return s.splitit(p);
	}

	private VProgram splitit(final VProgram program) {
					// Determine if the process needs to be split into multiple processes
						// Split the process if there are if/else statements so that the if/else statements only assign values to one pin
// TODO: 35 lines snipped
		ImmutableList<Statement> new_process = ImmutableList.of();
		ImmutableList<DesignUnit> designunit_list = ImmutableList.of();
		//ImmutableList<Statement> splitted_process = ImmutableList.of();
		for(DesignUnit desingunit: program.designUnits)
		{
			for(Statement statement: desingunit.arch.statements)
			{
				if(statement instanceof Process)
				{
					for(Statement seqstatement: ((Process)statement).sequentialStatements)
					{
						if(seqstatement instanceof IfElseStatement){
							for(Statement new_statement: splitIfElseStatement((IfElseStatement)seqstatement))
							{
								new_process = new_process.append(new_statement);
							}
						}
						else
						{
							new_process = new_process.append((Process)statement);
							break;
						}
					}
				}
				else
					new_process = new_process.append(statement);
			}
			Architecture new_arch = new Architecture(new_process, desingunit.arch.components, 
					desingunit.arch.signals, desingunit.arch.entityName, desingunit.arch.architectureName);
			new_process = ImmutableList.of();
			designunit_list = designunit_list.append(new DesignUnit(new_arch, desingunit.entity));
		}
		return new VProgram(designunit_list);
//throw new ece351.util.Todo351Exception();
	}
	
	// You do not have to use this helper method, but we found it useful
	
	private ImmutableList<Statement> splitIfElseStatement(final IfElseStatement ifStmt) {
		ImmutableList<Statement> new_statements = ImmutableList.of();
		ImmutableList<Statement> splitted_Process = ImmutableList.of();
		ImmutableList<AssignmentStatement> new_ifstmt = ImmutableList.of();
		ImmutableList<AssignmentStatement> new_elsestmt = ImmutableList.of();
		// loop over each statement in the ifBody
			for(AssignmentStatement if_statement: ifStmt.ifBody){
				this.usedVarsInExpr.clear();
				new_ifstmt = ImmutableList.of();
				new_elsestmt = ImmutableList.of();
				new_ifstmt = new_ifstmt.append(if_statement);
			// loop over each statement in the elseBody
				for(AssignmentStatement else_statement: ifStmt.elseBody)
				{
					// check if outputVars are the same
					if(if_statement.outputVar.equals(else_statement.outputVar))
					{
						new_elsestmt = new_elsestmt.append(else_statement);
					}
				}
				
					// initialize/clear this.usedVarsInExpr
					// call traverse a few times to build up this.usedVarsInExpr
					// build the resulting list of split statements
			IfElseStatement new_ifStmt = new IfElseStatement(new_elsestmt, new_ifstmt, ifStmt.condition);
			new_statements = ImmutableList.of();
			new_statements = new_statements.append(new_ifStmt);
			traverseExpr(ifStmt.condition);
			for(AssignmentStatement ifstmts : new_ifStmt.ifBody){
				traverseExpr(ifstmts.expr);
			}
			for(AssignmentStatement elsestmts : new_ifStmt.elseBody){
				traverseExpr(elsestmts.expr);
			}
			// build sensitivity list from this.usedVarsInExpr
						ImmutableList<String> sensitivity = ImmutableList.of();
						for(String signal: this.usedVarsInExpr)
						{
							sensitivity = sensitivity.append(signal);
						}
			Process new_process = new Process(new_statements, sensitivity);
			splitted_Process = splitted_Process.append(new_process);
			}
			return splitted_Process;
		// return result
// TODO: 34 lines snipped
		
//throw new ece351.util.Todo351Exception();
	}

	@Override
	public Expr visitVar(final VarExpr e) {
		this.usedVarsInExpr.add(e.identifier);
		return e;
	}

	// no-ops
	@Override public Expr visitConstant(ConstantExpr e) { return e; }
	@Override public Expr visitNot(NotExpr e) { return e; }
	@Override public Expr visitAnd(AndExpr e) { return e; }
	@Override public Expr visitOr(OrExpr e) { return e; }
	@Override public Expr visitXOr(XOrExpr e) { return e; }
	@Override public Expr visitNAnd(NAndExpr e) { return e; }
	@Override public Expr visitNOr(NOrExpr e) { return e; }
	@Override public Expr visitXNOr(XNOrExpr e) { return e; }
	@Override public Expr visitEqual(EqualExpr e) { return e; }
	@Override public Expr visitNaryAnd(NaryAndExpr e) { return e; }
	@Override public Expr visitNaryOr(NaryOrExpr e) { return e; }

}
