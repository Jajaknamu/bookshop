package com.bookshop.controller;

import com.bookshop.domain.Member;
import com.bookshop.dto.MemberDto;
import com.bookshop.dto.MemberUpdateDto;
import com.bookshop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Member API", description = "회원 관리 API(등록,조회,수정,삭제)")
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    //회원가입 요청
    @Operation(summary = "회원등록", description = "회원정보를 입력 받아 저장")
    @ApiResponse(responseCode = "200", description = "회원등록 성공")
    @PostMapping
    public ResponseEntity<Long> saveMember(@RequestBody MemberDto dto) {
        Member member = dto.toEntity();
        Long id = memberService.join(member);
        return ResponseEntity.ok(id);
    }

    //모든 회원 조회 요청
    @Operation(summary = "회원 목록 조회", description = "전체 회원 목록을 조회")
    @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공")
    @GetMapping
    public List<MemberDto> list() {
        return memberService.findMembers().stream()
                .map(MemberDto::from)
                .collect(Collectors.toList());
    }

    //회원 수정 요청
    @Operation(summary = "회원 수정", description = "회원 정보를 수정")
    @ApiResponse(responseCode = "200", description = "회원 정보 수정")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto dto) {
        memberService.updateMember(id, dto);
        return ResponseEntity.ok("회원 정보가 수정되었습니다");
    }

    //회원삭제
    @Operation(summary = "회원삭제", description = "회원 ID로 회원 삭제")
    @ApiResponse(responseCode = "200", description = "회원 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@PathVariable Long id) {
        log.info("Deleting member id={}", id); // 로그 찍기
        memberService.deleteMember(id);
        return ResponseEntity.ok("회원이 삭제되었습니다");
    }
}
