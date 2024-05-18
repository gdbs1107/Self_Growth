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

    //테스트입니다
    private final DiaryService diaryService;

    @GetMapping("/date")
    public String showDiaryDatePage() {
        return "diaryDate";
    }

    @GetMapping("/{selectedDate}")
    public List<Diary> getDiaries(@PathVariable("selectedDate") String selectedDate) {
        // 선택된 날짜의 다이어리 리스트 가져오기
        return diaryService.getDiariesByDate(selectedDate);
    }

    @PostMapping
    public void saveDiary(@RequestBody Diary diary) {
        // 생성한 일기 저장
        diaryService.addDiary(diary);
    }

    @GetMapping
    public List<Diary> getAllDiaries() {
        return diaryService.getAllDiaries();
    }

    @PutMapping("/{id}")
    public void updateDiary(@PathVariable("id") Long id, @RequestBody Diary updatedDiary) {
        // 수정할 다이어리 객체 가져오기
        Diary diary = diaryService.getDiaryById(id);
        if (diary != null) {
            // 다이어리 정보 업데이트
            diary.setTitle(updatedDiary.getTitle());
            diary.setContent(updatedDiary.getContent());
            diary.setWeather(updatedDiary.getWeather());

            // 업데이트된 다이어리 저장
            diaryService.updateDiary(diary);
        }
    }

}

