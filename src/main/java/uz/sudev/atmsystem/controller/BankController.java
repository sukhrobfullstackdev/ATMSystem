package uz.sudev.atmsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.atmsystem.annotations.CheckPermission;
import uz.sudev.atmsystem.entity.Bank;
import uz.sudev.atmsystem.payload.BankDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.service.BankService;

@RestController
@RequestMapping(value = "/bank")
public class BankController {
    final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @CheckPermission(value = "VIEW_BANKS")
    @GetMapping
    public ResponseEntity<Page<Bank>> getBanks(@RequestParam int page, @RequestParam int size) {
        return bankService.getBanks(page, size);
    }

    @CheckPermission(value = "VIEW_BANKS")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Bank> getBank(@PathVariable Integer id) {
        return bankService.getBank(id);
    }

    @CheckPermission(value = "ADD_BANK")
    @PostMapping
    public ResponseEntity<Message> addBank(@RequestBody BankDTO bankDTO) {
        return bankService.addBank(bankDTO);
    }

    @CheckPermission(value = "EDIT_BANK")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editBank(@PathVariable Integer id, @RequestBody BankDTO bankDTO) {
        return bankService.editBank(id, bankDTO);
    }
    @CheckPermission(value = "DELETE_BANK")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteBank(@PathVariable Integer id) {
        return bankService.deleteBank(id);
    }
}
