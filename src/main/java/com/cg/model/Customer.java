package com.cg.model;

import com.cg.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity  implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(precision = 10, scale = 0, nullable = false, updatable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private String address;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted;

    @OneToMany
    private List<Deposit> deposits;

    @OneToMany
    private List<Withdraw> withdraws;

    @OneToMany
    private List<Transfer> senders;

    @OneToMany
    private List<Transfer> recipients;

    public Customer() {
    }

    public Customer(Long id, String fullName, String email, String phone, BigDecimal balance, String address, boolean deleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.balance = balance;
        this.address = address;
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

        String fullName = customer.fullName;
        String email = customer.email;
        String phone = customer.phone;
        String address = customer.address;


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