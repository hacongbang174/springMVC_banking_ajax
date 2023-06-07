package com.cg.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.regex.Pattern;

@Entity
@Table(name = "deposits")
public class Deposit extends BaseEntity implements Validator {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    public Deposit() {
    }

    public Deposit(Long id, Customer customer, BigDecimal transactionAmount) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Deposit.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Deposit deposit = (Deposit) target;

        BigDecimal transactionAmount = deposit.transactionAmount;


        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "transactionAmount.null", "Tiền gửi là bắt buộc");
        } else {
            if (transactionAmount.toString().trim().length() == 0) {
                errors.rejectValue("transactionAmount", "transactionAmount.empty", "Tiền gửi là không được để trống");
            } else {
                if (!transactionAmount.toString().matches("\\d+")) {
                    errors.rejectValue("transactionAmount", "transactionAmount.match", "Tiền gửi phải là ký tự số");
                }
                if (!Pattern.matches("\\b([1-9]\\d{2,11}|999999999999)\\b", transactionAmount.toString())) {
                    errors.rejectValue("transactionAmount", "transactionAmount.length.min-max", "Tiền gửi phải lớn hơn 100 và nhỏ hơn 13 số");
                }
            }
        }
    }

}
