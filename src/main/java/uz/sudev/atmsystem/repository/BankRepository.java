package uz.sudev.atmsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.atmsystem.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
