//package com.steam.tool;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.single.hadoop.tool.WordFilter;
//import org.apache.hadoop.io.IntWritable;
//import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.Text;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
//import org.apdplat.word.segmentation.Word;
//import org.springframework.stereotype.Service;
//
//
//import java.io.*;
//import java.lang.reflect.Array;
//import java.util.*;
//
//public class GameCommentget {
//
//    static {
//        System.setProperty("HADOOP_USER_NAME","czy");
//        String osInfo = System.getProperty("os.name");
//        if(osInfo.toLowerCase().indexOf("windows")!=-1) {
//            System.setProperty("hadoop.home.dir", "C:/dev/hadoop");
//            System.setProperty("hadoop.tmp.dir", "D:/tmp");
//        }
//    }
//    static enum Counter{
//        LINESKIP
//    }
//    public List<Object> getComment() throws IOException {
//        File jsonFile = new File("F:/IdeaProjects/nkmy/input/result(6)(1).json");
//        FileReader fileReader = new FileReader(jsonFile);
//        Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
//        int ch = 0;
//        StringBuffer sb = new StringBuffer();
//        while ((ch = reader.read()) != -1) {
//            sb.append((char) ch);
//        }
//        List<Object> list = null;
//        fileReader.close();
//        reader.close();
//        String jsonStr = sb.toString();
//        JSONArray array = JSON.parseArray(jsonStr);
//        for (Object a : array) {
//            JSONObject jsonObject = (JSONObject) a;
//            list.add(jsonObject.get("content"));
//
//        }
//        return list;
//    }
//    public static class mapper extends Mapper<LongWritable, Text,Text,Text>{
//        @Override
//        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//            File jsonFile = new File("F:/IdeaProjects/nkmy/input/result(6)(1).json");
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            List<Object> list = null;
//            fileReader.close();
//            reader.close();
//            String jsonStr = sb.toString();
//            JSONArray array = JSON.parseArray(jsonStr);
//            for (Object a : array) {
//                JSONObject jsonObject = (JSONObject) a;
//                context.write(value, (Text) jsonObject.get("content"));
//            }
//        }
//    }
//    public static class reduce extends Reducer<Text,Text,Text,IntWritable>{
//        @Override
//        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
//            Map<Word,Integer> map=new HashMap<>();
//            WordFilter wf=new WordFilter();
//            List<String> cds=new ArrayList<>();
//            int num=0;
//            for(Text value : values){
//                if(num>=50)
//                    break;
//                num++;
//                List<Word> list=wf.automaticSelection(line);
//                for(Word word:list){
//                    if(map.containsKey(word))
//                        map.put(word,map.get(word)+1);
//                    else map.put(word,1);
//                }
//            }
//            List<Map.Entry<Word, Integer>> list = new LinkedList<>(map.entrySet());
//            Collections.sort(list, Comparator.comparingInt(Map.Entry::getValue));
//            for(int i=list.size()-1;i>=list.size()-120;i--){
//                if(i<0)
//                    break;
//                int curWeight=list.get(i).getValue();
//                String curWord=list.get(i).getKey().toString();
//                context.write(new Text(curWord),new IntWritable(curWeight)));
//            }
//
//        }
//    }
//}
