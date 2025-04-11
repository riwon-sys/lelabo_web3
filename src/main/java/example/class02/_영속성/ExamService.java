package example.class02._영속성;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service @Transactional
@RequiredArgsConstructor
public class ExamService {
    // 엔티티 매니저 이용한 영속성 조작
    @PersistenceContext // 영속성 컨테스트 (영속성들이 저장된 메모리) 의 객체 주입
    private EntityManager entityManager;

    public void get(){
        // 1. 비영속(Transient)
        ExamEntity examEntity1 = new ExamEntity();
        examEntity1.setName("주우재");
        System.out.println("★ 비영속 상태 : " + examEntity1);

        // 2.영속(Persistent) .persist 영속성 연결부여
        entityManager.persist(examEntity1); // 영속성 연결 부여
        // 자바객체 <-- 영속성 --> DB 매핑
        System.out.println("★ 영속 상태 : " + examEntity1);

        // [*] 영속 상태에서 수정
        examEntity1.setName("캉호동");
        entityManager.flush(); // 트랜잭션 중간에 commit (완료)
        System.out.println("★영속상태 : " + examEntity1);

        // 3. 준영속(Detached) .detach 영속성 연결일시해제
        entityManager.detach( examEntity1 );
        System.out.println("★비 영속 상태 : " + examEntity1);
           // [*] 준영속 상태에서의 수정
           examEntity1.setName("신통엽");
           entityManager.flush();
        System.out.println("★비영속상태 : " + examEntity1 );

        // 3-2 준영속 에서 다시 영속상태로 이동
        entityManager.merge( examEntity1 );

        // 4. 삭제 상태 ( Remove) . remove 영속성 아예 삭제
//        entityManager.remove(examEntity1);
    }



}