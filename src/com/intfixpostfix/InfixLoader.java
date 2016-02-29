package com.intfixpostfix;

//Ivan Craddock
//CSCD 300
//Homework 4
//Written on version 1.8.8_15  
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class InfixLoader {// shared class for testing postfix conversion methods

	public static void main(String[] args) {

		String fileName = "infix-test.txt";// filename as per instructions

		try {// try catch loop for IO
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// objects for use in copying file contents
			BigInteger[] symbols = new BigInteger[26];
			// array for variable reference
			boolean symTable = true;
			boolean infixData = false;

			System.out.println("\n Processing symbol table\n");

			while ((strLine = br.readLine()) != null) {
				// ends loop at end of infile

				strLine = strLine.trim();
				if (strLine.length() == 0) // ignores blank lines
					continue;

				if (strLine.startsWith("#")) {
					// signals loop termination at pound sign
					if (!infixData) {
						System.out.println("\n Processing infix Expressions\n");
						symTable = false;
						infixData = true;
					} // end iner if

					continue;
				}
				if (symTable) {
					String delims = "[ ]+";// removes whitespace
					String[] tokens = strLine.split(delims);
					System.out.println("\n Storing Symbol [" + tokens[0] + "]\n");
					int index = tokens[0].charAt(0) - 'A';
					// assigns variable references alphabetically in array

					symbols[index] = new BigInteger(tokens[1]);
				}

				if (infixData) {// outputs postfix conversions until end of file
					while (strLine.compareTo("#") != 0) {
						methods.calculate(strLine, symbols);
						// calls method from outside file
						strLine = br.readLine();// reads string from next line
					}
				}
			}

			in.close();// closes infile

		} catch (Exception e) {// prints stack trace from exception
			e.printStackTrace();
		}

	}
}// end class
