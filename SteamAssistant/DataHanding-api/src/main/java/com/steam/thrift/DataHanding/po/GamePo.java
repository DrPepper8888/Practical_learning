package com.steam.thrift.DataHanding.po;

public class GamePo {
    private String gameId;
    private String gameName;
    private int commentsNum;
    private String LikeRate;
    private String discount;
    private String price;
    private String img;
    private String outDate;
    private String Developers;
    private String diyLabels;

    public GamePo() {
    }

    public GamePo(String gameId, String gameName, int commentsNum, String likeRate, String discount, String price, String img, String outDate, String developers, String diyLabels) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.commentsNum = commentsNum;
        LikeRate = likeRate;
        this.discount = discount;
        this.price = price;
        this.img = img;
        this.outDate = outDate;
        Developers = developers;
        this.diyLabels = diyLabels;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public String getLikeRate() {
        return LikeRate;
    }

    public void setLikeRate(String likeRate) {
        LikeRate = likeRate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getDevelopers() {
        return Developers;
    }

    public void setDevelopers(String developers) {
        Developers = developers;
    }

    public String getDiyLabels() {
        return diyLabels;
    }

    public void setDiyLabels(String diyLabels) {
        this.diyLabels = diyLabels;
    }

    @Override
    public String toString() {
        return "GamePo{" +
                "gameId='" + gameId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", commentsNum=" + commentsNum +
                ", LikeRate='" + LikeRate + '\'' +
                ", discount='" + discount + '\'' +
                ", price='" + price + '\'' +
                ", img='" + img + '\'' +
                ", outDate='" + outDate + '\'' +
                ", Developers='" + Developers + '\'' +
                ", diyLabels='" + diyLabels + '\'' +
                '}';
    }
}
