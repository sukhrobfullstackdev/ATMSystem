package uz.sudev.atmsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.atmsystem.annotations.CheckPermission;
import uz.sudev.atmsystem.entity.Card;
import uz.sudev.atmsystem.payload.CardDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.service.CardService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/card")
public class CardController {
    final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @CheckPermission(value = "VIEW_CARDS")
    @GetMapping
    public ResponseEntity<Page<Card>> getCards(@RequestParam int page, @RequestParam int size) {
        return cardService.getCards(page, size);
    }

    @CheckPermission(value = "VIEW_CARDS")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Card> getCard(@PathVariable UUID id) {
        return cardService.getCard(id);
    }

    @CheckPermission(value = "ADD_CARD")
    @PostMapping
    public ResponseEntity<Message> addCard(@RequestBody CardDTO cardDTO) {
        return cardService.addCard(cardDTO);
    }

    @CheckPermission(value = "EDIT_CARD")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editCard(@PathVariable UUID id, @RequestBody CardDTO cardDTO) {
        return cardService.editCard(id, cardDTO);
    }
    @CheckPermission(value = "DELETE_CARD")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteCard(@PathVariable UUID id) {
        return cardService.deleteCard(id);
    }
}
