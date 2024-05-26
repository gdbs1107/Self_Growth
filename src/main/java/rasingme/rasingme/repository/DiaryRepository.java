package rasingme.rasingme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rasingme.rasingme.domain.Diary;
import rasingme.rasingme.domain.Member;

import java.util.Date;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findByDate(Date selectedDate);

    List<Diary> findByMember(Member member);

    List<Diary> findByMemberAndDate(Member member, Date date);
    // 추가적인 일기 관련 메소드가 필요하다면 여기에 추가할 수 있습니다.
}