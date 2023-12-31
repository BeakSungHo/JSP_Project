package com.ctj.sbb.Repository;

import java.util.List;

import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);

    //쿼리문으로 사용할때 아래 의 코드 사용  (하단 자바문과 역할은 같음 )
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//    @Query("select "
//            + "distinct q "
//            + "from Question q "
//            + "left outer join SiteUser u1 on q.author=u1 "
//            + "left outer join Answer a on a.question=q "
//            + "left outer join SiteUser u2 on a.author=u2 "
//            + "where "
//            + "   q.subject like %:kw% "
//            + "   or q.content like %:kw% "
//            + "   or u1.username like %:kw% "
//            + "   or a.content like %:kw% "
//            + "   or u2.username like %:kw% ")
//    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

    Page<Question> findAll(Pageable pageable);

    
    //spec은 검색을 위한장치
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}