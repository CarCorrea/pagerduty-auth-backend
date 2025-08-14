package com.TechnicalTest.list_service.controller;

import com.TechnicalTest.list_service.service.PagerDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagerDutyController {

    @Autowired
    private PagerDutyService pagerDutyService;

    @GetMapping("/pager/services")
    public String getServices(){
        return pagerDutyService.getServices();
    }
}
