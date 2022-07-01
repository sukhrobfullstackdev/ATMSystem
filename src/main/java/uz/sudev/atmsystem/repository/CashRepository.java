package uz.sudev.atmsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.atmsystem.entity.Cash;
import uz.sudev.atmsystem.entity.enums.CashType;

@Repository
public interface CashRepository extends JpaRepository<Cash, Integer> {
    boolean existsByCashTypeAndValue(CashType cashType, Double value);
    boolean existsByCashTypeAndValueAndIdNot(CashType cashType, Double value, Integer id);
}
