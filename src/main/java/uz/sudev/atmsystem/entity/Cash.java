package uz.sudev.atmsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.sudev.atmsystem.entity.enums.CashType;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Cash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CashType cashType;
    @Column(nullable = false)
    private Double value;

    public Cash(CashType cashType, Double value) {
        this.cashType = cashType;
        this.value = value;
    }
}
