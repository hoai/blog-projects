package com.lampart.actuator.mvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Controller {
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public Map<String, String> getResource() {
        Map<String, String> resource = new HashMap<String, String>();
        resource.put("resource", "here is some resource");
        return resource;
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public Map<String, String> getPerson() {
        Person person = new Person("John", "Smith", LocalDate.of(1980, Month.JANUARY, 12));
        Map<String, String> resource = new HashMap<String, String>();
        resource.put("Name", personService.getFullName(person));
        resource.put("Age", String.valueOf(personService.getAge(person)));
        // System.out.println("Name is:" + personService.getFullName(person));
        // System.out.println("Age is:" + String.valueOf(personService.getAge(person)));
        return resource;
    }

    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public Map<String, Integer> setResource(@RequestBody Customer customer) {
        Map<String, Integer> resource = new HashMap<String, Integer>();
        resource.put("resource", customer.getCustomerId());
        return resource;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
