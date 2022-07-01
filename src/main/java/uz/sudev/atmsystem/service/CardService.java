package uz.sudev.atmsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sudev.atmsystem.entity.Bank;
import uz.sudev.atmsystem.entity.Card;
import uz.sudev.atmsystem.payload.CardDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.repository.BankRepository;
import uz.sudev.atmsystem.repository.CardRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CardService {
    final CardRepository cardRepository;
    final PasswordEncoder passwordEncoder;
    final BankRepository bankRepository;

    public CardService(CardRepository cardRepository, PasswordEncoder passwordEncoder, BankRepository bankRepository) {
        this.cardRepository = cardRepository;
        this.passwordEncoder = passwordEncoder;
        this.bankRepository = bankRepository;
    }

    public ResponseEntity<Page<Card>> getCards(int page, int size) {
        return ResponseEntity.ok(cardRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Card> getCard(UUID id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        return optionalCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addCard(CardDTO cardDTO) {
        if (!cardRepository.existsByCardNumber(cardDTO.getCardNumber())) {
            Optional<Bank> optionalBank = bankRepository.findById(cardDTO.getBankId());
            if (optionalBank.isPresent()) {
                cardRepository.save(new Card(cardDTO.getCardNumber(), optionalBank.get(), cardDTO.getCvvCode(), cardDTO.getFirstName(), cardDTO.getSurname(), cardDTO.getExpiresAt(), passwordEncoder.encode(cardDTO.getPassword()), cardDTO.isBlock(), cardDTO.getCardType()));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The bank is successfully saved!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The bank is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The card number is already in use!"));
        }
    }

    public ResponseEntity<Message> editCard(UUID id, CardDTO cardDTO) {
        if (!cardRepository.existsByCardNumberAndIdNot(cardDTO.getCardNumber(), id)) {
            Optional<Card> optionalCard = cardRepository.findById(id);
            Optional<Bank> optionalBank = bankRepository.findById(cardDTO.getBankId());
            if (optionalCard.isPresent()) {
                if (optionalBank.isPresent()) {
                    Card card = optionalCard.get();
                    card.setCardNumber(cardDTO.getCardNumber());
                    card.setCardType(cardDTO.getCardType());
                    card.setBank(optionalBank.get());
                    card.setBlock(cardDTO.isBlock());
                    card.setCvvCode(cardDTO.getCvvCode());
                    card.setExpiresAt(cardDTO.getExpiresAt());
                    card.setFirstName(cardDTO.getFirstName());
                    card.setLastName(cardDTO.getSurname());
                    card.setPassword(passwordEncoder.encode(cardDTO.getPassword()));
                    cardRepository.save(card);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The card is successfully saved!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected bank is not found!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The card is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The card number is already in use!"));
        }
    }

    public ResponseEntity<Message> deleteCard(UUID id) {
        Optional<Card> optionalCard = cardRepository.findById(id);
        if (optionalCard.isPresent()) {
            cardRepository.delete(optionalCard.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The card is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The card is not found!"));
        }
    }
}
