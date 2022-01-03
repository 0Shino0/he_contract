package com.ruoyi.common.utils.ipfs;


import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * IPFS 工具类
 *
 * @author Henriport
 * @since 2021/11/16
 */
public class IPFSUtils {
    /**
     * ipfs的服务器地址和端口
     */
    private static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

    /**
     * 将文件url转换为InputStream
     * @param url
     * @return
     */
    public static InputStream getFileInputSteam(String url){
          try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            return inStream;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文件url转换为byte[]
     * @param url
     * @return
     */
    public static byte[] getFileSteam(String url){
          try {
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)httpUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据
            byte[] btImg = readInputStream(inStream);
            return btImg;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

     /**
   * 从输入流中获取数据
   * @param inStream 输入流
   * @return
   * @throws Exception
   */
   public static byte[] readInputStream(InputStream inStream) throws Exception{
     ByteArrayOutputStream outStream = new ByteArrayOutputStream();
     byte[] buffer = new byte[1024];
     int len = 0;
     while( (len=inStream.read(buffer)) != -1 ){
         outStream.write(buffer, 0, len);
     }
     inStream.close();
     return outStream.toByteArray();
   }

    /**
     * 整合，根据URL地址上传文件
     * @param url
     * @return
     * @throws IOException
     */
   public static String uploadByUrl(String url) throws IOException {
       InputStream fileInputSteam = getFileInputSteam(url);
       return upload(fileInputSteam);
   }

    /**
     * 根据文件路径上传文件
     * src/image/hello.jpg
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String upload(String fileName) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(fileName));
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 根据inputstream上传文件
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String upload(InputStream inputStream) throws IOException {
        NamedStreamable.InputStreamWrapper file = new NamedStreamable.InputStreamWrapper(inputStream);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 根据文件二进制内容上传文件
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String upload(byte[] data) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toString();
    }

    /**
     * 根据hash值下载文件
     *
     * @param hash
     * @return
     */
    public static byte[] download(String hash) {
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 替换文件？
     *
     * @param hash
     * @param destFile
     */
    public static void download(String hash, String destFile) {
        byte[] data = null;
        try {
            data = ipfs.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data != null && data.length > 0) {
            File file = new File(destFile);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
