package study.com.tcpconnect.com.android.tcp;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;


public class JavaSelectorClient {
	private static int port=9538;
	public static void main(String[] args) throws IOException, InterruptedException {
		SocketChannel socketChannel = SocketChannel.open();
		// 创建SocketChanne
		InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), port);
		try {
			socketChannel.socket().connect(address);
		} catch (ConnectException e1) {
			System.out.println("服务器未开启......");
			socketChannel.close();
			e1.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ByteBuffer buffer = ByteBuffer.allocate(1024); // 创建Buffer
		while(true){
			try {
				buffer.clear();
				String time = sdf.format(new Date());
				buffer.put(time.getBytes());
				buffer.flip();
				socketChannel.write(buffer);
				Thread.sleep(2000);
			} catch (Exception e) {
				System.out.println("Connection Close");
				break;
			}
		}
	}
}
