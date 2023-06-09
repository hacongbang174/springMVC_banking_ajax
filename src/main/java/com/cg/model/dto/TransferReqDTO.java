package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransferReqDTO implements Validator {
    private Long senderId;
    private Long recipientId;
    private Long fees;
    private BigDecimal feesAmount;
    private BigDecimal transferAmount;
    private BigDecimal transactionAmount;
    public Transfer toTransfer(Long id, CustomerDTO sender, CustomerDTO recipient) {
        Transfer transfer = new Transfer();
        transfer.setId(id);
        transfer.setSender(sender.toCustomer());
        transfer.setRecipient(recipient.toCustomer());
        transfer.setFees(fees);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransferAmount(transferAmount);
        transfer.setTransactionAmount(transactionAmount);

        return transfer;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return TransferReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransferReqDTO transferReqDTO = (TransferReqDTO) target;

        Long fees = transferReqDTO.fees;
        BigDecimal transferAmount = transferReqDTO.transferAmount;

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
