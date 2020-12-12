package com.bmbatch.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bmbatch.member.entity.MemberEntity;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
	List<MemberEntity> findAllByOrderByRankingDesc();
}
