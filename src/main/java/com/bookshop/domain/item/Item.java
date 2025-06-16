package com.bookshop.domain.item;

import jakarta.persistence.*;
import com.bookshop.exception.NoEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype") //값타입 싱글테이블이여서 볼때 구분하는거
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

    //비즈니스 로직 추가
    /**
     * stock 증가 - 재고증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock 감소 - 재고감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NoEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    //stockQuantity를 변경할 일이 있으면 addStock(),removeStock() 같은 메소드를 사용해서 변경해야 함
    //setter사용 보단 비즈니스 로직 사용 권장
}
