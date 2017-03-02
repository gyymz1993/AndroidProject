package com.test.studyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.test.studyandroid.view.LinedText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ActivityMain extends Activity {
	
	//客户端连接Socket
	Socket mSocket = null;
	//输入流
	BufferedReader mBufferedReader = null;
	//输出流
	PrintWriter mPrintWriter = null;
	//发送的消息内容
	EditText msgEditText = null;
	//显示消息内容
	LinedText msgText = null;
	//读取数据的线程标记
	boolean flag = false;
	
	//更新视图
	Handler	mHandler = new Handler() {										
		  public void handleMessage(Message msg){											
			  super.handleMessage(msg);													
			  try{	
				  String msgStr = msg.obj.toString();
			      //消息更新到TextView
				  msgText.append(msgStr);
				  msgText.append("\n");
			  }	catch (Exception e)	{																					
				  e.printStackTrace();									
			  }
		  }									
	 };
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcp_main);
        final Button sendBtn = (Button)findViewById(R.id.sendBtn);
		msgEditText =(EditText)findViewById(R.id.msgEditText);
		//ListView显示消息
		msgText =(LinedText)findViewById(R.id.msgText);
		//连接服务端]
		new  Thread(new Runnable() {
			@Override
			public void run() {
				tcpConnect();
				tcpReceive();
			}
		}).start();


		//发送消息
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					//取得编辑框中我们输入的内容
					String msg = msgEditText.getText().toString();
					mPrintWriter.println(msg);
					mPrintWriter.println();
					mPrintWriter.flush();
					msgEditText.setText("");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		

    }

	private void tcpConnect() {
		try {
			//连接服务器
			mSocket = new Socket("192.168.1.218", 8885);
			//取得输入、输出流
			//编码为GB2312
			mBufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream(),"GBK"));
			mPrintWriter = new PrintWriter(mSocket.getOutputStream(), true);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}

	public  void tcpReceive(){
		char[] buffer = new char[256];
		int count = 0;
		while (true)
		{
			try
			{
				if((count = mBufferedReader.read(buffer))>0)
				{
					String receiveInfoClient = "接收信息 "+"\""+getInfoBuff(buffer, count)+"\"" +"\n";//消息换行
					//更新界面显示
					Message message = new Message();
					message.obj = receiveInfoClient;
					mHandler.sendMessage(message);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return;
			}
		}

	}

	private String getInfoBuff(char[] buff, int count)
	{
		char[] temp = new char[count];
		for(int i=0; i<count; i++)
		{
			temp[i] = buff[i];
		}
		return new String(temp);
	}

	/**
     * 返回退出
     */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		try {
			mPrintWriter.print("EXIT\n");
			mPrintWriter.flush();
			flag = false;
			//关闭连接
			mPrintWriter.close();
			mBufferedReader.close();
			mSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onBackPressed();
	}
    
}