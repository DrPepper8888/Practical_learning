package com.steam.thrift.DataHanding.po;

public class RegionPricePo {
    private String gameId;
    private String region;
    private String Price;

    public RegionPricePo() {
    }

    public RegionPricePo(String gameId, String region, String price) {
        this.gameId = gameId;
        this.region = region;
        Price = price;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "RegionPricePo{" +
                "gameId='" + gameId + '\'' +
                ", region='" + region + '\'' +
                ", Price='" + Price + '\'' +
                '}';
    }
}
