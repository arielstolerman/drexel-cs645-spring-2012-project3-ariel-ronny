package main;

import java.io.PrintStream;

/**
 * A class for handling multiple print streams, e.g. System.out + logging to file.
 * 
 * @author Ariel Stolerman
 *
 */
public class MultiplePrintStream {
	
	protected PrintStream[] psArr;
	
	public MultiplePrintStream(PrintStream... ps) {
		this.psArr = new PrintStream[ps.length];
		for (int i = 0; i < ps.length; i++)
			this.psArr[i] = ps[i];
	}
	
	public void println() {
		for (PrintStream ps: psArr)
			if (ps != null)
				ps.println();
	}
	
	public void println(Object o) {
		for (PrintStream ps: psArr)
			if (ps != null)
				ps.println(o);
	}
	
	public void println(String s) {
		for (PrintStream ps: psArr)
			if (ps != null)
				ps.println(s);
	}
	
	public void println(String s, int maxTokensPerLine) {
		String[] tokens = s.split("\\s+");
		String modified = "";
		for (int i = 0; i < tokens.length; i++) {
			modified += tokens[i] + " ";
			if ((i + 1) % maxTokensPerLine == 0)
				modified += "\n";
		}
		println(modified);
	}

	public void print(String s) {
		for (PrintStream ps: psArr)
			if (ps != null)
				ps.print(s);
	}
	
	public void printf(String s, Object... args) {
		for (PrintStream ps: psArr)
			if (ps != null)
				ps.printf(s, args);
	}
}
