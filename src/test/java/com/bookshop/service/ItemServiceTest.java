package com.bookshop.service;

import com.bookshop.domain.item.Book;
import com.bookshop.domain.item.Item;
import com.bookshop.repository.ItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional // 테스트 이후 롤백 됨
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Test
    public void 상품등록_조회_성공() {
        //given
        Book book = new Book();
        book.setName("스프링 jpa");
        book.setPrice(30000);
        book.setStockQuantity(100);
        book.setAuthor("김영한");
        book.setIsbn("123456");

        //when
        itemService.saveItem(book);
        Item found = itemService.findOne(book.getId());

        //then
        assertThat(found.getName()).isEqualTo("스프링 jpa");
        assertThat(found.getPrice()).isEqualTo(30000);
    }

    @Test
    public void 상품_수정() {
        Book book = new Book();
        book.setName("초기명");
        book.setPrice(30000);
        book.setStockQuantity(100);
        itemService.saveItem(book);

        itemService.updateItem(book.getId(), "수정함", 20000, 10);
        Item updated = itemService.findOne(book.getId());

        assertThat(updated.getName()).isEqualTo("수정함");
        assertThat(updated.getPrice()).isEqualTo(20000);
        assertThat(updated.getStockQuantity()).isEqualTo(10);
    }
}