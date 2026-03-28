package com.bookshop.member.repository;

import com.bookshop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    //중복 검사용
    List<Member> findAllByName(String name);

    //로그인 시 name 확인용
    Optional<Member> findByName(String name);
}
