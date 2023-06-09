package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.model.Transfer;
import com.cg.model.Withdraw;
import com.cg.model.dto.*;
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
import java.util.ArrayList;
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
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerService.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOS.add(customer.toCustomerDTO());
        }
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> findById(@PathVariable String customerId) {
        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CustomerDTO customerDTO = customerOptional.get().toCustomerDTO();

        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerCreateReqDTO customerCreateReqDTO, BindingResult bindingResult) {

        new CustomerCreateReqDTO().validate(customerCreateReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Boolean existEmail = customerService.existsByEmail(customerCreateReqDTO.getEmail());

        if (existEmail) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        Boolean existsPhone = customerService.existsByPhone(customerCreateReqDTO.getPhone());

        if (existsPhone) {
            throw new EmailExistsException("Phone đã tồn tại");
        }
        Customer customer = customerCreateReqDTO.toCustomer(null, BigDecimal.ZERO);
        customerService.save(customer);


        return new ResponseEntity<>(customer.toCustomerCreateResDTO(), HttpStatus.CREATED);
    }

    @PatchMapping("/edit/{customerId}")
    public ResponseEntity<?> update(@PathVariable String customerId, @Validated @RequestBody CustomerUpdateReqDTO customerUpdateReqDTO, BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng không hợp lệ");
        }

        new CustomerCreateReqDTO().validate(customerUpdateReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng không tồn tại");
        }

        Boolean existEmail = customerService.existsByEmailAndIdIsNot(customerUpdateReqDTO.getEmail(), id);

        if (existEmail) {
            throw new EmailExistsException("Email đã tồn tại");
        }

        Customer customer = customerUpdateReqDTO.toCustomer();
        Customer customerUpdate = customerOptional.get();

        customer.setId(customerUpdate.getId());
        customer.setBalance(customerOptional.get().getBalance());

        customerService.save(customer);


        return new ResponseEntity<>(customer.toCustomerUpdateResDTO(), HttpStatus.OK);
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
    public ResponseEntity<?> deposit(@PathVariable String customerId, @RequestBody DepositReqDTO depositReqDTO,  BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng nộp tiền không hợp lệ");
        }
        new DepositReqDTO().validate(depositReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng nộp tiền không tồn tại");
        }
        Customer customer = customerOptional.get();
        CustomerDTO customerDTO = customer.toCustomerDTO();

        BigDecimal newBalance = customer.getBalance().add(depositReqDTO.getTransactionAmount());

        if(newBalance.toString().length() > 12) {
            throw new DataInputException("Vượt quá định mức cho phép. Tổng tiền gửi nhỏ hơn 13 số");
        }

        customerDTO.setBalance(newBalance);

        Deposit deposit = depositReqDTO.toDeposit(null, customerDTO);

        customerService.save(customerDTO.toCustomer());
        depositService.save(deposit);
        return new ResponseEntity<>(deposit, HttpStatus.OK);
    }

    @GetMapping("/historyWithdraws")
    public ResponseEntity<List<Withdraw>> getAllWithdraw() {
        List<Withdraw> withdraws = withdrawService.findAll();
        return new ResponseEntity<>(withdraws, HttpStatus.OK);
    }

    @PatchMapping("/withdraws/{customerId}")
    public ResponseEntity<?> withdraw(@PathVariable String customerId, @RequestBody WithdrawReqDTO withdrawReqDTO, BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách hàng rút tiền không hợp lệ");
        }
        new WithdrawReqDTO().validate(withdrawReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptional = customerService.findById(id);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("Mã khách hàng rút tiền không tồn tại");
        }
        Customer customer = customerOptional.get();
        CustomerDTO customerDTO = customer.toCustomerDTO();

        BigDecimal newBalance = customer.getBalance().subtract(withdrawReqDTO.getTransactionAmount());

        if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new DataInputException("Số dư hiện tại không đủ");
        }

        customerDTO.setBalance(newBalance);

        Withdraw withdraw = withdrawReqDTO.toWithdraw(null, customerDTO);

        customerService.save(customerDTO.toCustomer());
        withdrawService.save(withdraw);
        return new ResponseEntity<>(withdraw, HttpStatus.OK);
    }

    @GetMapping("/historyTransfers")
    public ResponseEntity<List<Transfer>> getAllTransfers() {
        List<Transfer> transfers = transferService.findAll();
        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }

    @PatchMapping("/transfers/{customerId}")
    public ResponseEntity<?> transfer(@PathVariable String customerId, @RequestBody TransferReqDTO transferReqDTO, BindingResult bindingResult) {

        if (!validateUtils.isNumberValid(customerId)) {
            throw new DataInputException("Mã khách chuyển tiền hàng không hợp lệ");
        }
        new TransferReqDTO().validate(transferReqDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        Long id = Long.parseLong(customerId);

        Optional<Customer> customerOptionalSender = customerService.findById(id);

        if (!customerOptionalSender.isPresent()) {
            throw new DataInputException("Mã khách hàng chuyển tiền không tồn tại");
        }

        if (!validateUtils.isNumberValid(transferReqDTO.getRecipientId().toString())) {
            throw new DataInputException("Mã khách nhận tiền hàng không hợp lệ");
        }

        Optional<Customer> customerOptionalRecipient = customerService.findById(transferReqDTO.getRecipientId());

        if (!customerOptionalRecipient.isPresent()) {
            throw new DataInputException("Mã khách hàng nhận tiền không tồn tại");
        }

        Customer sender = customerOptionalSender.get();
        CustomerDTO senderDTO = sender.toCustomerDTO();
        Customer recipient = customerOptionalRecipient.get();
        CustomerDTO recipientDTO = recipient.toCustomerDTO();

        BigDecimal newBalanceSender = sender.getBalance().subtract(transferReqDTO.getTransactionAmount());
        BigDecimal newBalanceRecipient = recipient.getBalance().add(transferReqDTO.getTransferAmount());

        if(newBalanceSender.compareTo(BigDecimal.ZERO) < 0) {
            throw new DataInputException("Số dư hiện tại không đủ để gửi");
        }

        if(newBalanceRecipient.toString().length() > 12) {
            throw new DataInputException("Vượt quá định mức cho phép của người nhận. Tổng định mức nhỏ hơn 13 số");
        }

        senderDTO.setBalance(newBalanceSender);
        recipientDTO.setBalance(newBalanceRecipient);
        Transfer transfer = transferReqDTO.toTransfer(null, senderDTO, recipientDTO);
        customerService.save(senderDTO.toCustomer());
        customerService.save(recipientDTO.toCustomer());
        transferService.save(transfer);
        return new ResponseEntity<>(transfer, HttpStatus.OK);
    }
}
