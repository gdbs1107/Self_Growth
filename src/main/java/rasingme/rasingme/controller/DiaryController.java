package rasingme.rasingme.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/date")
    public String showDiaryDatePage() {
        return "diaryDate";
    }

    @GetMapping("/{memberId}/{selectedDate}")
    public ResponseEntity<List<Diary>> getDiaries(@RequestHeader("Authorization") String token,
                                                  @PathVariable("memberId") Long memberId,
                                                  @PathVariable("selectedDate") String selectedDate) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(diaryService.getDiariesByMemberIdAndDate(memberId, selectedDate));
    }

    @PostMapping
    public ResponseEntity<Void> saveDiary(@RequestHeader("Authorization") String token,
                                          @RequestParam Long memberId,
                                          @RequestBody Diary diary) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        diaryService.addDiary(diary, memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}/{selectedDate}")
    public ResponseEntity<Void> deleteDiary(@RequestHeader("Authorization") String token,
                                            @PathVariable("memberId") Long memberId,
                                            @PathVariable("selectedDate") String selectedDate) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        diaryService.deleteDiary(memberId, selectedDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<Diary>> getAllDiariesByMemberId(@RequestHeader("Authorization") String token,
                                                               @PathVariable("memberId") Long memberId) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(diaryService.getAllDiariesByMemberId(memberId));
    }

    @PutMapping("/{memberId}/{selectedDate}")
    public ResponseEntity<Void> updateDiary(@RequestHeader("Authorization") String token,
                                            @PathVariable("memberId") Long memberId,
                                            @PathVariable("selectedDate") String selectedDate,
                                            @RequestBody Diary updatedDiary) {
        if (!validateToken(token)) {
            return ResponseEntity.status(401).build();
        }
        diaryService.updateDiary(memberId, selectedDate, updatedDiary);
        return ResponseEntity.ok().build();
    }
}