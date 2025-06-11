package com.bookshop.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.bookshop.domain.Member;
import com.bookshop.domain.Order;
import com.bookshop.domain.item.Item;
import com.bookshop.repository.OrderSearch;
import com.bookshop.service.ItemService;
import com.bookshop.service.MemberService;
import com.bookshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

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
                            HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginMember") == null) {
            return "redirect:/login";
        }

        Member loginMember = (Member) session.getAttribute("loginMember");

        List<Order> orders;

        //관리자는 전체 주문, 일반 사용자는 본인 주문만 확인가능
        if ("ADMIN".equals(loginMember.getRole())) {
            orders = orderService.findOrders(orderSearch);
        } else {
            orders = orderService.findOrders(orderSearch).stream()
                    .filter(order -> order.getMember().getId().equals(loginMember.getId()))
                    .toList();
        }
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    //주문 취소
    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}