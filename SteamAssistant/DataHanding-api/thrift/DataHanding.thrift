# 包名
namespace java com.steam.thrift.DataHanding
#Game  包含Game的所有信息
struct Game{
    1:string gameId;
    2:string gameName;
    3:i32 commentsNum;
    4:string LikeRate;
    5:string discount;
    6:string price;
    7:string img;
    8:string outDate;
    9:string Developers;
    10:string diyLabels;
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
    3:string price;
}
#要发布的功能
service DataHandingService {
     Game getInfoByName(1:string username);
     list<Game> getInfoByPrice(1:string Price);
     list<Game> getInfoByLabel(1:string Label);
     list<RegionPrice> getRegionPrice(1:string id);
     list<HistoryPrice> getHistoryPrice(1:string id);
     list<Comment> getComment(1:string id);
}