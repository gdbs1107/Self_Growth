package rasingme.rasingme.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rasingme.rasingme.domain.Diary;
import rasingme.rasingme.domain.Member;
import rasingme.rasingme.repository.DiaryRepository;
import rasingme.rasingme.repository.MemberRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;

    public List<Diary> getAllDiaries() {
        return diaryRepository.findAll();
    }

    public List<Diary> getDiariesByDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        return diaryRepository.findByDate(date);
    }

    public void addDiary(Diary diary, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));
        diary.setMember(member);
        diaryRepository.save(diary);
    }

    public Diary getDiaryById(Long id) {
        return diaryRepository.findById(id).orElse(null);
    }

    public void updateDiary(Diary diary) {
        diaryRepository.save(diary);
    }
}