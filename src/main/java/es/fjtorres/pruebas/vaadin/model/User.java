package es.fjtorres.pruebas.vaadin.model;

import java.io.Serializable;

import com.google.common.base.Objects;

public class User implements Serializable {

	private static final long serialVersionUID = -1843638755484312753L;

	private Long id;
	
	private String username;

	private String password;

	private String firstName;

	private String lastName;

	@Override
	public boolean equals(final Object pObj) {
		boolean isEquals = false;
		if (this == pObj) {
			isEquals = true;
		} else if (pObj != null && pObj instanceof User) {
			final User other = (User) pObj;
			isEquals |= Objects.equal(getFirstName(), other.getFirstName());
			isEquals |= Objects.equal(getLastName(), other.getLastName());
			isEquals |= Objects.equal(getPassword(), other.getPassword());
			isEquals |= Objects.equal(getUsername(), other.getFirstName());
		}
		return isEquals;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getFirstName(), getLastName(), getPassword(),
				getUsername());
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).omitNullValues().toString();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String pUsername) {
		username = pUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String pPassword) {
		password = pPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(final String pFirstName) {
		firstName = pFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(final String pLastName) {
		lastName = pLastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long pId) {
		id = pId;
	}
}
