package com.steam.thrift.DataHanding.po;


public class GamePo {
    private String gameID;
    private String gameName;
    private int commentsNum;
    private String LikeRate;
    private String discount;
    private String price;
    private String img;
    private String Developers;
    private String tag;
    private String publishers;

    public GamePo() {
    }

    public GamePo(String gameID, String gameName, int commentsNum, String likeRate, String discount, String price, String img, String developers, String tag, String publishers) {
        this.gameID = gameID;
        this.gameName = gameName;
        this.commentsNum = commentsNum;
        LikeRate = likeRate;
        this.discount = discount;
        this.price = price;
        this.img = img;
        Developers = developers;
        this.tag = tag;
        this.publishers = publishers;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
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

    public String getDevelopers() {
        return Developers;
    }

    public void setDevelopers(String developers) {
        Developers = developers;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    @Override
    public String toString() {
        return "GamePo{" +
                "gameID='" + gameID + '\'' +
                ", gameName='" + gameName + '\'' +
                ", commentsNum=" + commentsNum +
                ", LikeRate='" + LikeRate + '\'' +
                ", discount='" + discount + '\'' +
                ", price='" + price + '\'' +
                ", img='" + img + '\'' +
                ", Developers='" + Developers + '\'' +
                ", tag='" + tag + '\'' +
                ", publishers='" + publishers + '\'' +
                '}';
    }
}
