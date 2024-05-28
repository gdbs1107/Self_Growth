package rasingme.rasingme.controller;


import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import rasingme.rasingme.Service.MemberService;
import rasingme.rasingme.domain.Member;
import rasingme.rasingme.token.JwtProvider;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class    MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        if (memberService.authenticate(username, password)) {
            // 로그인 인증 결과가 성공이면 토큰을 발급
            return jwtProvider.createToken(username);
        } else {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public void processRegistration(@RequestBody Member member) {
        // 회원 가입 처리
        memberService.join(member);
    }

    @DeleteMapping("/{memberId}")
    public void withdrawMember(@PathVariable("memberId") Long memberId, HttpServletRequest request) {
        // 회원 탈퇴 처리 전에 토큰 검증
        Claims claims = validateToken(request);
        memberService.withdraw(memberId);
    }


    @PutMapping("/{memberId}")
    public void updateMember(@PathVariable("memberId") Long memberId, @RequestBody Member updatedMember, HttpServletRequest request) {
        Claims claims = validateToken(request);
        memberService.update(memberId, updatedMember);
    }

    @GetMapping("/{memberId}")
    public Member getMember(@PathVariable("memberId") Long memberId,HttpServletRequest request) {
        Claims claims = validateToken(request);
        return memberService.findById(memberId);
    }

    private Claims validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing token");
        }
        return jwtProvider.parseJwtToken(token);
    }
}