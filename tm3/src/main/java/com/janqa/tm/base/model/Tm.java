package com.janqa.tm.base.model;

/**
 * 
 * @author Jan Bronnikau
 * @version 3.0 <br/>
 *          Field name is renamed to code. Field definition added.
 * 
 */

public class Tm extends Entity {
	private static final long serialVersionUID = 7472925334081940057L;

	/**
	 * 
	 * @author Jan Bronnikau
	 * @version 2.0 <br/>
	 *          TINYINT is changed to INT, DATETIME to TIME. STRING added.
	 */
	public enum Type {
		BIT, INT, FLOAT, TIME, BINARY, STRING
	}

	private String code;
	private String definition;
	private Type type;

	public Tm() {

	}

	public Tm(String code, Type type) {
		this.code = code;
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tm other = (Tm) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

}
