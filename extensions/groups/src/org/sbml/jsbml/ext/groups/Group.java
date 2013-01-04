/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.groups;

import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.xml.parsers.GroupsParser;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev$
 */
public class Group extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 2361503116934849753L;
	/**
	 * 
	 */
	protected ListOf<Member> listOfMembers = null;
	
	/**
	 * Defined in version 3 of the groups proposal.
	 */
	private GroupKind kind = null;

	/**
	 * 
	 */
	public Group() {
		super();
		initDefaults();
	}
	
	private void initDefaults() {
		addNamespace(GroupsParser.namespaceURI);		
	}

	/**
	 * 
	 * @param group
	 */
	public Group(Group group) {
		super(group);
		if (group.isSetListOfMembers()) {
		  for (Member m : group.listOfMembers) {
			  addMember(m.clone());
		  }
		}
		if (group.isSetKind()) {
		  setKind(group.getKind());
		}
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Group(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public Group clone() {
		return new Group(this);
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Member> getListOfMembers() {
		if (!isSetListOfMembers()) {
			listOfMembers = new ListOf<Member>();
			listOfMembers.addNamespace(GroupsParser.namespaceURI);
			registerChild(listOfMembers);
			listOfMembers.setSBaseListType(ListOf.Type.other);
		}

		return listOfMembers;
	}

	/**
	 * 
	 * @param i
	 * @return
	 */
	public Member getMember(int i) {
		if (i >= 0 && i < getListOfMembers().size()) {
			return getListOfMembers().get(i);
		}
		
		return null;
	}
	
	public boolean addMember(Member member) {
		return getListOfMembers().add(member);
	}
	
	/* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return false;
  }

	/**
	 * 
	 * @return
	 */
	public boolean isSetListOfMembers() {
		if (listOfMembers == null) {
			return false;			
		}
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetKind() {
	  return kind!=null;
	}
	
	/**
	 * 
	 * @return
	 */
	public GroupKind getKind() {
	  return kind;
	}
	
	/**
	 * 
	 * @param kind
	 */
	public void setKind(GroupKind kind) {
	  this.kind = kind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead && attributeName.equals("kind")) {
      this.setKind(GroupKind.valueOf(value));
      isAttributeRead = true;
		}
		
		return isAttributeRead;
	}

	
	/* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Group [id=" + getId() + ", name=" + getName() 
        + (isSetKind()?", kind=" + getKind():"")
        + ", listOfMembers=" + listOfMembers + "]";
  }

  /**
	 * Removes the {@link #listOfMembers} from this {@link Model} and notifies
	 * all registered instances of {@link TreeNodeChangeListener}.
	 * 
	 * @return {@code true} if calling this method lead to a change in this
	 *         data structure.
	 */
	public boolean unsetListOfMembers() {
		if (isSetListOfMembers()) {
			ListOf<Member> oldListOfMembers = this.listOfMembers;
			this.listOfMembers = null;
			oldListOfMembers.fireNodeRemovedEvent();
			return true;
		}
		return false;
	}

  /*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(GroupsParser.shortLabel+ ":id", getId());
		}
		if (isSetName()) {
			attributes.remove("name");
			attributes.put(GroupsParser.shortLabel+ ":name", getName());
		}
    if (isSetKind()) {
      attributes.remove("kind");
      attributes.put(GroupsParser.shortLabel+ ":kind", getKind().toString());
    }
		
		return attributes;
	}
	
	public int getChildCount() {
		if (isSetListOfMembers()) {
			return 1;
		}
		return 0;
	}
	
	public TreeNode getChildAt(int index) {
		if (index < 0 || index >= 1) {
			return null;
		}
		
		if (isSetListOfMembers()) {
			return listOfMembers;
		}
		
		return null;
	}

  /**
   * @param symbol
   * @return
   */
	public Member createMember(String symbol) {
	  Member m = new Member();
	  m.setSymbol(symbol);
	  addMember(m);
	  return m;
	}
	
	
}
