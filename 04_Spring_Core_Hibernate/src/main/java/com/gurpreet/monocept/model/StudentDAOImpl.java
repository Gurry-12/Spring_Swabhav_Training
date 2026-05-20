package com.gurpreet.monocept.model;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO {

	EntityManager entityManager;

	@Autowired
	public StudentDAOImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void save(Student student) {
		entityManager.persist(student);

	}

	@Override
	public Student findStudentById(Integer id) {
		return entityManager.find(Student.class, id);
	}

	@Override
	public List<Student> findAllStudent() {
		return entityManager.createQuery("from Student ", Student.class).getResultList();
	}

}
