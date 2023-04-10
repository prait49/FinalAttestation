package com.example.finalattestation.repository;

import com.example.finalattestation.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {
    Optional <Person>findByLogin(String login);
}
