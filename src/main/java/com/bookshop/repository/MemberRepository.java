package com.bookshop.repository;

import jakarta.persistence.EntityManager;
import com.bookshop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //@PersistenceContext //jpa엔티티 매니저 자동 주입해줌
    private final EntityManager em;

    //멤버 저장하는 로직
    public void save(Member member) {
        em.persist(member);
    }
    //조회하는 로직(단건)
    public Member findOne(Long id){
        return em.find(Member.class, id); //jpa가 제공하는 find()메소드 사용해서 멤버 찾아서 반환
    }
    //회원목록들 조회하는 로직(여러건)
    public List<Member> findAll() {
        //jpql로 작성, from의 대상이 테이블이 아닌 엔티티(Member)
        return em.createQuery("select m from Member m", Member.class)
                //createQuery("jpql쿼리 작성", 반환타입)
                .getResultList(); //인라인으로 합쳐줌
    }
    //이름으로 조회하는 기능(파라미터 바인딩-이름 바인딩 방식 사용)
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
