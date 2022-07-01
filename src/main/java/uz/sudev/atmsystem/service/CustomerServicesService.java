package uz.sudev.atmsystem.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.atmsystem.entity.ATM;
import uz.sudev.atmsystem.entity.Card;
import uz.sudev.atmsystem.payload.CustomerServicesDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.repository.ATMRepository;
import uz.sudev.atmsystem.repository.CardRepository;

import java.util.Optional;

@Service
public class CustomerServicesService {
    final CardRepository cardRepository;
    final ATMRepository atmRepository;

    public CustomerServicesService(CardRepository cardRepository, ATMRepository atmRepository) {
        this.cardRepository = cardRepository;
        this.atmRepository = atmRepository;
    }

    public ResponseEntity<Message> getCashFromTerminal(CustomerServicesDTO customerServicesDTO) {
        Optional<ATM> optionalATM = atmRepository.findById(customerServicesDTO.getATMId());
        if (optionalATM.isPresent()) {
            Optional<Card> optionalCard = cardRepository.findByPasswordAndCardNumber(customerServicesDTO.getPassword(), customerServicesDTO.getCardNumber());
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                ATM atm = optionalATM.get();
                if (card.isBlock()) {
                    if (card.getAmount() > customerServicesDTO.getAmount() * (atm.getThisBankCashCommission() + 1)) {
                        card.setAmount(card.getAmount() - (customerServicesDTO.getAmount() * (atm.getThisBankCashCommission() + 1)));
                        return ResponseEntity.status(HttpStatus.OK).body(new Message(true, "The operation was successful!", customerServicesDTO.getAmount()));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Not enough money!"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The card is blocked!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The password is incorrect!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ATM is not found!"));
        }
    }

    public ResponseEntity<Message> fillTerminalWithCash(CustomerServicesDTO customerServicesDTO) {
        Optional<ATM> optionalATM = atmRepository.findById(customerServicesDTO.getATMId());
        if (optionalATM.isPresent()) {
            Optional<Card> optionalCard = cardRepository.findByPasswordAndCardNumber(customerServicesDTO.getPassword(), customerServicesDTO.getCardNumber());
            if (optionalCard.isPresent()) {
                Card card = optionalCard.get();
                ATM atm = optionalATM.get();
                if (card.isBlock()) {
                    if (card.getAmount() > customerServicesDTO.getAmount() * (atm.getThisBankCashCommission() + 1)) {
                        card.setAmount(card.getAmount() + customerServicesDTO.getAmount() - (atm.getThisBankCashCommission() + 1));
                        return ResponseEntity.status(HttpStatus.OK).body(new Message(true, "The operation was successful!"));
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Not enough money!"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The card is blocked!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The password is incorrect!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ATM is not found!"));
        }
    }
}
