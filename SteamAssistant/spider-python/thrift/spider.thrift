
namespace java com.steam.thrift.message

namespace py spider.api


service SpiderService{
    bool SpiderRank();
    bool SpiderDetailInfo(1:string id);
    bool SpiderHistoryPrice(1:string id);
    bool SpiderDbinfo(1:string id);
    bool SpiderNumberOnline();
    bool SpiderComments();
}