package com.cg.api;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.transfer.ITransferService;
import com.cg.service.withdraw.IWithdrawService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    private ITransferService transferService;
    @Autowired
    private AppUtils appUtils;
    @Autowired
    private ValidationUtils validationUtils;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer, BindingResult bindingResult) {
        validationUtils.validate(customer, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        customerService.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getById(@PathVariable Long customerId) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        Customer customer = customerOptional.get();

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PatchMapping("/edit/{customerId}")
    public ResponseEntity<?> editCustomer(@PathVariable Long customerId, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        Optional<Customer> customerOptional = customerService.findById(customerId);
        customer.setId(customerId);
        customer.setBalance(customerOptional.get().getBalance());
        customer.setDeleted(customerOptional.get().isDeleted());

        validationUtils.validate(customer, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        customerService.save(customer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/historyDeposits")
    public ResponseEntity<List<Deposit>> getAllDeposits() {
        List<Deposit> deposits = depositService.findAll();
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }

    @PatchMapping("/deposits/{customerId}")
    public ResponseEntity<?> deposit(@PathVariable Long customerId, @RequestBody Deposit deposit) {
        customerService.save(deposit.getCustomer());
        depositService.save(deposit);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }

    @GetMapping("/historyWithdraws")
    public ResponseEntity<List<Withdraw>> getAllWithdraw() {
        List<Withdraw> withdraws = withdrawService.findAll();
        return new ResponseEntity<>(withdraws, HttpStatus.OK);
    }

    @PatchMapping("/withdraws/{customerId}")
    public ResponseEntity<?> withdraw(@PathVariable Long customerId, @RequestBody Withdraw withdraw) {
        customerService.save(withdraw.getCustomer());
        withdrawService.save(withdraw);
        return new ResponseEntity<>(withdraw, HttpStatus.OK);
    }

    @GetMapping("/historyTransfers")
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        List<Transfer> transfers = transferService.findAll();
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }

    @PatchMapping("/transfers/{customerId}")
    public ResponseEntity<?> withdraw(@PathVariable Long customerId, @RequestBody Transfer transfer) {
        customerService.save(transfer.getSender());
        customerService.save(transfer.getRecipient());
        transferService.save(transfer);
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }
}
