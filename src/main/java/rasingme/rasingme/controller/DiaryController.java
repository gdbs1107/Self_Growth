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

    @GetMapping("/{username}/{selectedDate}")
    public List<Diary> getDiaries(@PathVariable("username") String username, @PathVariable("selectedDate") String selectedDate) {
        return diaryService.getDiariesByUsernameAndDate(username, selectedDate);
    }

    @PostMapping
    public void saveDiary(@RequestParam String username, @RequestBody Diary diary) {
        diaryService.addDiary(diary, username);
    }

    @GetMapping("/{username}")
    public List<Diary> getAllDiariesByUsername(@PathVariable("username") String username) {
        return diaryService.getAllDiariesByUsername(username);
    }

    @PutMapping("/{username}/{selectedDate}")
    public void updateDiary(@PathVariable("username") String username, @PathVariable("selectedDate") String selectedDate, @RequestBody Diary updatedDiary) {
        diaryService.updateDiary(username, selectedDate, updatedDiary);
    }
}