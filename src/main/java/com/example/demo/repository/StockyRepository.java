package com.example.demo.repository;

import com.example.demo.model.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockyRepository extends JpaRepository<Stock, Long> {
}
