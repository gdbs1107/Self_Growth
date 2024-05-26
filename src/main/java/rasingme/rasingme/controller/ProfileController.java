package rasingme.rasingme.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import rasingme.rasingme.Service.MemberService;

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
