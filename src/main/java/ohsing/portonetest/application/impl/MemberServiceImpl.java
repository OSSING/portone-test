package ohsing.portonetest.application.impl;

import lombok.RequiredArgsConstructor;
import ohsing.portonetest.application.service.MemberService;
import ohsing.portonetest.domain.entity.Member;
import ohsing.portonetest.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member autoRegister() {
        Member member = Member.builder()
                .username(UUID.randomUUID().toString())
                .email("example@example.com")
                .address("대전광역시 서구 변동")
                .build();

        return memberRepository.save(member);
    }
}
