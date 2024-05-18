package rasingme.rasingme.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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

    public Member findByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void withdraw(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

    public boolean authenticate(String username, String password) {
        Member member = memberRepository.findByUsername(username)
                .orElse(null);
        return member != null && member.getPassword().equals(password);
    }

    // 개인정보 수정 기능 추가
    public Member update(Member updatedMember) {
        Member member = memberRepository.findByUsername(updatedMember.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        member.setName(updatedMember.getName());
        member.setEmail(updatedMember.getEmail());
        member.setBirthDate(updatedMember.getBirthDate());
        member.setUsername(updatedMember.getUsername());
        member.setPassword(updatedMember.getPassword());
        return memberRepository.save(member);
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    // ID로 회원 조회
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // 이메일, 아이디 등으로 회원 조회하는 메소드 등을 추가할 수 있습니다.
}
