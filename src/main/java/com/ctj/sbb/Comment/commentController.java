package com.ctj.sbb.Comment;


import com.ctj.sbb.Answer.AnswerService;
import com.ctj.sbb.User.UserService;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Comment;
import com.ctj.sbb.entity.SiteUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class commentController {
    private final CommentService commentService;
    private final AnswerService answerService;
    private final UserService userService;
    //보안속성추가

    @PreAuthorize("isAuthenticated")
    @PostMapping("/create/{id}")
    public String createComment (Model model, @PathVariable("id") Integer id,
                                 @Valid CommentForm commentForm, BindingResult bindingResult,
                                 Principal principal){
        System.out.println("생성자 호출");
        Answer answer = this.answerService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("answer",answer);
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
