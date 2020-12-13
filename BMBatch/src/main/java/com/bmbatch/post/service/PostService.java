package com.bmbatch.post.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmbatch.post.entity.PostEntity;
import com.bmbatch.post.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	public List<PostEntity> findTarget(Long memberIdx) {
		LocalDateTime regDate = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0, 0));
		return postRepository.findByMemberIdxAndRegDateGreaterThanEqual(memberIdx, regDate);
	}
}
