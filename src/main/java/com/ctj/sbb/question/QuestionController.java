package com.ctj.sbb.question;

import com.ctj.sbb.Answer.AnswerForm;
import com.ctj.sbb.Answer.AnswerService;
//import com.ctj.sbb.Comment.CommentService;
//import com.ctj.sbb.Repository.CommentRepository;
import com.ctj.sbb.User.UserService;
import com.ctj.sbb.entity.Answer;
//import com.ctj.sbb.entity.Comment;
import com.ctj.sbb.entity.Question;
import com.ctj.sbb.entity.SiteUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
    // 보안성의 문제로 서비스 클래스를 추가해서 설정
    //private final QuestionRepository questionRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;
//    private final CommentService commentService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value="page", defaultValue="0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        //List<Question> questionList = this.questionRepository.findAll();

        Page<Question> paging = this.questionService.getList(page,kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question/question_list";
    }
    //내가 새로 추가한내용
    @GetMapping(value = "/detail/{id}")
    public String detail(
            Model model,
            @PathVariable("id") Integer id, AnswerForm answerForm,
                //추가됨
            @RequestParam(value="page", defaultValue="0") int page) {

        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);

        //추가함
        Page<Answer> answer_paging = this.answerService.getListByIds(page ,question.getAnswerList());
        //comment를 추출하기위해서 리스트화
//        List<Page<Comment>> list_comments_paging = this.commentService.getListByAnswer(page,answer_paging.getContent());

        model.addAttribute("answer_paging",answer_paging);

//        model.addAttribute("comment_paging",comment_paging);

        return "question/question_detail";
    }
//백업용
//    @GetMapping(value = "/detail/{id}")
//    public String detail(
//            Model model,
//            @PathVariable("id") Integer id, AnswerForm answerForm) {
//        Question question = this.questionService.getQuestion(id);
//        model.addAttribute("question", question);
//        return "question/question_detail";
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question/question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult, Principal principal ) {

        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(),
                questionForm.getContent(),
                siteUser);
        return "redirect:/question/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question/question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question/question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/question/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

}