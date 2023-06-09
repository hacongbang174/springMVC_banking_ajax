package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    List<Customer> findAll();

    List<Customer> findAllByIdNot(Long id);

    List<Customer> findAllByEmailLike(String email);

    Page<Customer> findAll(Pageable pageable);

    Optional<Customer> findById(Long id);

    Boolean existsByEmail(String email);
    boolean existsByEmailAndIdIsNot(String email, Long id);
    Boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdIsNot(String phone, Long id);

    Customer save(Customer customer);


}
