package com.bookshop.repository;

import com.bookshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    //중복 검사용
    List<Member> findByName(String name);
}
