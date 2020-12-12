package com.bmbatch.post.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmbatch.post.entity.PostEntity;
import com.bmbatch.post.repository.PostRepository;

@Service
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public List<PostEntity> findTarget(Long memberIdx) {
		LocalDateTime regDate = LocalDateTime.now().minusDays(30);
		return postRepository.findByMemberIdxAndRegDateGreaterThanEqual(memberIdx, regDate);
	}
}
