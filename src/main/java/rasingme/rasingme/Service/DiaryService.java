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

@Transactional
@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final MemberRepository memberRepository;


    public List<Diary> getAllDiariesByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        return diaryRepository.findByMemberId(memberId);
    }

    public List<Diary> getDiariesByMemberIdAndDate(Long memberId, String dateStr) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다.");
        }

        return diaryRepository.findByMemberIdAndDate(memberId, date);
    }

    public void addDiary(Diary diary, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        diary.setMember(member);
        diaryRepository.save(diary);
    }

    public void updateDiary(Long memberId, String selectedDate, Diary updatedDiary) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("날짜 형식이 올바르지 않습니다.");
        }
        List<Diary> diaries = diaryRepository.findByMemberIdAndDate(memberId, date);

        if (diaries.isEmpty()) {
            throw new IllegalArgumentException("해당 회원과 날짜에 대한 다이어리를 찾을 수 없습니다.");
        }

        Diary diary = diaries.get(0);

        diary.setTitle(updatedDiary.getTitle());
        diary.setContent(updatedDiary.getContent());
        diary.setWeather(updatedDiary.getWeather());
        diaryRepository.save(diary);
    }
}