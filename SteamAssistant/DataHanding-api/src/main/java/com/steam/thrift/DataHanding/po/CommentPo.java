package com.steam.thrift.DataHanding.po;

public class CommentPo {
    private String gameId;
    private String text;

    public CommentPo() {
    }

    public CommentPo(String gameId, String text) {
        this.gameId = gameId;
        this.text = text;
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

    @Override
    public String toString() {
        return "CommentPo{" +
                "gameId='" + gameId + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
