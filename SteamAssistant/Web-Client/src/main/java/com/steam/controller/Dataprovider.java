package com.steam.controller;

import com.alibaba.fastjson.JSONObject;
import com.steam.service.web.impl.ShowServiceImpl;
import com.steam.thrift.DataHanding.po.GamePo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/DATA")
public class Dataprovider {
    @Resource
    private ShowServiceImpl showService;

    @GetMapping("/game-data")
    public String gatGames()
    {
        List<GamePo> games=showService.getGame();
        String jsonString = JSONObject.toJSONString(games);
        return jsonString;

    }
}
