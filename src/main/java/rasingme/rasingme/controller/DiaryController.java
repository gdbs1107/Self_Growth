package rasingme.rasingme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rasingme.rasingme.Service.DiaryService;
import rasingme.rasingme.domain.Diary;
import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @GetMapping("/date")
    public String showDiaryDatePage() {
        return "diaryDate";
    }

    @GetMapping("/{memberId}/{selectedDate}")
    public List<Diary> getDiaries(@PathVariable("memberId") Long memberId, @PathVariable("selectedDate") String selectedDate) {
        return diaryService.getDiariesByMemberIdAndDate(memberId, selectedDate);
    }

    @PostMapping
    public void saveDiary(@RequestParam Long memberId, @RequestBody Diary diary) {
        diaryService.addDiary(diary, memberId);
    }

    @GetMapping("/{memberId}")
    public List<Diary> getAllDiariesByMemberId(@PathVariable("memberId") Long memberId) {
        return diaryService.getAllDiariesByMemberId(memberId);
    }

    @PutMapping("/{memberId}/{selectedDate}")
    public void updateDiary(@PathVariable("memberId") Long memberId, @PathVariable("selectedDate") String selectedDate, @RequestBody Diary updatedDiary) {
        diaryService.updateDiary(memberId, selectedDate, updatedDiary);
    }

    @DeleteMapping("/{memberId}")
    public void deleteDiary(@PathVariable("memberId") Long memberId, @RequestParam("date") String dateStr) {
        diaryService.deleteDiary(memberId, dateStr);
    }
}