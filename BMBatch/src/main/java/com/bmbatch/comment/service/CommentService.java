package com.bmbatch.comment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmbatch.comment.entity.CommentEntity;
import com.bmbatch.comment.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;

	public List<CommentEntity> findTarget(Long memberIdx) {
		LocalDateTime regDate = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0, 0));
		return commentRepository.findByMemberIdxAndRegDateGreaterThanEqual(memberIdx, regDate);
	}
}
