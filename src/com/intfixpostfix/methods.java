package com.intfixpostfix;
// Ivan Craddock

// CSCD 300
// Homework 4
// Written on version 1.8.8_15

import java.math.BigInteger;
import java.util.Stack;

public class methods {

	public static void calculate(String infix, BigInteger[] table) {
		infix = infix.replaceAll("\\s", "");
		// remove whitespace from incoming string
		System.out.println("Infix Expression:\t\t\t" + infix);
		char[] postfix = postLoader(infix);
		// method for loading char array with postfix expression
		System.out.print("Postfix Expression:\t\t\t");
		for (int i = 0; i < postfix.length; i++)
			// prints postfix expression from array
			System.out.print(postfix[i]);

		System.out.println();
		System.out.println("Expression after Postfix Evaluation:\t" + mathy(postfix, table) + "\n");

	}

	// method for loading infix string into postfix array
	private static char[] postLoader(String infix) {
		char[] postAra = new char[infix.length()];
		int pCounter = 0;// pCounter == number of times ')' and '(' characters
							// occur in string for removal from final array
		Stack<Character> inStack = new Stack<Character>();
		// stack for opperator placement
		int j = 0;// keeps track of place for opperator/opparand placement in
					// temp array
		for (int i = 0; i < infix.length(); i++) {
			char temp = infix.charAt(i);
			if (isOperand(temp)) {
				postAra[j] = temp;
				j++;
			} // loads opperands into temp array
			else {// method for tracking parenthesis
				if (inStack.empty())
					inStack.push(temp);
				else if (temp == ')') {
					while (inStack.peek() != '(') {
						postAra[j] = inStack.peek();
						inStack.pop();
						j++;
					}
					inStack.pop();
					pCounter += 2;
				} else if (stackPrec(temp) >= stackPrec(inStack.peek()))
					inStack.push(temp);
				else {// method for determining opperator priority on stack
					boolean done = false;
					while (inStack.size() > 0 && done == false) {
						done = true;
						if ((stackPrec(temp) < stackPrec(inStack.peek())) && temp != '(') {
							postAra[j] = inStack.peek();
							inStack.pop();
							j++;
							done = false;
						}
					}
					inStack.push(temp);
				}
			}
		}
		while (!inStack.empty()) {// pops remaining opperators from stack into
									// temp postfix array
			postAra[j] = inStack.peek();
			inStack.pop();
			j++;
		}
		char[] returnAra = new char[infix.length() - pCounter];
		for (int q = 0; q < returnAra.length; q++) {// coppies post fix
													// expression from temp
													// array to final array
			returnAra[q] = postAra[q];
		}
		return returnAra;
	}

	private static BigInteger mathy(char[] postfix, BigInteger[] table) throws ArithmeticException {// method
																									// for
																									// calculating
																									// postfix
																									// expressions
																									// of
																									// variables
																									// using
																									// a
																									// BigInteger
																									// array
																									// for
																									// variable
																									// reference
		Stack<BigInteger> calcStack = new Stack<BigInteger>();
		BigInteger stacker, i1, i2 = new BigInteger("0");// Big Integers used
															// for math
															// opperations
		for (int i = 0; i < postfix.length; i++) {// loads stack with contents
													// of array
			char temp = postfix[i];
			if (isOperand(temp)) {// loads opperand on stack
				stacker = table[temp - 'A'];
				calcStack.push(stacker);
			} else if (temp == '/') {// divids top two opperands on stack
				i1 = calcStack.peek();
				calcStack.pop();
				i2 = calcStack.peek();
				calcStack.pop();
				int pp = i1.intValue();
				if (pp == 0) {// throws exception if divide by zero is attempted
					System.out.println("\n\nError: Integer divide by zero\n\n");
					throw new ArithmeticException();
				}
				i1 = i2.divide(i1);
				calcStack.push(i1);
				// returns calculated value to stack
			} else if (temp == '+') {// adds two opperands on stack
				i1 = calcStack.peek();
				calcStack.pop();
				i2 = calcStack.peek();
				calcStack.pop();
				i1 = i2.add(i1);
				calcStack.push(i1);
				// returns calculated value to stack
			} else if (temp == '-') {// subtracts two opperands on stack
				i1 = calcStack.peek();
				calcStack.pop();
				i2 = calcStack.peek();
				calcStack.pop();
				i1 = i2.subtract(i1);
				calcStack.push(i1);
				// returns calculated value to stack

			} else if (temp == '*') {// multiplies two opperands on stack
				i1 = calcStack.peek();
				calcStack.pop();
				i2 = calcStack.peek();
				calcStack.pop();
				i1 = i2.multiply(i1);
				calcStack.push(i1);
				// returns calculated value to stack

			} else if (temp == '^') {// exponentiates two opperands on stack
				i1 = calcStack.peek();
				calcStack.pop();
				i2 = calcStack.peek();
				calcStack.pop();
				int qq = i1.intValue();
				if (qq > Integer.MAX_VALUE) {// throws exception if exponent is
												// too large
					System.out.println("\n\nError: Exponent large than MAX_VALUE\n\n");
					throw new ArithmeticException();
				} else if (qq < 0) {// throws exception if exponent is negative
					System.out.println("\n\nError: Negative exponent\n\n");
					throw new ArithmeticException();
				}
				i1 = i2.pow(qq);
				calcStack.push(i1);
				// returns calculated value to stack
			}
		}
		i2 = calcStack.peek();
		return i2;
	}

	private static int stackPrec(char cIn) {// method for determining opperator
											// priority on stack
		int res = -200;

		if (cIn == '(')
			res = 0;
		else if (cIn == ')')
			res = 99;
		else if (cIn == '^')
			res = 5;
		else if (cIn == '/')
			res = 4;
		else if (cIn == '*')
			res = 4;
		else if (cIn == '+')
			res = 2;
		else if (cIn == '-')
			res = 2;

		return res;
	}

	// Not used
	private static int inputPrec(char cIn) {
		// shared method for determining opperator priority

		int res = -200;

		if (cIn == '(')
			res = 100;
		else if (cIn == ')')
			res = 0;
		else if (cIn == '^')
			res = 6;
		else if (cIn == '/')
			res = 3;
		else if (cIn == '*')
			res = 3;
		else if (cIn == '+')
			res = 1;
		else if (cIn == '-')
			res = 1;

		return res;
	}

	private static boolean isOperand(char cIn) {// shared method for determining
												// opperand
		return (cIn >= 'A' && cIn <= 'Z');
	}

}