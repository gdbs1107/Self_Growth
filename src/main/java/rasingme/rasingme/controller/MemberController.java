package rasingme.rasingme.controller;


import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rasingme.rasingme.Service.MemberService;
import rasingme.rasingme.domain.Member;
import rasingme.rasingme.token.JwtProvider;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    private boolean validateToken(String token) {
        try {
            jwtProvider.parseJwtToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getSubjectFromToken(String token) {
        Claims claims = jwtProvider.parseJwtToken(token);
        return claims.getSubject();
    }

    @PostMapping("/login")
    public ResponseEntity<String> processLogin(@RequestParam("username") String username,
                                               @RequestParam("password") String password) {
        // 로그인 인증 결과를 반환
        if (memberService.authenticate(username, password)) {
            // 인증 성공 시 JWT 토큰 생성
            String token = jwtProvider.createToken(username);
            return ResponseEntity.ok(token);
        } else {
            // 인증 실패 시 401 Unauthorized 응답
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Void> processRegistration(@RequestBody Member member) {
        // 회원 가입 처리
        memberService.join(member);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> withdrawMember(@RequestHeader("Authorization") String token,
                                               @PathVariable("memberId") Long memberId) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        memberService.withdraw(memberId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@RequestHeader("Authorization") String token,
                                             @PathVariable("memberId") Long memberId,
                                             @RequestBody Member updatedMember) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        memberService.update(memberId, updatedMember);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<Member> getMember(@RequestHeader("Authorization") String token,
                                            @PathVariable("memberId") Long memberId) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        Member member = memberService.findById(memberId);
        return ResponseEntity.ok(member);
    }
}