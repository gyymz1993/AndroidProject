package com.android.udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class BankSeach extends Thread{
	public int port=9538;
	public static final String BROADCAST_ADDRESS = "192.168.16.255";

	private static final int TIMEOUT = 5000;  //设置接收数据的超时时间
	private static final int MAXNUM = 3;      //设置重发数据的最多次数
	byte[] buf = new byte[1024];
	DatagramPacket dp_receive = new DatagramPacket(buf, 1024);
	DatagramPacket packet;
	DatagramSocket socket;
	public void run() {
		byte [] buf=new byte[]{(byte) 0xFF,0x01,0x01,0x02};
		try {
			socket=new DatagramSocket();
			packet = new DatagramPacket(buf,
					buf.length, InetAddress.getByName(BROADCAST_ADDRESS),
					port);
			socket.setSoTimeout(TIMEOUT);           //设置接收数据时阻塞的最长时间
			int tries = 0;                         //重发数据的次数
			boolean receivedResponse = false;     //是否接收到数据的标志位
			//直到接收到数据，或者重发次数达到预定值，则退出循环
			while(!receivedResponse && tries<MAXNUM){
				//发送数据
				socket.send(packet);
				try{

					//接收从服务端发送回来的数据
					//同一个Socket
					socket.receive(dp_receive);
					//如果接收到的数据不是来自目标地址，则抛出异常
					dp_receive.getAddress();
					System.out.println(dp_receive.getAddress());
					//如果接收到数据。则将receivedResponse标志位改为true，从而退出循环
					receivedResponse = true;
				}catch(InterruptedIOException e){
					//如果接收数据时阻塞超时，重发并减少一次重发的次数
					tries += 1;
					System.out.println("Time out," + (MAXNUM - tries) + " more tries..." );

				}
			}if(receivedResponse){
				//如果收到数据，则打印出来
				System.out.println("client received data from server：");
				String str_receive = new String(dp_receive.getData(),0,dp_receive.getLength()) +
						" from " + dp_receive.getAddress().getHostAddress() + ":" + dp_receive.getPort();
				//String str=new String(dp_receive.getData(),"UTF-8");
				System.out.println(str_receive);
				System.out.println("接收的数据"+str_receive);
				dp_receive.setLength(1024);

			}else{
				//如果重发MAXNUM次数据后，仍未获得服务器发送回来的数据，则打印如下信息
				System.out.println("No response -- give up.");

			}
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new BankSeach().start();
	}
}
