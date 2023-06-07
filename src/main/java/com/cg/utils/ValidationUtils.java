package com.cg.utils;

import com.cg.model.Customer;
import com.cg.service.customer.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.regex.Pattern;

@Component
public class ValidationUtils implements Validator {
    @Autowired
    private ICustomerService customerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

        String fullName = customer.getFullName();
        String email = customer.getEmail();
        String phone = customer.getPhone();

        if (fullName.length() == 0) {
            errors.rejectValue("fullName", "fullName.length", "Độ dài họ tên phải lớn hơn 0");
        }

        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            errors.rejectValue("email", "email.match", "Email chưa đúng định dạng");
        }
        if (customerService.existsByEmail(email)) {
            errors.rejectValue("email", "email.exists", "Email đã tồn tại");
        }
        if (customerService.existsByEmailAndIdIsNot(email, customer.getId())) {
            errors.rejectValue("email", "email.exists", "Email đã tồn tại");
        }
        if (!phone.matches("\\d+")) {
            errors.rejectValue("phone", "phone.match", "Số điện thoại phải là ký tự số");
        }

    }
}
