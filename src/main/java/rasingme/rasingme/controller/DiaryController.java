package rasingme.rasingme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rasingme.rasingme.Service.DiaryService;
import rasingme.rasingme.domain.Diary;
import rasingme.rasingme.domain.Weather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @GetMapping("/{selectedDate}")
    public List<Diary> getDiaries(@PathVariable("selectedDate") String selectedDate) {
        return diaryService.getDiariesByDate(selectedDate);
    }

    @PostMapping
    public void saveDiary(@RequestBody String username, @RequestBody Diary diary) {
        diaryService.addDiary(diary, username);
    }

    @GetMapping
    public List<Diary> getAllDiaries() {
        return diaryService.getAllDiaries();
    }

    @PutMapping("/{id}")
    public void updateDiary(@PathVariable("id") Long id, @RequestBody Diary updatedDiary) {
        Diary diary = diaryService.getDiaryById(id);
        if (diary != null) {
            diary.setTitle(updatedDiary.getTitle());
            diary.setContent(updatedDiary.getContent());
            diary.setWeather(updatedDiary.getWeather());
            diaryService.updateDiary(diary);
        }
    }
}