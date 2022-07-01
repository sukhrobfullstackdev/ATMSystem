package uz.sudev.atmsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.atmsystem.entity.Card;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID> {
    boolean existsByCardNumber(String cardNumber);
    boolean existsByCardNumberAndIdNot(String cardNumber, UUID id);
    Optional<Card> findByPasswordAndCardNumber(String password, String cardNumber);
}
