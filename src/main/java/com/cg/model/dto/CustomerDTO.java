package com.cg.model.dto;

import com.cg.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private BigDecimal balance;
    private String address;

    public Customer toCustomer() {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setBalance(balance);
        customer.setAddress(address);

        return customer;
    }
}
