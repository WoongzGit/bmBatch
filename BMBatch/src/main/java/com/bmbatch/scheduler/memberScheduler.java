package com.bmbatch.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bmbatch.job.MemberRankingBatchJob;

@Component
public class memberScheduler {
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	MemberRankingBatchJob memberRankingBatchJob;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Scheduled(initialDelay = 3000, fixedDelay = 86400000)
	public void runJob() {
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters = new JobParameters(confMap);

        try {
            jobLauncher.run(memberRankingBatchJob.memberRankingJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {

        	logger.error(e.getMessage());
        }
	}
}
