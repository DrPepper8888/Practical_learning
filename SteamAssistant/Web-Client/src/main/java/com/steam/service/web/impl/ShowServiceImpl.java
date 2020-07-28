package com.steam.service.web.impl;

import com.steam.service.web.ShowService;
import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {
    @Override
    public List<CommentPo> getComment() {
        return null;
    }

    @Override
    public List<GamePo> getGame() {
        return null;
    }

    @Override
    public List<HistoryPricePo> getHistoryPrice() {
        return null;
    }

    @Override
    public List<RegionPricePo> getRegionPrice() {
        return null;
    }

    @Override
    public GamePo getOneGame() {
        return null;
    }
}
