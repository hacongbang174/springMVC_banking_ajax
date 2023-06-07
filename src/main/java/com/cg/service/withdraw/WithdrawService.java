package com.cg.service.withdraw;

import com.cg.model.Withdraw;
import com.cg.repository.IWithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class WithdrawService implements IWithdrawService {

    @Autowired
    private IWithdrawRepository withdrawRepository;

    @Override
    public List<Withdraw> findAll() {
        return withdrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return withdrawRepository.findById(id);
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }

    @Override
    public void delete(Withdraw withdraw) {
        withdrawRepository.delete(withdraw);
    }

    @Override
    public void deleteById(Long id) {
        withdrawRepository.deleteById(id);
    }
}
