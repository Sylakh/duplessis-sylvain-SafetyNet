package com.openclassrooms.safetynet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openclassrooms.safetynet.model.Person;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

	Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

	List<Person> findAllByAddress(String address);

	List<Person> findAll();

	List<Person> findAllByCity(String city);

	List<Person> findAllByLastName(String lastName);

	void deleteById(Long id);

	@Modifying
	@Query(value = "UPDATE person SET firestation_address = NULL WHERE id = :id", nativeQuery = true)
	void updateFireStationReferenceNull(@Param("id") Long id);

}