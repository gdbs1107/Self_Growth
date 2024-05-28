package rasingme.rasingme.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rasingme.rasingme.Service.DiaryService;
import rasingme.rasingme.domain.Diary;
import rasingme.rasingme.token.JwtProvider;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final JwtProvider jwtProvider;

    @GetMapping("/date")
    public String showDiaryDatePage() {
        return "diaryDate";
    }

    @GetMapping("/{memberId}/{selectedDate}")
    public List<Diary> getDiaries(@PathVariable("memberId") Long memberId, @PathVariable("selectedDate") String selectedDate, HttpServletRequest request) {
        Claims claims = validateToken(request);
        return diaryService.getDiariesByMemberIdAndDate(memberId, selectedDate);
    }

    @PostMapping
    public void saveDiary(@RequestParam Long memberId, @RequestBody Diary diary, HttpServletRequest request) {
        Claims claims = validateToken(request);
        diaryService.addDiary(diary, memberId);
    }

    @DeleteMapping("/{memberId}/{selectedDate}")
    public void deleteDiary(@PathVariable("memberId") Long memberId, @PathVariable("selectedDate") String selectedDate, HttpServletRequest request) {
        Claims claims = validateToken(request);
        diaryService.deleteDiary(memberId, selectedDate);
    }

    @GetMapping("/{memberId}")
    public List<Diary> getAllDiariesByMemberId(@PathVariable("memberId") Long memberId, HttpServletRequest request) {
        Claims claims = validateToken(request);
        return diaryService.getAllDiariesByMemberId(memberId);
    }

    @PutMapping("/{memberId}/{selectedDate}")
    public void updateDiary(@PathVariable("memberId") Long memberId, @PathVariable("selectedDate") String selectedDate, @RequestBody Diary updatedDiary,HttpServletRequest request) {
        Claims claims = validateToken(request);
        diaryService.updateDiary(memberId, selectedDate, updatedDiary);
    }

    private Claims validateToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid or missing token");
        }
        return jwtProvider.parseJwtToken(token);
    }

}