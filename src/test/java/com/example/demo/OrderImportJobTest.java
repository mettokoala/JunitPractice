package com.example.demo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBatchTest
@SpringBootTest(properties = "spring.batch.job.enabled=false")
@ActiveProfiles("test")
class OrderImportJobTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	void job全体を起動して正常終了すること() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("run.id", System.currentTimeMillis())
				.toJobParameters();

		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

		assertThat(jobExecution.getExitStatus())
				.isEqualTo(ExitStatus.COMPLETED);
	}

	@Test
	void step単体を起動して正常終了すること() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("orderImportStep");

		assertThat(jobExecution.getExitStatus())
				.isEqualTo(ExitStatus.COMPLETED);
	}

}
