package com.steam.service.web;

import com.steam.thrift.DataHanding.DataHandingService;
import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;

import java.util.List;

public interface ShowService {
    List<CommentPo> getComment(String name, DataHandingService.Iface dataHanding);
    List<GamePo> getGame(DataHandingService.Iface dataHanding);
    List<HistoryPricePo> getHistoryPrice(String name, DataHandingService.Iface dataHanding);
    List<RegionPricePo> getRegionPrice(String name, DataHandingService.Iface dataHanding);
    GamePo getOneGame(String name, DataHandingService.Iface dataHanding);
    List<GamePo> getGameByFactor(int lowPrice,int highPrice,String tag, DataHandingService.Iface dataHanding);
}
