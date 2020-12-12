package com.bmbatch.comment.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.bmbatch.comment.entity.CommentEntity;
import com.bmbatch.comment.repository.CommentRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;

	public List<CommentEntity> findTarget(Long memberIdx) {
		LocalDateTime regDate = LocalDateTime.now().minusDays(30);
		return commentRepository.findByMemberIdxAndRegDateGreaterThanEqual(memberIdx, regDate);
	}
}
