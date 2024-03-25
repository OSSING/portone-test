package ohsing.portonetest.domain.repository;

import ohsing.portonetest.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
