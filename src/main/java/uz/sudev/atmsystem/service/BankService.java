package uz.sudev.atmsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.atmsystem.entity.Bank;
import uz.sudev.atmsystem.payload.BankDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.repository.BankRepository;

import java.util.Optional;

@Service
public class BankService {
    final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public ResponseEntity<Page<Bank>> getBanks(int page, int size) {
        return ResponseEntity.ok(bankRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Bank> getBank(Integer id) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
        return optionalBank.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addBank(BankDTO bankDTO) {
        if (!bankRepository.existsByName(bankDTO.getName())) {
            bankRepository.save(new Bank(bankDTO.getName()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The bank is successfully saved!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The bank is already exists!"));
        }
    }

    public ResponseEntity<Message> editBank(Integer id, BankDTO bankDTO) {
        if (!bankRepository.existsByNameAndIdNot(bankDTO.getName(), id)) {
            Optional<Bank> optionalBank = bankRepository.findById(id);
            if (optionalBank.isPresent()) {
                Bank bank = optionalBank.get();
                bank.setName(bank.getName());
                bankRepository.save(bank);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The bank is successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The bank is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The bank is already exists!"));
        }
    }

    public ResponseEntity<Message> deleteBank(Integer id) {
        Optional<Bank> optionalBank = bankRepository.findById(id);
        if (optionalBank.isPresent()) {
            bankRepository.delete(optionalBank.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The bank is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The bank is not found!"));
        }
    }
}
