/* Generated By:JavaCC: Do not edit this line. FormulaParser.java */
/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2016 jointly by the following organizations:
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
package org.sbml.jsbml.text.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.resources.Resource;

/**
 * Class used to parse infix mathematical formula and returns a representation of it as an Abstract Syntax Tree (AST).
 * <p>
 * Support almost the same syntax as defined in <a href="http://sbml.org/Software/libSBML/docs/java-api/org/sbml/libsbml/libsbml.html#parseFormula(java.lang.String)">
 * the LibSBML C-inspired infix notation taken from SBML Level 1 parser</a>.
 *
 * @author Alexander D&ouml;rr
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class FormulaParser implements IFormulaParser, FormulaParserConstants {
  public static Properties stringToType = new Properties();

  static
  {
    String path = "cfg/ASTNodeTokens.xml";
    try
    {
      stringToType.loadFromXML(Resource.class.getResourceAsStream(path));
    }
    catch (InvalidPropertiesFormatException e)
    {
      throw new RuntimeException("Invalid configuration file entries in file " + Resource.class.getResource(path), e);
    }
    catch (IOException e)
    {
      throw new RuntimeException("Could not read configuration file " + Resource.class.getResource(path), e);
    }
  }

  private void checkSize(ArrayList < ASTNode > arguments, int i) throws ParseException
  {
    if (arguments.size() > i)
    {
      throw new ParseException();
    }
  }

  private Integer getInteger(ASTNode node)
  {
    if (node.isUMinus())
    {
      if (node.getChild(0).isInteger())
      {
        return - node.getChild(0).getInteger();
      }
      else
      {
        return null;
      }
    }
    else
    {
      if (node.isInteger())
      {
        return node.getInteger();
      }
      else
      {
        return null;
      }
    }
  }

  /**
   * Returns a piecewise {@link ASTNode} representing the modulo operation between the left and right child given.
   *
   * <p/> The formula produced for 'a % b' or modulo(a, b) is 'piecewise(floor(a/b), gt(a/b, 0), ceil(a/b))'
   *
   * @param leftChild
   * @param rightChild
   * @return a piecewise {@link ASTNode} representing the modulo operation between the left and right child given.
   * @see "http://sbml.org/Documents/FAQ#Why_can.27t_I_use_the_.3Crem.3E_operator_in_SBML_MathML.3F"
   */
  private ASTNode createModulo(ASTNode leftChild, ASTNode rightChild)
  {
    ASTNode node = new ASTNode(ASTNode.Type.FUNCTION_PIECEWISE);

    ASTNode floorNode = new ASTNode(ASTNode.Type.FUNCTION_FLOOR);
    ASTNode aDividedByB = new ASTNode(ASTNode.Type.DIVIDE);
    aDividedByB.addChild(leftChild);
    aDividedByB.addChild(rightChild);

    floorNode.addChild(aDividedByB);
    node.addChild(floorNode);

    ASTNode greaterThan = new ASTNode(ASTNode.Type.RELATIONAL_GT);
    greaterThan.addChild(aDividedByB.clone());
    greaterThan.addChild(new ASTNode(0));

    node.addChild(greaterThan);

    ASTNode ceilNode = new ASTNode(ASTNode.Type.FUNCTION_CEILING);
    ceilNode.addChild(aDividedByB.clone());

    node.addChild(ceilNode);

    return node;
  }

  final public Token string() throws ParseException {
    Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LOG:
      t = jj_consume_token(LOG);
      break;
    case STRING:
      t = jj_consume_token(STRING);
      break;
    default:
      jj_la1[0] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) {
      return t;
    }}
    throw new Error("Missing return statement in function");
  }

  @Override
  final public ASTNode parse() throws ParseException {
    ASTNode node = null;
    node = Expression();
    {if (true) {
      return node;
    }}
    throw new Error("Missing return statement in function");
  }

  final private ASTNode Expression() throws ParseException {
    ASTNode value = null;
    value = TermLvl1();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 0:
      jj_consume_token(0);
      break;
    case EOL:
      jj_consume_token(EOL);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) {
      return value;
    }}
    throw new Error("Missing return statement in function");
  }

  final private ASTNode TermLvl3() throws ParseException {
    ASTNode rightChild;
    ASTNode leftChild;
    ASTNode node = null;
    leftChild = Primary();
    label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case POWER:
        case FACTORIAL:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_1;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case POWER:
          jj_consume_token(POWER);
          rightChild = Primary();
          node = new ASTNode(Type.POWER);
          node.addChild(leftChild);
          node.addChild(rightChild);
          leftChild = node;
          break;
        case FACTORIAL:
          jj_consume_token(FACTORIAL);
          node = new ASTNode(Type.FUNCTION_FACTORIAL);
          node.addChild(leftChild);
          leftChild = node;
          break;
        default:
          jj_la1[3] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    {if (true) {
      return leftChild;
    }}
    throw new Error("Missing return statement in function");
  }

  final private ASTNode TermLvl2() throws ParseException {
    ASTNode rightChild;
    ASTNode leftChild;
    ASTNode node = null;
    Token t;
    Type type = null;
    leftChild = TermLvl3();
    label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case TIMES:
        case DIVIDE:
        case MODULO:
          ;
          break;
        default:
          jj_la1[4] = jj_gen;
          break label_2;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case TIMES:
          jj_consume_token(TIMES);
          rightChild = TermLvl3();
          node = new ASTNode('*');
          node.addChild(leftChild);
          node.addChild(rightChild);
          leftChild = node;
          break;
        case DIVIDE:
          jj_consume_token(DIVIDE);
          rightChild = TermLvl3();
          Integer left, right;
          left = getInteger(leftChild);
          right = getInteger(rightChild);
          if (left != null && right != null)
          {
            node = new ASTNode(Type.RATIONAL);
            node.setValue(left, right);
            leftChild = node;
          }
          else
          {
            node = new ASTNode('/');
            node.addChild(leftChild);
            node.addChild(rightChild);
            leftChild = node;
          }
          break;
        case MODULO:
          jj_consume_token(MODULO);
          rightChild = TermLvl3();
          node = createModulo(leftChild, rightChild);
          leftChild = node;
          break;
        default:
          jj_la1[5] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    {if (true) {
      return leftChild;
    }}
    throw new Error("Missing return statement in function");
  }

  final private ASTNode TermLvl1() throws ParseException {
    ASTNode rightChild = null;
    ASTNode leftChild;
    ASTNode node = null;
    Token t;
    Type type = null;
    String s;
    leftChild = TermLvl2();
    label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
        case MINUS:
        case COMPARISON:
        case BOOLEAN_LOGIC:
          ;
          break;
        default:
          jj_la1[6] = jj_gen;
          break label_3;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PLUS:
          jj_consume_token(PLUS);
          rightChild = TermLvl2();
          node = new ASTNode('+');
          node.addChild(leftChild);
          node.addChild(rightChild);
          leftChild = node;
          break;
        case MINUS:
          jj_consume_token(MINUS);
          rightChild = TermLvl2();
          node = new ASTNode('-');
          node.addChild(leftChild);
          node.addChild(rightChild);
          leftChild = node;
          break;
        case BOOLEAN_LOGIC:
          t = jj_consume_token(BOOLEAN_LOGIC);
          rightChild = TermLvl2();
          s = t.image;

          if (s.equalsIgnoreCase("or") || s.equals("||"))
          {
            type = ASTNode.Type.LOGICAL_OR;
          }
          else if (s.equalsIgnoreCase("and") || s.equals("&&"))
          {
            type = ASTNode.Type.LOGICAL_AND;
          }
          else if (s.equalsIgnoreCase("xor"))
          {
            type = ASTNode.Type.LOGICAL_XOR;
          }
          node = new ASTNode(type);
          node.addChild(leftChild);
          node.addChild(rightChild);
          leftChild = node;
          break;
        case COMPARISON:
          t = jj_consume_token(COMPARISON);
          rightChild = TermLvl2();
          s = t.image;
          if (s.equalsIgnoreCase("<"))
          {
            type = ASTNode.Type.RELATIONAL_LT;
          }
          else if (s.equalsIgnoreCase(">"))
          {
            type = ASTNode.Type.RELATIONAL_GT;
          }
          else if (s.equalsIgnoreCase("=="))
          {
            type = ASTNode.Type.RELATIONAL_EQ;
          }
          else if (s.equalsIgnoreCase("!="))
          {
            type = ASTNode.Type.RELATIONAL_NEQ;
          }
          else if (s.equalsIgnoreCase(">="))
          {
            type = ASTNode.Type.RELATIONAL_GEQ;
          }
          else if (s.equalsIgnoreCase("<="))
          {
            type = ASTNode.Type.RELATIONAL_LEQ;
          }
          node = new ASTNode(type);
          node.addChild(leftChild);
          node.addChild(rightChild);
          leftChild = node;
          break;
        default:
          jj_la1[7] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    {if (true) {
      return leftChild;
    }}
    throw new Error("Missing return statement in function");
  }

  final private ASTNode Primary() throws ParseException, NumberFormatException {
    Token t;
    double d;
    int i;
    ASTNode node = null;
    ASTNode vector = new ASTNode();
    ASTNode child, furtherChild;
    String s;
    String vals [ ];
    ArrayList < ASTNode > arguments = new ArrayList < ASTNode > ();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER:
      t = jj_consume_token(INTEGER);
      i = Integer.parseInt(t.image);
      node = new ASTNode(Type.INTEGER);
      node.setValue(i);
      {if (true) {
      return node;
    }}
      break;
    case NUMBER:
      t = jj_consume_token(NUMBER);
      d = Double.parseDouble(t.image);
      node = new ASTNode(Type.REAL);
      node.setValue(d);
      {if (true) {
      return node;
    }}
      break;
    case EXPNUMBER:
      t = jj_consume_token(EXPNUMBER);
      s = t.image;
      vals = s.toLowerCase().split("e");
      if (vals [ 1 ].startsWith("+"))
      {
        i = Integer.parseInt(vals [ 1 ].substring(1));
      }
      else
      {
        i = Integer.parseInt(vals [ 1 ]);
      }
      node = new ASTNode(Type.REAL_E);
      node.setValue(Double.parseDouble(vals [ 0 ]), i);
      {if (true) {
      return node;
    }}
      break;
    default:
      jj_la1[12] = jj_gen;
      if (jj_2_1(2)) {
        t = string();
        jj_consume_token(OPEN_PAR);
        child = TermLvl1();
        label_4:
          while (true) {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case SLPITTER:
              ;
              break;
            default:
              jj_la1[8] = jj_gen;
              break label_4;
            }
            jj_consume_token(SLPITTER);
            furtherChild = TermLvl1();
            arguments.add(furtherChild);
          }
        jj_consume_token(CLOSE_PAR);
        s = t.image;
        Type type = null;

        if (stringToType.containsKey(s.toLowerCase()))
        {
          type = ASTNode.Type.valueOf(stringToType.getProperty(s.toLowerCase()).toUpperCase());
        }
        if (s.equalsIgnoreCase("abs"))
        {
          type = Type.FUNCTION_ABS;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("factorial"))
        {
          checkSize(arguments, 1);
          type = Type.FUNCTION_FACTORIAL;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("eq"))
        {
          checkSize(arguments, 1);
          type = Type.VECTOR;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("pow"))
        {
          checkSize(arguments, 1);
          type = Type.FUNCTION_POWER;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("sqr"))
        {
          checkSize(arguments, 0);
          type = Type.FUNCTION_EXP;
          node = new ASTNode(type);
          node.addChild(child);
          node.addChild(new ASTNode(2));
        }
        else if (s.equalsIgnoreCase("sqrt"))
        {
          checkSize(arguments, 0);
          type = Type.FUNCTION_ROOT;
          node = new ASTNode(type);
          node.addChild(new ASTNode(2));
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("not"))
        {
          checkSize(arguments, 0);
          type = Type.LOGICAL_NOT;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("ln"))
        {
          checkSize(arguments, 0);
          type = Type.FUNCTION_LN;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("lt"))
        {
          checkSize(arguments, 0);
          type = Type.RELATIONAL_LT;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("lambda"))
        {
          type = Type.LAMBDA;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("piecewise"))
        {
          type = Type.FUNCTION_PIECEWISE;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else if (s.equalsIgnoreCase("selector"))
        {
          type = Type.FUNCTION_SELECTOR;
          node = new ASTNode(type);
          node.addChild(child);
        }
        else
        {
          node = new ASTNode(Type.FUNCTION);
          node.addChild(child);
        }

        if (type == null)
        {
          node = new ASTNode();
          node.setName(s);
        }
        for (ASTNode argument : arguments)
        {
          node.addChild(argument);
        }
        {if (true) {
      return node;
    }}
      } else if (jj_2_2(4)) {
        t = jj_consume_token(STRING);
        ASTNode selector = new ASTNode();
        selector.setType(ASTNode.Type.FUNCTION_SELECTOR);
        selector.addChild(new ASTNode(t.image));
        label_5:
          while (true) {
            jj_consume_token(LEFT_BRACKET);
            node = TermLvl1();
            selector.addChild(node);
            jj_consume_token(RIGHT_BRACKET);
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case LEFT_BRACKET:
              ;
              break;
            default:
              jj_la1[9] = jj_gen;
              break label_5;
            }
          }
        {if (true) {
    return selector;
  }}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case OPEN_PAR:
          jj_consume_token(OPEN_PAR);
          node = TermLvl1();
          jj_consume_token(CLOSE_PAR);
          {if (true) {
      return node;
    }}
          break;
        default:
          jj_la1[13] = jj_gen;
          if (jj_2_3(2)) {
            jj_consume_token(LEFT_BRACES);
            node = TermLvl1();
            ASTNode selector = new ASTNode();
            boolean isSelector = false;
            selector.setType(ASTNode.Type.FUNCTION_SELECTOR);
            vector.setType(ASTNode.Type.VECTOR);
            vector.addChild(node);
            label_6:
              while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case SLPITTER:
                  ;
                  break;
                default:
                  jj_la1[10] = jj_gen;
                  break label_6;
                }
                jj_consume_token(SLPITTER);
                node = TermLvl1();
                vector.addChild(node);
              }
            jj_consume_token(RIGHT_BRACES);
            selector.addChild(vector);
            label_7:
              while (true) {
                switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
                case LEFT_BRACKET:
                  ;
                  break;
                default:
                  jj_la1[11] = jj_gen;
                  break label_7;
                }
                jj_consume_token(LEFT_BRACKET);
                node = TermLvl1();
                isSelector = true;
                selector.addChild(node);
                jj_consume_token(RIGHT_BRACKET);
              }
            {if (true) {
    return isSelector ? selector : vector;
  }}
          } else {
            switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
            case LEFT_BRACES:
              jj_consume_token(LEFT_BRACES);
              vector.setType(ASTNode.Type.VECTOR);
              jj_consume_token(RIGHT_BRACES);
              {if (true) {
      return vector;
    }}
              break;
            case MINUS:
              jj_consume_token(MINUS);
              node = Primary();
              ASTNode uiMinus = new ASTNode('-');
              uiMinus.addChild(node);
              {if (true) {
      return uiMinus;
    }}
              break;
            case NOT:
              jj_consume_token(NOT);
              node = TermLvl1();
              ASTNode not = new ASTNode(Type.LOGICAL_NOT);
              not.addChild(node);
              {if (true) {
      return not;
    }}
              break;
            case LOG:
              jj_consume_token(LOG);
              child = Primary();
              node = new ASTNode(Type.FUNCTION_LN);
              node.addChild(child);
              {if (true) {
      return node;
    }}
              break;
            case STRING:
              t = jj_consume_token(STRING);
              s = t.image;
              if (s.equalsIgnoreCase("true"))
              {
                node = new ASTNode(Type.CONSTANT_TRUE);
              }
              else if (s.equalsIgnoreCase("false"))
              {
                node = new ASTNode(Type.CONSTANT_FALSE);
              }
              else if (s.equalsIgnoreCase("pi"))
              {
                node = new ASTNode(Type.CONSTANT_PI);
              }
              else if (s.equalsIgnoreCase("avogadro"))
              {
                node = new ASTNode(Type.NAME_AVOGADRO);
              }
              else if (s.equalsIgnoreCase("time"))
              {
                node = new ASTNode(Type.NAME_TIME);
              }
              else if (s.equalsIgnoreCase("exponentiale"))
              {
                node = new ASTNode(Type.CONSTANT_E);
              }
              else if (s.equalsIgnoreCase("-infinity"))
              {
                node = new ASTNode(Double.NEGATIVE_INFINITY);
              }
              else if (s.equalsIgnoreCase("infinity"))
              {
                node = new ASTNode(Double.POSITIVE_INFINITY);
              }
              else
              {
                node = new ASTNode(s);
              }
              {if (true) {
      return node;
    }}
              break;
            default:
              jj_la1[14] = jj_gen;
              jj_consume_token(-1);
              throw new ParseException();
            }
          }
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  private boolean jj_3R_12() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_15()) {
      jj_scanpos = xsp;
      if (jj_3R_16()) {
        jj_scanpos = xsp;
        if (jj_3R_17()) {
          jj_scanpos = xsp;
          if (jj_3R_18()) {
      return true;
    }
        }
      }
    }
    return false;
  }

  private boolean jj_3R_15() {
    if (jj_scan_token(PLUS)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_14() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_21()) {
      jj_scanpos = xsp;
      if (jj_3R_22()) {
        jj_scanpos = xsp;
        if (jj_3R_23()) {
      return true;
    }
      }
    }
    return false;
  }

  private boolean jj_3R_21() {
    if (jj_scan_token(TIMES)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_25() {
    if (jj_scan_token(NUMBER)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_28() {
    if (jj_scan_token(LEFT_BRACES)) {
      return true;
    }
    if (jj_scan_token(RIGHT_BRACES)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_10() {
    if (jj_3R_11()) {
      return true;
    }
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_12()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3_2() {
    if (jj_scan_token(STRING)) {
      return true;
    }
    Token xsp;
    if (jj_3R_9()) {
      return true;
    }
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_9()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3R_11() {
    if (jj_3R_13()) {
      return true;
    }
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_14()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3R_24() {
    if (jj_scan_token(INTEGER)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_19() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_24()) {
      jj_scanpos = xsp;
      if (jj_3R_25()) {
        jj_scanpos = xsp;
        if (jj_3R_26()) {
          jj_scanpos = xsp;
          if (jj_3_1()) {
            jj_scanpos = xsp;
            if (jj_3_2()) {
              jj_scanpos = xsp;
              if (jj_3R_27()) {
                jj_scanpos = xsp;
                if (jj_3_3()) {
                  jj_scanpos = xsp;
                  if (jj_3R_28()) {
                    jj_scanpos = xsp;
                    if (jj_3R_29()) {
                      jj_scanpos = xsp;
                      if (jj_3R_30()) {
                        jj_scanpos = xsp;
                        if (jj_3R_31()) {
                          jj_scanpos = xsp;
                          if (jj_3R_32()) {
      return true;
    }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  private boolean jj_3R_9() {
    if (jj_scan_token(LEFT_BRACKET)) {
      return true;
    }
    if (jj_3R_10()) {
      return true;
    }
    if (jj_scan_token(RIGHT_BRACKET)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_18() {
    if (jj_scan_token(COMPARISON)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_23() {
    if (jj_scan_token(MODULO)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_32() {
    if (jj_scan_token(STRING)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_34() {
    if (jj_scan_token(FACTORIAL)) {
      return true;
    }
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_8()) {
      return true;
    }
    if (jj_scan_token(OPEN_PAR)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_31() {
    if (jj_scan_token(LOG)) {
      return true;
    }
    if (jj_3R_19()) {
      return true;
    }
    return false;
  }

  private boolean jj_3_3() {
    if (jj_scan_token(LEFT_BRACES)) {
      return true;
    }
    if (jj_3R_10()) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_33() {
    if (jj_scan_token(POWER)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_20() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3R_33()) {
      jj_scanpos = xsp;
      if (jj_3R_34()) {
      return true;
    }
    }
    return false;
  }

  private boolean jj_3R_17() {
    if (jj_scan_token(BOOLEAN_LOGIC)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_30() {
    if (jj_scan_token(NOT)) {
      return true;
    }
    if (jj_3R_10()) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_3R_19()) {
      return true;
    }
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3R_20()) { jj_scanpos = xsp; break; }
    }
    return false;
  }

  private boolean jj_3R_27() {
    if (jj_scan_token(OPEN_PAR)) {
      return true;
    }
    if (jj_3R_10()) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_8() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_scan_token(27)) {
      jj_scanpos = xsp;
      if (jj_scan_token(28)) {
      return true;
    }
    }
    return false;
  }

  private boolean jj_3R_16() {
    if (jj_scan_token(MINUS)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_29() {
    if (jj_scan_token(MINUS)) {
      return true;
    }
    if (jj_3R_19()) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_22() {
    if (jj_scan_token(DIVIDE)) {
      return true;
    }
    return false;
  }

  private boolean jj_3R_26() {
    if (jj_scan_token(EXPNUMBER)) {
      return true;
    }
    return false;
  }

  /** Generated Token Manager. */
  public FormulaParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[15];
  static private int[] jj_la1_0;
  static {
    jj_la1_init_0();
  }
  private static void jj_la1_init_0() {
    jj_la1_0 = new int[] {0x18000000,0x80000001,0x4200,0x4200,0x3800,0x3800,0x600500,0x600500,0x80,0x80000,0x80,0x80000,0x68,0x8000,0x1c020400,};
  }
  final private JJCalls[] jj_2_rtns = new JJCalls[3];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public FormulaParser(java.io.InputStream stream) {
    this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public FormulaParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new FormulaParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) {
      jj_la1[i] = -1;
    }
    for (int i = 0; i < jj_2_rtns.length; i++) {
      jj_2_rtns[i] = new JJCalls();
    }
  }

  /** Reinitialise. */
  @Override
  public void ReInit(java.io.InputStream stream) {
    ReInit(stream, null);
  }
  /** Reinitialise. */
  @Override
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) {
      jj_la1[i] = -1;
    }
    for (int i = 0; i < jj_2_rtns.length; i++) {
      jj_2_rtns[i] = new JJCalls();
    }
  }

  /** Constructor. */
  public FormulaParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new FormulaParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) {
      jj_la1[i] = -1;
    }
    for (int i = 0; i < jj_2_rtns.length; i++) {
      jj_2_rtns[i] = new JJCalls();
    }
  }

  /** Reinitialise. */
  @Override
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) {
      jj_la1[i] = -1;
    }
    for (int i = 0; i < jj_2_rtns.length; i++) {
      jj_2_rtns[i] = new JJCalls();
    }
  }

  /** Constructor with generated Token Manager. */
  public FormulaParser(FormulaParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) {
      jj_la1[i] = -1;
    }
    for (int i = 0; i < jj_2_rtns.length; i++) {
      jj_2_rtns[i] = new JJCalls();
    }
  }

  /** Reinitialise. */
  public void ReInit(FormulaParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 15; i++) {
      jj_la1[i] = -1;
    }
    for (int i = 0; i < jj_2_rtns.length; i++) {
      jj_2_rtns[i] = new JJCalls();
    }
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) {
      token = token.next;
    } else {
      token = token.next = token_source.getNextToken();
    }
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) {
              c.first = null;
            }
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) {
        jj_add_error_token(kind, i);
      }
    }
    if (jj_scanpos.kind != kind) {
      return true;
    }
    if (jj_la == 0 && jj_scanpos == jj_lastpos) {
      throw jj_ls;
    }
    return false;
  }


  /** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) {
      token = token.next;
    } else {
      token = token.next = token_source.getNextToken();
    }
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  /** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) {
        t = t.next;
      } else {
        t = t.next = token_source.getNextToken();
      }
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null) {
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    } else {
      return (jj_ntk = jj_nt.kind);
    }
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) {
      return;
    }
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) {
        jj_lasttokens[(jj_endpos = pos) - 1] = kind;
      }
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[32];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 15; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 32; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 3; i++) {
      try {
        JJCalls p = jj_2_rtns[i];
        do {
          if (p.gen > jj_gen) {
            jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
            switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            }
          }
          p = p.next;
        } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
