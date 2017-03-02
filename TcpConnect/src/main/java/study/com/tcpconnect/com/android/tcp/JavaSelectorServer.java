package study.com.tcpconnect.com.android.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class JavaSelectorServer {
	private static int port=9538;

	public JavaSelectorServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		InetSocketAddress address = new InetSocketAddress(
				InetAddress.getLocalHost(), port);
		serverSocketChannel.socket().bind(address);
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		// 注册Accept事件
		while (true) {
			// 如果注册的事情发生
			if (selector.select() > 0) {
				// 获取发生的事件
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				while (it.hasNext()) {
					SelectionKey selectionKey = it.next();
					// 如果是Accept事件
					if (selectionKey.isAcceptable()) {
						// 获取注册的ServerSocketChannel
						serverSocketChannel = (ServerSocketChannel) selectionKey
								.channel();
						// 建立连接
						SocketChannel socketChannel = serverSocketChannel
								.accept();
						socketChannel.configureBlocking(false);
						// 注册该连接的Read事件
						socketChannel.register(selector, SelectionKey.OP_READ);
						System.out.println("Connected: "
								+ socketChannel.socket()
								.getRemoteSocketAddress());
						// 如果是Read事件
					} else if (selectionKey.isReadable()) {
						// 获取注册的SocketChannel
						SocketChannel socketChannel = (SocketChannel) selectionKey
								.channel();
						ByteBuffer buff = ByteBuffer.allocate(1024);
						while (socketChannel.read(buff) > 0) {
							// 读取接收到的数据
							buff.flip();
							byte[] dst = new byte[buff.limit()];
							buff.get(dst);
							System.out.println(new String(dst));
						}
					}
					it.remove(); // 需要将处理过的事件移除
				}
			}
			Thread.sleep(100);
		}
	}

}
