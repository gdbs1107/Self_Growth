package rasingme.rasingme.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rasingme.rasingme.Service.MemberService;
import rasingme.rasingme.domain.Member;

@Controller
public class ProfileController {

    private final MemberService memberService;
    private final HttpServletRequest request;

    public ProfileController(MemberService memberService, HttpServletRequest request) {
        this.memberService = memberService;
        this.request = request;
    }

    @GetMapping()
    public String test(){
        return "home";
    }
}
