package com.ctj.sbb.question;

import com.ctj.sbb.Answer.AnswerForm;
import com.ctj.sbb.entity.Question;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    // 보안성의 문제로 서비스 클래스를 추가해서 설정
    //private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {
        //List<Question> questionList = this.questionRepository.findAll();
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question/question_list";
    }
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question/question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question/question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}