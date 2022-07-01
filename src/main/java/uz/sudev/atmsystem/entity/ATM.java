package uz.sudev.atmsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.sudev.atmsystem.entity.enums.CardType;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ATM {
    @Id
    @GeneratedValue
    private UUID id;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @Column(nullable = false)
    private Double maxCashAmount;
    @Column(nullable = false)
    private Double thisBankCashCommission;
    @Column(nullable = false)
    private Double thisBankTerminalCommission;
    @Column(nullable = false)
    private Double foreignBankCashCommission;
    @Column(nullable = false)
    private Double foreignBankTerminalCommission;
    @Column(nullable = false)
    private Double minCashAmountToReport;
    @Column(nullable = false, unique = true)
    private String address;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "atm", cascade = CascadeType.ALL)
    private List<CashBox> cashBoxes;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    private Bank bank;
}
