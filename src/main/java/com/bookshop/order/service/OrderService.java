package com.bookshop.order.service;

import com.bookshop.order.domain.item.Item;
import com.bookshop.member.domain.Member;
import com.bookshop.payment.domain.Payment;
import com.bookshop.order.repository.ItemRepository;
import com.bookshop.member.repository.MemberRepository;
import com.bookshop.order.repository.OrderRepository;
import com.bookshop.order.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberID, Long itemId, int count, Payment payment) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberID);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 연관관계 설정
        payment.setOrder(order);          // Payment → Order 연관관계
        order.setPayment(payment);       // Order → Payment 연관관계

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }
    // 기존 주문 메서드 (변경하지 않음)
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // ✅ 오버로딩된 메서드 호출해서 중복 줄이기
        return order(memberId, itemId, count, null);
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소 완료
        order.cancel();
    }

    //검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllWithMember(orderSearch);
    }

    @Transactional(readOnly = true)
    public Order findOne(Long orderId) {
        return orderRepository.findOne(orderId);
    }
    //Payment 연관관계 저장을 위한 추가 메서드
    @Transactional
    public void save(Order order) {
        orderRepository.save(order);
    }
}
