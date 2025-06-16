package com.bookshop.controller;

import com.bookshop.domain.item.Book;
import com.bookshop.dto.BookDto;
import com.bookshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/items")
@RequiredArgsConstructor
public class AdminItemController {

    private final ItemService itemService;

    //책 목록
    @GetMapping
    public List<BookDto> list() {
        return itemService.findItems().stream()
                .map(item -> BookDto.from((Book) item))
                .toList();
    }

    //책 등록
    @PostMapping("/new)")
    public ResponseEntity<Long> save(@ModelAttribute BookForm form) throws IOException {
        Book book = form.toEntityWithImage();// 이미지 업로드 + 필드 설정
        itemService.saveItem(book);
        return ResponseEntity.ok(book.getId());
    }
/*
    //책 수정
    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody BookDto dto) {
        itemService.updateItem(id, dto.getName(), dto.getPrice(), dto.getStockQuantity());
        return ResponseEntity.ok("수정 완료");
    }*/

    //책 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("삭제 완료");
    }
}
