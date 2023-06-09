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
public class CustomerCreateReqDTO implements Validator {

    private String fullName;
    private String email;
    private String phone;
    private String address;

    public Customer toCustomer(Long id, BigDecimal balance) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setBalance(balance);
        customer.setAddress(address);

        return customer;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerCreateReqDTO.class.isAssignableFrom(clazz);
    }
    @Override
    public void validate(Object target, Errors errors) {
        CustomerCreateReqDTO customerCreateReqDTO = (CustomerCreateReqDTO) target;

        String fullName = customerCreateReqDTO.fullName;
        String email = customerCreateReqDTO.email;
        String phone = customerCreateReqDTO.phone;
        String address = customerCreateReqDTO.address;


        if (fullName == null) {
            errors.rejectValue("fullName", "fullName.null", "Họ tên là bắt buộc");
        }
        else {
            if (fullName.trim().length() == 0) {
                errors.rejectValue("fullName", "fullName.empty", "Họ tên là không được để trống");
            }
            else {
                if (fullName.trim().length() > 50 || fullName.trim().length() < 5) {
                    errors.rejectValue("fullName", "fullName.length.min-max", "Họ tên phải từ 5-50 ký tự");
                }
            }
        }

        if (email == null) {
            errors.rejectValue("email", "email.null", "Email là bắt buộc");
        }
        else {
            if (email.trim().length() == 0) {
                errors.rejectValue("email", "email.empty", "Email là không được để trống");
            }
            else {
                if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
                    errors.rejectValue("email", "email.match", "Email chưa đúng định dạng");
                }
            }
        }

        if (phone == null) {
            errors.rejectValue("phone", "phone.null", "Số điện thoại là bắt buộc");
        }
        else {
            if (phone.trim().length() == 0) {
                errors.rejectValue("phone", "phone.empty", "Số điện thoại là không được để trống");
            }
            else {
                if (!phone.matches("\\d+")) {
                    errors.rejectValue("phone", "phone.match", "Số điện thoại phải là ký tự số");
                }
            }
        }


        if (address == null) {
            errors.rejectValue("address", "address.null", "Địa chỉ là bắt buộc");
        }
        else {
            if (address.trim().length() == 0) {
                errors.rejectValue("address", "address.empty", "Địa chỉ là không được để trống");
            }
            else {
                if (address.trim().length() > 200  || address.trim().length() < 5) {
                    errors.rejectValue("address", "address.length.min-max", "Địa chỉ phải từ 5-200 ký tự");
                }
            }
        }

    }
}
