package com.bookshop.controller;

import com.bookshop.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j //로그찍는 어노테이션
public class HomeController {
    private ItemService itemService;

    @RequestMapping("/") //첫번째화면 여기로 잡힘
    public String home() {
        log.info("home controller");
        return "bookList"; //home.html 파일로 타임리프가 찾아들어감
    }

}
