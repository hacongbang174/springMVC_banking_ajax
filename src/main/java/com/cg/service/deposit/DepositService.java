package com.cg.service.deposit;

import com.cg.model.Deposit;
import com.cg.repository.IDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DepositService implements IDepositService {

    @Autowired
    private IDepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {
        return depositRepository.findAll();
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return depositRepository.findById(id);
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void delete(Deposit deposit) {
        depositRepository.delete(deposit);
    }

    @Override
    public void deleteById(Long id) {
        depositRepository.deleteById(id);
    }
}
