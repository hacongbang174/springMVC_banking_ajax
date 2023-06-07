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
@Table(name = "withdraws")
public class Withdraw extends BaseEntity implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    public Withdraw() {
    }

    public Withdraw(Long id, Customer customer, BigDecimal transactionAmount) {
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
        return Withdraw.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Withdraw withdraw = (Withdraw) target;

        BigDecimal transactionAmount = withdraw.transactionAmount;


        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "transactionAmount.null", "Tiền rút là bắt buộc");
        } else {
            if (transactionAmount.toString().trim().length() == 0) {
                errors.rejectValue("transactionAmount", "transactionAmount.empty", "Tiền rút là không được để trống");
            } else {
                if (!transactionAmount.toString().matches("\\d+")) {
                    errors.rejectValue("transactionAmount", "transactionAmount.match", "Tiền rút phải là ký tự số");
                }
                if (!Pattern.matches("\\b([1-9]\\d{2,11}|999999999999)\\b", transactionAmount.toString())) {
                    errors.rejectValue("transactionAmount", "transactionAmount.length.min-max", "Tiền rút phải lớn hơn 100 và nhỏ hơn 13 số");
                }
            }
        }
    }
}
