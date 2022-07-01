package uz.sudev.atmsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.atmsystem.entity.ATM;
import uz.sudev.atmsystem.entity.Bank;
import uz.sudev.atmsystem.entity.CashBox;
import uz.sudev.atmsystem.payload.ATMDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.repository.ATMRepository;
import uz.sudev.atmsystem.repository.BankRepository;
import uz.sudev.atmsystem.repository.CashBoxRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ATMService {
    final ATMRepository atmRepository;
    final BankRepository bankRepository;
    final CashBoxRepository cashBoxRepository;

    public ATMService(BankRepository bankRepository, ATMRepository atmRepository, CashBoxRepository cashBoxRepository) {
        this.atmRepository = atmRepository;
        this.bankRepository = bankRepository;
        this.cashBoxRepository = cashBoxRepository;
    }

    public ResponseEntity<Page<ATM>> getATMs(int page, int size) {
        return ResponseEntity.ok(atmRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<ATM> getATM(UUID id) {
        Optional<ATM> optionalATM = atmRepository.findById(id);
        return optionalATM.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addATM(ATMDTO atmdto) {
        if (!atmRepository.existsByCardTypeAndAddress(atmdto.getCardType(), atmdto.getAddress())) {
            Optional<Bank> optionalBank = bankRepository.findById(atmdto.getBankId());
            if (optionalBank.isPresent()) {
                ATM atm = new ATM();
                atm.setAddress(atmdto.getAddress());
                atm.setBank(optionalBank.get());
                atm.setCardType(atmdto.getCardType());
                atm.setForeignBankCashCommission(atmdto.getForeignBankCashCommission());
                atm.setForeignBankTerminalCommission(atmdto.getForeignBankTerminalCommission());
                atm.setMaxCashAmount(atmdto.getMaxCashAmount());
                atm.setMinCashAmountToReport(atmdto.getMinCashAmountToReport());
                atm.setThisBankCashCommission(atmdto.getThisBankCashCommission());
                atm.setThisBankTerminalCommission(atmdto.getThisBankTerminalCommission());
                if (!atmdto.getCashBoxesIds().isEmpty()) {
                    List<CashBox> cashBoxes = new ArrayList<>();
                    for (UUID cashBoxId : atmdto.getCashBoxesIds()) {
                        Optional<CashBox> optionalCashBox = cashBoxRepository.findById(cashBoxId);
                        if (optionalCashBox.isPresent()) {
                            cashBoxes.add(optionalCashBox.get());
                        } else {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash box which has id " + cashBoxId + " is not found!"));
                        }
                    }
                    atm.setCashBoxes(cashBoxes);
                }
                atmRepository.save(atm);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The ATM is successfully saved!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected bank is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The ATM which works with this card type in this address is already exists!"));
        }
    }

    public ResponseEntity<Message> editATM(UUID id, ATMDTO atmdto) {
        Optional<ATM> optionalATM = atmRepository.findById(id);
        if (optionalATM.isPresent()) {
            Optional<Bank> optionalBank = bankRepository.findById(atmdto.getBankId());
            if (optionalBank.isPresent()) {
                if (atmRepository.existsByCardTypeAndAddressAndIdNot(atmdto.getCardType(), atmdto.getAddress(), id)) {
                    ATM atm = optionalATM.get();
                    atm.setAddress(atmdto.getAddress());
                    atm.setBank(optionalBank.get());
                    atm.setCardType(atmdto.getCardType());
                    atm.setForeignBankCashCommission(atmdto.getForeignBankCashCommission());
                    atm.setForeignBankTerminalCommission(atmdto.getForeignBankTerminalCommission());
                    atm.setMaxCashAmount(atmdto.getMaxCashAmount());
                    atm.setMinCashAmountToReport(atmdto.getMinCashAmountToReport());
                    atm.setThisBankCashCommission(atmdto.getThisBankCashCommission());
                    atm.setThisBankTerminalCommission(atmdto.getThisBankTerminalCommission());
                    if (!atmdto.getCashBoxesIds().isEmpty()) {
                        List<CashBox> cashBoxes = new ArrayList<>();
                        for (UUID cashBoxId : atmdto.getCashBoxesIds()) {
                            Optional<CashBox> optionalCashBox = cashBoxRepository.findById(cashBoxId);
                            if (optionalCashBox.isPresent()) {
                                cashBoxes.add(optionalCashBox.get());
                            } else {
                                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The cash box which has id " + cashBoxId + " is not found!"));
                            }
                        }
                        atm.setCashBoxes(cashBoxes);
                    }
                    atmRepository.save(atm);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The ATM is successfully saved!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The ATM which works with this card type in this address is already exists!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected bank is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ATM is not found!"));
        }
    }

    public ResponseEntity<Message> deleteATM(UUID id) {
        Optional<ATM> optionalATM = atmRepository.findById(id);
        if (optionalATM.isPresent()) {
            atmRepository.delete(optionalATM.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The ATM is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ATM is not found!"));
        }
    }
}
