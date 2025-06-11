package com.bookshop.service;

import com.bookshop.domain.Address;
import com.bookshop.domain.Member;
import com.bookshop.dto.MemberUpdateDto;
import com.bookshop.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //component 자동스캔대상이라 스프링빈에 자동등록 됨
@Transactional(readOnly = true) //데이터변경은 이 어노테이션 필수
@RequiredArgsConstructor //final이 있는 필드만 가지고 생성자 자동으로 만들어줌(롬복참고)
public class MemberService {

    private final MemberJpaRepository memberJpaRepository;

    /*회원가입*/
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);//중복회원 검증
        memberJpaRepository.save(member);
        return member.getId();
    }

    //중복회원검증 로직
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberJpaRepository.findByName(member.getName());//같은 이름 있는지 확인, 유니크 제약조건 주는것도 좋은 방법(동시가입 이슈)
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberJpaRepository.findAll();
    }

    //회원 단건 조회
    public Member findOne(Long memberId) {
        return memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 존재x. id=" + memberId));
    }

    //회원 수정
    @Transactional
    public void updateMember(Long id, MemberUpdateDto dto) {
        Member member = findOne(id);
        if (dto.getName() != null) member.setName(dto.getName());
        if (dto.getPassword() != null) member.setPassword(dto.getPassword());
        if (dto.getCity() != null || dto.getStreet() != null || dto.getZipcode() != null) {
            member.setAddress(new Address(
                    dto.getCity() != null ? dto.getCity() : member.getAddress().getCity(),
                    dto.getStreet() !=null ? dto.getStreet() : member.getAddress().getStreet(),
                    dto.getZipcode() != null ? dto.getZipcode() : member.getAddress().getZipcode()
            )); // Address는 불변 객체라 필드변경 불가 -> 새로 생성해서 넣어줌
        }
    }

    //회원삭제
    @Transactional
    public void deleteMember(Long id) {
        Member member = findOne(id); // 존재하는 회원인지 확인
        memberJpaRepository.delete(member);
    }

    //회원 로그인 시 이름 검사
    public Member findByName(String name) {
        List<Member> members = memberJpaRepository.findByName(name);
        if (members.isEmpty()) {
            return null;
        }
        return members.get(0);
    }
}
