package com.bookshop.order.controller;

import com.bookshop.order.domain.Order;
import com.bookshop.payment.domain.Payment;
import com.bookshop.member.service.MemberService;
import com.bookshop.order.service.ItemService;
import com.bookshop.order.service.OrderService;
import com.bookshop.payment.service.PaymentService;
import com.bookshop.payment.service.TossPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.bookshop.member.domain.Member;
import com.bookshop.order.domain.item.Item;
import com.bookshop.order.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;
    private final PaymentService paymentService;
    private final TossPaymentService tossPaymentService;

    //상품 주문 페이지 호출 -> 등록된 회원, 아이템 보여짐
    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }
    //상품 주문 폼 정보 받아서 넘어온거 저장 -> 멤버id, 아이템id, 수량
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    //주문 내역 페이지 호출 -> 주문 내역 리스트 보임
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model,
                            HttpServletRequest request, Authentication authentication) {

        //디버깅 용
        log.info("🔍 authentication: {}", authentication);
        log.info("🔍 isAuthenticated: {}", authentication != null && authentication.isAuthenticated());
        log.info("🔍 principal: {}", authentication != null ? authentication.getPrincipal() : "null");
        log.info("🔍 authorities: {}", authentication != null ? authentication.getAuthorities() : "null");


        //인증이 없거나 익명이라면 로그인 페이지로
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/loginPage";
        }
        //인증된 사용자 식별값 꺼내기
        String loginName = authentication.getName();
        log.info("🔍 loginName: {}", loginName);

        //db에서 로그인 사용자 조회(세션 대신)
        Member loginMember = memberService.findByName(loginName);
        log.info("🔍 loginMember: {}", loginMember);  // ← 이거 추가!


        if (loginMember == null) {
            //토큰은 있는데 db에 사용자가 없으면 비정상 케이스 -> 로그인 풀기 유도
            return "redirect:/loginPage";
        }

        List<Order> orders;

        // 1. 주문 조회 (관리자 vs 일반 사용자)
        if ("ADMIN".equals(loginMember.getRole())) {
            orders = orderService.findOrders(orderSearch);
        } else {
            orders = orderService.findOrders(orderSearch).stream()
                    .filter(order -> order.getMember().getId().equals(loginMember.getId()))
                    .toList();
        }
        // 2. 각 주문에 해당하는 결제 정보(paymentKey) 조회
        // orderId(String) = payment.merchantUid 기준
        Map<Long, String> paymentKeyMap = new HashMap<>();
        for (Order order : orders) {
            try {
                Payment payment = paymentService.findByOrderId(String.valueOf(order.getId()));
                paymentKeyMap.put(order.getId(), payment.getImpUid()); // = paymentKey
            } catch (Exception e) {
                log.warn("결제 정보 없음: orderId={}", order.getId());
            }
        }
        // 3. 모델에 데이터 담기
        model.addAttribute("orders", orders);
        model.addAttribute("paymentKeyMap", paymentKeyMap);
        return "order/orderList";
    }

    //주문 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId,
                              @RequestParam("paymentKey") String paymentKey) {

        log.info("주문 취소 요청 들어옴: orderId={}",orderId);

        try {
            //1. 주문번호(orderId)를 문자열로 변환해서 결제 정보 조회
            Payment payment = paymentService.findByPaymentKey(paymentKey);
            log.info("✅ Payment 조회 성공: paymentKey={}, merchantUid={}", payment.getImpUid(), payment.getMerchantUid());


            //2. 토스 결제 취소 api 요청
            tossPaymentService.cancelPayment(
                    payment.getImpUid(), //토스의 paymentKey
                    payment.getAmount(), //결제 금액
                    "사용자 주문 취소" //취소 사유
            );
        }catch (Exception e) {
            log.error("❌ Payment 조회 실패: orderId={}", orderId);
            throw e; // 테스트용으로 다시 던져줌
        }

        //3. 주문 db 상태 업데이트 (CANCEL 처리 및 재고 복원)
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}