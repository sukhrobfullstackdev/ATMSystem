package uz.sudev.atmsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.atmsystem.annotations.CheckPermission;
import uz.sudev.atmsystem.entity.ATM;
import uz.sudev.atmsystem.payload.ATMDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.service.ATMService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/atm")
public class ATMController {
    final ATMService atmService;

    public ATMController(ATMService atmService) {
        this.atmService = atmService;
    }

    @CheckPermission(value = "VIEW_ATMS")
    @GetMapping
    public ResponseEntity<Page<ATM>> getATMs(@RequestParam int page, @RequestParam int size) {
        return atmService.getATMs(page, size);
    }

    @CheckPermission(value = "VIEW_ATMS")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ATM> getATM(@PathVariable UUID id) {
        return atmService.getATM(id);
    }

    @CheckPermission(value = "ADD_ATM")
    @PostMapping
    public ResponseEntity<Message> addATM(@RequestBody ATMDTO atmdto) {
        return atmService.addATM(atmdto);
    }

    @CheckPermission(value = "EDIT_ATM")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editATM(@PathVariable UUID id, @RequestBody ATMDTO atmdto) {
        return atmService.editATM(id,atmdto);
    }
    @CheckPermission(value = "DELETE_ATM")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteATM(@PathVariable UUID id) {
        return atmService.deleteATM(id);
    }
}
