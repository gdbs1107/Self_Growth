package rasingme.rasingme.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rasingme.rasingme.domain.Diary;
import rasingme.rasingme.repository.DiaryRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor

public class DiaryService {

    private final DiaryRepository diaryRepository;

    public Diary addDiary(Diary diary) {
        return diaryRepository.save(diary);
    }

    public List<Diary> getAllDiaries() {
        return diaryRepository.findAll();
    }

    public Diary getDiaryById(Long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid diary ID"));
    }

    public void deleteDiary(Long id) {
        diaryRepository.deleteById(id);
    }

    public Diary updateDiary(Diary updatedDiary) {
        // 업데이트할 다이어리를 데이터베이스에서 가져옵니다.
        Diary existingDiary = diaryRepository.findById(updatedDiary.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid diary ID"));

        // 업데이트된 내용으로 다이어리를 수정합니다.
        existingDiary.setTitle(updatedDiary.getTitle());
        existingDiary.setContent(updatedDiary.getContent());
        existingDiary.setWeather(updatedDiary.getWeather());

        // 수정된 다이어리를 저장하고 반환합니다.
        return diaryRepository.save(existingDiary);
    }

    public List<Diary> getDiariesByDate(String selectedDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(selectedDate);
            return diaryRepository.findByDate(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format");
        }
    }
}