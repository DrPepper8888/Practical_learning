package com.steam.service.web;

import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;

import java.util.List;

public interface ShowService {
    List<CommentPo> getComment();
    List<GamePo> getGame();
    List<HistoryPricePo> getHistoryPrice();
    List<RegionPricePo> getRegionPrice();
    GamePo getOneGame();
}
