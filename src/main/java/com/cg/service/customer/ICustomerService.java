package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.service.IGeneralService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    List<Customer> findAll();

    List<Customer> findAllByIdNot(Long id);

    List<Customer> findAllByEmailLike(String email);

    Optional<Customer> findById(Long id);

    Boolean existsByEmail(String email);
    boolean existsByEmailAndIdIsNot(String email, Long id);

    Customer save(Customer customer);


}
