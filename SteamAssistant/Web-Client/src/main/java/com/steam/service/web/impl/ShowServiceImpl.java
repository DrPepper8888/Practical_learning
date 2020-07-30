package com.steam.service.web.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.steam.datahanding.Utils.HBaseUtils;
import com.steam.service.web.ShowService;
import com.steam.thrift.DataHanding.Game;
import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShowServiceImpl implements ShowService {
    @Autowired
    HBaseUtils hBaseUtils;
    @Override
    public List<CommentPo> getComment(String name) {
        return null;
    }

    @Override
    public List<GamePo> getGame() {
        try {
            ArrayList<Map<String, Object> > arrayList=hBaseUtils.scanInfoByTime("gameinfo");
            List<GamePo> gamePos=new ArrayList<>();
            for(Map<String,Object> map:arrayList){
                gamePos.add((GamePo) map.get("gamepo"));
            }
            return gamePos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HistoryPricePo> getHistoryPrice(String name) {
        try {
            String gameId=hBaseUtils.selectValue("game",name,"info","history");
            String rowInfo=hBaseUtils.selectValue("game",name,"info","historyPrice");
            rowInfo.replaceAll("\\[","");
            rowInfo.replaceAll("\\]","");
            String[] dataSet=rowInfo.split(",");
            List<HistoryPricePo> historyPricePos=new ArrayList<>();
            for(int i=0;i<dataSet.length/2;i++){
                String time=dataSet[i*2];
                String price=dataSet[i*2+1];
                HistoryPricePo historyPricePo=new HistoryPricePo(gameId,time,price);
                historyPricePos.add(historyPricePo);
            }
            return historyPricePos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RegionPricePo> getRegionPrice(String name) {
        try {
            String gameId=hBaseUtils.selectValue("game",name,"info","regionPrice");
            String rowInfo=hBaseUtils.selectValue("game",name,"info","regionPrice");
            rowInfo.replaceAll("\\{","");
            rowInfo.replaceAll("\\}","");
            String[] infos=rowInfo.split(",");
            List<RegionPricePo> regionPricePos=new ArrayList<>();
            for(String info:infos) {
                JSONObject jsonObject = JSON.parseObject(info);
                String region=jsonObject.getString("country");
                String price=jsonObject.getString("price");
                RegionPricePo regionPricePo=new RegionPricePo(gameId,region,price);
                regionPricePos.add(regionPricePo);
            }
            return regionPricePos;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GamePo getOneGame(String name) {
        try {
            String info=hBaseUtils.selectRow("game",name);
            String[] qualify=info.split("\n");
            GamePo gamePo=new GamePo();
            gamePo.setGameName(name);
            for(String one:qualify){
                String[] factor=one.split("\t");
                if(factor[2].equals("gameID")) {
                    gamePo.setGameID(factor[3]);
                }if(factor[2].equals("commentsNum")) {
                    gamePo.setCommentsNum(Integer.parseInt(factor[3]));
                }if(factor[2].equals("LikeRate")) {
                    gamePo.setLikeRate(factor[3]);
                }if(factor[2].equals("discount")) {
                    gamePo.setDiscount(factor[3]);
                }if(factor[2].equals("price")) {
                    gamePo.setPrice(factor[3]);
                }if(factor[2].equals("img")) {
                    gamePo.setImg(factor[3]);
                }if(factor[2].equals("Developers")) {
                    gamePo.setDevelopers(factor[3]);
                }if(factor[2].equals("tag")) {
                    gamePo.setTag(factor[3]);
                }if(factor[2].equals("publishers")) {
                    gamePo.setPublishers(factor[3]);
                }
            }
            return gamePo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GamePo> getGameByFactor(int lowPrice, int highPrice, String tag) {
        return null;
    }
}
