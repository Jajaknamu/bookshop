package com.bookshop.member.controller;

import com.bookshop.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.bookshop.member.domain.Member;
import com.bookshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider; //JWT 발급 도구
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //비밀번호 검증 도구


    //로그인 화면
    @GetMapping("/loginPage")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    //JWT 로그인 API -> 토큰을 JSON으로 반환
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> apiLogin(@RequestBody LoginForm loginForm) {
        Member loginMember = memberService.findByName(loginForm.getName()); //이름으로 회원 찾기
        if (loginMember == null) { //회원 없으면
            return ResponseEntity.status(401).body(new LoginResponse("회원없음! 실패")); //401 반환
        }

        if (!bCryptPasswordEncoder.matches(loginForm.getPassword(), loginMember.getPassword())) { //비밀번호 비교
            return ResponseEntity.status(401).body(new LoginResponse("비밀번호틀림! 실패")); //401 반환
        }

        String token = jwtTokenProvider.generateToken(loginMember); //JWT 생성

        //HttpOnly 쿠키로 토큰 내려주기
        ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true) //js접근 차단
                .secure(false) //https면 true 권장(로컬 개발이라 false)
                .path("/") //전체경로
                .sameSite("Lax") //기본적인 CSRF 완화
                .maxAge(60 * 60) //1시간 예시(원하는 만료로)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Ok");//토큰 반환
    }

    /**
     * ✅ [수정] 로그아웃: 세션 invalidate가 아니라 쿠키 삭제
     */
    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {

        ResponseCookie deleteCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(false)   // HTTPS면 true
                .path("/")
                .maxAge(0)       // ✅ 즉시 만료(삭제)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
        return "redirect:/";
    }

    /*//로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm,
                        HttpServletResponse response,
                                Model model) {
        Member loginMember = memberService.findByName(loginForm.getName()); //이름으로 회원 찾기
        if (loginMember == null) { //회원이 없으면
            model.addAttribute("loginError", "회원이 존재 하지 않음");
            return "login";
        }
        if (!bCryptPasswordEncoder.matches(loginForm.getPassword(), loginMember.getPassword())) { //비밀번호 비교
            model.addAttribute("loginError", "비밀번호가 일치하지 않음"); //비밀번호 오류 표시
            return "login"; //로그인 페이지로 다시
        }
        String token = jwtTokenProvider.generateToken(loginMember); // jwt 생성

        Cookie cookie = new Cookie("accessToken", token);
        cookie.setHttpOnly(true); //자바스크립트 접근 차단
        cookie.setPath("/"); //전체 경로에서 쿠키 사용
        response.addCookie(cookie); //응답에 쿠키 추가

        return "redirect:/"; //로그인 성공 후 홈으로 이동
    }*/

}
