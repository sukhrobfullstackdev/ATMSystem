package uz.sudev.atmsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.atmsystem.annotations.CheckPermission;
import uz.sudev.atmsystem.entity.CashBox;
import uz.sudev.atmsystem.payload.CashBoxDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.service.CashBoxService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/cashBox")
public class CashBoxController {
    final CashBoxService cashBoxService;

    public CashBoxController(CashBoxService cashBoxService) {
        this.cashBoxService = cashBoxService;
    }

    @CheckPermission(value = "VIEW_CASH_BOXES")
    @GetMapping
    public ResponseEntity<Page<CashBox>> getCashBoxes(@RequestParam int page, @RequestParam int size) {
        return cashBoxService.getCashBoxes(page, size);
    }

    @CheckPermission(value = "VIEW_CASH_BOXES")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CashBox> getCashBox(@PathVariable UUID id) {
        return cashBoxService.getCashBox(id);
    }

    @CheckPermission(value = "ADD_CASH_BOX")
    @PostMapping
    public ResponseEntity<Message> addCashBox(@RequestBody CashBoxDTO cashBoxDTO) {
        return cashBoxService.addCashBox(cashBoxDTO);
    }

    @CheckPermission(value = "EDIT_CASH_BOX")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editCashBox(@PathVariable UUID id, @RequestBody CashBoxDTO cashBoxDTO) {
        return cashBoxService.editCashBox(id, cashBoxDTO);
    }
    @CheckPermission(value = "DELETE_CASH_BOX")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteCashBox(@PathVariable UUID id) {
        return cashBoxService.deleteCashBox(id);
    }
}
