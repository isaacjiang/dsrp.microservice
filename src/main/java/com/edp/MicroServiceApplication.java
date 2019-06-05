package com.edp;

import com.edp.organization.OrganizationDataService;
import com.edp.system.DatabaseService;
import com.edp.system.ScheduleService;
import com.edp.system.SystemDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableAsync
@EnableWebFluxSecurity
public class MicroServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MicroServiceApplication.class);
		context.getBean(DatabaseService.class).start();
        context.getBean(OrganizationDataService.class).start();
		context.getBean(SystemDataService.class).start();
		context.getBean(ScheduleService.class).start();

	}

}

