package com.techsophy.tsf.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.MULTI_TENANCY_PACKAGE_NAME;
import static com.techsophy.tsf.workflow.constants.WorkFlowConstants.PACKAGE_NAME;

@RefreshScope
@EnableMongoRepositories
@EnableMongoAuditing
@SpringBootApplication
@ComponentScan({PACKAGE_NAME, MULTI_TENANCY_PACKAGE_NAME})
public class TechsophyPlatformWorkflowModelerApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(TechsophyPlatformWorkflowModelerApplication.class, args);
	}
}
