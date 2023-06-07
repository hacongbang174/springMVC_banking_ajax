package com.cg.service.transfer;


import com.cg.model.Transfer;
import com.cg.repository.ITransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransferService implements ITransferService {

    @Autowired
    private ITransferRepository transferRepository;

    @Override
    public List<Transfer> findAll() {
        return transferRepository.findAll();
    }

    @Override
    public Optional<Transfer> findById(Long id) {
        return transferRepository.findById(id);
    }

    @Override
    public Transfer save(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public void delete(Transfer transfer) {
        transferRepository.delete(transfer);
    }

    @Override
    public void deleteById(Long id) {
        transferRepository.deleteById(id);
    }
}
