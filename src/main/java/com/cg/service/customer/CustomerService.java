package com.cg.service.customer;


import com.cg.model.Customer;
import com.cg.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;


    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByEmailAndIdIsNot(String email, Long id) {
        return customerRepository.existsByEmailAndIdIsNot(email, id);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return customerRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsByPhoneAndIdIsNot(String phone, Long id) {
        return customerRepository.existsByPhoneAndIdIsNot(phone, id);
    }
}
