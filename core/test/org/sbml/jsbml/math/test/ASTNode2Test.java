/*
 * $Id:  ASTNode2Test.java 10:06:57 AM victor-kofia $
 * $URL: ASTNode2Test.java $
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
package org.sbml.jsbml.math.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.ASTArithmeticOperatorNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTFactory;


/**
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Jun 29, 2014
 */
public class ASTNode2Test {

  /**
   * Test method for {@link org.sbml.jsbml.math.ASTFactory#reduceToBinary()}.
   */
  @Test
  public void testReduceToBinary() {
    ASTArithmeticOperatorNode plus = new ASTArithmeticOperatorNode(Type.PLUS);
    plus.addChild(new ASTCnIntegerNode(15));
    plus.addChild(new ASTCnIntegerNode(20));
    plus.addChild(new ASTCnIntegerNode(25));
    ASTFactory.reduceToBinary(plus);
    assertTrue(plus.getChildCount() == 2);
  }

}
