package com.swabhav.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "email", unique = true)
	@NotNull(message = "email can not be null")
	private String email;

	@Column(name = "phone")
	@NotNull(message = "phone can not be null")
	private String phone;
	
	@NotNull(message = "city can not be null")
	@Column(name = "city")
	
	private String city;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", nullable = false, unique = true)
	private Student student;

}
