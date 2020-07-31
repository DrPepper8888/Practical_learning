package com.steam.controller;

import com.alibaba.fastjson.JSONObject;
import com.steam.service.thrift.DHservice;
import com.steam.service.web.impl.ShowServiceImpl;
import com.steam.thrift.DataHanding.DataHandingService;
import com.steam.thrift.DataHanding.po.*;
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
//        public String getOneGame(String GameName){
//        GameName="MEGA VR GAME BUNDLE";
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
    public String getComment(@RequestParam("name") String GameName){
//    public String getComment(String GameName) {
//        GameName="Age of Wonders: Planetfall";
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<WordCloudPo> comments=showService.getComment(GameName,dataHanding);
        String jsonString = JSONObject.toJSONString(comments);
        return jsonString;

    }
    @GetMapping(value="/game-History")
    @ResponseBody
    public String getHistoryData(@RequestParam("name") String GameName){
//    public String getHistoryData(String GameName){
//        GameName="Tropico 6 - The Llama of Wall Street";
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<HistoryPricePo> historyPrices=showService.getHistoryPrice(GameName,dataHanding);
        String jsonString = JSONObject.toJSONString(historyPrices);
        return jsonString;

    }
    @GetMapping(value="/game-RegionPrice")
    @ResponseBody
    public String getRegionPrice(@RequestParam("name") String GameName) {
//    public String getRegionPrice(String GameName) {
//        GameName="Age of Wonders II: The Wizard's Throne";
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<RegionPricePo> Prices=showService.getRegionPrice(GameName,dataHanding);
        String jsonString = JSONObject.toJSONString(Prices);
        return jsonString;

    }

    @GetMapping(value="/game-search")
    @ResponseBody
    public String getGameByFactor(@RequestParam("lowPrice") int lowPrice,
                                  @RequestParam("highPrice") int highPrice,
                                  @RequestParam("tag") String tag) {
//        public String getGameByFactor(Integer lowPrice, Integer highPrice, String tag) {
//        lowPrice=10;highPrice=20;tag="冒险";
        DataHandingService.Iface dataHanding=datahandingservice.startService();
        List<GamePo> gamePos=showService.getGameByFactor(lowPrice,highPrice,tag,dataHanding);
        String jsonString = JSONObject.toJSONString(gamePos);
        return jsonString;
    }

}
