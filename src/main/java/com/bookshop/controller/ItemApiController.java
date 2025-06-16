package com.bookshop.controller;

import com.bookshop.domain.item.Book;
import com.bookshop.dto.BookDto;
import com.bookshop.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Item API", description = "상품 관리 API(등록, 조회, 수정, 삭제)")
@RequestMapping("/api/items")
public class ItemApiController {

    private final ItemService itemService;

    //상품 등록
    @Operation(summary = "상품 등록", description = "상품정보를 입력 받아 저장")
    @ApiResponse(responseCode = "200", description = "상품등록 성공")
    @PostMapping
    public ResponseEntity<Long> createItem(@RequestBody BookDto dto) {
        Book book = dto.toEntity();
        itemService.saveItem(book);
        return ResponseEntity.ok(book.getId());
    }

    //모든 상품 목록 조회
    @Operation(summary = "모든 상품 조회", description = "등록된 전체 상품목록을 조회")
    @ApiResponse(responseCode = "200", description = "전체 상품목록 조회 성공")
    @GetMapping
    public List<BookDto> getItems() {
        return itemService.findItems().stream()
                .filter(item -> item instanceof Book)
                .map(item -> BookDto.from((Book) item))
                .collect(Collectors.toList());
    }

    //상품 단건 조회
    @Operation(summary = "상품 단건 조회", description = "하나의 주문 목록만 조회")
    @ApiResponse(responseCode = "200", description = "상품 단건 조회 성공")
    @GetMapping("/{id}")
    public BookDto getItem(@PathVariable Long id) {
        Book book = (Book) itemService.findOne(id);
        return BookDto.from(book);
    }

    //상품 수정
    @Operation(summary = "상품 수정", description = "상품정보를 수정")
    @ApiResponse(responseCode = "200", description = "상품 수정 성공")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateItem(@PathVariable Long id, @RequestBody BookDto dto) {
        itemService.updateItem(id,dto.getName(), dto.getPrice(), dto.getStockQuantity());
        return ResponseEntity.ok("상품이 수정되었습니다.");
    }
    //상품 삭제
    @Operation(summary = "상품 삭제",  description = "상품 ID로 상품을 삭제")
    @ApiResponse(responseCode = "200", description = "상품 삭제 성공")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("상품이 삭제되었습니다.");
    }
}
