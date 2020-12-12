package com.bmbatch.post.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmbatch.post.entity.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
	List<PostEntity> findByMemberIdxAndRegDateGreaterThanEqual(Long MemberIdx, LocalDateTime regDate);
}
