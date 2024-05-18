package rasingme.rasingme.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rasingme.rasingme.Service.MemberService;
import rasingme.rasingme.domain.Member;

@RestController
public class ProfileController {

    private final MemberService memberService;
    private final HttpServletRequest request;

    public ProfileController(MemberService memberService, HttpServletRequest request) {
        this.memberService = memberService;
        this.request = request;
    }

//    @GetMapping("/details")
//    public String showProfile(Model model) {
//        Member loggedInUser = (Member) request.getSession().getAttribute("loggedInUser");
//        Member member = memberService.findByUsername(loggedInUser.getUsername());
//        model.addAttribute("member", member);
//        return "details";
//    }
//
//    @PostMapping("/update")
//    @ResponseBody
//    public String updateProfile(@RequestParam("field") String field, @RequestParam("value") String value) {
//        Member loggedInUser = (Member) request.getSession().getAttribute("loggedInUser");
//        Member member = memberService.findByUsername(loggedInUser.getUsername());
//
//        if ("name".equals(field)) {
//            member.setName(value);
//        } else if ("email".equals(field)) {
//            member.setEmail(value);
//        } else if ("birthDate".equals(field)) {
//            member.setBirthDate(value);
//        } else if ("username".equals(field)) {
//            member.setUsername(value);
//        } else if ("password".equals(field)) {
//            member.setPassword(value);
//        }
//
//        // Member 객체를 저장하여 DB에 반영
//        memberService.save(member);
//
//        return "success";
//    }

//    @GetMapping("/profile")
//    public String showProfilePage() {
//        return "profile";
//    }


}
