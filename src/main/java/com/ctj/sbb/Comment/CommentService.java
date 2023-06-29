package com.ctj.sbb.Comment;


import com.ctj.sbb.DataNotFoundException;
import com.ctj.sbb.Repository.CommentRepository;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Comment;
import com.ctj.sbb.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Answer answer,
                          String comment,
                          SiteUser author) {

        Comment comments = new Comment();
        comments.setComment(comment);
        comments.setCreateDate(LocalDateTime.now());
        comments.setAnswer(answer);
        comments.setAuthor(author);
        this.commentRepository.save(comments);
        return comments;
    }
    //Answer의 키값과 비교해서 뽑아낼 키값을 비교하기위해서
    // 만든 용도 특정상황에 사용하기 위해서 일딴보류
//    public List<Page<Comment>> getListByAnswer(int page,List<Answer> answers){
//        List<Sort.Order>sorts = new ArrayStack<>();
//        List<Integer> ids=new ArrayList<>();
//        List<Page<Comment>> pages = new ArrayList<>();
//        for(Answer a :answers){
//            for(Comment c :a.getCommentList())
//                ids.add((Integer)c.getId());
//        }
//        sorts.add(Sort.Order.desc("createDate"));
//
//        Pageable pageable= PageRequest.of(page,5 , Sort.by(sorts));
//        this.commentRepository.findAllByIds(ids,pageable);
//        return pages;
//
//
//    }
    //없을경우 순환문
    public Comment getAnswer(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }
    public void vote(Comment comment, SiteUser siteUser) {
        comment.getVoter().add(siteUser);
        this.commentRepository.save(comment);
    }
}
