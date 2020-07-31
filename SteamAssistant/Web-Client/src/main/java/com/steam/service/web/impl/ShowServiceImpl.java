package com.steam.service.web.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.steam.datahanding.Utils.HBaseUtils;
import com.steam.datahanding.Utils.thrifttoPo;
import com.steam.service.web.ShowService;
import com.steam.thrift.DataHanding.*;
import com.steam.thrift.DataHanding.po.*;
import com.steam.tool.WordFilter;
import org.apache.thrift.TException;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.util.*;

@Service
public class ShowServiceImpl implements ShowService {
//    @Autowired
    thrifttoPo thriftToPo;
    @Override
    public List<WordCloudPo> getComment(String name, DataHandingService.Iface dataHanding) {
//        try {
//            Comment comment=dataHanding.getComment(name);
//            String text=comment.getText();
//            String gameId=comment.getGameId();
//            List<WordCloudPo> wordCloudPos=new ArrayList<>();
//            List<Word> words= WordFilter.automaticSelection(text);
//            for(Word word:words){
//                WordCloudPo wordCloudPo=new WordCloudPo(gameId,word.toString(), (int) (Math.random()*10));
//                wordCloudPos.add(wordCloudPo);
//            }
//            return wordCloudPos;
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//        return null;


        Comment comment= null;
        try {
            comment = dataHanding.getComment(name);
            String text=comment.getText();
            String gameId=comment.getGameId();
            Map<Word,Integer> map=new HashMap<>();
            WordFilter wf=new WordFilter();
            List<WordCloudPo> cds=new ArrayList<>();
            int num=0;
            List<Word> words= wf.automaticSelection(text);
            for(Word word:words){
                if(map.containsKey(word))
                    map.put(word,map.get(word)+1);
                else map.put(word,1);
            }
            List<Map.Entry<Word, Integer>> list = new LinkedList<>(map.entrySet());
            Collections.sort(list, Comparator.comparingInt(Map.Entry::getValue));
            for(int i=list.size()-1;i>=list.size()-100;i--){
                if(i<0)
                    break;
                int curWeight=list.get(i).getValue();
                String curWord=list.get(i).getKey().toString();
                cds.add(new WordCloudPo(gameId,curWord,curWeight));
            }
            return cds;
        } catch (TException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<GamePo> getGame(DataHandingService.Iface dataHanding) {
        try {
            List<Game> gameList=dataHanding.getGame();
            System.out.println(gameList);
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
