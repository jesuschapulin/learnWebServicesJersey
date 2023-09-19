package com.freeLearn.service;

import jakarta.inject.Named;
import org.jvnet.hk2.annotations.Service;

@Service @Named("local")
public class MessajeLocal implements MessageService {

    @Override
    public String getHello() {
        return "Hello from local message from service in jersey";
    }

}