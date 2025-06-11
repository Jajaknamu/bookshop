package com.bookshop.service;

import com.bookshop.domain.item.Item;
import com.bookshop.repository.ItemJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기전용
@RequiredArgsConstructor
public class ItemService {

    private final ItemJpaRepository itemJpaRepository;
    //이 서비스는 상품 리포지토리에 단순히 위임만 하는 클래스임

    @Transactional //우선권을 가짐 -> 읽기전용 적용안됨
    public void saveItem(Item item) {
        itemJpaRepository.save(item);
    }

    //조회
    public List<Item> findItems() {
        return itemJpaRepository.findAll();
    }
    //단건 조회
    public Item findOne(Long itemId) {
       return itemJpaRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("없는 상품입니다."));
    }
    //상품 수정
    @Transactional
    public void updateItem(Long id, String name, int price, int stockQuantity) {
        Item item = itemJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 상품입니다"));
        item.setName(name);
        item.setPrice(price);
        item.setStockQuantity(stockQuantity);
    }

    //상품 삭제
    @Transactional
    public void deleteItem(Long itemId) {
        itemJpaRepository.deleteById(itemId);
    }
}
