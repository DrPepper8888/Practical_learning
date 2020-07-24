package com.steam.controller;

import com.steam.service.thrift.datahandingservice;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class datahandingcontroller {
    @Resource
    public datahandingservice datahandingservice;
}
