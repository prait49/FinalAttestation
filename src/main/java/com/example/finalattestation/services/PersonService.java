package com.example.finalattestation.services;

import com.example.finalattestation.models.Order;
import com.example.finalattestation.models.Person;
import com.example.finalattestation.models.Product;
import com.example.finalattestation.repository.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    public final PersonRepository personRepository;
    public  final PasswordEncoder passwordEncoder;
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<Person> getAllPerson(){
        return personRepository.findAll();
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
    @Transactional
    //Данный метод позволяет обновить данные о  роле пользователе
    public  void updatePersonRole(int id, String role){
        Person existingPerson = personRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        existingPerson.setRole(role);
        personRepository.save(existingPerson);
        System.out.println();
    }

    @Transactional
    //Данный метод позволяет удалить пользователя по ID
    public  void deletePerson(int id){

        personRepository.deleteById(id);
    }

    //Данный метод позволяет получить данные пользователя по id
    public Person getPersonId(int id){
        Optional<Person> optionalPerson=personRepository.findById(id);
        return  optionalPerson.orElse(null );
    }
}




