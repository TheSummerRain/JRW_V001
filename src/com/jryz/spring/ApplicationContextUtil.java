package com.jryz.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextUtil implements ApplicationContextAware {

	private volatile static ApplicationContext context;

	public static ApplicationContext getContext() {
		/*
        if (context != null) {
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			return wac;
		}
		*/
		return context;
	}

    @Override
    @SuppressWarnings("static-access")
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        this.context = context;
    }
}
