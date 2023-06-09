package com.cg.model.dto;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WithdrawReqDTO implements Validator {
    private BigDecimal transactionAmount;
    public Withdraw toWithdraw(Long id, CustomerDTO customer) {
        Withdraw withdraw = new Withdraw();
        withdraw.setId(id);
        withdraw.setCustomer(customer.toCustomer());
        withdraw.setTransactionAmount(transactionAmount);

        return withdraw;
    }
    @Override
    public boolean supports(Class<?> clazz) {
        return WithdrawReqDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WithdrawReqDTO withdrawReqDTO = (WithdrawReqDTO) target;

        BigDecimal transactionAmount = withdrawReqDTO.transactionAmount;


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
