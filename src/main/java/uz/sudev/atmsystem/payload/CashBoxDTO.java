package uz.sudev.atmsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CashBoxDTO {
    @NotEmpty(message = "Please select the cash!")
    private Integer cashId;
    @NotBlank(message = "Please enter the amount!")
    private Double amount;
    @NotEmpty(message = "Please select the ATM!")
    private UUID atmId;
}
