package com.cg.repository;

import com.cg.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findAllByIdNot(Long id);
    List<Customer> findAllByEmailLike(String email);
    Boolean existsByEmail(String email);
    boolean existsByEmailAndIdIsNot(String email, Long id);
}
