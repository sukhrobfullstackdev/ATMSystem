package uz.sudev.atmsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uz.sudev.atmsystem.entity.enums.CardType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Card {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false, length = 16)
    private String cardNumber;
    @ManyToOne(optional = false)
    private Bank bank;
    @Column(nullable = false, length = 3)
    private String cvvCode;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(nullable = false)
    private Timestamp expiresAt;
    @Column(nullable = false, length = 4)
    private String password;
    private boolean isBlock;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @Column(nullable = false)
    private Double amount;

    public Card(String cardNumber, Bank bank, String cvvCode, String firstName, String lastName, Timestamp expiresAt, String password, boolean isBlock, CardType cardType) {
        this.cardNumber = cardNumber;
        this.bank = bank;
        this.cvvCode = cvvCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.expiresAt = expiresAt;
        this.password = password;
        this.isBlock = isBlock;
        this.cardType = cardType;
    }

    public String getCardType() {
        return cardType.name();
    }
}
