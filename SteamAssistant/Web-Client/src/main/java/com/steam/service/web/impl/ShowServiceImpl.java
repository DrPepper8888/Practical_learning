package com.steam.service.web.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.steam.datahanding.Utils.HBaseUtils;
import com.steam.datahanding.Utils.thrifttoPo;
import com.steam.service.web.ShowService;
import com.steam.thrift.DataHanding.DataHandingService;
import com.steam.thrift.DataHanding.Game;
import com.steam.thrift.DataHanding.HistoryPrice;
import com.steam.thrift.DataHanding.RegionPrice;
import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShowServiceImpl implements ShowService {
//    @Autowired
    thrifttoPo thriftToPo;
    @Override
    public List<CommentPo> getComment(String name, DataHandingService.Iface dataHanding) {
        return null;
    }

    @Override
    public List<GamePo> getGame(DataHandingService.Iface dataHanding) {
        try {
            List<Game> gameList=dataHanding.getGame();
            List<GamePo> gamePoList=new ArrayList<>();
            for(Game game:gameList){
                GamePo gamePo=thriftToPo.toGamePo(game);
                gamePoList.add(gamePo);
            }
            return gamePoList;
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HistoryPricePo> getHistoryPrice(String name, DataHandingService.Iface dataHanding) {
        try {
            List<HistoryPrice> historyPrices=dataHanding.getHistoryPrice(name);
            List<HistoryPricePo> historyPricePos=new ArrayList<>();
            for(HistoryPrice historyPrice:historyPrices){
                HistoryPricePo historyPricePo=thriftToPo.tohistoryPricePo(historyPrice);
                historyPricePos.add(historyPricePo);
            }
            return historyPricePos;
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RegionPricePo> getRegionPrice(String name, DataHandingService.Iface dataHanding) {
        try {
            List<RegionPrice> regionPrices=dataHanding.getRegionPrice(name);
            List<RegionPricePo> regionPricePos=new ArrayList<>();
            for(RegionPrice regionPrice:regionPrices){
                RegionPricePo regionPricePo=thriftToPo.toRegionPricePo(regionPrice);
                regionPricePos.add(regionPricePo);
            }
            return regionPricePos;
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GamePo getOneGame(String name, DataHandingService.Iface dataHanding) {
        try {
            Game game=dataHanding.getInfoByName(name);
            GamePo gamePo=thriftToPo.toGamePo(game);
            return gamePo;
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GamePo> getGameByFactor(int lowPrice, int highPrice, String tag, DataHandingService.Iface dataHanding) {
        try {
            List<Game> games=dataHanding.getInfoByFactor(lowPrice,highPrice,tag);
            List<GamePo> gamePos=new ArrayList<>();
            for(Game game:games){
                GamePo gamePo=thriftToPo.toGamePo(game);
                gamePos.add(gamePo);
            }
            return gamePos;
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }
}
