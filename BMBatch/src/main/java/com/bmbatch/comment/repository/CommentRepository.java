package com.bmbatch.comment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmbatch.comment.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	
	List<CommentEntity> findByMemberIdxAndRegDateGreaterThanEqual(Long memberIdx, LocalDateTime regDate);
}
