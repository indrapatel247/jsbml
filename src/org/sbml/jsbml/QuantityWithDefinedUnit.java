/*
 * $Id: QuantityWithDefinedUnit.java 173 2010-04-09 06:32:34Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/QuantityWithDefinedUnit.java $
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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
package org.sbml.jsbml;

/**
 * This object represents an element with identifier and name, a value, and a
 * defined unit. In particular, this class defines methods to access and
 * manipulate the value and the unit properties of an element within a model.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @date 2010-04-20
 * 
 */
public abstract class QuantityWithDefinedUnit extends AbstractNamedSBase
		implements Quantity {

	/**
	 * a boolean to help knowing is the value as been set by the user or is the
	 * default one.
	 */
	private boolean isSetValue = false;
	/**
	 * The unit attribute of this variable.
	 */
	private String unitsID;

	/**
	 * The size, initial amount or concentration, or the actual value of this
	 * variable.
	 */
	private Double value = Double.NaN;

	/**
	 * 
	 */
	public QuantityWithDefinedUnit() {
		super();
		this.unitsID = null;
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public QuantityWithDefinedUnit(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * 
	 * @param qwdu
	 */
	public QuantityWithDefinedUnit(QuantityWithDefinedUnit qwdu) {
		super(qwdu);
		if (qwdu.isSetValue()) {
			this.value = new Double(qwdu.getValue());
			isSetValue = true;
		} else {
			this.value = null;
		}
		setUnits(qwdu.isSetUnits() ? new String(qwdu.getUnits()) : null);
	}

	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public QuantityWithDefinedUnit(String id, int level, int version) {
		super(id, null, level, version);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public QuantityWithDefinedUnit(String id, String name, int level,
			int version) {
		super(id, name, level, version);
		this.unitsID = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#clone()
	 */
	public abstract QuantityWithDefinedUnit clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
	 */
	public boolean containsUndeclaredUnits() {
		return !isSetUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof QuantityWithDefinedUnit) {
			boolean equal = super.equals(o);
			QuantityWithDefinedUnit v = (QuantityWithDefinedUnit) o;
			equal &= v.isSetUnits() == isSetUnits();
			if (v.isSetUnits() && isSetUnits()) {
				equal &= v.getUnits().equals(getUnits());
			}
			if (!(Double.isNaN(v.getValue()) && Double.isNaN(getValue())))
				equal &= v.getValue() == getValue();
			else
				equal &= (Double.isNaN(v.getValue()) && Double
						.isNaN(getValue()));
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		if (isSetUnitsInstance()) {
			return getUnitsInstance();
		}
		UnitDefinition ud = new UnitDefinition(getLevel(), getVersion());
		ud.addUnit(new Unit(getLevel(), getVersion()));
		return ud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	public String getDerivedUnits() {
		return getUnits();
	}

	/**
	 * 
	 * @return the unitsID of this Symbol. The empty String if it is not set.
	 */
	public String getUnits() {
		return isSetUnits() ? unitsID : "";
	}

	/**
	 * 
	 * @return The UnitDefinition instance which has the unitsID of this Symbol
	 *         as id. Null if it doesn't exist. In case that the unit of this
	 *         Symbol represents a base unit, a new UnitDefinition will be
	 *         created and returned by this method. This new UnitDefintion will
	 *         only contain the one unit represented by the unit identifier in
	 *         this Symbol. Note that the corresponding model will not contain
	 *         this UnitDefinition. The identifier of this new UnitDefinition
	 *         will be set to the same value as the name of the base unit.
	 */
	public UnitDefinition getUnitsInstance() {
		if (Unit.isUnitKind(unitsID, getLevel(), getVersion())) {
			UnitDefinition ud = new UnitDefinition(unitsID, getLevel(),
					getVersion());
			ud.addUnit(new Unit(Unit.Kind.valueOf(unitsID), getLevel(),
					getVersion()));
			return ud;
		}
		Model model = getModel();
		return model == null ? null : model.getUnitDefinition(unitsID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#getValue()
	 */
	public double getValue() {
		return value != null ? value : 0;
	}

	/**
	 * 
	 * @return true if the unitsID of this element is not null.
	 */
	public boolean isSetUnits() {
		return unitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the unitsID of this Symbol
	 *         as id is not null.
	 */
	public boolean isSetUnitsInstance() {
		Model model = getModel();
		return model == null ? false
				: model.getUnitDefinition(this.unitsID) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#isSetValue()
	 */
	public boolean isSetValue() {
		return isSetValue;
	}

	/**
	 * Sets the unitsID of this Symbol.
	 * 
	 * @param units
	 */
	public void setUnits(String units) {
		if (units != null && units.trim().length() == 0) {
			this.unitsID = null;
		} else {
			this.unitsID = units;
		}
		stateChanged();
	}

	/**
	 * Sets the Unit of this Symbol.
	 * 
	 * @param unit
	 */
	@SuppressWarnings("deprecation")
	public void setUnits(Unit unit) {
		UnitDefinition ud = new UnitDefinition(unit.getKind().toString(),
				getLevel(), getVersion());
		ud.addUnit(unit);
		if (unit.getExponent() != 1 || unit.getScale() != 0
				|| unit.getMultiplier() != 1d || unit.getOffset() != 0d) {
			StringBuilder sb = new StringBuilder();
			sb.append(unit.getMultiplier());
			sb.append('_');
			sb.append(unit.getScale());
			sb.append('_');
			sb.append(unit.getKind().toString());
			sb.append('_');
			sb.append(unit.getExponent());
			ud.setId(sb.toString());
			Model m = getModel();
			if (m != null)
				m.addUnitDefinition(ud);
		}
		setUnits(ud);
	}

	/**
	 * Sets the unit of this Symbol.
	 * 
	 * @param unitKind
	 */
	public void setUnits(Unit.Kind unitKind) {
		setUnits(new Unit(unitKind, getLevel(), getVersion()));
	}

	/**
	 * Set the unit attribute of this variable to the given unit definition.
	 * 
	 * @param units
	 */
	public void setUnits(UnitDefinition units) {
		this.unitsID = units != null ? units.getId() : null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#setValue(double)
	 */
	public void setValue(double value) {
		this.value = value;
		if (!Double.isNaN(value)) {
			isSetValue = true;
		}
		stateChanged();
	}

	/**
	 * Sets the unitsID of this Quantity to null.
	 */
	public void unsetUnits() {
		this.unitsID = null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#unsetValue()
	 */
	public void unsetValue() {
		value = Double.NaN;
		isSetValue = false;
		stateChanged();
	}
}
