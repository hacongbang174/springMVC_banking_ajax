package com.cg.model;


import com.cg.model.dto.CustomerDTO;
import com.cg.model.dto.DepositDTO;
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
@Table(name = "deposits")
public class Deposit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    public DepositDTO toDepositDTO() {
        return new DepositDTO()
                .setId(id)
                .setCustomerDTO(customer.toCustomerDTO())
                .setTransactionAmount(transactionAmount)
                ;
    }
}
