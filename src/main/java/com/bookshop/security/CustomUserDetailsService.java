package com.bookshop.security;

import com.bookshop.member.domain.Member;
import com.bookshop.member.repository.MemberJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //로그 확인용
        log.info("로그인 시도: {}", name);

        Optional<Member> member = memberJpaRepository.findByName(name);

        if (member.isPresent()){
            log.info("사용자 있음: {}",member.get().getName());
            return new CustomUserDetails(member.get());
        } else {
            log.info("사용자 찾을수 없음: {}",name);
            throw  new UsernameNotFoundException("사용자를 찾을수없음: " + name);
        }
    }
}
