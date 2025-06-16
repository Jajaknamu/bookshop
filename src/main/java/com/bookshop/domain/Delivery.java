package com.bookshop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //XToOne은 항상 LAZY로 설정할 것. n+1이슈 생김 방지해줌
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //ORDINAL옵션은 사용금지, STRING은 중간에 추가되도 순서 밀림 없음
    private DeliveryStatus status; //READY, COMP
}
