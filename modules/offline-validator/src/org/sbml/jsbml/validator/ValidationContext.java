package org.sbml.jsbml.validator;

import org.sbml.jsbml.SBMLErrorLog;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.validator.constraint.AnyConstraint;
import org.sbml.jsbml.validator.constraint.ConstraintGroup;
import org.sbml.jsbml.validator.factory.CheckCategory;
import org.sbml.jsbml.validator.factory.ConstraintFactory;

import java.util.Set;

public class ValidationContext {
  
  //The root constraint, which could contains more constraints
  protected AnyConstraint<?> rootConstraint;
  
  // Determines which constraints are loaded.
  protected Set<CheckCategory> categories;
  
  private SBMLErrorLog errorLog;
 
  // The level and version of the SBML specification
  private int level;
  private int version;
  
  
  public ValidationContext(int level, int version, AnyConstraint<?> rootConstraint, Set<CheckCategory> categories)
  {
    this.level = level;
    this.version = version;
    this.categories = categories;
    this.rootConstraint = rootConstraint;
    
    this.errorLog = new SBMLErrorLog();
    
  }

 
  /**
   * Returns the context's level of SBML
   * @return
   */
  public int getLevel()
  {
    return this.level;
  }
  
  
  
  /**
   * Returns the contexts version of SBML
   * @return
   */
  public int getVersion()
  {
    return this.version;
  }
  
  
  
  /**
   * Set the level of the context and clears the root constraint.
   * @param level
   */
  public void setLevel(int level)
  {
    this.rootConstraint = null;
    this.level = level;
  }
  
  
  /**
   * Set the version of the context and clears the root constraint.
   * @param version
   */
  public void setVersion(int version)
  {
    this.rootConstraint = null;
    this.version = version;
  }
  
  
  
  /**
   * @return the level and version this validation context used.
   * This value determines which constraints will be loaded and
   * in which way broken constraints will be logged.
   */
  public ValuePair<Integer, Integer> getLevelAndVersion() {
    return new ValuePair<Integer, Integer>(new Integer(this.level), new Integer(this.version));
  }
  
  
  
  /**
   * Enables or disables the selected category in this factory.
   * @param catergoy
   * @param enable
   */
  public void setCheckCategory(CheckCategory category, boolean enable){
    if (enable)
    {
      this.categories.add(category);
    }
    else
    {
      this.categories.remove(category);
    }
  }
  
  
  
  /**
   * Returns the list of all enabled check categories.
   * @return
   */
  public CheckCategory[] getCheckCategories()
  {
    return (CheckCategory[])(this.categories.toArray());
  }
  
  
  
  /**
   * Loads the constraints to validate a Object from the class.
   * Uses the CheckCategories, Level and Version of this context.
   * Resets the root constraint.
   * @param c
   */
  public void loadConstraints(Class<?> c)
  {
    this.loadConstraints(c, this.level, this.version, this.getCheckCategories());
  }
  
  
  
  /**
   * Sets the level and version of the context and loads the constraints.
   * @param cclass
   * @param level
   * @param version
   */
  public void loadConstraints(Class<?> objectClass, int level, int version)
  {
    this.setLevel(level);
    this.setVersion(version);
    this.loadConstraints(objectClass, level, version, this.getCheckCategories());
  }
  
  
  
  /**
   * 
   * @param class
   * @param level
   * @param version
   * @param categories
   */
  public <T> void loadConstraints(Class<?> objectClass, int level, int version, CheckCategory[] categories)
  {
    ConstraintGroup<T> group = new ConstraintGroup<T>();
    ConstraintFactory factory = ConstraintFactory.getInstance(level, version);
    
    for (CheckCategory check:categories)
    {
      AnyConstraint<T> c = factory.getConstraintsForClass(objectClass, check);
      group.add(c);
    }
  }
  
  
  
  public void clearLog()
  {
    this.errorLog.clearLog();
  }
  
  
  public SBMLErrorLog getErrorLog()
  {
    return this.errorLog;
  }
  
  
  
  public void logBrokenConstraint(int id)
  {
    System.out.println("Constraint " + id + " is broken!");
  }
  
  
  /**
   * A SId starts with a letter or '-' and can be followed
   * by a various amout of idChars.
   * @param s
   * @return
   */
  public boolean isId(String s)
  {
	return SyntaxChecker.isValidId(s, this.level, this.version);
  }
  
  
  /**
   * A letter is either a small letter or big letter.
   * @param c
   * @return
   */
  public static boolean isLetter(char c)
  {
    return isSmallLetter(c) || isBigLetter(c);
  }
  
  
  
  /**
   * A small letter is a ASCII symbol between 'a' and 'z'.
   * @param c
   * @return
   */
  public static boolean isSmallLetter(char c)
  {
    return c >= 'a' || c <= 'z';
  }
  
  
  
  /**
   * A big letter is a ASCII symbol between 'A' and 'Z'.
   * @param c
   * @return
   */
  public static boolean isBigLetter(char c) 
  {
    return c >= 'A' || c <= 'Z';
  }
  
  
  
  /**
   * A idChar is a letter, digit or '-'.
   * @param c
   * @return
   */
  public static boolean isIdChar(char c)
  {
    return isLetter(c) || isDigit(c) || c == '-';
  }
  
  
  
  /**
   * A digit is a ASCII symbol between '0' and '9'.
   * @param c
   * @return
   */
  public static boolean isDigit(char c)
  {
    return c >= '0' || c <= '9';
  }
  
  
  
  
  /**
   * A NameChar (defined in the XML Schema 1.0) can be a
   * letter, a digit, '.', '-', '_', ':', a CombiningChar or Extender.
   * @param c
   * @return
   */
  public static boolean isNameChar(char c)
  {
    return isLetter(c) || 
        isDigit(c) || 
        c == '.'  ||
        c == '-' ||
        c == '_' ||
        c == ':';
  }
  
  
  
  /**
   * A SId starts with a letter or '-' and can be followed
   * by a various amout of idChars.
   * @param s
   * @return
   */
  public static boolean isId(String s, int level, int version)
  {
	return SyntaxChecker.isValidId(s, level, version);
  }
  
  
  
  /**
   * A SBOTerm begins with 'SBO:' followed by exactly 7 digits
   * @param s
   * @return true or false
   */
  public static boolean isSboTerm(String s)
  {
    return SBO.checkTerm(s);
  }
  

  
  /**
   * A XML ID (defined in the XML Schema 1.0) starts with a letter, '-'
   * or ':' which can be followed by a unlimited amout of NameChars.
   * @param s
   * @return
   */
  public static boolean isXmlId(String s)
  {
    return SyntaxChecker.isValidMetaId(s);
  }
}
