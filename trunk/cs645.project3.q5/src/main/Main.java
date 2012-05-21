package main;

import java.io.*;
import java.util.*;

public class Main {

	static MultiplePrintStream log;
	
	/**
	 * Run OTP break attempts
	 * @param args
	 */
	
	public static void main(String[] args) throws Exception
	{		
		log = new MultiplePrintStream(System.out,
				new PrintStream(new File(
						word.replaceAll("[^\\w']", "_") + "_" + Utils.getLogPath()
						)));
		
		// read files and convert to byte arrays
		String[] contents = readFiles(Constants.CT);
		byte[][] bytes = new byte[contents.length][];
		for (int i = 0; i < contents.length; i++)
		{
			bytes[i] = strToBytes(contents[i]);
		}

		// print out xor combinations
		byte[] x;
		int i, j;
		/*
		int n = contents.length;
		for (i = 0; i < n; i++)
		{
			for (j = (i + 1); j < n; j++)
			{
				x = xor(bytes[i],bytes[j]);
				log.println("> CT " + (i + 1) + " xor " + (j + 1) + ":");
				log.println("-------------");
				log.println(toAsciiString(x));
				log.println("-------------");
			}
			log.println("===============================================");
			log.println();
		}
		 */

		// run XOR experiments of p1 XOR p2 XOR <some word>
		// the pairs:
		// 0, 3
		// 1, 5
		// 2, 4
		i = 1;
		j = 5;
		x = xor(bytes[i],bytes[j]);
		log.println("> CT " + i + " xor " + j + ":");
		log.println("-------------");

		Map<Integer,String> fixed = new HashMap<Integer, String>();
		//fixed.put(41," of them live ");

		log.println("XOR results with the word '" + word + "':");
		xorWithWord(x, word, fixed);
		log.println();
		log.println();
		
		/*
		String s = "";
		byte[] b = s.getBytes();
		log.println(toShortAsciiString(b));
		log.println(toShortAsciiString(xor(x,b)));
		*/
	}
	
	public static String word = " ";

	public static boolean readable(char c)
	{
		int i = (int) c;
		return (
				i == 9 || // tab
				i == 10 || // newline
				(32 <= i && i <= 126)
				);
	}
	public static char block = (char) 176;


	// ========================================================================
	// ========================================================================


	/**
	 * Read the file and return its content.
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readFile(File file) throws IOException
	{
		Scanner scan = new Scanner(new FileReader(file));
		String res = "";
		// read file content
		while (scan.hasNext())
		{
			res += scan.nextLine() + "\n";
		}
		scan.close();
		return res;
	}

	/**
	 * Reads the files in the given directory path and return an array of
	 * their content.
	 * @param dirPath
	 * @return
	 * @throws IOException
	 */
	public static String[] readFiles(String dirPath) throws IOException
	{
		File[] files = new File(dirPath).listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File arg0, String arg1) {
				return !arg1.startsWith(".");
			}
		});
		String[] contents = new String[files.length];
		for (int i = 0; i < files.length; i++)
		{
			contents[i] = readFile(files[i]);
		}
		return contents;
	}

	/**
	 * Reads the input string as HEX tokens and returns them as an array
	 * of bytes.
	 * @param src
	 * @return
	 */
	public static byte[] strToBytes(String src)
	{
		Scanner scan = new Scanner(new StringReader(src));
		List<Byte> byteList = new ArrayList<Byte>();

		// read tokens and convert to bytes
		String token;
		byte b;
		while (scan.hasNext())
		{
			token = scan.next();
			b = (byte) Integer.parseInt(token.substring(2,token.length()),16);
			byteList.add(b);
		}
		byte[] res = new byte[byteList.size()];
		for (int i = 0; i < res.length; i++)
		{
			res[i] = byteList.get(i).byteValue();
		}
		return res;
	}

	/**
	 * XORs the two input bytes and returns their value
	 * @param a
	 * @param b
	 * @return
	 */
	public static byte[] xor(byte[] a, byte[] b)
	{
		assert(a.length == b.length);
		byte[] res = new byte[a.length];
		for (int i = 0; i < a.length; i++)
		{
			res[i] = (byte) (a[i] ^ b[i]);
		}
		return res;
	}


	/**
	 * Prints an array of bytes to the screen as hex
	 * @param a
	 * @return
	 */
	public static String toString(byte[] a)
	{
		String res = "";
		for (int i = 0; i < a.length; i++)
		{
			res += String.format("%02x ", a[i]);
			if ((i + 1) % 16 == 0)
				res += "\n";
		}
		res += "\n";
		return res;
	}

	public static String toString2(byte[] a)
	{
		String res = "";
		for (int i = 0; i < a.length; i++)
		{
			res += String.format("0x%02x\n", a[i]);
		}
		return res;
	}

	/**
	 * Prints an array of bytes to the screen as characters
	 * by their ascii codes.
	 * if the ascii code is not a readable character, puts a block instead.
	 * @param a
	 * @return
	 */
	public static String toAsciiString(byte[] a)
	{
		String res = "";
		char c;
		for (int i = 0; i < a.length; i++)
		{
			c = (char) a[i];
			if (readable(c))
			{
				if (c == '\t')
					res += "\\t  ";
				else if (c == '\n')
					res += "\\n  ";
				else if (c == '\r')
					res += "\\r  ";
				else
					res += (char) a[i] + "   ";
			}
			else
				res += block + "   ";
		}
		return res;
	}
	
	public static String toShortAsciiString(byte[] a)
	{
		String res = "";
		char c;
		for (int i = 0; i < a.length; i++)
		{
			c = (char) a[i];
			if (readable(c))
			{
				if (c == '\t')
					res += "\\t";
				else if (c == '\n')
					res += "\\n";
				else if (c == '\r')
					res += "\\r";
				else
					res += (char) a[i];
			}
			else
				res += block;
		}
		return res;
	}

	public static String toBitString(byte[] a)
	{
		String res = "";
		int n = 16;
		byte b;
		for (int i = 0; i < a.length; i++)
		{
			if (i % n == 0)
			{
				for (int j = 0; j < n; j++)
				{
					res += String.format("%03d      ", j + i);
				}
				res += "\n";
			}
			b = a[i];
			for (int j = 0; j < 8; j++)
			{
				res += (b & 1) == 1 ? "1" : "0";
				b >>= 1;
			}
			if ((i + 1) % n == 0)
				res += "\n";
			else
				res += " ";
		}
		return res;
	}

	/**
	 * XORs the given array with arrays constructed of copies of the given
	 * word with leading zeros. For instance, for the word 'hello', the following
	 * arrays will be XORed with:
	 * hellohello...
	 * 0hellohell...
	 * 00hellohel...
	 * 000hellohe...
	 * 0000helloh...
	 * 
	 * The given fixed mapping is for fixing letters at certain positions (to accumulate
	 * results of previous experiments).
	 * 
	 * @param a
	 * @param word
	 * @return
	 */
	public static byte[][] xorWithWord(byte[] a, String word, Map<Integer,String> fixed)
	{
		int len = word.length();
		byte[] w;
		int j;
		byte[][] res = new byte[len][];
		byte[] wordByte = word.getBytes();
		int lead = 0;
		byte[] fixedWordBytes;

		// try out XORing a with a sequence of concatenated copies of
		// the word
		for (int i = 0; i < len; i++)
		{
			// create the array to xor with
			w = new byte[a.length];
			j = 0;
			// add leading zeros
			for (; j < lead; j++)
			{
				w[j] = 0;
			}
			// add copies of the word
			while (j < w.length)
			{
				w[j] = wordByte[(j - lead) % len];
				j++;
			}
			// override fixed words
			for (Integer start: fixed.keySet())
			{
				fixedWordBytes = fixed.get(start).getBytes();
				for (j = 0; j < fixedWordBytes.length; j++)
				{
					w[start + j] = fixedWordBytes[j];
				}
			}

			// xor
			res[i] = xor(a,w);
			log.print("> POS: ");
			for (j = 0; j < a.length; j++)
				log.print(String.format("%03d ", j));
			log.println();
			log.println("> ARR: " + toAsciiString(a));
			log.println("> WRD: " + toAsciiString(w));
			log.println("> XOR: " + toAsciiString(res[i]));
			log.println("> ARR (bits):\n" + toBitString(a));
			log.println("> WRD (bits):\n" + toBitString(w));
			log.println("> XOR (bits):\n" + toBitString(res[i]));
			log.println();

			// advance leading zeros
			lead++;
		}

		return res;
	}
}





