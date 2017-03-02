package com.test.studyandroid.viewutils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2016/3/25.
 */
public class TcpConnect extends Thread{


    //客户端连接Socket
    Socket mSocket = null;
    //输入流
    BufferedReader mBufferedReader = null;
    //输出流
    PrintWriter mPrintWriter = null;
    @Override
    public void run() {
        try {
            //连接服务器
            mSocket = new Socket("192.168.1.47", 8889);
            //取得输入、输出流
            //编码为GB2312
            mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"GBK"));
            mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
