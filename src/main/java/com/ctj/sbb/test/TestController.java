package com.ctj.sbb.test;

import com.ctj.sbb.entity.Question;
import com.ctj.sbb.question.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


//파일쪽 추가
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@RequestMapping("/test")
@RequiredArgsConstructor
@Controller
public class TestController {
    private final QuestionService questionService;
    @GetMapping("/1")
    public String text_1(){
        return "test/test_1";
    }

    @PostMapping("/2")
    public String text_2(Model model,
                         @RequestParam(value="page", defaultValue="0") int page,
                         @RequestParam(value = "kw", defaultValue = "") String kw) {
        //List<Question> questionList = this.questionRepository.findAll();

        Page<Question> paging = this.questionService.getList(page,kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question/question_list";
    }
    @PostMapping("/3")
    public String text_3(@RequestParam("profile") MultipartFile file, HttpServletRequest req) {
        try {
            // 저장할 디렉토리 경로 설정
            String directoryPath = "C:\\dawon";

            // 디렉토리가 존재하지 않을 경우 생성
            Path directory = Path.of(directoryPath);
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }

            // 파일 저장
            String fileName = file.getOriginalFilename();
            Path filePath = directory.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String clientHost = req.getHeader("Referer");
            System.out.println(clientHost);
            // 리다이렉트 처리
            return "redirect:/";
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            // 실패 시 처리할 로직 작성
            return "redirect:/error";
        }
    }


}
