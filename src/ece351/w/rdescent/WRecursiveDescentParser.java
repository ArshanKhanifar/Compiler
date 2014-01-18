package ece351.w.rdescent;

import org.parboiled.common.ImmutableList;

import ece351.util.Lexer;
import ece351.w.ast.WProgram;
import ece351.w.ast.Waveform;

public final class WRecursiveDescentParser {
    private final Lexer lexer;

    public WRecursiveDescentParser(final Lexer lexer) {
        this.lexer = lexer;
    }

    public static WProgram parse(final String input) {
    	final WRecursiveDescentParser p = new WRecursiveDescentParser(new Lexer(input));
        return p.parse();
    }

    public WProgram parse() {
    	// STUB: return null;
// TODO: 30 lines snipped
    	//Waveform waveform = new Waveform();
    	ImmutableList<Waveform> waveform_list = ImmutableList.of();
    	while (!lexer.inspectEOF())
    	{
    		String name = "";
    		ImmutableList<String> bits = ImmutableList.of();
        		while(lexer.inspectID())
        		{
        			name += lexer.consumeID();
        		}
        		if(lexer.inspect(":"))
        			lexer.consume(":");
        		while(lexer.inspect("0", "1"))
        		{
        			bits = bits.append(lexer.consume("0","1"));
        		}
        		//if(lexer.inspect(";"))
        			lexer.consume(";");
        		Waveform waveform = new Waveform(bits, name);
        		waveform_list = waveform_list.append(waveform);
    	}
    	WProgram wprogram = new WProgram(waveform_list);
    	//lexer.consumeEOF();
    	return wprogram;
//throw new ece351.util.Todo351Exception();
    }
}
