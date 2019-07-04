package com.lampart.actuator.mvc;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import com.lampart.actuator.performancemonitor.LogExecutionTime;

@Service
public class PersonService {
    @LogExecutionTime
    public String getFullName(Person person) {
        return person.getLastName() + " " + person.getFirstName();
    }

    @LogExecutionTime
    public int getAge(Person person) {
        Period p = Period.between(person.getDateOfBirth(), LocalDate.now());
        return p.getYears();
    }

}
