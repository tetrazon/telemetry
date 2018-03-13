package com.janqa.tm.base.model;

import java.io.Serializable;

/**
 * 
 * @author Jan Bronnikau
 * @version 2.0
 *
 */
public class Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7618901779282408584L;

	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
