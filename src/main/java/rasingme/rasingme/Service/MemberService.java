package rasingme.rasingme.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rasingme.rasingme.domain.Member;
import rasingme.rasingme.repository.MemberRepository;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor

public class MemberService {

    private final MemberRepository memberRepository;
    private final Validator validator;

    //회원가입 로직
    public Member join(Member member) {

        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 중복된 아이디 체크
        if (memberRepository.existsByUsername(member.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 이름입니다.");
        }

        // Validate member object
        Set<ConstraintViolation<Member>> violations = validator.validate(member);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ConstraintViolation<Member> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        return memberRepository.save(member);
    }

    //회원조회 로직
    // 회원조회 로직
    public Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    // 회원삭제 로직
    @Transactional
    public void withdraw(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

    //로그인 로직
    public boolean authenticate(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElse(null);
        return member != null && member.getPassword().equals(password);
    }

    // 개인정보 수정 로직
    public Member update(Long memberId, Member updatedMember) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        member.setName(updatedMember.getName());
        member.setEmail(updatedMember.getEmail());
        member.setBirthDate(updatedMember.getBirthDate());
        member.setUsername(updatedMember.getUsername());
        member.setPassword(updatedMember.getPassword());
        return memberRepository.save(member);
    }

}
