package com.xxm.druiddemo.controller;

import com.xxm.druiddemo.entity.Street;
import com.xxm.druiddemo.repository.StreetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StreetContoller {
    @Autowired
    private StreetRepository streetRepository;

    @RequestMapping("/getStreet")
    public List<Street> getStreet() {
        return streetRepository.findAll();
    }


}
