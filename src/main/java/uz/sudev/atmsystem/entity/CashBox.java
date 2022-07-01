package uz.sudev.atmsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class CashBox {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(optional = false)
    private Cash cash;
    @Column(nullable = false)
    private Double amount;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ATM atm;

    public CashBox(Cash cash, Double amount, ATM atm) {
        this.cash = cash;
        this.amount = amount;
        this.atm = atm;
    }
}
