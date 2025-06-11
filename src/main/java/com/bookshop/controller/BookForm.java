package com.bookshop.controller;

import com.bookshop.domain.item.Book;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Getter @Setter
public class BookForm {

      private Long id;

      private String name;
      private int price;
      private int stockQuantity;

      private String author;
      private String isbn;

      //이미지 파일 추가
      private MultipartFile imageFile;

      //Book 엔티티 생성 + 이미지 저장처리 포함
      public Book toEntityWithImage() throws IOException {
            Book book = new Book();
            book.setName(this.name);
            book.setPrice(this.price);
            book.setStockQuantity(this.stockQuantity);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);

            //이미지 업로드 처리
            if (imageFile != null && !imageFile.isEmpty()) {
                  String uuidFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                  String uploadPath = new File("src/main/resources/static/images", uuidFileName).getAbsolutePath();

                  //실제 파일 저장
                  imageFile.transferTo(new File(uploadPath));
                  //DB에는 파일명만 저장
                  book.setImageName(uuidFileName);
            }
            return book;
      }
}
