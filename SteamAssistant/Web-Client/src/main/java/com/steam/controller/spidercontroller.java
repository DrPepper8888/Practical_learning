package com.steam.controller;

import com.steam.service.thrift.spiderservice;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class spidercontroller {
    @Resource
    private spiderservice spiderservice;
}
