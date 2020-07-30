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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/DATA")
public class Dataprovider {
    @Resource
    private ShowServiceImpl showService;

    @GetMapping("/game-detail")
    public String getOneGame(@RequestParam("name") String GameName){
        GamePo game=showService.getOneGame(GameName);
        String jsonString =JSONObject.toJSONString(game);
        return jsonString;
    }

    @GetMapping("/game-data")
    public String getGames()
    {
        List<GamePo> games=showService.getGame();
        String jsonString = JSONObject.toJSONString(games);
        return jsonString;

    }
    @GetMapping("/game-Comment")
    public String getComment(@RequestParam("name") String GameName)
    {
        List<CommentPo> comments=showService.getComment(GameName);
        String jsonString = JSONObject.toJSONString(comments);
        return jsonString;

    }
    @GetMapping("/game-History")
    public String getHistoryData(@RequestParam("name") String GameName)
    {
        List<HistoryPricePo> historyPrices=showService.getHistoryPrice(GameName);
        String jsonString = JSONObject.toJSONString(historyPrices);
        return jsonString;

    }
    @GetMapping("/game-RegionPrice")
    public String getRegionPrice(@RequestParam("name") String GameName)
    {
        List<RegionPricePo> Prices=showService.getRegionPrice(GameName);
        String jsonString = JSONObject.toJSONString(Prices);
        return jsonString;

    }

    @GetMapping("/game-search")
    public String getGameByFactor(@RequestParam("lowPrice") int lowPrice,
                                  @RequestParam("highPrice") int highPrice,
                                  @RequestParam("tag") String tag)
    {
        List<GamePo> gamePos=showService.getGameByFactor(lowPrice,highPrice,tag);
        String jsonString = JSONObject.toJSONString(gamePos);
        return jsonString;
    }

}
