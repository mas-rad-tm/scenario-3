package ch.globaz.tmmas.personnesservice.application.configuration;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;


class MdcTaskDecorator implements TaskDecorator {

	@Override
	public Runnable decorate(Runnable runnable) {
		//web thread context!!!
		//recup des donnes mdc du thread courant
		Map<String, String> contexttMap = MDC.getCopyOfContextMap();

		return () -> {

			try{
				//async thread contexte
				// restauration du contexte mdc du web
				MDC.setContextMap(contexttMap);
				runnable.run();
			}
			finally{
				MDC.clear();
			}

		};


	}
}
