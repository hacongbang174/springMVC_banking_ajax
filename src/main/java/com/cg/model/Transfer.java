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
@Table(name = "transfers")
public class Transfer extends BaseEntity implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;

    @Column(name = "transfer_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transferAmount;

    @Column(nullable = false)
    Long fees;

    @Column(name = "fees_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal feesAmount;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    public Transfer() {
    }

    public Transfer(Long id, Customer sender, Customer recipient, BigDecimal transferAmount, Long fees, BigDecimal feesAmount, BigDecimal transactionAmount) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.transferAmount = transferAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getFees() {
        return fees;
    }

    public void setFees(Long fees) {
        this.fees = fees;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(BigDecimal feesAmount) {
        this.feesAmount = feesAmount;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Transfer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Transfer transfer = (Transfer) target;

        Long fees = transfer.fees;
        BigDecimal transferAmount = transfer.transferAmount;

        if (fees == null) {
            errors.rejectValue("fees", "fees.null", "Phí chuyển tiền là bắt buộc");
        } else {
            if (fees.toString().trim().length() == 0) {
                errors.rejectValue("fees", "fees.empty", "Phí chuyển tiền là không được để trống");
            } else {
                if (!fees.toString().matches("\\d+")) {
                    errors.rejectValue("fees", "fees.match", "Phí chuyển tiền phải là ký tự số");
                }
                if (!fees.toString().matches("^(?!0$)(?:[1-9][0-9]?|100)$")) {
                    errors.rejectValue("fees", "fees.length.min-max", "Phí chuyển tiền từ 1-100 %");
                }
            }
        }


        if (transferAmount == null) {
            errors.rejectValue("transferAmount", "transferAmount.null", "Tiền chuyển là bắt buộc");
        } else {
            if (transferAmount.toString().trim().length() == 0) {
                errors.rejectValue("transferAmount", "transferAmount.empty", "Tiền chuyển là không được để trống");
            } else {
                if (!transferAmount.toString().matches("\\d+")) {
                    errors.rejectValue("transferAmount", "transferAmount.match", "Tiền chuyển phải là ký tự số");
                }
                if (!transferAmount.toString().matches("\\b([1-9]\\d{2,11}|999999999999)\\b")) {
                    errors.rejectValue("transferAmount", "transferAmount.length.min-max", "Tiền chuyển phải lớn hơn 100 và nhỏ hơn 13 số");
                }
            }
        }
    }
}
