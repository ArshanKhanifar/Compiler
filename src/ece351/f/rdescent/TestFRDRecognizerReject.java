package ece351.f.rdescent;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import ece351.TestPrelab;
import ece351.util.BaseTest351;
import ece351.util.CommandLine;
import ece351.util.TestInputs351;


@RunWith(Parameterized.class)
public final class TestFRDRecognizerReject extends BaseTest351 {

	private final File f;

	public TestFRDRecognizerReject(final File f) {
		this.f = f;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> files() {
		return TestInputs351.badFormulaFiles();
	}

	@Test
	public void accept() {
		final String inputSpec = f.getAbsolutePath();
		final CommandLine c = new CommandLine(inputSpec);
		final String input = c.readInputSpec();
		try {
			FRecursiveDescentRecognizer.main(input);
			fail("should have rejected but didn't:  " + inputSpec);
		} catch (final Exception e) {
			System.out.println("rejected, as expected:  " + inputSpec);
		}
	}


}
