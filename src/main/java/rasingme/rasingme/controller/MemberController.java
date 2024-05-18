package rasingme.rasingme.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rasingme.rasingme.Service.MemberService;
import rasingme.rasingme.domain.Member;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @PutMapping("/{id}")
    public void updateMember(@PathVariable("id") Long id, @RequestBody Member updatedMember) {
        // 회원 정보 업데이트 처리
        Member member = memberService.findById(id);
        if (member != null) {
            member.setName(updatedMember.getName());
            member.setEmail(updatedMember.getEmail());
            member.setBirthDate(updatedMember.getBirthDate());
            member.setUsername(updatedMember.getUsername());
            member.setPassword(updatedMember.getPassword());

            memberService.update(member);
        }
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable("id") Long id) {
        // 특정 ID의 회원 정보 반환
        return memberService.findById(id);
    }
}