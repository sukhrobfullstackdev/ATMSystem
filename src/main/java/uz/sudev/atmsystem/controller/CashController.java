package uz.sudev.atmsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.atmsystem.annotations.CheckPermission;
import uz.sudev.atmsystem.entity.Cash;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.service.CashService;

@RestController
@RequestMapping(value = "/cash")
public class CashController {
    final CashService cashService;

    public CashController(CashService cashService) {
        this.cashService = cashService;
    }

    @CheckPermission(value = "VIEW_CASHES")
    @GetMapping
    public ResponseEntity<Page<Cash>> getCashes(@RequestParam int page, @RequestParam int size) {
        return cashService.getCashes(page, size);
    }

    @CheckPermission(value = "VIEW_CASHES")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Cash> getCash(@PathVariable Integer id) {
        return cashService.getCash(id);
    }

    @CheckPermission(value = "ADD_CASH")
    @PostMapping
    public ResponseEntity<Message> addCash(@RequestBody Cash cash) {
        return cashService.addCash(cash);
    }

    @CheckPermission(value = "EDIT_CASH")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editCash(@PathVariable Integer id, @RequestBody Cash cash) {
        return cashService.editCash(id, cash);
    }
    @CheckPermission(value = "DELETE_CASH")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteCash(@PathVariable Integer id) {
        return cashService.deleteCash(id);
    }
}
