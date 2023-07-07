package com.ctj.sbb.Comment;


import com.ctj.sbb.Answer.AnswerForm;
import com.ctj.sbb.Answer.AnswerService;
import com.ctj.sbb.User.UserService;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Comment;
import com.ctj.sbb.entity.Question;
import com.ctj.sbb.entity.SiteUser;
import com.ctj.sbb.question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class commentController {
    private final CommentService commentService;
    private final AnswerService answerService;
    private final UserService userService;
    private final QuestionService questionService;
    //보안속성추가
//    @PreAuthorize("isAuthenticated")
//    @PostMapping("/create/{id}")
//    public String createComment (Model model, @PathVariable("id") Integer id,
//                                 @Valid CommentForm commentForm, BindingResult bindingResult,
//                                 Principal principal){
//        System.out.println("생성자 호출");
//        Answer answer = this.answerService.getAnswer(id);
//        SiteUser siteUser = this.userService.getUser(principal.getName());
//        if(bindingResult.hasErrors()){
//            model.addAttribute("answer",answer);
//            return "question/tail";
//        }
//        Comment comment = this.commentService.create(answer,
//                commentForm.getComment(),siteUser);
////아래 리턴문은 앵커용
//        return String.format("redirect:/question/detail/%s#answer_%s",
//                answer.getQuestion().getId(),comment.getAnswer().getId());
//
//    }
    @PreAuthorize("isAuthenticated")
    @PostMapping("/create/{id}")
    public String createComment (Model model,
                                 @PathVariable("id") Integer id,
                                 @Valid CommentForm commentForm,
                                 BindingResult bindingResult,
                                 AnswerForm answerForm,
                                 Principal principal,
                                 @RequestParam(value="page", defaultValue="0") int page){
        System.out.println("생성자 호출");
        Answer answer = this.answerService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Question question = answer.getQuestion();
//        Question question = this.questionService.getQuestion(id);
        Page<Answer> answer_paging = this.answerService.getListByIds(page ,question.getAnswerList());

        if(bindingResult.hasErrors()){
            model.addAttribute("answer_paging",answer_paging);
            model.addAttribute("question", question);
            return "question/question_detail";
        }
        Comment comment = this.commentService.create(answer,
                commentForm.getComment(),siteUser);
//아래 리턴문은 앵커용
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.getQuestion().getId(),comment.getAnswer().getId());

    }






//추천사항
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String commentVote(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.commentService.vote(comment, siteUser);
        return String.format("redirect:/question/detail/%s#comment_%s",
                comment.getAnswer().getQuestion().getId(), comment.getId());
    }
}
