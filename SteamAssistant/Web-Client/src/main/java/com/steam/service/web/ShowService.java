package com.steam.service.web;

import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;

import java.util.List;

public interface ShowService {
    List<CommentPo> getComment(String name);
    List<GamePo> getGame();
    List<HistoryPricePo> getHistoryPrice(String name);
    List<RegionPricePo> getRegionPrice(String name);
    GamePo getOneGame(String name);
    List<GamePo> getGameByFactor(int lowPrice,int highPrice,String tag);
}
