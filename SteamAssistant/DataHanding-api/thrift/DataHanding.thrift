# 包名
namespace java com.steam.thrift.DataHanding
#Game  包含Game的所有信息
struct Game{
    1:string gameID;
    2:string gameName;
    3:i32 commentsNum;
    4:string LikeRate;
    5:string discount;
    6:string price;
    7:string img;
    8:string Developers;
    9:string tag;
    10:string publishers;
}
#Comment 包含所有的评论信息，通过id查找
struct Comment{
    1:string gameId;
    2:string text;
}
#History 包含游戏的历史价格走向
struct HistoryPrice{
    1:string gameId;
    2:string ChangeTime;
    3:string Price;
}
#不同地区的价格
struct RegionPrice{
    1:string gameId;
    2:string region;
    3:string Price;
}
#要发布的功能
service DataHandingService {
     Comment getComment(1:string name);
     list<Game> getGame();
     list<HistoryPrice> getHistoryPrice(1:string name);
     list<RegionPrice> getRegionPrice(1:string name);
     Game getInfoByName(1:string name);
     list<Game> getInfoByFactor(1:i32 lowPrice,2:i32 highPrice,3:string label);
}