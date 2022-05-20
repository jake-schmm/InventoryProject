package com.example.inventory.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.inventory.model.Inventory;

import java.util.List;
@Repository
public interface RecipeRepository extends JpaRepository<Inventory, Long> {
  
}
