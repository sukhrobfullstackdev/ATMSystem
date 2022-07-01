package uz.sudev.atmsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Component;
import uz.sudev.atmsystem.entity.enums.CardType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class CardDTO {
    @Length(min = 16, max = 16, message = "The card number should be 16 numbers!")
    private String cardNumber;
    @NotEmpty(message = "Please select the bank!")
    private Integer bankId;
    @Length(min = 3, max = 3, message = "The cvv code should be 3 numbers!")
    private String cvvCode;
    @Length(min = 3, message = "Please enter client first name!")
    private String firstName;
    @Length(min = 5, message = "Please enter client last name!")
    private String surname;
    @NotNull(message = "Please enter card's expire date!")
    private Timestamp expiresAt;
    @Length(min = 4, max = 4, message = "Please enter card's password!")
    private String password;
    private boolean isBlock = true;
    @NotNull(message = "Please select card type!")
    private CardType cardType;
}
