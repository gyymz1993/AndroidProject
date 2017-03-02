package com.broadcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity  implements View.OnClickListener {

    private EditText mEdName;
    private EditText mEdPwd;
    /**
     * 登录
     */
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        initView();

    }

    private void initView() {
        mEdName = (EditText) findViewById(R.id.ed_name);
        mEdPwd = (EditText) findViewById(R.id.ed_pwd);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
            jumpMainActivity();
            break;
        }
    }

    public void jumpMainActivity(){
        String userName=mEdName.getText().toString();
        String password=mEdPwd.getText().toString();
        if (userName.equals("admin")&&password.equals("123456")){
            Intent intent=new Intent(this,MainActivity.class);
            intent.putExtra(IntentKey.USER_NAME,userName);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this,"用户名密码错误",Toast.LENGTH_LONG).show();
        }
    }
}
