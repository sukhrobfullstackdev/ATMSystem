package uz.sudev.atmsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.atmsystem.entity.ATM;
import uz.sudev.atmsystem.entity.Cash;
import uz.sudev.atmsystem.entity.CashBox;

import java.util.UUID;

@Repository
public interface CashBoxRepository extends JpaRepository<CashBox, UUID> {
    boolean existsByAtmAndCash(ATM atm, Cash cash);
    boolean existsByAtmAndCashAndIdNot(ATM atm, Cash cash, UUID id);
}
