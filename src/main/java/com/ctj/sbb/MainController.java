package com.ctj.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }
//    @GetMapping("/")
//    public String root() {  //루트로 들어왓을때 아래경로로 redirect 해버림
//        return "redirect:/question/list";
//    }

    @GetMapping("/")
    public String root() {  //루트로 들어왓을때 아래경로로 redirect 해버림
        return "redirect:/logo";
    }
}