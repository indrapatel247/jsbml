package org.sbml.jsbml.text.parser;

import java.io.InputStream;
import java.io.Reader;

import org.sbml.jsbml.ASTNode;

/**
 * An interface for the FormulaParser generated by JavaCC to parse infix math formula.
 * <p>
 * This allow users of jsbml to develop easily their own formula parser and pass it to the 
 * {@link ASTNode#parseFormula(String, IFormulaParser)} method.
 * 
 * @author rodrigue
 *
 */
public interface IFormulaParser {

	public void ReInit(Reader stream);
	
	// public void ReInit(FormulaParserTokenManager tm);
	
	public void ReInit(InputStream stream, String encoding);
	
	public void ReInit(InputStream stream);
	
	public ASTNode parse() throws Exception;
}
