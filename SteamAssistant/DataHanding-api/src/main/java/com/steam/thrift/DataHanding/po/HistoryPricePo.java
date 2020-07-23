package com.steam.thrift.DataHanding.po;

public class HistoryPricePo {
    private String gameId;
    private String ChangeTime;
    private String Price;

    public HistoryPricePo() {
    }

    public HistoryPricePo(String gameId, String changeTime, String price) {
        this.gameId = gameId;
        ChangeTime = changeTime;
        Price = price;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getChangeTime() {
        return ChangeTime;
    }

    public void setChangeTime(String changeTime) {
        ChangeTime = changeTime;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "HistoryPricePo{" +
                "gameId='" + gameId + '\'' +
                ", ChangeTime='" + ChangeTime + '\'' +
                ", Price='" + Price + '\'' +
                '}';
    }
}
