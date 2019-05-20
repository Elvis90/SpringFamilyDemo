package com.elvis.demo.repository;

import com.elvis.demo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long>{

}
