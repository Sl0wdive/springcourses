package com.example.aopdemo.service;
import org.springframework.stereotype.Service;


@Service
public class TrafficFortuneServiceImpl implements TrafficFortuneService{
    @Override
    public String getFortune(boolean tripWire) {

        if (tripWire) {
            throw new RuntimeException("Major accident! Highway is closed!");
        }

        return  "Expect heavy traffic!";

    }
}
