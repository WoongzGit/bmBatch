package com.bmbatch.batch;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bmbatch.comment.entity.CommentEntity;
import com.bmbatch.comment.service.CommentService;
import com.bmbatch.member.entity.MemberEntity;
import com.bmbatch.member.service.MemberService;
import com.bmbatch.post.entity.PostEntity;
import com.bmbatch.post.service.PostService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class bmBatchJob {
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private PostService postService;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	public Job memberRankingJob(JobBuilderFactory jobBuilderFactory, Step memberPostStep, Step memberCommentStep, Step memberRankingStep) {
		logger.info("=================memberRankingJob=================");
		return jobBuilderFactory.get("memberRankingJob" + LocalDateTime.now())
				.start(memberPostStep)
				.next(memberCommentStep)
				.next(memberRankingStep)
				.build();
	}
	
	@Bean public Step memberPostStep(StepBuilderFactory stepBuilderFactory) {
		logger.info("=================memberPostStep=================");
		return stepBuilderFactory.get("memberPostStep").<MemberEntity, MemberEntity>chunk(10)
				.reader(this.memberPostReader())
				.processor(this.memberPostProcessor())
				.writer(this.memberPostWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public ListItemReader<MemberEntity> memberPostReader(){
		logger.info("=================memberPostReader=================");
		List<MemberEntity> listMemberEntity = memberService.findAll();
		return new ListItemReader<>(listMemberEntity);
	}
	
	public ItemProcessor<MemberEntity, MemberEntity> memberPostProcessor(){
		return new ItemProcessor<MemberEntity, MemberEntity>(){
			@Override
			public MemberEntity process(MemberEntity member) throws Exception {
				logger.info("=================memberPostProcessor=================");
				List<PostEntity> listPostEntity = postService.findTarget(member.getMemberIdx());
				member.setRanking(listPostEntity.size() * 5);
				return member;
			}
		};
	}
	
	public ItemWriter<MemberEntity> memberPostWriter(){
		logger.info("=================memberPostWriter=================");
		return((List<? extends MemberEntity> memberList) -> memberService.updateAll(memberList));
	}
	
	@Bean public Step memberCommentStep(StepBuilderFactory stepBuilderFactory) {
		logger.info("=================memberCommentStep=================");
		return stepBuilderFactory.get("memberCommentStep").<MemberEntity, MemberEntity>chunk(10)
				.reader(this.memberCommentReader())
				.processor(this.memberCommentProcessor())
				.writer(this.memberCommentWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public ListItemReader<MemberEntity> memberCommentReader(){
		logger.info("=================memberCommentReader=================");
		List<MemberEntity> listMemberEntity = memberService.findAll();
		return new ListItemReader<>(listMemberEntity);
	}
	
	public ItemProcessor<MemberEntity, MemberEntity> memberCommentProcessor(){
		return new ItemProcessor<MemberEntity, MemberEntity>(){
			@Override
			public MemberEntity process(MemberEntity member) throws Exception {
				logger.info("=================memberCommentProcessor=================");
				List<CommentEntity> listCommentEntity = commentService.findTarget(member.getMemberIdx());
				member.setRanking(member.getRanking() + (listCommentEntity.size() * 3));
				return member;
			}
		};
	}
	
	public ItemWriter<MemberEntity> memberCommentWriter(){
		logger.info("=================memberCommentWriter=================");
		return((List<? extends MemberEntity> memberList) -> memberService.updateAll(memberList));
	}
	
	@Bean public Step memberRankingStep(StepBuilderFactory stepBuilderFactory) {
		logger.info("=================memberRankingStep=================");
		return stepBuilderFactory.get("memberRankingStep").<MemberEntity, MemberEntity>chunk(10)
				.reader(this.memberRankingReader())
				.processor(this.memberRankingProcessor())
				.writer(this.memberRankingWriter())
				.build();
	}
	
	@Bean
	@StepScope
	public ListItemReader<MemberEntity> memberRankingReader(){
		logger.info("=================memberRankingReader=================");
		List<MemberEntity> listMemberEntity = new ArrayList();
		listMemberEntity.add(new MemberEntity());
		return new ListItemReader<>(listMemberEntity);
	}
	
	public ItemProcessor<MemberEntity, MemberEntity> memberRankingProcessor(){
		return new ItemProcessor<MemberEntity, MemberEntity>(){
			@Override
			public MemberEntity process(MemberEntity member) throws Exception {
				logger.info("=================memberRankingProcessor=================");
				
				List<MemberEntity> listMemberEntity = memberService.findAllByOrderByRankingDesc();
				int totalMemberCnt = listMemberEntity.size();
				int grade1 = (int)(totalMemberCnt * 0.01);
				int grade2 = (int)(totalMemberCnt * 0.1);
				int grade3 = (int)(totalMemberCnt * 0.4);
				
				for(int i = 0; i < totalMemberCnt; i++) {
					if(i < grade1) {
						listMemberEntity.get(i).setRanking(1);
					}else if(i < grade2) {
						listMemberEntity.get(i).setRanking(2);
					}else if(i < grade3) {
						listMemberEntity.get(i).setRanking(3);
					}else {
						listMemberEntity.get(i).setRanking(4);
					}
				}
				
				return member;
			}
		};
	}
	
	public ItemWriter<MemberEntity> memberRankingWriter(){
		logger.info("=================memberRankingWriter=================");
		return((List<? extends MemberEntity> memberList) -> memberService.findById((long)1));
	}
}
