package com.steam.controller;

import com.alibaba.fastjson.JSONObject;
import com.steam.service.web.impl.ShowServiceImpl;
import com.steam.thrift.DataHanding.RegionPrice;
import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;
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
    public String getGames()
    {
        List<GamePo> games=showService.getGame();
        String jsonString = JSONObject.toJSONString(games);
        return jsonString;

    }
    @GetMapping("/game-Comment")
    public String getComment()
    {
        List<CommentPo> comments=showService.getComment();
        String jsonString = JSONObject.toJSONString(comments);
        return jsonString;

    }
    @GetMapping("/game-History")
    public String getHistoryData()
    {
        List<HistoryPricePo> historyPrices=showService.getHistoryPrice();
        String jsonString = JSONObject.toJSONString(historyPrices);
        return jsonString;

    }
    @GetMapping("/game-Comment")
    public String getRegionPrice()
    {
        List<RegionPricePo> Prices=showService.getRegionPrice();
        String jsonString = JSONObject.toJSONString(Prices);
        return jsonString;

    }

}
