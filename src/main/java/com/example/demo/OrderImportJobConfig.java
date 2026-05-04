package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class OrderImportJobConfig {
	@Bean
	Job orderImportJob(
			JobRepository jobRepository,
			Step orderImportStep) {
		return new JobBuilder("orderImportJob", jobRepository)
				.start(orderImportStep)
				.build();
	}

	@Bean
	Step orderImportStep(
			JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			OrderRepository orderRepository) {
		return new StepBuilder("orderImportStep", jobRepository)
				.tasklet((contribution, chunkContext) -> {
					orderRepository.save("ノートPC", 2);
					return RepeatStatus.FINISHED;
				}, transactionManager)
				.build();
	}
}
