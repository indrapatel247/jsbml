/*
 *
 * @file    TestEvent.java
 * @brief   SBML Event unit tests
 *
 * @author rodrigue (jsbml conversion)
 * @author  Akiya Jouraku (Java conversion)
 * @author  Ben Bornstein 
 *
 * $Id$
 * $URL$
 *
 * This test file was converted from libsbml http://sbml.org/software/libsbml
 *
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */

package org.sbml.jsbml.test.sbml;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.sbml.jsbml.element.ASTNode;
import org.sbml.jsbml.element.Delay;
import org.sbml.jsbml.element.Event;
import org.sbml.jsbml.element.EventAssignment;
import org.sbml.jsbml.element.Trigger;


public class TestEvent {

  private Event E;

  @Before public void setUp() throws Exception
  {
    E = new  Event(2,4);
    if (E == null);
    {
    }
  }

  @After public void tearDown() throws Exception
  {
    E = null;
  }

  @SuppressWarnings("deprecation")
@Test public void test_Event_create()
  {
//    assertTrue( E.getTypeCode() == libsbml.SBML_EVENT );
    assertTrue( E.getMetaId().equals("") == true );
//    assertTrue( E.getNotes() == null );
    assertTrue( E.getAnnotation() == null );
    assertTrue( E.getId().equals("") == true );
    assertTrue( E.getName().equals("") == true );
    assertEquals(E.getTrigger(),null);
    assertEquals(E.getDelay(),null);
    assertTrue( E.getTimeUnits().equals("") == true );
    assertTrue( E.getNumEventAssignments() == 0 );
  }

  /*
   // TODO : add again if we have constructor with SBMLNamespaces
  @Test public void test_Event_createWithNS()
  {
    XMLNamespaces xmlns = new  XMLNamespaces();
    xmlns.add( "http://www.sbml.org", "testsbml");
    SBMLNamespaces sbmlns = new  SBMLNamespaces(2,4);
    sbmlns.addNamespaces(xmlns);
    Event object = new  Event(sbmlns);
    assertTrue( object.getTypeCode() == libsbml.SBML_EVENT );
    assertTrue( object.getMetaId().equals("") == true );
    assertTrue( object.getNotes() == null );
    assertTrue( object.getAnnotation() == null );
    assertTrue( object.getLevel() == 2 );
    assertTrue( object.getVersion() == 4 );
    assertTrue( object.getNamespaces() != null );
    assertTrue( object.getNamespaces().getLength() == 2 );
    object = null;
  }
*/
  
  @Test public void test_Event_full()
  {
    ASTNode math1 = ASTNode.parseFormula("0"); // TODO : API changes to document (was libsbml.parseFormula("0"))
    Trigger trigger = new  Trigger(2,4);
    ASTNode math = ASTNode.parseFormula("0");
    Event e = new  Event(2,4);
    EventAssignment ea = new  EventAssignment(2,4);
    ea.setVariable( "k");
    ea.setMath(math);
    trigger.setMath(math1);
    e.setTrigger(trigger);
    e.setId( "e1");
    e.setName( "Set k2 to zero when P1 <= t");
    e.addEventAssignment(ea);
    assertTrue( e.getNumEventAssignments() == 1 );
    assertTrue(e.getEventAssignment(0) != ea);
    math = null;
    e = null;
  }

  @Test public void test_Event_removeEventAssignment()
  {
    EventAssignment o1,o2,o3;
    o1 = E.createEventAssignment();
    o2 = E.createEventAssignment();
    o3 = E.createEventAssignment();
    o3.setVariable("test");
    assertTrue( E.removeEventAssignment(0).equals(o1) );
    assertTrue( E.getNumEventAssignments() == 2 );
    assertTrue( E.removeEventAssignment(0).equals(o2) );
    assertTrue( E.getNumEventAssignments() == 1 );
    assertTrue( E.removeEventAssignment("test").equals(o3) );
    assertTrue( E.getNumEventAssignments() == 0 );
    o1 = null;
    o2 = null;
    o3 = null;
  }

  @Test public void test_Event_setDelay()
  {
    ASTNode math1 = ASTNode.parseFormula("0");
    Delay Delay = new  Delay(2,4);
    Delay.setMath(math1);
    E.setDelay(Delay);
    assertTrue(E.getDelay() != null);
    assertEquals( true, E.isSetDelay() );
    if (E.getDelay() == Delay);
    {
    }
    E.setDelay(E.getDelay());
    assertTrue(E.getDelay() != Delay);
    E.setDelay(null);
    assertEquals( false, E.isSetDelay() );
    if (E.getDelay() != null);
    {
    }
  }

  @Test public void test_Event_setId()
  {
    String id =  "e1";;
    E.setId(id);
    assertTrue(E.getId().equals(id));
    assertEquals( true, E.isSetId() );
    if (E.getId() == id);
    {
    }
    E.setId(E.getId());
    assertTrue(E.getId().equals(id));
    E.setId("");
    assertEquals( false, E.isSetId() );
    if (E.getId() != null);
    {
    }
  }

  @Test public void test_Event_setName()
  {
    String name =  "Set_k2";;
    E.setName(name);
    assertTrue(E.getName().equals(name));
    assertEquals( true, E.isSetName() );
    if (E.getName() == name);
    {
    }
    E.setName(E.getName());
    assertTrue(E.getName().equals(name));
    E.setName("");
    assertEquals( false, E.isSetName() );
    if (E.getName() != null);
    {
    }
  }

  @SuppressWarnings("deprecation")
@Test public void test_Event_setTimeUnits()
  {
    Event E1 = new  Event(2,1);
    String units =  "second";;
    E1.setTimeUnits(units);
    assertTrue(E1.getTimeUnits().equals(units));
    assertEquals( true, E1.isSetTimeUnits() );
    if (E1.getTimeUnits() == units);
    {
    }
    E1.setTimeUnits(E1.getTimeUnits());
    assertTrue(E1.getTimeUnits().equals(units));
    E1.setTimeUnits("");
    assertEquals( false, E1.isSetTimeUnits() );
    if (E1.getTimeUnits() != null);
    {
    }
    E1 = null;
  }

  @Test public void test_Event_setTrigger()
  {
    ASTNode math1 =  ASTNode.parseFormula("0");
    Trigger trigger = new  Trigger(2,4);
    trigger.setMath(math1);
    E.setTrigger(trigger);
    assertTrue(E.getTrigger() != null);
    assertEquals( true, E.isSetTrigger() );
    if (E.getTrigger() == trigger);
    {
    }
    E.setTrigger(E.getTrigger());
    assertTrue(E.getTrigger() != trigger);
    E.setTrigger(null);
    assertEquals( false, E.isSetTrigger() );
    if (E.getTrigger() != null);
    {
    }
  }

  @Test public void test_Event_setUseValuesFromTriggerTime()
  {
    Event object = new  Event(2,4);
    object.setUseValuesFromTriggerTime(false);
    assertTrue( object.getUseValuesFromTriggerTime() == false );
    object.setUseValuesFromTriggerTime(true);
    assertTrue( object.getUseValuesFromTriggerTime() == true );
    object = null;
  }

}
