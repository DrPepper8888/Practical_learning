package com.steam.Utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.*;
import java.net.URI;
import java.util.*;

public class HadoopUtils {


    static {
        System.setProperty("HADOOP_USER_NAME","czy");
        String osInfo = System.getProperty("os.name");
        if(osInfo.toLowerCase().indexOf("windows")!=-1)
            System.setProperty("hadoop.home.dir","C:/dev/hadoop");
    }

    /**
     * 得到FileSystem类
     * @param URISTR
     * @return
     * @throws IOException
     */
    public static FileSystem getFileSystem(String URISTR) throws IOException {
        URI hdfsRootURI =URI.create(URISTR);
        Configuration conf =new Configuration() ;
        FileSystem fs = FileSystem.get(hdfsRootURI,conf);
        return fs;
    }
    /**
     * 检查文件或者文件夹是否存在
     *
     * @param filename
     * @return
     */
    public boolean checkFileExist(String filename,String URISTR) throws IOException {
        FileSystem hdfs =getFileSystem(URISTR) ;
        try {
            Path f = new Path(filename);
            return hdfs.exists(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param dirName
     * @return
     */
    public boolean mkdir(String dirName,String URISTR) throws IOException {
        FileSystem hdfs =getFileSystem(URISTR) ;
        if (checkFileExist(dirName,URISTR))
            return true;
        try {
            Path f = new Path(dirName);
            System.out.println("Create and Write :" + f.getName() + " to hdfs");
            return hdfs.mkdirs(f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * 删除文件或者文件夹
     *
     * @param src
     * @throws Exception
     */
    public void delete(String src,String URISTR) throws Exception {
        FileSystem hdfs =getFileSystem(URISTR) ;
        Path p1 = new Path(src);
        if (hdfs.isDirectory(p1)) {
            hdfs.delete(p1, true);
            System.out.println("删除文件夹成功: " + src);
        } else if (hdfs.isFile(p1)) {
            hdfs.delete(p1, false);
            System.out.println("删除文件成功: " + src);
        }
    }


    /**
     * 创建一个空文件
     *
     * @param filePath
     *            文件的完整路径名称
     * @return
     */
    public boolean mkfile(String filePath,String URISTR) throws IOException {
        FileSystem hdfs =getFileSystem(URISTR) ;
        try {
            Path f = new Path(filePath);
            FSDataOutputStream os = hdfs.create(f, true);
            os.close();
            return true;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获得状态
     * @param URISTR
     * @return
     * @throws IOException
     */
    public List<Long> getstatus(String URISTR) throws IOException {
        FileSystem hdfs =getFileSystem(URISTR) ;
        FsStatus fsStatus = hdfs.getStatus();
        List<Long> list=new ArrayList<>();
        long used = fsStatus.getUsed();
        long remaining = fsStatus.getRemaining();
        long capacity = fsStatus.getCapacity();
        list.add(used);
        list.add(remaining);
        list.add(capacity);
        return list;
    }



    /**
     * 从hdfs下载
     * @param hdfsFilename
     * @param localPath
     * @return
     */
    public boolean downloadFileFromHdfs(String hdfsFilename, String localPath,String URISTR) {
        try {
            Path f = new Path(hdfsFilename);
            FileSystem hdfs =getFileSystem(URISTR) ;
            FSDataInputStream dis = hdfs.open(f);
            File file = new File(localPath + "/" + f.getName());
            FileOutputStream os = new FileOutputStream(file);

            byte[] buffer = new byte[1024000];
            int length = 0;
            while ((length = dis.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }

            os.close();
            dis.close();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 上传到hdfs
     * @param localFilename
     * @param hdfsPath
     * @return
     */
    public boolean copyLocalFileToHDFS(String localFilename, String hdfsPath,String URISTR) throws IOException {
        FileSystem hdfs =getFileSystem(URISTR) ;
        try {
            // 如果路径不存在就创建文件夹
            mkdir(hdfsPath,URISTR);

            File file = new File(localFilename);
            FileInputStream is = new FileInputStream(file);

            // 如果hdfs上已经存在文件，那么先删除该文件
            if (this.checkFileExist(hdfsPath + "/" + file.getName(),URISTR)) {
                delete(hdfsPath + "/" + file.getName(),URISTR);
            }

            Path f = new Path(hdfsPath + "/" + file.getName());

            FSDataOutputStream os = hdfs.create(f, true);
            byte[] buffer = new byte[10240000];
            int nCount = 0;

            while (true) {
                int bytesRead = is.read(buffer);
                if (bytesRead <= 0) {
                    break;
                }

                os.write(buffer, 0, bytesRead);
                nCount++;
                if (nCount % (100) == 0)
                    System.out.println((new Date()).toLocaleString() + ": Have move " + nCount + " blocks");
            }

            is.close();
            os.close();
            System.out.println((new Date()).toLocaleString() + ": Write content of file " + file.getName()
                    + " to hdfs file " + f.getName() + " success");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 移动
     * @param src
     * @param dst
     * @throws Exception
     */
    public void movefile(String src, String dst,String URISTR) throws Exception {
        FileSystem hdfs =getFileSystem(URISTR) ;
        Path p1 = new Path(src);
        Path p2 = new Path(dst);

        hdfs.rename(p1, p2);
    }


    /**
     * 完成复制
     * @param srcfile
     * @param desfile
     * @return
     */
    public boolean hdfsCopyUtils(String srcfile, String desfile) {
        Configuration conf = new Configuration();
        Path src = new Path(srcfile);
        Path dst = new Path(desfile);
        try {
            FileUtil.copy(src.getFileSystem(conf), src, dst.getFileSystem(conf), dst, false, conf);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * 遍历文件 ls
     * @param hdfs
     * @param listPath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<String> recursiveHdfsPath(FileSystem hdfs, Path listPath)
            throws FileNotFoundException, IOException {
        List<String> list = new ArrayList<String>();
        FileStatus[] files = null;
        files = hdfs.listStatus(listPath);
        for (FileStatus f : files) {
            if (files.length == 0 || f.isFile()) {
                list.add(f.getPath().toUri().getPath());
            } else {
                list.add(f.getPath().toUri().getPath());
                recursiveHdfsPath(hdfs, f.getPath());
            }
        }
        for (String a : list) {
            System.out.println(a);
        }
        return list;
    }




}
