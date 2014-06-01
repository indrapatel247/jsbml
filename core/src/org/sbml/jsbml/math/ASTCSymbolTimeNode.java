/*
 * $Id:  ASTCSymbolTimeNode.java 2:04:33 PM victor-kofia $
 * $URL: https://svn.code.sf.net/p/jsbml/code/trunk/core/src/org/sbml/jsbml/math/ASTCSymbolTimeNode.java $
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.math;


/**
 * An Abstract Syntax Tree (AST) node representing time
 *  
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTCSymbolTimeNode extends ASTCiNumberNode implements
  ASTCSymbolNode {
  
  /**
   * The encodingURL of the MathML element associated with 
   * this ASTCiSymbolTimeNode
   */
  private String encodingURL;
  
  /**
   * Returns the definitionURL of the MathML element represented by 
   * this ASTCiSymbolTimeNode
   * 
   * @return String definitionURL
   */
  public String getDefinitionURL() {
    return this.definitionURL;
  }

  /**
   * Set the definitionURL of the MathML element represented by 
   * this ASTCiSymbolTimeNode
   * 
   * @param String definitionURL
   */
  public void setDefinitionURL(String definitionURL) {
    this.definitionURL = definitionURL;
  }
  
  /**
   * Returns the encodingURL of the MathML element represented by 
   * this ASTCiSymbolTimeNode
   * 
   * @return String encodingURL
   */
  public String getEncodingURL() {
    return this.encodingURL;
  }

  /**
   * Set the encodingURL of the MathML element represented by 
   * this ASTCiSymbolTimeNode
   * 
   * @param String encodingURL
   */
  public void setEncodingURL(String encodingURL) {
    this.encodingURL = encodingURL;
  }
 
}