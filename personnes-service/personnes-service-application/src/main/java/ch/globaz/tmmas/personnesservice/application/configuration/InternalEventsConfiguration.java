package ch.globaz.tmmas.personnesservice.application.configuration;

import ch.globaz.tmmas.personnesservice.application.event.DistributiveEventMulticaster;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InternalEventsConfiguration {



    @Bean(name = "syncApplicationEventMulticaster")
    public ApplicationEventMulticaster syncApplicationEventMulticaster(){
        return new SimpleApplicationEventMulticaster();
    }

    @Bean(name = "asyncApplicationEventMulticaster")
    public ApplicationEventMulticaster asyncApplicationEventMulticaster(){
        SimpleApplicationEventMulticaster eventMulticaster
                = new SimpleApplicationEventMulticaster();

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();
        executor.setTaskDecorator(new MdcTaskDecorator());

        eventMulticaster.setTaskExecutor(executor);
        return eventMulticaster;
    }

    @Bean(name = "applicationEventMultiCaster")
    public DistributiveEventMulticaster applicationEventMultiCaster(){
        return new DistributiveEventMulticaster();
    }

/**
	@Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster
				= new SimpleApplicationEventMulticaster();

		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.initialize();
		executor.setTaskDecorator(new MdcTaskDecorator());

		eventMulticaster.setTaskExecutor(executor);
		return eventMulticaster;
	}
*/



}
