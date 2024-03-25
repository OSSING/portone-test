package ohsing.portonetest.domain.repository;

import ohsing.portonetest.domain.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
