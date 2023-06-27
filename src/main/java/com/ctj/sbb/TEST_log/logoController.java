package com.ctj.sbb.TEST_log;
import com.ctj.sbb.Repository.QuestionRepository;
import com.ctj.sbb.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/logo")
@RequiredArgsConstructor
@Controller
public class logoController {

    @GetMapping("")
    public String log(Model model) {
        return "/logo/mainform";
    }
}