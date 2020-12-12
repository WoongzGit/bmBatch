package com.bmbatch.member.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bmbatch.member.entity.MemberEntity;
import com.bmbatch.member.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	private MemberRepository memberRepository;
	
	public List<MemberEntity> findAll() {
		return memberRepository.findAll();
	}
	
	
	public Optional<MemberEntity> findById(Long memberIdx) {
		return memberRepository.findById(memberIdx);
	}
	
	public List<MemberEntity> findAllByOrderByRankingDesc() {
		return memberRepository.findAllByOrderByRankingDesc();
	}
	
	public void updateAll(List<? extends MemberEntity> memberList) {
		for(MemberEntity member : memberList) {
			memberRepository.save(member);
		}
	}
}
