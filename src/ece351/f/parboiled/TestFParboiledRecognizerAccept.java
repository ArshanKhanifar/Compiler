package ece351.f.parboiled;

import static org.junit.Assert.assertTrue;

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
public final class TestFParboiledRecognizerAccept extends BaseTest351 {

	private final File f;

	public TestFParboiledRecognizerAccept(final File f) {
		this.f = f;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> files() {
		return TestInputs351.formulaFiles();
	}

	@Test
	public void accept() {
		final String inputSpec = f.getAbsolutePath();
		final CommandLine c = new CommandLine(inputSpec);
		final String input = c.readInputSpec();
		FParboiledRecognizer.main(input);
		System.out.println("accepted, as expected:  " + inputSpec);
	}


}
