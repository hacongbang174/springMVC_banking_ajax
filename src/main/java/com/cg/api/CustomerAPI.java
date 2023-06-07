package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.service.customer.ICustomerService;
import com.cg.service.deposit.IDepositService;
import com.cg.service.transfer.ITransferService;
import com.cg.service.withdraw.IWithdrawService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtils;
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
    private ValidateUtils validateUtils;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();

        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer, BindingResult bindingResult) {

        new Customer().validate(customer, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existEmail = customerService.existsByEmail(customer.getEmail());

        if (existEmail) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        Boolean existsPhone = customerService.existsByPhone(customer.getPhone());

        if (existsPhone) {
            throw new EmailExistsException("Phone đã tồn tại");
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
    public ResponseEntity<?> editCustomer(@PathVariable String customerId, @Validated @RequestBody Customer customer, BindingResult bindingResult) {
        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }

        new Customer().validate(customer, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng không tồn tại");
        }

        Boolean existEmail = customerService.existsByEmailAndIdIsNot(customer.getEmail(), id);

        if (existEmail) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        Customer updateCustomer = customerOptional.get();

        updateCustomer.setFullName(customer.getFullName());
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setPhone(customer.getPhone());
        updateCustomer.setAddress(customer.getAddress());

        customerService.save(updateCustomer);

        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String customerId) {
        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }
        customerService.deleteById(Long.parseLong(customerId));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/historyDeposits")
    public ResponseEntity<List<Deposit>> getAllDeposits() {
        List<Deposit> deposits = depositService.findAll();
        return new ResponseEntity<>(deposits, HttpStatus.OK);
    }

    @PatchMapping("/deposits/{customerId}")
    public ResponseEntity<?> deposit(@PathVariable String customerId, @RequestBody Deposit deposit,  BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng nộp tiền không hợp lệ");
        }
        new Deposit().validate(deposit, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng nộp tiền không tồn tại");
        }

        if(deposit.getCustomer().getBalance().toString().length() > 12) {
            throw new DataInputException("Vượt quá định mức cho phép. Tổng tiền gửi nhỏ hơn 13 số");
        }

        Customer customer = customerOptional.get();

        customer.setBalance(deposit.getCustomer().getBalance());

        customerService.save(customer);

        depositService.save(deposit);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }

    @GetMapping("/historyWithdraws")
    public ResponseEntity<List<Withdraw>> getAllWithdraw() {
        List<Withdraw> withdraws = withdrawService.findAll();
        return new ResponseEntity<>(withdraws, HttpStatus.OK);
    }

    @PatchMapping("/withdraws/{customerId}")
    public ResponseEntity<?> withdraw(@PathVariable String customerId, @RequestBody Withdraw withdraw, BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng rút tiền không hợp lệ");
        }
        new Withdraw().validate(withdraw, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng rút tiền không tồn tại");
        }

        if(withdraw.getCustomer().getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new DataInputException("Số dư hiện tại không đủ");
        }

        Customer customer = customerOptional.get();

        customer.setBalance(withdraw.getCustomer().getBalance());

        customerService.save(customer);
        withdrawService.save(withdraw);
        return new ResponseEntity<>(withdraw, HttpStatus.OK);
    }

    @GetMapping("/historyTransfers")
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        List<Transfer> transfers = transferService.findAll();
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }

    @PatchMapping("/transfers/{customerId}")
    public ResponseEntity<?> transfer(@PathVariable String customerId, @RequestBody Transfer transfer, BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách chuyển tiền hàng không hợp lệ");
        }
        new Transfer().validate(transfer, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptionalSender = customerService.findById(id);

        if (!customerOptionalSender.isPresent()) {
            throw new DataInputException("Mã khách hàng chuyển tiền không tồn tại");
        }

        if (!validateUtils.isNumberValid(transfer.getRecipient().getId().toString())) {
            throw new DataInputException("Mã khách nhận tiền hàng không hợp lệ");
        }

        Optional<Customer> customerOptionalRecipient = customerService.findById(transfer.getRecipient().getId());

        if (!customerOptionalRecipient.isPresent()) {
            throw new DataInputException("Mã khách hàng nhận tiền không tồn tại");
        }

        Customer sender = customerOptionalSender.get();
        Customer recipient = customerOptionalRecipient.get();

        if(transfer.getSender().getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new DataInputException("Số dư hiện tại không đủ để gửi");
        }

        if(transfer.getRecipient().getBalance().toString().length() > 12) {
            throw new DataInputException("Vượt quá định mức cho phép của người nhận. Tổng định mức nhỏ hơn 13 số");
        }

        sender.setBalance(transfer.getSender().getBalance());
        recipient.setBalance(transfer.getRecipient().getBalance());

        customerService.save(sender);
        customerService.save(recipient);
        transferService.save(transfer);
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }
}
