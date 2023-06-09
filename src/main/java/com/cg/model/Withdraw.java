package com.cg.model;

import com.cg.model.dto.DepositDTO;
import com.cg.model.dto.WithdrawDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.math.BigDecimal;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "withdraws")
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    public WithdrawDTO toWithdrawDTO() {
        return new WithdrawDTO()
                .setId(id)
                .setCustomerDTO(customer.toCustomerDTO())
                .setTransactionAmount(transactionAmount)
                ;
    }
}
