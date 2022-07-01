package uz.sudev.atmsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import uz.sudev.atmsystem.entity.enums.CardType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class ATMDTO {
    @NotNull(message = "Please enter card type!")
    private CardType cardType;
    @NotEmpty(message = "Please enter the maximum cash amount!")
    private Double maxCashAmount;
    @NotEmpty(message = "Please enter the commission percent to our bank client for cash!")
    private Double thisBankCashCommission;
    @NotEmpty(message = "Please enter the commission percent to our bank client for terminal!")
    private Double thisBankTerminalCommission;
    @NotEmpty(message = "Please enter the commission percent to foreign bank client for cash!")
    private Double foreignBankCashCommission;
    @NotEmpty(message = "Please enter the commission percent to foreign bank client for terminal!")
    private Double foreignBankTerminalCommission;
    @NotEmpty(message = "Please enter the minimum cash amount to report an employee to fill!")
    private Double minCashAmountToReport;
    @NotEmpty(message = "Please enter the ATM's address!")
    private String address;
    private List<UUID> cashBoxesIds;
    @NotEmpty(message = "Please enter the ATM's bank!")
    private Integer bankId;
}
