package study.com.tcpconnect.com.android.tcp;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Timer;

public class NIOClient {
	// 信道选择器
	private Selector selector;

	// 与服务器通信的信道
	SocketChannel socketChannel;

	// 要连接的服务器IP地址
	private String hostIp;
	private InetAddress inetAddress;

	// 要连接的远程服务器在监听的端口
	private int hostListenningPort;

	private static boolean timeTocken = false;
	private static Timer timer = new Timer();
	private static boolean timerSet = false;

	/**
	 * 构造函数
	 *
	 * @param HostIp
	 * @param HostListenningPort
	 * @throws IOException
	 */
	public NIOClient(String HostIp, int HostListenningPort) throws IOException {
		this.hostIp = HostIp;
		this.hostListenningPort = HostListenningPort;
		initialize();
	}

	public NIOClient(InetAddress inetAddress, int HostListenningPort) throws IOException {
		this.inetAddress = inetAddress;
		this.hostListenningPort = HostListenningPort;
		initialize();
	}

	private void initialize() throws IOException {
		// 打开监听信道并设置为非阻塞模式
		try {
			socketChannel = SocketChannel.open(new InetSocketAddress(hostIp,
					hostListenningPort));
		} catch (ConnectException e) {
			System.out
					.println("Error happened when establishing connection, try again 5s later");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (!timerSet) {
				timer.schedule(new SetTocken(), 15000);
				timerSet = true;
			}
			if (!timeTocken) {
				initialize();
			}
			return;
		}
		socketChannel.configureBlocking(false);
		// 打开并注册选择器到信道
		selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_READ);

		// 启动读取线程
		new TCPClientReadThread(selector);
	}

	/**
	 * 发送字符串到服务器
	 *
	 * @param message
	 * @throws IOException
	 */
	public void sendMsg(String message) throws IOException {
		ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes("UTF-8"));
		socketChannel.write(writeBuffer);
	}

	public static void main(String[] args) throws IOException {
		NIOClient client = new NIOClient(InetAddress.getLocalHost().getHostAddress(), 9538);
		client.sendMsg("This is a NIOClient, testing");
		client.end();
		timer.cancel();
	}

	private void end() {
		try {
			socketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class SetTocken extends java.util.TimerTask {
		@Override
		public void run() {
			timeTocken = true;
		}
	}
}