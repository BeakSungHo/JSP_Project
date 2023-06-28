package com.ctj.sbb;

import com.ctj.sbb.Answer.AnswerService;
import com.ctj.sbb.Repository.AnswerRepository;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Question;
import com.ctj.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private AnswerService answersService;
	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
//			this.questionService.create(subject, content);
		}
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