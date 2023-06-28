package com.ctj.sbb.Repository;
import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Question;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {


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


    @Query(value = "SELECT a FROM Answer a WHERE a.id in (:ids)")
    Page<Answer> findAllByIds(@Param("ids") List<Integer> ids, Pageable pageable);

    @Query("SELECT a FROM Answer a WHERE a.id IN (:ids)")
    Page<Answer> findAllByIds2(@Param("ids") List<Integer> ids, Pageable pageable);

    Page<Answer> findAllByQuestion(Question question, Pageable pageable);

    Page<Answer> findAll(Pageable pageable);
    Page<Answer> findAll(Specification<Answer> spec, Pageable pageable);



}