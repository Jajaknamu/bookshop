package com.bookshop.controller;

import com.bookshop.domain.item.Book;
import com.bookshop.dto.BookDto;
import com.bookshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemApiController {

    private final ItemService itemService;

    //상품 등록
    @PostMapping
    public ResponseEntity<Long> createItem(@RequestBody BookDto dto) {
        Book book = dto.toEntity();
        itemService.saveItem(book);
        return ResponseEntity.ok(book.getId());
    }

    //모든 상품 목록 조회
    @GetMapping
    public List<BookDto> getItems() {
        return itemService.findItems().stream()
                .filter(item -> item instanceof Book)
                .map(item -> BookDto.from((Book) item))
                .collect(Collectors.toList());
    }

    //상품 단건 조회
    @GetMapping("/{id}")
    public BookDto getItem(@PathVariable Long id) {
        Book book = (Book) itemService.findOne(id);
        return BookDto.from(book);
    }
    //상품 수정
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody BookDto dto) {
        itemService.updateItem(id,dto.getName(), dto.getPrice(), dto.getStockQuantity());
        return ResponseEntity.ok("상품이 수정되었습니다.");
    }
    //상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("상품이 삭제되었습니다.");
    }
}
