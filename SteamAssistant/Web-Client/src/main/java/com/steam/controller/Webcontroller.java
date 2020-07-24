package com.steam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/show")
public class Webcontroller {
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String turnToMainPage() {
        return "MainPage";
    }
    //排行页面
    @RequestMapping(value = "rank", method = RequestMethod.GET)
    public String turnTorankPage() {
        return "rankPage";
    }
    //去详情目录
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String turnToDetailPage() {
        return "DetailPage";
    }
    @RequestMapping(value = "/search-by", method = RequestMethod.GET)
    public String turnTosearchresultPage() {
        return "search-by";
    }

    //网页跳转
    @RequestMapping(value = "toPage", method = RequestMethod.GET)
    public String toPage(HttpServletRequest request) {
        String url = request.getParameter("url");
        return url;
    }
}
