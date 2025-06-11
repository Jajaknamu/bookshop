package com.bookshop.controller;

import com.bookshop.domain.Member;
import com.bookshop.dto.MemberDto;
import com.bookshop.dto.MemberUpdateDto;
import com.bookshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    //회원가입 요청
    @PostMapping
    public ResponseEntity<Long> saveMember(@RequestBody MemberDto dto) {
        Member member = dto.toEntity();
        Long id = memberService.join(member);
        return ResponseEntity.ok(id);
    }

    @GetMapping
    public List<MemberDto> list() {
        return memberService.findMembers().stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }

    //회원 수정 요청
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto dto) {
        memberService.updateMember(id, dto);
        return ResponseEntity.ok("회원 정보가 수정되었습니다");
    }

    //회원삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        log.info("Deleting member id={}", id); // 로그 찍기
        memberService.deleteMember(id);
        return ResponseEntity.ok("회원이 삭제되었습니다");
    }
}
