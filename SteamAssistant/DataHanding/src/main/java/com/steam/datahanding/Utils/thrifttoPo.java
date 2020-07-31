package com.steam.datahanding.Utils;

import com.steam.thrift.DataHanding.Comment;
import com.steam.thrift.DataHanding.Game;
import com.steam.thrift.DataHanding.HistoryPrice;
import com.steam.thrift.DataHanding.RegionPrice;
import com.steam.thrift.DataHanding.po.CommentPo;
import com.steam.thrift.DataHanding.po.GamePo;
import com.steam.thrift.DataHanding.po.HistoryPricePo;
import com.steam.thrift.DataHanding.po.RegionPricePo;
import org.springframework.beans.BeanUtils;

public class thrifttoPo {

        public static CommentPo toCommentPo(Comment comment) {
            CommentPo  commentPo = new CommentPo();

            BeanUtils.copyProperties(comment, commentPo);
            return commentPo;
        }
    public static GamePo toGamePo(Game game) {
        GamePo  gamePo = new GamePo();

        BeanUtils.copyProperties(game, gamePo);
        return gamePo;
    }
    public static HistoryPricePo tohistoryPricePo(HistoryPrice historyPrice) {
        HistoryPricePo  historyPricePo = new HistoryPricePo();

        BeanUtils.copyProperties(historyPrice, historyPricePo);
        return historyPricePo;
    }
    public static RegionPricePo toRegionPricePo(RegionPrice regionPrice) {
        RegionPricePo  regionPricePo = new RegionPricePo();

        BeanUtils.copyProperties(regionPrice, regionPricePo);
        return regionPricePo;
    }
}
