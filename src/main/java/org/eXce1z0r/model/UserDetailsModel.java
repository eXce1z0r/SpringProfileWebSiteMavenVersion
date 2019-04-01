package org.eXce1z0r.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="accounts_additional_info")
public class UserDetailsModel 
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;
	
	@Column(name="name")
	@NotFound(action=NotFoundAction.IGNORE)
	private String userName;
	
	@Column(name="surname")
	@NotFound(action=NotFoundAction.IGNORE)
	private String userSurname;
	
	@Column(name="patronymic")
	@NotFound(action=NotFoundAction.IGNORE)
	private String userPatronymic;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getUserPatronymic() {
		return userPatronymic;
	}

	public void setUserPatronymic(String userPatronymic) {
		this.userPatronymic = userPatronymic;
	}
	
	

}
