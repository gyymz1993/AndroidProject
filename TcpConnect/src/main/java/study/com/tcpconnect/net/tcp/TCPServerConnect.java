package study.com.tcpconnect.net.tcp;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import study.com.tcpconnect.net.cmd.CmdmandProcessThread;

/**
  * 创建人：Yangshao
  * 创建时间：2017/2/23 16:19
  * @version
  *
  */
public class TCPServerConnect {
   protected ServerSocket mServerSocket;
    protected List<Socket> mSockets;/**获取连接自己的所有Socket**/
    protected OnReveiveMessageListener  mReveiveMessageListener;
    protected OnReceiveStrListener  mReceiveStrListener;

    public void setReceiveStrListener(OnReceiveStrListener receiveStrListener) {
        mReceiveStrListener = receiveStrListener;
    }

    public void setReveiveMessageListener(OnReveiveMessageListener reveiveMessageListener) {
        mReveiveMessageListener = reveiveMessageListener;
    }

    public TCPServerConnect(int port){
        try {
            mServerSocket=new ServerSocket(port);
            Log.e("TAG","ServerSocket创建成功"+mServerSocket.getInetAddress()+"等待连接.....");
            mSockets=new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG","创建TCPServerConnect线程");
                    while (!mServerSocket.isClosed()){
                        Socket socket;
                        try {
                            socket=mServerSocket.accept();
                            Log.e("TAG","客户端创建"+socket.getInetAddress()+"连接成功");
                            mSockets.add(socket);
                            /**每个Socket创建自己的线程**/
                            TCPConnect tcpConnect=new TCPConnect(socket,mSockets);
                            tcpConnect.setReveiveMessageListener(new OnReveiveMessageListener() {
                                @Override
                                public void onReceive(final int socketId, final int customCode, final byte[] bytes) {
                                    // 由命令处理线程处理
                                    CmdmandProcessThread.getInstance().getHandler().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mReveiveMessageListener!=null){
                                                mReveiveMessageListener.onReceive(socketId, customCode, bytes);
                                            }

                                        }
                                    });
                                }
                            });
                            tcpConnect.setReceiveStrListener(new OnReceiveStrListener() {
                                @Override
                                public void onReceive(String string) {
                                    if (mReceiveStrListener!=null){
                                        mReceiveStrListener.onReceive(string);
                                    }
                                }
                            });
                            tcpConnect.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("TAG","连接socket失败");
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("TAG","创建ServerSocket失败");
        }
    }



    public class TCPConnect extends  Thread{

        protected Socket mSocket;
        protected List<Socket> mSockets;
        protected OnReveiveMessageListener  mReveiveMessageListener;

        protected OnReceiveStrListener  mReceiveStrListener;

        public void setReceiveStrListener(OnReceiveStrListener receiveStrListener) {
            mReceiveStrListener = receiveStrListener;
        }

        public void setReveiveMessageListener(OnReveiveMessageListener reveiveMessageListener) {
            mReveiveMessageListener = reveiveMessageListener;
        }

        public TCPConnect(Socket socket, final List<Socket> socketList){
            this.mSocket=socket;
            this.mSockets=socketList;
        }
        @Override
        public void run() {
            BufferedReader  mBufferedReaderServer = null;
            try {
                Log.e("TAG","创建TCPConnect线程，线程名为" + currentThread().getName());
                String message = null;
                //接受客服端数据BufferedReader对象
                mBufferedReaderServer = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                char[] buffer = new char[256];
                /**Socket没有关闭**/
                while (!mSocket.isClosed()){
                    //获取输入流接受客服端数据BufferedReader对象
                    int count = 0;
                    if ((count = mBufferedReaderServer.read(buffer)) > 0) ;
                    {
                        message = getInfoBuff(buffer, count) + "\n";//消息换行
                    }
                    Log.e("TAG","接收到"+mSocket.getInetAddress().getHostAddress()+"端口"+ mSocket.getLocalPort()+"的数据:"+message);
                    if (mReceiveStrListener!=null){
                        mReceiveStrListener.onReceive("接收到"+mSocket.getInetAddress().getHostAddress()+"端口"+ mSocket.getLocalPort()+"的数据:"+message);
                    }
                }
                // 从socket列表中移除
                mSockets.remove(mSocket);
                Log.e("TAG","与" + mSocket.getInetAddress().getHostAddress() + "的端口为" +
                        mSocket.getLocalPort() + "的TCP连接已断开");
            }catch (Exception e){
                mSockets.remove(mSocket);
                Log.e("TAG","与" + mSocket.getInetAddress().getHostAddress() + "的端口为" +
                        mSocket.getLocalPort() + "的TCP连接已断开");
            }finally {
                try {
                    mBufferedReaderServer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
     * 根据socket获取socket的标识id
     * @param socket
     * @return
     */
    public static int getSocketId(Socket socket){
        try {
            byte[] ipAddr = socket.getInetAddress().getAddress();
            int id = (ipAddr[0] & 0xff) | ((ipAddr[1] << 8) & 0xff00)
                    | ((ipAddr[2] << 16) & 0xff0000)
                    | ((ipAddr[3] << 24) & 0xff000000);
            return id;
        } catch (NullPointerException e) {
            return 0;
        }
    }



    public void read(Socket socket) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bys = new byte[1024];
        int len = 0;
        String result = "";
        while ((len = bis.read(bys)) != -1) {
            result= new String(bys, 0, len);
        }
        Log.e("TAG","接收到端口"+ socket.getLocalPort()+"的数据"+result);
        // 释放资源
        bis.close();
    }

    public void bufferRead(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
        String line;
        String result = "";
        while((line=reader.readLine()) != null ) result+=line;
        Log.e("TAG","接收到端口"+ socket.getLocalPort()+"的数据"+result);
        reader.close();
    }

    public interface OnReveiveMessageListener{
        /**当有命令接受到后回调
         * @param socketId 发送信息来的链接id
         * @param customCode 自定义码
         * @param bytes
         */
        void onReceive(int socketId, int customCode, byte[] bytes);
    }

    public interface OnReceiveCmdListener {
        /**当有命令接受到后回调
         * @param socketId 发送信息来的链接id
         * @param bytes
         */
        void onReceive(int socketId, byte[] bytes);
    }

    public interface OnReceiveStrListener {
        /**当有命令接受到后回调
         */
        void onReceive(String string);
    }

}
