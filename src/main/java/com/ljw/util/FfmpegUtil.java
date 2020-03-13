package com.ljw.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * @Classname FfmpegUtil
 * @Description TODO
 * @Date 2020/3/13 9:32
 * @Created by 刘俊伟
 */
public class FfmpegUtil {
    // 视频缩略图截取
// inFile 输入文件(包括完整路径)
// outFile 输出文件(可包括完整路径)
    public static boolean transfer(String inFile, String outFile,String startTime) {
        String command ="/opt/ffmpeg/bin/ffmpeg -i " + inFile
                + " -y -f image2 -ss "+startTime+" -t 00:00:01 "
                + outFile;

        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
                System.out.println(line);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @Author 远
     * @Description 除去之前的Bgm，会生成一个多余的视频
     * @Param inFile： 上传的视频    outFile：生成的多余的视频在哪个位置？
     * @return
     **/
    public static boolean transferClearMusic(String inFile, String outFile) {
        String command ="/opt/ffmpeg/bin/ffmpeg -i " + inFile
                + " -vcodec copy -an "+outFile;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
                System.out.println(line);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @Author 远
     * @Description 合成bgm生成一个新视频
     * @Param
     * inFile：生成的多余的视频在哪个位置
     * startTime：设置视频的时间
     * outFile: 合成的视频
     * Bgm: 背景音乐
     * @return
     **/
    public static boolean transferBgm(String inFile,String outFile,String Bgm,String startTime) {
        String command ="/opt/ffmpeg/bin/ffmpeg -i " + inFile
                + " -i "+Bgm+" -vcodec copy -acodec copy -t  "+startTime+" "+outFile;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
                System.out.println(line);
        } catch (Throwable t) {
            t.printStackTrace();
            return false;
        }
        return true;
    }



    public static void main(String[] arg){
        //文件上传的视频
        String inFile ="/opt/video/fileImage/0e9da852130046ecb424bffb1eb1afc4video/tmp_4c64d6532e6ccde5dbfd820d169b9f0968d8b5850def8e28.mp4";
        //文件夹废弃视频的路径
        String abandonOutFile ="/opt/video/fileImage/abandonFile/tmp_4c64d6532e6ccde5dbfd820d169b9f0968d8b5850def8e28.mp4";
        //背景音乐
        String Bgm = "/opt/video/fileImage/music/爱要坦荡荡_.mp3";
        //最后生成的文件
        String outFile ="/opt/video/fileImage/ok.mp4";
        //生成一个废弃的视频，但是这个视频会进行和bgm进行整合！
        boolean transfer = FfmpegUtil.transferClearMusic(inFile, abandonOutFile);
        //
        boolean transferBgm = FfmpegUtil.transferBgm(abandonOutFile,outFile,Bgm,"7");
        System.out.print(transfer);
        System.out.println(transferBgm);

    }

}
