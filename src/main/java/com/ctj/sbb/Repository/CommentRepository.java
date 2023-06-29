package com.ctj.sbb.Repository;

import com.ctj.sbb.entity.Answer;
import com.ctj.sbb.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comment, Integer> {
//    @Query("SELECT c FROM Comment c WHERE c.id IN (:ids)")
//    Page<Comment> findAllByIds(@Param("ids") List<Integer> ids, Pageable pageable);
    Page<Comment> findAll(Pageable pageable);
}
