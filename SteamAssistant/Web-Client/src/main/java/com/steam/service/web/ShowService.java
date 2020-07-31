package com.steam.service.web;

import com.steam.thrift.DataHanding.DataHandingService;
import com.steam.thrift.DataHanding.po.*;

import java.util.List;

public interface ShowService {
    List<WordCloudPo> getComment(String name, DataHandingService.Iface dataHanding);
    List<GamePo> getGame(DataHandingService.Iface dataHanding);
    List<HistoryPricePo> getHistoryPrice(String name, DataHandingService.Iface dataHanding);
    List<RegionPricePo> getRegionPrice(String name, DataHandingService.Iface dataHanding);
    GamePo getOneGame(String name, DataHandingService.Iface dataHanding);
    List<GamePo> getGameByFactor(int lowPrice,int highPrice,String tag, DataHandingService.Iface dataHanding);
}
