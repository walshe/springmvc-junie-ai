package walshe.juniemvc.juniemvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import walshe.juniemvc.juniemvc.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
