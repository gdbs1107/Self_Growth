package rasingme.rasingme;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import rasingme.rasingme.Service.MemberService;
import rasingme.rasingme.domain.Member;
import rasingme.rasingme.repository.MemberRepository;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test()
    public void memberTest(){
        Member memberA = new Member();
        Member memberB = new Member();
        memberA.setUsername("a");
        memberB.setUsername("a");
        memberA.setEmail("fmkl");

        memberService.join(memberA);
        memberService.join(memberB);

    }




}
