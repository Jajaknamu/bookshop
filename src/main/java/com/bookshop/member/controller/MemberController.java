package com.bookshop.member.controller;

import com.bookshop.member.dto.MemberDto;
import com.bookshop.order.domain.Address;
import jakarta.validation.Valid;
import com.bookshop.member.domain.Member;
import com.bookshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //비밀번호 암호화 도구

    //회원가입 페이지 호출
    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "createMemberForm";
    }

    //회원 가입 정보 폼으로 받아서 넘어온거 저장
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        log.info("회원가입 요청");
        log.info("회원가입한 name: " + form.getName());

        MemberDto memberDto = new MemberDto();
        memberDto.setName(form.getName());
        memberDto.setPassword(form.getPassword());
        memberDto.setCity(form.getCity());
        memberDto.setStreet(form.getStreet());
        memberDto.setZipcode(form.getZipcode());

        memberService.join(memberDto);
        return "redirect:/";
    }

    /*//회원 가입 정보 폼으로 받아서 넘어온거 저장
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        if(result.hasErrors()){
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }*/

    //회원 목록 페이지 호출 -> 모든 회원 목록 보임
    @GetMapping("/members")
    public String list(Model model) {
        model.addAttribute("members", memberService.findMembers()); //inline으로 합침
        return "members/memberList";
    }
}
