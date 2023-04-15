package com.example.finalattestation.repository;


import com.example.finalattestation.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {
    com.example.finalattestation.models.Category findByName(String name);
}
