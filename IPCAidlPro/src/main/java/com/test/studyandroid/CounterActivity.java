package com.test.studyandroid;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/3/2.
 */
public class CounterActivity extends Activity {

    @Bind(R.id.id_btn_drg)
    Button idBtnDrg;
    @Bind(R.id.id_btn_sin)
    Button idBtnSin;
    @Bind(R.id.id_btn_cos)
    Button idBtnCos;
    @Bind(R.id.id_btn_tan)
    Button idBtnTan;
    @Bind(R.id.id_btn_n)
    Button idBtnN;
    @Bind(R.id.id_btn_bk)
    Button idBtnBk;
    @Bind(R.id.id_btn_seven)
    Button idBtnSeven;
    @Bind(R.id.id_btn_eigth)
    Button idBtnEigth;
    @Bind(R.id.id_btn_nine)
    Button idBtnNine;
    @Bind(R.id.id_btn_devided)
    Button idBtnDevided;
    @Bind(R.id.id_btn_bracket_left)
    Button idBtnBracketLeft;
    @Bind(R.id.id_btn_bracket_right)
    Button idBtnBracketRight;
    @Bind(R.id.id_btn_four)
    Button idBtnFour;
    @Bind(R.id.id_btn_five)
    Button idBtnFive;
    @Bind(R.id.id_btn_six)
    Button idBtnSix;
    @Bind(R.id.id_btn_mulpitly)
    Button idBtnMulpitly;
    @Bind(R.id.id_btn_one)
    Button idBtnOne;
    @Bind(R.id.id_btn_two)
    Button idBtnTwo;
    @Bind(R.id.id_btn_three)
    Button idBtnThree;
    @Bind(R.id.id_btn_subtarct)
    Button idBtnSubtarct;
    @Bind(R.id.id_btn_log)
    Button idBtnLog;
    @Bind(R.id.id_btn_in)
    Button idBtnIn;
    @Bind(R.id.id_btn_zero)
    Button idBtnZero;
    @Bind(R.id.id_btn_dot)
    Button idBtnDot;
    @Bind(R.id.id_btn_equel)
    Button idBtnEquel;
    @Bind(R.id.id_btn_add)
    Button idBtnAdd;
    @Bind(R.id.id_btn_exit)
    Button idBtnExit;
    @Bind(R.id.id_tv_mem)
    TextView idTvMem;
    // 保存原来 数据格式样子
    private String  str_old;
    //变换后的样子
    private String str_new;
    //输入控制 true为重新输入  false为接着输入
    private boolean vbegin =true;
    //控制DRG键 true为角度 false为弧度
    private boolean drg_flag=true;
    //pai值31.4
    private  double pi=4*Math.atan(1);
    //true位输入正确可 继续   false不能继续
    private boolean tip_look=true;
    //是否按=之后的输入    true是输入等于号 之前
    private boolean equals_flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_counter);
        ButterKnife.bind(this);


        setOnClick();
    }

    private void setOnClick() {
        OnBtnOnclickListener onBtnOnclickListener=new OnBtnOnclickListener();
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
        idBtnDrg.setOnClickListener(onBtnOnclickListener);
    }

    public boolean right(String rigthStr) {
        return true;
    }

    //命令缓存  用于检测合法性
    String [] Tipcommand=new String[500];
    //Tipcommand 的指针
    int tip_i=0;
    public class OnBtnOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取按键命令
            String command = ((Button) v).getText().toString();
            //获取显示器显示字符串
            String visiableStr = idTvMem.getText().toString();
            //检测是否合法字符
            if (equals_flag == false && "0123456789.()sincostanlnlogn!+-*/√=".indexOf(command) != -1) {
                //检查显示器的字符串 是否合法
                if (right(visiableStr)) {
                    if ("+-*/√)".indexOf(command) != -1) {
                        for (int i = 0; i < visiableStr.length(); i++) {
                            Tipcommand[tip_i] = String.valueOf(visiableStr.charAt(i));
                            tip_i++;
                        }
                        vbegin = false;
                    }
                } else {
                    idTvMem.setText("0");
                    vbegin = true;
                    tip_i = 0;
                    tip_look = true;
                }
            }
            if (tip_i > 0) {
                TipChecker(Tipcommand[tip_i - 1], command);
            } else {
                TipChecker("#", command);
            }
            if ("0123456789.()sincostanlnlogn!+-*/√=".indexOf(command) != -1 && tip_look) {
                Tipcommand[tip_i] = command;
                tip_i++;
            }
            //若输入正确  则输出显示器
            if ("0123456789.()sincostanlnlogn!+-*/√=".indexOf(command) != -1 && tip_look) {
                print(command);
            }
            else  if (command.compareTo("DRG") == 0 && tip_look) {
                if (drg_flag==true) {
                    vbegin = false;
                }else {
                    vbegin = true;
                }
                //如果 输入是退格键   并且在==之前
            }else if (command.compareTo("Bksp")==0&&equals_flag){
                //一次性删除三个字符
                if (TTO(visiableStr)==3){
                    if (visiableStr.length()>3){
                        idTvMem.setText(visiableStr.substring(0,visiableStr.length()-3));
                    }else if (visiableStr.length()==3){
                        idTvMem.setText("0");
                        vbegin=true;
                        tip_i=0;
                    }
                }else if (TTO(visiableStr)==2){
                    if(visiableStr.length()>2){
                        idTvMem.setText(visiableStr.substring(0,visiableStr.length()-1));
                    }else if (visiableStr.length()==2){
                        idTvMem.setText("0");
                        vbegin=true;
                        tip_i=0;
                    }

                }else if(TTO(visiableStr)==1){
                    if (right(visiableStr)){
                        if (visiableStr.length()>1){
                            idTvMem.setText(visiableStr.substring(0,visiableStr.length()-1));
                        }else{
                            idTvMem.setText("0");
                            vbegin=true;
                            tip_i=0;
                        }
                    }
                }if (idTvMem.toString().compareTo("-")==0||equals_flag==false){
                    idTvMem.setText("0");
                    vbegin=true;
                    tip_i=0;
                }
                tip_look=true;
                if (tip_i>0){
                    tip_i--;
                    //如果是按等于号之后输入退格键
                }else if (command.compareTo("Bksp")==0&&equals_flag==false){
                    idTvMem.setText("0");
                    vbegin=true;
                    //缓存清0
                    tip_i=0;
                    tip_look=true;
                }
                //如果是 清除键
                else if (command.compareTo("C")==0){
                    idTvMem.setText("0");
                    vbegin=true;
                    tip_i=0;
                    //可以继续输入
                    tip_look=true;
                    //表明输入=之前
                    equals_flag=true;
                }else if (command.compareTo("MC")==0){
                    idTvMem.setText("0");
                }else if (command.compareTo("exit")==0){
                    System.exit(0);
                }else if (command.compareTo("=")==0&&tip_look&&right(visiableStr)&&equals_flag){
                    tip_i=0;
                    //不可继续输入
                    tip_look=false;
                    //表明输入=之后
                    equals_flag=false;
                    str_old=visiableStr;
                    visiableStr=visiableStr.replaceAll("sin","s");
                    visiableStr=visiableStr.replaceAll("cos","c");
                    visiableStr=visiableStr.replaceAll("tan","t");
                    visiableStr=visiableStr.replaceAll("log","g");
                    visiableStr=visiableStr.replaceAll("ln","l");
                    visiableStr=visiableStr.replaceAll("n!","!");
                    //重新设置标志为true
                    vbegin=true;
                    //将-1x转成-
                    str_new=visiableStr.replaceAll("-","-1x");
                    //计算结果
                    new Calc().process(str_new);
                }
                //可以继续 输入
                tip_look=true;
            }
            switch (v.getId()) {
                case R.id.id_btn_drg:
                    break;
                case R.id.id_btn_sin:
                    break;
                case R.id.id_btn_cos:
                    break;
                case R.id.id_btn_tan:
                    break;
                case R.id.id_btn_n:
                    break;
                case R.id.id_btn_bk:
                    break;
                case R.id.id_btn_one:
                    break;
                case R.id.id_btn_two:
                    break;
                case R.id.id_btn_three:
                    break;
                case R.id.id_btn_four:
                    break;
                case R.id.id_btn_five:
                    break;
                case R.id.id_btn_six:
                    break;
                case R.id.id_btn_seven:
                    break;
                case R.id.id_btn_eigth:
                    break;
                case R.id.id_btn_nine:
                    break;
                case R.id.id_btn_zero:
                    break;
                case R.id.id_btn_equel:
                    break;
                case R.id.id_btn_add:
                    break;
                case R.id.id_btn_subtarct:
                    break;
                case R.id.id_btn_mulpitly:
                    break;
                case R.id.id_btn_devided:
                    break;

            }
        }
    }

    private int TTO(String visiableStr) {
        return 0;
    }

    private void print(String command) {
    }

    private void TipChecker(String s, String command) {

    }

    public class Calc{
        public Calc(){
        }
        final int MAXLEN=500;
        public void process(String  str){
            //wp 表示优先级  wt表示临时优先级
            //topop表示计数器  flag 1为正数， -1为负数
            int weightPlus=0,topOp=0,
            topNum=0,flag=0,weightTmep=0;
            int weight [];
            double number[];
            char ch,ca_gai,operator[];  //operator保存运算符 ，topOp为计数器
            String num;//计数数字
            weight=new int[MAXLEN];
            number=new double[MAXLEN];
            operator=new char[MAXLEN];
            String expression=str;
            StringTokenizer  expToken=new StringTokenizer(expression,"+-*/()sctgl!√^");
            int i=0;
            while(i<expression.length()){
                ch=expression.charAt(i);
                if (i==0){
                    if (ch=='-')
                        flag=-1;
                }else if (expression.charAt(i-1)=='('&&ch=='-'){
                    flag=-1;
                }if(ch<='9'&&ch>='0'||ch=='.'||ch=='E'){
                    num=expToken.nextToken();
                    ca_gai=ch;
                    while(i<expression.length()&&(ca_gai<='9'&&ca_gai>='0'||ca_gai=='.'||ca_gai=='E')){
                        ca_gai=expression.charAt(i++);
                    }
                    //将指正退回之前 的位置
                    if (i>=expression.length())i-=1;else {i-=2;}
                    if (num.compareTo(".")==0) number[topNum++]=0;
                    else {
                        number[topNum++]=Double.parseDouble(num)*flag;
                        flag=1;
                    }
                }
                //计算器 运算符优先级
                if (ch=='(') weightPlus+=4;
                if (ch==')') weightPlus-=4;
                if (ch=='-'&&flag==1||ch=='+'||ch=='x'||ch=='1'||ch=='s'
                        ||ch=='c'||ch=='t'||ch=='g'||ch=='1'||
                        ch=='!'||ch=='√'||ch=='^'){
                    switch (ch){
                        case '+':
                        case '-':
                            weightTmep=1+weightPlus;
                            break;
                        case '*':
                        case '/':
                            weightTmep=2+weightPlus;
                            break;
                        case 's':
                        case 'c':
                        case 't':
                        case 'g':
                        case 'l':
                        case '!':
                            weightTmep=3+weightPlus;
                            break;
                        default:
                            weightTmep=4+weightPlus;
                            break;
                    }
                    //如果当前的优先级大于推站的顶部元素，则直接入站
                    if(topOp==0||weight[topOp-1]<weightTmep){
                        weight[topOp]=weightTmep;
                        operator[topOp]=ch;
                        topOp++;
                    }else {
                        while (topOp>0&&weight[topOp-1]>=weightTmep){
                            switch (operator[topOp-1]){
                                case '+':
                                    number[topNum-2]+=number[topNum-1];
                                case '-':
                                    number[topNum-2]-=number[topNum-1];
                                    break;
                                case '*':
                                    number[topNum-2]*=number[topNum-1];
                                    break;
                                case '/':
                                    if (number[topNum-1]==0){
                                        showError(1,str_old);
                                        return;
                                    }
                                    number[topNum-2]/=number[topNum-1];
                                    break;
                                case '√':
                                        if (number[topNum-1]==0||(number[topNum-2])<0&&number[topNum-1]%2==0){
                                            return;
                                        }
                                    number[topNum-2]=Math.pow(number[topNum-2],1/number[topNum-1]);
                                    break;
                                case '^':
                                    number[topNum-2]=Math.pow(number[topNum-2],1/number[topNum-1]);
                                    break;
                                //计算san
                                case 's':
                                    if (drg_flag==true){
                                       // number[topNum-1]
                                    }
                            }
                        }
                    }
                }
            }
        }

    }

    private void showError(int i, String str_old) {
    }

}
