package ece351.f.rdescent;

import ece351.util.CommandLine;
import ece351.util.Lexer;
import ece351.vhdl.VConstants;

public final class FRecursiveDescentRecognizer implements VConstants {
   
    private final Lexer lexer;

    public static void main(final String arg) {
    	main(new String[]{arg});
    }
    
    public static void main(final String[] args) {
    	final CommandLine c = new CommandLine(args);
        final Lexer lexer = new Lexer(c.readInputSpec());
        final FRecursiveDescentRecognizer r = new FRecursiveDescentRecognizer(lexer);
        r.recognize();
    }

    public FRecursiveDescentRecognizer(final Lexer lexer) {
        this.lexer = lexer;
    }

    public void recognize() {
        program();
    }

    void program() {
    	do {
    		formula();
    	} while (!lexer.inspectEOF());
        lexer.consumeEOF();
    }

    void formula() {
        var();
        lexer.consume("<=");
        expr();
        lexer.consume(";");
    }
    
    // STUB2: void expr() { throw new ece351.util.Todo351Exception(); } // TODO
    void expr() { throw new ece351.util.Todo351Exception(); } // TODO
    // STUB2: void term() { throw new ece351.util.Todo351Exception(); } // TODO
    void term() { throw new ece351.util.Todo351Exception(); } // TODO
	// STUB2: void factor() { throw new ece351.util.Todo351Exception(); } // TODO
	void factor() { throw new ece351.util.Todo351Exception(); } // TODO
	// STUB2: void var() { throw new ece351.util.Todo351Exception(); } // TODO
	void var() { throw new ece351.util.Todo351Exception(); } // TODO
	// STUB2: void constant() { throw new ece351.util.Todo351Exception(); } // TODO
	void constant() { throw new ece351.util.Todo351Exception(); } // TODO

// TODO: 45 lines snipped

    // helper functions
    private boolean peekConstant() {
        final boolean result = lexer.inspect("'"); //constants start (and end) with single quote
    	return result;
    }

}

