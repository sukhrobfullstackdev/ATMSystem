package uz.sudev.atmsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CustomerServicesDTO {
    private String cardNumber;
    private String password;
    private UUID ATMId;
    private Double amount;
}
