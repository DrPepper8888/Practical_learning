package com.steam.datahanding.Utils;
import com.steam.datahanding.HbaseConfig;
import com.steam.datahanding.SpringContextHolder;
import com.steam.thrift.DataHanding.Game;
import com.steam.thrift.DataHanding.po.GamePo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@DependsOn("springContextHolder")//控制依赖顺序，保证springContextHolder类在之前已经加载
@Component
public class HBaseUtils {
    static{
        System.setProperty("HADOOP_USER_NAME", "icss");
        String osInfo = System.getProperty("os.name");
        if (osInfo.toLowerCase().indexOf("windows") != -1){
            System.setProperty("hadoop.home.dir", "D:/DevTools/hadoop");
            System.setProperty("hadoop.tmp.dir", "d:/mrtmp");
        }
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //手动获取hbaseConfig配置类对象
    private static HbaseConfig hbaseConfig = SpringContextHolder.getBean("hbaseConfig");

    private static Configuration conf = HBaseConfiguration.create();
    private static ExecutorService pool = Executors.newScheduledThreadPool(20);	//设置hbase连接池
    private static Connection connection = null;
    private static HBaseUtils instance = null;
    private static Admin admin = null;

    private HBaseUtils(){
        if(connection == null){
            try {
                //将hbase配置类中定义的配置加载到连接池中每个连接里
                Map<String, String> confMap = hbaseConfig.getconfMaps();
                for (Map.Entry<String,String> confEntry : confMap.entrySet()) {
                    conf.set(confEntry.getKey(), confEntry.getValue());
                }
                connection = ConnectionFactory.createConnection(conf, pool);
                admin = connection.getAdmin();
            } catch (IOException e) {
                logger.error("HbaseUtils实例初始化失败！错误信息为：" + e.getMessage(), e);
            }
        }
    }

    //简单单例方法，如果autowired自动注入就不需要此方法
    public static synchronized HBaseUtils getInstance(){
        if(instance == null){
            instance = new HBaseUtils();
        }
        return instance;
    }


    /**
     * 创建表
     *
     * @param tableName         表名
     * @param columnFamily      列族（数组）
     */
    public void createTable(String tableName, String[] columnFamily) throws IOException{
        TableName name = TableName.valueOf(tableName);
        //如果存在则删除
        if (admin.tableExists(name)) {
            admin.disableTable(name);
            admin.deleteTable(name);
            logger.error("create htable error! this table {} already exists!", name);
        } else {
            HTableDescriptor desc = new HTableDescriptor(name);
            for (String cf : columnFamily) {
                desc.addFamily(new HColumnDescriptor(cf));
            }
            admin.createTable(desc);
        }
    }

    /**
     * 插入记录（单行单列族-多列多值）
     *
     * @param tableName         表名
     * @param row               行名
     * @param columnFamilys     列族名
     * @param columns           列名（数组）
     * @param values            值（数组）（且需要和列一一对应）
     */
    public void insertRecords(String tableName, String row, String columnFamilys, String[] columns, String[] values) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Put put = new Put(Bytes.toBytes(row));
        for (int i = 0; i < columns.length; i++) {
            put.addColumn(Bytes.toBytes(columnFamilys), Bytes.toBytes(columns[i]), Bytes.toBytes(values[i]));
            table.put(put);
        }
    }

    /**
     * 插入记录（单行单列族-单列单值）
     *
     * @param tableName         表名
     * @param row               行名
     * @param columnFamily      列族名
     * @param column            列名
     * @param value             值
     */
    public void insertOneRecord(String tableName, String row, String columnFamily, String column, String value) throws IOException {
        TableName name = TableName.valueOf(tableName);
        Table table = connection.getTable(name);
        Put put = new Put(Bytes.toBytes(row));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        table.put(put);
    }

    /**
     * 删除一行记录
     *
     * @param tablename         表名
     * @param rowkey            行名
     */
    public void deleteRow(String tablename, String rowkey) throws IOException {
        TableName name = TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Delete d = new Delete(rowkey.getBytes());
        table.delete(d);
    }

    /**
     * 删除单行单列族记录
     * @param tablename         表名
     * @param rowkey            行名
     * @param columnFamily      列族名
     */
    public void deleteColumnFamily(String tablename, String rowkey, String columnFamily) throws IOException {
        TableName name = TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Delete d = new Delete(rowkey.getBytes()).addFamily(Bytes.toBytes(columnFamily));
        table.delete(d);
    }

    /**
     * 删除单行单列族单列记录
     *
     * @param tablename         表名
     * @param rowkey            行名
     * @param columnFamily      列族名
     * @param column            列名
     */
    public void deleteColumn(String tablename, String rowkey, String columnFamily, String column) throws IOException {
        TableName name = TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Delete d = new Delete(rowkey.getBytes()).addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        table.delete(d);
    }


    /**
     * 查找一行记录
     *
     * @param tablename         表名
     * @param rowKey            行名
     */
    public static String selectRow(String tablename, String rowKey) throws IOException {
        String record = "";
        TableName name=TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Get g = new Get(rowKey.getBytes());
        Result rs = table.get(g);
        NavigableMap<byte[], NavigableMap<byte[], NavigableMap<Long, byte[]>>> map = rs.getMap();
        for (Cell cell : rs.rawCells()) {
            StringBuffer stringBuffer = new StringBuffer()
                    .append(Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength())).append("\t")
                    .append(Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength())).append("\t")
                    .append(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())).append("\t")
                    .append(Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength())).append("\n");
            String str = stringBuffer.toString();
            record += str;
        }
        return record;
    }

    /**
     * 查找单行单列族单列记录
     *
     * @param tablename         表名
     * @param rowKey            行名
     * @param columnFamily      列族名
     * @param column            列名
     * @return
     */
    public static String selectValue(String tablename, String rowKey, String columnFamily, String column) throws IOException {
        TableName name=TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Get g = new Get(rowKey.getBytes());
        g.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        Result rs = table.get(g);
        return Bytes.toString(rs.value());
    }

    /**
     * 查询表中所有行（Scan方式）
     *
     * @param tablename
     * @return
     */
    public String scanAllRecord(String tablename) throws IOException {
        String record = "";
        TableName name=TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        try {
            for(Result result : scanner){
                for (Cell cell : result.rawCells()) {
                    StringBuffer stringBuffer = new StringBuffer()
                            .append(Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength())).append("\t")
                            .append(Bytes.toString(cell.getFamilyArray(), cell.getFamilyOffset(), cell.getFamilyLength())).append("\t")
                            .append(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())).append("\t")
                            .append(Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength())).append("\n");
                    String str = stringBuffer.toString();
                    record += str;
                }
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return record;
    }

    /**
     * 根据rowkey关键字查询报告记录
     *
     * @param tablename
     * @param rowKeyword
     * @return
     */
    public List scanReportDataByRowKeyword(String tablename, String rowKeyword) throws IOException {
        ArrayList<Object> list = new ArrayList<Object>();

        Table table = connection.getTable(TableName.valueOf(tablename));
        Scan scan = new Scan();

        //添加行键过滤器，根据关键字匹配
        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
        scan.setFilter(rowFilter);

        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
                //TODO 此处根据业务来自定义实现
                list.add(null);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return list;
    }

    /**
     * 根据rowkey关键字和时间戳范围查询报告记录
     *
     * @param tablename
     * @param rowKeyword
     * @return
     */
    public List scanReportDataByRowKeywordTimestamp(String tablename, String rowKeyword, Long minStamp, Long maxStamp) throws IOException {
        ArrayList<Object> list = new ArrayList<Object>();

        Table table = connection.getTable(TableName.valueOf(tablename));
        Scan scan = new Scan();
        //添加scan的时间范围
        scan.setTimeRange(minStamp, maxStamp);

        RowFilter rowFilter = new RowFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(rowKeyword));
        scan.setFilter(rowFilter);

        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner) {
                //TODO 此处根据业务来自定义实现
                list.add(null);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        return list;
    }


    /**
     * 删除表操作
     *
     * @param tablename
     */
    public void deleteTable(String tablename) throws IOException {
        TableName name=TableName.valueOf(tablename);
        if(admin.tableExists(name)) {
            admin.disableTable(name);
            admin.deleteTable(name);
        }
    }


    /**
     * 按照折扣搜索
     *
     * @param tablename
     */
    public ArrayList<Map<String, Object> > scanInfoByDiscount(String tablename) throws IOException {
        TableName name=TableName.valueOf(tablename);
        Table table = connection.getTable(name);
        ResultScanner results = table.getScanner(new Scan());
        ArrayList<Map<String,Object> > arrayList = new ArrayList<>();
        for (Result result : results){
            Map<String,Object> map = new HashMap<>();
            String gameID="";
            String gameName="";
            int commentsNum=0;
            String LikeRate="";
            String discount="";
            String price="";
            String img="";
            String Developers="";
            String tag="";
            String publishers="";
            for(Cell cell : result.rawCells()){
                String row = Bytes.toString(cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
                String colName = Bytes.toString(cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                gameName=row;
                if(colName.equals("gameID")){
                    gameID=value;
                }if(colName.equals("commentsNum")){
                    if(!value.equals(""))
                        commentsNum=Integer.parseInt(value);
                    else commentsNum=0;
                }if(colName.equals("LikeRate")){
                    LikeRate=value;
                }if(colName.equals("discount")){
                    discount=value;
                    if(!discount.equals("no-discount")){
                        String regEx="[^0-9]";
                        Pattern p=Pattern.compile(regEx);
                        Matcher m=p.matcher(discount);
                        map.put("discount",m.replaceAll("").trim());
                    }
                    else map.put("discount","0");
                }if(colName.equals("price")){
                    price=value;
                }if(colName.equals("img")){
                    img=value;
                }if(colName.equals("Developers")){
                    Developers=value;
                }if(colName.equals("tag")){
                    tag=value;
                }if(colName.equals("publishers")){
                    publishers=value;
                }
            }
            Game game=new Game(gameID,gameName,commentsNum,LikeRate,discount,price,img,Developers,tag,publishers);
            map.put("game",game);
            arrayList.add(map);
        }
        return sortByTime(arrayList);
    }

    /**
     * 按照打分搜索
     *
     * @param tablename
     */
    public ArrayList<Map<String, Object> > scanInfoByScore(String tablename,List<String> rowkeys) throws IOException {
        ArrayList<Map<String,Object> > arrayList = new ArrayList<>();
        for(String rowkey:rowkeys){
            String info=selectRow("search",rowkey);
            String[] qualify=info.split("\n");
            for(String one:qualify){
                String[] factor=one.split("\t");
                Map<String,Object> map = new HashMap<>();
                map.put("gameName",factor[2]);
                map.put("score",factor[3]);
                arrayList.add(map);
            }
        }
        return sortByTime(arrayList);
    }

    /**
     * 将arraylist按降价排序
     */
    public static ArrayList<Map<String,Object> > sortByTime(ArrayList<Map<String,Object>> inputArrayList){
        Comparator c = new Comparator<Map<String,String>>() {
            public int compare(Map<String,String> o1, Map<String,String> o2) {
                // TODO Auto-generated method stub
                if(o1.containsKey("discount")) {
                    if (Integer.parseInt(o1.get("discount")) < Integer.parseInt(o2.get("discount")))
                        return 1;
                    else if(Integer.parseInt(o1.get("discount")) > Integer.parseInt(o2.get("discount")))
                        return -1;
                    else return 0;
                }
                else if(o1.containsKey("score")){
                    if (Float.parseFloat(o1.get("score")) < Float.parseFloat(o2.get("score")))
                        return 1;
                    else if (Float.parseFloat(o1.get("score")) > Float.parseFloat(o2.get("score")))
                        return -1;
                    else return 0;
                }
                else return -1;
            }
        };
        Collections.sort(inputArrayList,c);
        return inputArrayList;
    }


}