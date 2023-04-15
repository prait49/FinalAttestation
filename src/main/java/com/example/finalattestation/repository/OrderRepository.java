package com.example.finalattestation.repository;

import com.example.finalattestation.models.Order;
import com.example.finalattestation.models.Person;
import com.example.finalattestation.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByPerson(Person person);

    //Поиск по 4 последним буквам/цифрам заказа.
    @Query(value = "SELECT * FROM orders WHERE lower(RIGHT(number, 4)) LIKE lower(concat('%', ?1, '%'))", nativeQuery = true)
    List<Order> findByLast4DigitsOfOrderNumber(String number);




}