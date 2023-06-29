package com.ctj.sbb;

import com.ctj.sbb.Answer.AnswerService;
import com.ctj.sbb.Comment.CommentService;
import com.ctj.sbb.Repository.AnswerRepository;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Comment;
import com.ctj.sbb.entity.Question;
import com.ctj.sbb.entity.SiteUser;
import com.ctj.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private AnswerRepository answerRepository;
	@Autowired
	private CommentService commentService;

	@Test
	void testJpa() {
		Question question = this.questionService.getQuestion(316);
		System.out.println("실행?");
		System.out.println(question.getAnswerList().get(0).getId());
		Page<Answer> answer_paging = this.answerService.getListByIds(0 ,question.getAnswerList());
		List<Answer> answers=answer_paging.getContent();


		this.commentService.create(answers.get(0),"저장",answers.get(0).getAuthor());
//		for (int i = 1; i <= 300; i++) {
//			String subject = String.format("테스트 데이터입니다:[%03d]", i);
//			String content = "내용무";
//			this.commentService.create( content);
//		}

	}


//	@Test
//	void t2() {
//		Question question = this.questionService.getQuestion(315);
//		Page<Answer> answers = this.answersService.getListByIds(1 ,question.getAnswerList());
//		List<Sort.Order> sorts = new ArrayList<>();
//		//셀렉트문에  사용할in
//		String ids= "0";
//
//		for(Answer a :answers){
//			ids = String.format(ids+","+a.getId());
//		}
//		//정렬
//		sorts.add(Sort.Order.desc("createDate"));
//
//
//		Pageable pageable = PageRequest.of(1, 5, Sort.by(sorts));
//		Page<Answer> test = this.answerRepository.findAllByIds(ids,pageable);
//
//
//	}
}