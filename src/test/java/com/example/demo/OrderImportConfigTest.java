package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

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
class OrderImportConfigTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	void test() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addLong("run.id", System.currentTimeMillis())
				.toJobParameters();
		JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
		assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
	}

	@Test
	void testStep() throws Exception {
		JobExecution jobExecution = jobLauncherTestUtils.launchStep("orderImportStep");
		assertEquals(jobExecution.getExitStatus(), ExitStatus.COMPLETED);
	}
}
