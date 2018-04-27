package ch.globaz.tmmas.personnesservice.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
public class InternalEventsConfiguration {


	@Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster
				= new SimpleApplicationEventMulticaster();

		SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
		executor.setTaskDecorator(new MdcTaskDecorator());

		eventMulticaster.setTaskExecutor(executor);
		return eventMulticaster;
	}


}
