package com.ctj.sbb.question;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ctj.sbb.DataNotFoundException;
import com.ctj.sbb.Repository.QuestionRepository;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Question;
import java.util.Optional;

import com.ctj.sbb.entity.SiteUser;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    //하나라도 포함되어잇으면 출력 가져오는 서치문
    private Specification<Question> question_Search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }
//위와같은 answer_Search만들에정
private Specification<Answer> answer_Search(String kw) {
    return new Specification<>() {
        private static final long serialVersionUID = 1L;
        @Override
        public Predicate toPredicate(Root<Answer> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
            query.distinct(true);  // 중복을 제거
            Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
            Join<Question, Question> a = q.join("answerList", JoinType.LEFT);
            Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
            return cb.or(cb.like(q.get("subject"), "%" + kw + "%"));
        }
    };
}




    public List<Question> getList() {
        return this.questionRepository.findAll();
    }
    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    public void create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        this.questionRepository.save(q);
    }
    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return this.questionRepository.findAll(pageable);
    }

    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = question_Search(kw);
        return this.questionRepository.findAll(spec,pageable);
//        아래는 쿼리문을 사용할경우의 반환문 위는 일반 자바 코드
//        return this.questionRepository.findAllByKeyword(kw, pageable);
    }

//    public p
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }
    public void delete(Question question) {
        this.questionRepository.delete(question);
    }
    public void vote(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}