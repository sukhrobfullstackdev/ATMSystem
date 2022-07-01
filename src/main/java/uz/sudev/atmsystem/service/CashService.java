package uz.sudev.atmsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.atmsystem.entity.Cash;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.repository.CashRepository;

import java.util.Optional;

@Service
public class CashService {
    final CashRepository cashRepository;

    public CashService(CashRepository cashRepository) {
        this.cashRepository = cashRepository;
    }

    public ResponseEntity<Page<Cash>> getCashes(int page, int size) {
        return ResponseEntity.ok(cashRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Cash> getCash(Integer id) {
        Optional<Cash> optionalCash = cashRepository.findById(id);
        return optionalCash.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addCash(Cash cash) {
        if (!cashRepository.existsByCashTypeAndValue(cash.getCashType(), cash.getValue())) {
            cashRepository.save(new Cash(cash.getCashType(), cash.getValue()));
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The cash type is successfully saved!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The cash type is already exists!"));
        }
    }

    public ResponseEntity<Message> editCash(Integer id, Cash cash) {
        if (!cashRepository.existsByCashTypeAndValueAndIdNot(cash.getCashType(), cash.getValue(), id)) {
            Optional<Cash> optionalCash = cashRepository.findById(id);
            if (optionalCash.isPresent()) {
                Cash editingCash = optionalCash.get();
                editingCash.setValue(cash.getValue());
                editingCash.setCashType(cash.getCashType());
                cashRepository.save(editingCash);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The cash type is successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash type is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The cash type is already exists!"));
        }
    }

    public ResponseEntity<Message> deleteCash(Integer id) {
        Optional<Cash> optionalCash = cashRepository.findById(id);
        if (optionalCash.isPresent()) {
            cashRepository.delete(optionalCash.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The cash type is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash type is not found!"));
        }
    }
}
