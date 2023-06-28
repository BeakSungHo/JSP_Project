package com.ctj.sbb.Answer;

import com.ctj.sbb.DataNotFoundException;
import com.ctj.sbb.Repository.AnswerRepository;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Question;
import com.ctj.sbb.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
    public Answer create(Question question,
                         String content,
                         SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }
    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }


    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }


    public Page<Answer> getListByIds2(int page, Question question) {
        List<Sort.Order> sorts = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));
        Page<Answer> answerList = answerRepository.findAllByQuestion(question, pageable);
        return answerList;
    }
    //페이지부분 findByIds
    public Page<Answer> getListByIds(int page, List<Answer> answers){
        List<Sort.Order> sorts = new ArrayList<>();
        //셀렉트문에  사용할in
       List<Integer> ids=new ArrayList<>();

        for(Answer a :answers){
        ids.add((Integer)a.getId());
        }
        //정렬
        sorts.add(Sort.Order.desc("createDate"));


        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));
        return this.answerRepository.findAllByIds2(ids,pageable);
    }

//    public Page<Answer> getList(int page, String kw) {
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createDate"));
//        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
////        Specification<Answer> spec = search(kw);
//        return this.answerRepository.findAll(pageable);
////        아래는 쿼리문을 사용할경우의 반환문 위는 일반 자바 코드
////        return this.questionRepository.findAllByKeyword(kw, pageable);
//    }

}