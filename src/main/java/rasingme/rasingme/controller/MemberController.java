package rasingme.rasingme.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import rasingme.rasingme.Service.MemberService;
import rasingme.rasingme.domain.Member;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class    MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public boolean processLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password) {
        // 로그인 인증 결과를 반환
        return memberService.authenticate(username, password);
    }

    @PostMapping("/register")
    public void processRegistration(@RequestBody Member member) {
        // 회원 가입 처리
        memberService.join(member);
    }

    @PostMapping("/withdraw")
    public void withdrawMember(@RequestParam("username") String username) {
        // 회원 탈퇴 처리
        memberService.withdraw(username);
    }

    @PutMapping("/{username}")
    public void updateMember(@PathVariable("username") String username, @RequestBody Member updatedMember) {
        memberService.update(username, updatedMember);
    }

    @GetMapping("/{username}")
    public Member getMember(@PathVariable("username") String username) {
        return memberService.findByUsername(username);
    }
}