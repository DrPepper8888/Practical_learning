package com.steam.thrift.DataHanding.po;

public class WordCloudPo {
    private String gameId;
    private String text;
    private int value;

    public WordCloudPo() {
    }

    public WordCloudPo(String gameId, String text, int value) {
        this.gameId = gameId;
        this.text = text;
        this.value = value;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "WordCloudPo{" +
                "gameId='" + gameId + '\'' +
                ", text='" + text + '\'' +
                ", value=" + value +
                '}';
    }
}
