package study.com.tcpconnect;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import study.com.tcpconnect.net.cmd.CmdmandProcessThread;
import study.com.tcpconnect.net.tcp.TCPServerConnect;
import study.com.tcpconnect.net.utils.BytesStringUtils;

public class MainActivity extends AppCompatActivity {

    private TextView mEtShow;
    private ScrollView mSvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        Log.e("TAG", "本机IP" + intToIp());
        initTCPConnet();
    }

    private String intToIp() {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return (ipAddress & 0xFF) + "." +
                ((ipAddress >> 8) & 0xFF) + "." +
                ((ipAddress >> 16) & 0xFF) + "." +
                (ipAddress >> 24 & 0xFF);
    }


    public void initTCPConnet() {
        CmdmandProcessThread commandProcessThread = CmdmandProcessThread.getInstance();
        commandProcessThread.start();

        final AxtionHandler handler = new AxtionHandler();
        TCPServerConnect serverConnect = new TCPServerConnect(8989);
        serverConnect.setReveiveMessageListener(new TCPServerConnect.OnReveiveMessageListener() {
            @Override
            public void onReceive(int socketId, int customCode, byte[] bytes) {
                //Log.e("TAG","socketId"+socketId+"customCode"+customCode+"bytes"+ BytesStringUtils.toStringShow(bytes));
                String strShow = BytesStringUtils.toStringShow(bytes);
                Message message = Message.obtain();
                message.obj = strShow;
                handler.sendMessage(message);
            }
        });
        serverConnect.setReceiveStrListener(new TCPServerConnect.OnReceiveStrListener() {
            @Override
            public void onReceive(String string) {
                String strShow = string;
                Message message = Message.obtain();
                message.obj = strShow;
                handler.sendMessage(message);
            }
        });
    }

    private void initView() {
        mEtShow = (TextView) findViewById(R.id.et_show);
        mSvShow = (ScrollView) findViewById(R.id.sv_show);
    }

    /**
     * 每次接收或者发送命令时，把信息打印到界面上
     */
    public  class AxtionHandler extends Handler {
        List<String> listShow = new ArrayList<String>();

        @Override
        public void handleMessage(Message msg) {
            mEtShow.setText((String)msg.obj);
        }
    }
}
