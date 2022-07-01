package uz.sudev.atmsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.atmsystem.entity.ATM;
import uz.sudev.atmsystem.entity.Cash;
import uz.sudev.atmsystem.entity.CashBox;
import uz.sudev.atmsystem.payload.CashBoxDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.repository.ATMRepository;
import uz.sudev.atmsystem.repository.CashBoxRepository;
import uz.sudev.atmsystem.repository.CashRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CashBoxService {
    final CashBoxRepository cashBoxRepository;
    final ATMRepository atmRepository;
    final CashRepository cashRepository;

    public CashBoxService(CashBoxRepository cashBoxRepository, ATMRepository atmRepository, CashRepository cashRepository) {
        this.cashBoxRepository = cashBoxRepository;
        this.atmRepository = atmRepository;
        this.cashRepository = cashRepository;
    }

    public ResponseEntity<Page<CashBox>> getCashBoxes(int page, int size) {
        return ResponseEntity.ok(cashBoxRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<CashBox> getCashBox(UUID id) {
        Optional<CashBox> optionalCashBox = cashBoxRepository.findById(id);
        return optionalCashBox.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addCashBox(CashBoxDTO cashBoxDTO) {
        Optional<Cash> optionalCash = cashRepository.findById(cashBoxDTO.getCashId());
        Optional<ATM> optionalATM = atmRepository.findById(cashBoxDTO.getAtmId());
        if (optionalCash.isPresent()) {
            if (optionalATM.isPresent()) {
                boolean exists = cashBoxRepository.existsByAtmAndCash(optionalATM.get(), optionalCash.get());
                if (!exists) {
                    cashBoxRepository.save(new CashBox(optionalCash.get(), cashBoxDTO.getAmount(), optionalATM.get()));
                    return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The cash box is successfully created!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The cash box is already exists!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ATM is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash is not found!"));
        }
    }

    public ResponseEntity<Message> editCashBox(UUID id, CashBoxDTO cashBoxDTO) {
        Optional<CashBox> optionalCashBox = cashBoxRepository.findById(id);
        if (optionalCashBox.isPresent()) {
            Optional<Cash> optionalCash = cashRepository.findById(cashBoxDTO.getCashId());
            Optional<ATM> optionalATM = atmRepository.findById(cashBoxDTO.getAtmId());
            if (optionalCash.isPresent()) {
                if (optionalATM.isPresent()) {
                    boolean exists = cashBoxRepository.existsByAtmAndCashAndIdNot(optionalATM.get(), optionalCash.get(), id);
                    if (!exists) {
                        CashBox cashBox = optionalCashBox.get();
                        cashBox.setCash(optionalCash.get());
                        cashBox.setAtm(optionalATM.get());
                        cashBox.setAmount(cashBoxDTO.getAmount());
                        cashBoxRepository.save(cashBox);
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The cash box is successfully edited!"));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The cash box is already exists!"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ATM is not found!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash box is not found!"));
        }
    }

    public ResponseEntity<Message> deleteCashBox(UUID id) {
        Optional<CashBox> optionalCashBox = cashBoxRepository.findById(id);
        if (optionalCashBox.isPresent()) {
            cashBoxRepository.delete(optionalCashBox.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The cash box is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash box is not found!"));
        }
    }
}
