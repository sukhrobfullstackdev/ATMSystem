package uz.sudev.atmsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.atmsystem.entity.ATM;
import uz.sudev.atmsystem.entity.enums.CardType;

import java.util.UUID;

@Repository
public interface ATMRepository extends JpaRepository<ATM, UUID> {
    boolean existsByCardTypeAndAddress(CardType cardType, String address);
    boolean existsByCardTypeAndAddressAndIdNot(CardType cardType, String address, UUID id);
}
