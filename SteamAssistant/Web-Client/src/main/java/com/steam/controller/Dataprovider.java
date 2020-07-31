package com.steam.controller;

import com.alibaba.fastjson.JSONObject;
import com.steam.service.thrift.DHservice;
import com.steam.service.web.impl.ShowServiceImpl;
import com.steam.thrift.DataHanding.DataHandingService;
import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping(value="/DATA")
public class Dataprovider {
    @Resource
    private ShowServiceImpl showService;

    @Resource
    private DHservice datahandingservice;


    @GetMapping(value="/game-detail")
    @ResponseBody
    public String getOneGame(@RequestParam("name") String GameName){
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        GamePo game=showService.getOneGame(GameName,dataHanding);
        String jsonString =JSONObject.toJSONString(game);
        return jsonString;
    }

    @GetMapping(value="/game-data")
    @ResponseBody
    public String getGames()
    {
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<GamePo> games=showService.getGame(dataHanding);
        String jsonString = JSONObject.toJSONString(games);
        return jsonString;

    }
    @GetMapping(value="/game-Comment")
    @ResponseBody
    public String getComment(@RequestParam("name") String GameName)
    {
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<CommentPo> comments=showService.getComment(GameName,dataHanding);
        String jsonString = JSONObject.toJSONString(comments);
        return jsonString;

    }
    @GetMapping(value="/game-History")
    @ResponseBody
    public String getHistoryData(@RequestParam("name") String GameName)
    {
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<HistoryPricePo> historyPrices=showService.getHistoryPrice(GameName,dataHanding);
        String jsonString = JSONObject.toJSONString(historyPrices);
        return jsonString;

    }
    @GetMapping(value="/game-RegionPrice")
    @ResponseBody
    public String getRegionPrice(@RequestParam("name") String GameName)
    {
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<RegionPricePo> Prices=showService.getRegionPrice(GameName,dataHanding);
        String jsonString = JSONObject.toJSONString(Prices);
        return jsonString;

    }

    @GetMapping(value="/game-search")
    @ResponseBody
    public String getGameByFactor(@RequestParam("lowPrice") int lowPrice,
                                  @RequestParam("highPrice") int highPrice,
                                  @RequestParam("tag") String tag)
    {
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<GamePo> gamePos=showService.getGameByFactor(lowPrice,highPrice,tag,dataHanding);
        String jsonString = JSONObject.toJSONString(gamePos);
        return jsonString;
    }

}
