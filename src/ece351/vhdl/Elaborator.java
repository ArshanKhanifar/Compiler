package ece351.vhdl;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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
import ece351.vhdl.ast.Component;
import ece351.vhdl.ast.DesignUnit;
import ece351.vhdl.ast.IfElseStatement;
import ece351.vhdl.ast.Process;
import ece351.vhdl.ast.Statement;
import ece351.vhdl.ast.VProgram;

/**
 * Inlines logic in components to architecture body.
 */
public final class Elaborator extends PostOrderExprVisitor {

	private final Map<String, String> current_map = new LinkedHashMap<String, String>();
	
	public static void main(String[] args) {
		System.out.println(elaborate(args));
	}
	
	public static VProgram elaborate(final String[] args) {
		return elaborate(new CommandLine(args));
	}
	
	public static VProgram elaborate(final CommandLine c) {
        final VProgram program = DeSugarer.desugar(c);
        return elaborate(program);
	}
	
	public static VProgram elaborate(final VProgram program) {
		final Elaborator e = new Elaborator();
		return e.elaborateit(program);
	}

	private VProgram elaborateit(final VProgram root) {									

// TODO: 58 lines snipped
		ImmutableList<Component> components = ImmutableList.of();
		ImmutableList<DesignUnit> designunit_list = ImmutableList.of();
		int component_count = 0;
		for(DesignUnit current_designunit: root.designUnits)
		{
			// In the elaborator, an architecture's list of signals, and set of statements may change (grow)
			ImmutableList<String> signals = current_designunit.arch.signals;
			ImmutableList<Statement> statement = current_designunit.arch.statements;
			for(Component component: current_designunit.arch.components)
			{
				component_count++;
				current_map.clear();
				
				for (DesignUnit instance_dunit : designunit_list){
				if (instance_dunit.entity.identifier.equals(component.entityName)){
						for( String toaddSignal : instance_dunit.arch.signals){
							signals = signals.append("comp" + component_count + "_" + toaddSignal);
							current_map.put(toaddSignal, "comp" + component_count + "_" + toaddSignal);
						}
						
				//populate dictionary/map
				Iterator<String> Input = instance_dunit.entity.input.iterator();
				Iterator<String> Output = instance_dunit.entity.output.iterator();
				Iterator<String> local =  component.signalList.iterator();
				
				//add input signals, map to ports
				while(Input.hasNext())
				{
					current_map.put(Input.next(), local.next());
				}
				
				//add output signals, map to ports
				while(Output.hasNext())
				{
					current_map.put(Output.next(), local.next());
				}
				
				//add local signals, add to signal list of i
				while(local.hasNext())
				{
					signals = signals.append(local.next());
				}
				
				//loop through the statements in the architecture body
					//make the appropriate variable substitutions for signal assignment statements
					//make the appropriate variable substitutions for processes (sensitivity list, if/else body statements)
				for(Statement child_statement: instance_dunit.arch.statements)
				{
					if(child_statement instanceof Process)
						statement = statement.append((Statement)expandProcessComponent((Process)child_statement));
					else if(child_statement instanceof IfElseStatement)
						statement = statement.append((Statement)changeIfVars((IfElseStatement)child_statement));
					else if(child_statement instanceof AssignmentStatement)
						statement = statement.append((Statement)changeStatementVars((AssignmentStatement)child_statement));
				}
			}
				}
			}
			Architecture new_arch = new Architecture(statement, components, signals, 
					current_designunit.arch.entityName, current_designunit.arch.architectureName);
			DesignUnit new_designunit = new DesignUnit(new_arch, current_designunit.entity);
			designunit_list = designunit_list.append(new_designunit);
		}
		return new VProgram(designunit_list);
		
//throw new ece351.util.Todo351Exception();
	}
	
	// you do not have to use these helper methods; we found them useful though
	private Process expandProcessComponent(final Process process) {
// TODO: 15 lines snipped
		ImmutableList<String> new_sensitivitylist = ImmutableList.of();
		ImmutableList<Statement> new_sequentialstatement = ImmutableList.of();
		for(String signal: process.sensitivityList)
		{
			new_sensitivitylist = new_sensitivitylist.append(current_map.get(signal));
		}
		for(Statement statement: process.sequentialStatements)
		{
			if(statement instanceof IfElseStatement)
				new_sequentialstatement = new_sequentialstatement.append((Statement)changeIfVars((IfElseStatement)statement));
			else
				new_sequentialstatement = new_sequentialstatement.append((Statement)changeStatementVars((AssignmentStatement)statement));
		}
		return new Process(new_sequentialstatement, new_sensitivitylist);
//throw new ece351.util.Todo351Exception();
	}
	
	// you do not have to use these helper methods; we found them useful though
	private  IfElseStatement changeIfVars(final IfElseStatement s) {
// TODO: 14 lines snipped
		Expr new_condition = traverseExpr(s.condition);
		ImmutableList<AssignmentStatement> new_IfBody = ImmutableList.of();
		ImmutableList<AssignmentStatement> new_ElseBody = ImmutableList.of();
		
		for(AssignmentStatement ifbody: s.ifBody)
		{
			new_IfBody = new_IfBody.append(changeStatementVars(ifbody));
		}
		for(AssignmentStatement elsebody: s.elseBody)
		{
			new_ElseBody = new_ElseBody.append(changeStatementVars(elsebody));
		}
		return new IfElseStatement(new_ElseBody, new_IfBody, new_condition);
//throw new ece351.util.Todo351Exception();
	}

	// you do not have to use these helper methods; we found them useful though
	private AssignmentStatement changeStatementVars(final AssignmentStatement s){
// TODO: 2 lines snipped
		VarExpr new_outputvar = (VarExpr)traverseExpr(s.outputVar);
		Expr new_expr = traverseExpr(s.expr);
		return new AssignmentStatement(new_outputvar, new_expr);
		
//throw new ece351.util.Todo351Exception();
	}
	
	
	@Override
	public Expr visitVar(VarExpr e) {
		// TODO replace/substitute the variable found in the map
// TODO: 1 lines snipped
		String Var = current_map.get(e.identifier);
		if (Var == null){
			return e;
		}
		return new VarExpr(Var);
//throw new ece351.util.Todo351Exception();
	}
	
	// do not rewrite these parts of the AST
	@Override public Expr visitConstant(ConstantExpr e) { return e; }
	@Override public Expr visitNot(NotExpr e) { return e; }
	@Override public Expr visitAnd(AndExpr e) { return e; }
	@Override public Expr visitOr(OrExpr e) { return e; }
	@Override public Expr visitXOr(XOrExpr e) { return e; }
	@Override public Expr visitEqual(EqualExpr e) { return e; }
	@Override public Expr visitNAnd(NAndExpr e) { return e; }
	@Override public Expr visitNOr(NOrExpr e) { return e; }
	@Override public Expr visitXNOr(XNOrExpr e) { return e; }
	@Override public Expr visitNaryAnd(NaryAndExpr e) { return e; }
	@Override public Expr visitNaryOr(NaryOrExpr e) { return e; }
}
