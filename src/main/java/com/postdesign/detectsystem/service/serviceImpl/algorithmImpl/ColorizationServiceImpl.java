package com.postdesign.detectsystem.service.serviceImpl.algorithmImpl;

import com.postdesign.detectsystem.service.algorithmService.ColorizationService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ColorizationServiceImpl implements ColorizationService {

    @Override
    public byte[] colorization(String uname, byte[] input) {
        try {
            String filename = UUID.randomUUID().toString();
            Path output = Paths.get("src/main/saveimg/colorization", uname);
            if (Files.notExists(output)){
                Files.createDirectory(output);
            }
            Path resolve = output.resolve(filename + ".png");
            saveImg(input, resolve.toString());
            String root = "D:\\IdeaObjects\\DetectSystemWeb\\src\\main\\saveimg\\colorization\\";
            String demoPy = "src/main/python/colorization_release.py";
            String i = "-i"+root+uname+"\\"+filename + ".png";
            String o = "-o"+root+uname+"\\"+filename;

            Process proc;
            String[] args1=new String[]{"python",demoPy,i,o};
            System.out.println("waiting...");
            proc = Runtime.getRuntime().exec(args1);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            System.out.println(proc.waitFor());
            System.out.println("finish");
            return img2byte(root+uname+"\\"+filename + "_siggraph17.png");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] img2byte(String path){
        System.out.println("img 2 byte");
        File file = new File(path);
        byte[] data = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            data = baos.toByteArray();
            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private void saveImg(byte[] img, String savepath){
        ByteArrayInputStream bis = null;
        ByteArrayOutputStream outStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            bis = new ByteArrayInputStream(img);
            outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while( (len=bis.read(buffer)) != -1 ){
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            byte[] data = outStream.toByteArray();
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File(savepath);
            //创建输出流
            fileOutputStream = new FileOutputStream(imageFile);
            //写入数据
            fileOutputStream.write(data);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭输出流
            try {
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
                if (bis != null){
                    bis.close();
                }
                if (outStream != null){
                    outStream.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
