package com.example.finalattestation.services;

import com.example.finalattestation.models.Person;
import com.example.finalattestation.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PersonService {

    public final PersonRepository personRepository;
    public  final PasswordEncoder passwordEncoder;
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Person findByLogin(Person person){
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }
    @Transactional
    public void register(Person person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
       person.setRole("ROLE_USER");
        personRepository.save(person);
    }
//    @Transactional
//    public void delete(Person person){
//        personRepository.delete(person);
//    }
}
