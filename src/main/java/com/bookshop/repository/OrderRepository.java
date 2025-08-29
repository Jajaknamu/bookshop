package com.bookshop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import com.bookshop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    //주문 저장
    public void save(Order order) {
        em.persist(order);
    }

    //주문 단건 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //모든 주문 조회
    public List<Order> findAll(OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status=:status";
        }

        //회원이름 검색
        if(StringUtils.hasText(orderSearch.getMemberName())) {
            if(isFirstCondition){
                jpql += " where";
                isFirstCondition = false;
            }else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class);
        query
                .setMaxResults(1000); //최대 1000건

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    // Order와 Member를 한번에 join해서 조회
    public List<Order> findAllWithMember(OrderSearch search) {
        return em.createQuery(
                        "select distinct o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.orderItems oi" +
                                " join fetch oi.item i" +
                                " left join fetch o.payment p" , Order.class)
                .getResultList();
    }

}
