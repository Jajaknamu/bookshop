package com.bookshop.controller;

import jakarta.servlet.http.HttpSession;
import com.bookshop.domain.Member;
import com.bookshop.domain.item.Book;
import com.bookshop.domain.item.Item;
import com.bookshop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    //상품 등록 페이지 호출
    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    //상품 등록 정보 받아옴
    @PostMapping("/items/new")
    public String create(@ModelAttribute BookForm form) throws IOException {
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        //이미지 업로드 처리
        MultipartFile imageFile = form.getImageFile();
        if (!imageFile.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadPath = "C:/upload"; //이미지가 저장되는 경로 - WebConfig랑 주소릉 같이 맞춰줌
            File saveFile = new File(uploadPath, fileName);

            // ✅ 로그 출력
            System.out.println("이미지 저장 경로: " + saveFile.getAbsolutePath());
            System.out.println("저장된 파일명: " + fileName);

            imageFile.transferTo(saveFile);
            book.setImageName(fileName);// book에 이미지 이름 저장

            /* 이전 코드
            String fullpath = new File("src/main/resources/static/images/", fileName).getAbsolutePath();

            imageFile.transferTo(new File(fullpath));
            book.setImageName(fileName); // book에 이미지 이름 저장*/
        }
        itemService.saveItem(book);
        return "redirect:/admin/items";
    }

    //상품 목록 페이지 호출
    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items";
    }

    /**
     * 상품수정 폼
     * @param itemId
     * @param model
     * @return
     */
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item =(Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);
        return "admin/updateItemForm";

    }
    /**
     * 상품 수정
     * @param form
     * @return
     */
    @PostMapping("/items/{itemId}/edit")
    public String updateItem(@ModelAttribute BookForm form, @PathVariable Long itemId) throws IOException {
        Book book = new Book();
        book.setId(itemId); // 기존 Book의 ID 설정

        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        MultipartFile imageFile = form.getImageFile();

        if (imageFile != null && !imageFile.isEmpty()) {
            // 새 이미지 업로드
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            String uploadPath = "C:/upload"; // 등록과 동일한 경로

            File saveFile = new File(uploadPath, fileName);
            imageFile.transferTo(saveFile);
            book.setImageName(fileName);

            // 기존 이미지 삭제
            if (form.getImageName() != null) {
                File oldFile = new File(uploadPath, form.getImageName());
                if (oldFile.exists()) oldFile.delete();
            }
        } else {
            // 이미지 안 바꿨으면 기존 이미지 그대로
            book.setImageName(form.getImageName());
        }

        itemService.saveItem(book);
        return "redirect:/api/admin/items";
    }

    //책 상세 페이지
    @GetMapping("/items/{itemId}")
    public String itemDetails(@PathVariable Long itemId, Model model, HttpSession session) {
        Book book =(Book) itemService.findOne(itemId); //Book으로 다운캐스팅

        model.addAttribute("book", book);

        Member loginMember = (Member) session.getAttribute("loginMember");
        model.addAttribute("loginMember", loginMember);
        return "itemDetail";
    }
}
