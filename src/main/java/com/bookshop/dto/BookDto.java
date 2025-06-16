package com.bookshop.dto;

import com.bookshop.domain.item.Book;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDto {
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String author;
    private String isbn;

    //이미지 이름 포함
    private String imageName;

    //entity -> dto 변환용
    public static BookDto from(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setName(book.getName());
        dto.setPrice(book.getPrice());
        dto.setStockQuantity(book.getStockQuantity());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setImageName(book.getImageName());
        return dto;
    }

    //dto -> entity 변환용
    public Book toEntity() {
        Book book = new Book();
        book.setName(this.name);
        book.setPrice(this.price);
        book.setStockQuantity(this.stockQuantity);
        book.setAuthor(this.author);
        book.setIsbn(this.isbn);
        book.setImageName(this.imageName);
        return book;
    }

}
