package com.example.barista.barista.repository;

import com.example.barista.barista.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Elvis
 * @create 2019-05-28 15:41
 */
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder,Long> {
}
