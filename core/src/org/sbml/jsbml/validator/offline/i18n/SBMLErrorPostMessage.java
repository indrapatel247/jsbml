/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.validator.offline.i18n;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Contains the post messages for each {@link SBMLError} in the English language.
 * 
 * <p>'post' mean that this is added at the end of the generic error message defined in the {@link SBMLErrorMessage} bundle.
 * This part of the error has, in general, several parameters to be able to customize the message to provide some information
 * about which element contained the error and why the error was raised.</p>
 * 
 * <p>The key for each post message is the integer defined for each {@link SBMLError} in {@link SBMLErrorCodes}.</p>
 * 
 * @see ResourceBundle
 * @author rodrigue
 * @since 1.3
 */
public class SBMLErrorPostMessage extends ResourceBundle {  


  
  /**
   * 
   */
  private static final Map<String, String> contents = new HashMap<String, String>();
  
  static {
      
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10214), "The formula ''{0}'' in the math element of the {1} uses ''{2}'' which is not a function definition id.");
      contents.put(Integer.toString(SBMLErrorCodes.CORE_10215), "The formula ''{0}'' in the math element of the {1} uses ''{2}'' which is not a function definition id.");
      
  }

  @Override
  protected Object handleGetObject(String key) {

    return contents.get(key);
  }

  @Override
  public Enumeration<String> getKeys() {
    
    return java.util.Collections.enumeration(contents.keySet());
  }

}
