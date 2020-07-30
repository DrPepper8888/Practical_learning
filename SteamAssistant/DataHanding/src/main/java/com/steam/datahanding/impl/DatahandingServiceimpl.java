package com.steam.datahanding.impl;

import com.steam.datahanding.Utils.HBaseUtils;
import com.steam.datahanding.mapper.DatahandingMapper;
import com.steam.thrift.DataHanding.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatahandingServiceimpl implements DataHandingService.Iface {
    @Autowired
    HBaseUtils hBaseUtils;

    public DatahandingServiceimpl() {
        super();
    }

    @Override
    public Game getInfoByName(String username) throws TException {
        return null;
    }

    @Override
    public List<Game> getInfoByPrice(String Price) throws TException {
        return null;
    }

    @Override
    public List<Game> getInfoByLabel(String Label) throws TException {
        return null;
    }

    @Override
    public List<RegionPrice> getRegionPrice(String id) throws TException {
        return null;
    }

    @Override
    public List<HistoryPrice> getHistoryPrice(String id) throws TException {
        return null;
    }

    @Override
    public List<Comment> getComment(String id) throws TException {
        return null;
    }
}
