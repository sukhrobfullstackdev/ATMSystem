package uz.sudev.atmsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sudev.atmsystem.payload.CustomerServicesDTO;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.service.CustomerServicesService;

@RestController
@RequestMapping(value = "/customerServices")
public class CustomerServicesController {

    final CustomerServicesService customerServicesService;

    public CustomerServicesController(CustomerServicesService customerServicesService) {
        this.customerServicesService = customerServicesService;
    }

    @PostMapping(value = "/getCashFromTerminal")
    public ResponseEntity<Message> getCashFromTerminal(@RequestBody CustomerServicesDTO customerServicesDTO) {
        return customerServicesService.getCashFromTerminal(customerServicesDTO);
    }
    @PostMapping(value = "/fillTerminalWithCash")
    public ResponseEntity<Message> fillTerminalWithCash(@RequestBody CustomerServicesDTO customerServicesDTO) {
        return customerServicesService.fillTerminalWithCash(customerServicesDTO);
    }
}
