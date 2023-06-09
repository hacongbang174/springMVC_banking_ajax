package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICustomerService extends IGeneralService<Customer, Long> {
    Boolean existsByEmail(String email);
    boolean existsByEmailAndIdIsNot(String email, Long id);
    Boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdIsNot(String phone, Long id);
}
