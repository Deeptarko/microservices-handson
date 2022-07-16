package com.deep.userservice.entity;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String username;
	private String password;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "roles_table", joinColumns = @JoinColumn(name = "id"))
	private Set<String> roles;

}
