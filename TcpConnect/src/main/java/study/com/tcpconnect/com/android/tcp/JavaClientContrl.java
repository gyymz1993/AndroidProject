package study.com.tcpconnect.com.android.tcp;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Timer;

public class JavaClientContrl {
	private static int port = 9538;
	private SocketChannel socketChannel;

	private static boolean timeTocken = false;
	private static Timer timer = new Timer();
	private static boolean timerSet = false;

	private SocketChannel getInstan() throws IOException {
		if (socketChannel == null) {
			socketChannel = SocketChannel.open();
		}
		return socketChannel;
	}

	public JavaClientContrl(byte[] contrl) throws IOException {
		socketChannel = getInstan();
		// 创建SocketChanne
		InetSocketAddress address = new InetSocketAddress(
				InetAddress.getLocalHost(), port);
		try {
			socketChannel.socket().connect(address);
		} catch (ConnectException e1) {
			e1.printStackTrace();
			socketChannel.close();
			System.out.println("服务器未开启......");
		}

		ByteBuffer writeBuffer = ByteBuffer.wrap(contrl);
		socketChannel.write(writeBuffer);

	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		new JavaClientContrl(new byte[] { (byte) 0x88, 0x66, 0x55, 0x44 });
	}
}
