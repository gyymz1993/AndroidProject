package study.com.tcpconnect.net.cmd;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 创建人：Yangshao
 * 创建时间：2017/2/23 16:12
 * @version  命令处理线程
 *    
 */
public class CmdmandProcessThread extends Thread{


    private static CmdmandProcessThread instance;

    private CmdmandProcessThread(){

    }
    public static CmdmandProcessThread  getInstance(){
        synchronized (CmdmandProcessThread.class){
            if (instance==null){
                instance=new CmdmandProcessThread();
            }
        }
        return instance;
    }



    public Handler mHandler;

    public Handler getHandler() {
        return mHandler;
    }


    @Override
    public void run() {
        Log.e("TAG","创建CommandProcess线程，线程名为"
                + currentThread().getName());
        Looper.prepare();
        mHandler=new CommandProcessHandler();
        Looper.loop();
    }

    static class  CommandProcessHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {

        }
    }

    @Override
    public synchronized void start() {
        if (!isAlive()){
            super.start();
        }
    }
}
