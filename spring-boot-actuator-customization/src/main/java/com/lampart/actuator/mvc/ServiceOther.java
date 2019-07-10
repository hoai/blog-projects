package com.lampart.actuator.mvc;

import org.springframework.stereotype.Component;

import com.lampart.actuator.performancemonitor.LogExecutionTime;

@Component
public class ServiceOther {

    @LogExecutionTime
    public void serve() throws InterruptedException {
        Thread.sleep(2000);
    }
}
