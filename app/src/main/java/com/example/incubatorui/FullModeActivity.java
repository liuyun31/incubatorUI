package com.example.incubatorui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.incubatorui.GetJPEG.MyRnnable;
import com.example.incubatorui.sendMessage.MyOnTouchListener;
import com.example.incubatorui.sendMessage.Response;
import com.example.incubatorui.utils.Printscreen;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class FullModeActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    private int  width;//屏幕宽度
    private int  height;//屏幕高度
    private int  width1;//右边按键的父级边控件宽度
    private LinearLayout imageLinearLayout;
    Button  btnScreenshot;
    private Button btnFocal;//焦距控制按键
    private Button btnVision;//视野控制按键
    private Button btnLed;//总灯光控制按键
    private Button btnLamplight;//灯光控制按钮

    //各个方向键
    private Button btnArrowKeyTop;
    private Button btnArrowKeyLeft;
    private Button btnArrowKeyRight;
    private Button btnArrowKeyBottom;
    private Button btnFocalTop;
    private Button btnFocalBottom;
    private Button btnFilterTop;
    private Button btnFilterBottom;
    //灯光
    RadioGroup radioGRLed;
    RadioGroup radioGGLed;
    RadioGroup radioGBLed;
    RadioGroup radioGWLed;
    RadioGroup radioGUV1Led;
    RadioGroup radioGUV2Led;
    //灯光的确定与取消
    Button btnLightRgbUV1_submit;
    Button btnLightRgbUV1_return;

    //灯光控制布局
    LinearLayout llLamplightControl;
    //微调控制布局
    LinearLayout llVisionControl;
    //微调内的相关布局
    LinearLayout llFocalFocal;
    LinearLayout llFocalFilter;
    LinearLayout llFocalMultiple;

    //微调的相关按键
    Button btnFocalFocal;
    Button btnFocalFiltration;
    Button btnFocalMultiple;
    //b步进倍数
    Button btnFocalSubmit;
    EditText editTextFocalNum;
    private LinearLayout llVision;
    private boolean returnMain = true;//是否返回主页面（MainActivity）

    LinearLayout imageView12;
    ApplicationUtil applicationUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        getSupportActionBar().hide();//隐藏标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_mode);
        context = this;
        applicationUtil = (ApplicationUtil)this.getApplication();
        applicationUtil.setFullModeActivity(this);

        definitionButton();//绑定按钮
        definitionListener();//绑定监听

        //measure方法的参数值都设为0即可
        imageLinearLayout.measure(0,0);
        //获取组件宽度
        width1 = imageLinearLayout.getMeasuredWidth();
        //获取屏幕的大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;

    }

    /**
     * 绑定按钮
     */
    public void definitionButton(){
        imageLinearLayout = findViewById(R.id.bottomLinearLayout);
        imageView12 = findViewById(R.id.viewJpeg);
        btnScreenshot = findViewById(R.id.btn_screenshot);
        llVision = findViewById(R.id.llVision);
        btnVision = findViewById(R.id.btnVision);
        btnLed = findViewById(R.id.btnLed);
        btnFocal = findViewById(R.id.btnFocal);
        btnArrowKeyTop   = findViewById(R.id.btnArrowKeyTop);
        btnArrowKeyLeft  = findViewById(R.id.btnArrowKeyLeft);
        btnArrowKeyRight = findViewById(R.id.btnArrowKeyRight);
        btnArrowKeyBottom= findViewById(R.id.btnArrowKeyBottom);
        btnFocalTop = findViewById(R.id.btnFocalTop);
        btnFocalBottom = findViewById(R.id.btnFocalBottom);
        radioGRLed = findViewById(R.id.radioG_RLED);
        radioGGLed = findViewById(R.id.radioG_GLED);
        radioGBLed = findViewById(R.id.radioG_BLED);
        radioGWLed = findViewById(R.id.radioG_WLED);
        radioGUV1Led = findViewById(R.id.radioG_UV1LED1);
        radioGUV2Led = findViewById(R.id.radioG_UV1LED2);

        btnLightRgbUV1_submit = findViewById(R.id.btn_light_RGB_UV1_submit);
        btnLightRgbUV1_return = findViewById(R.id.btn_light_RGB_UV1_return);

        btnLamplight = findViewById(R.id.btn_lamplight);

        llLamplightControl = findViewById(R.id.ll_lamplight_control);
        llVisionControl = findViewById(R.id.ll_vision_control);

        btnFocalFocal = findViewById(R.id.btn_Focal_focal);
        btnFocalFiltration = findViewById(R.id.btn_Focal_filtration);
        btnFocalMultiple = findViewById(R.id.btn_Focal_multiple);

        llFocalMultiple = findViewById(R.id.llFocal_multiple);
        llFocalFocal = findViewById(R.id.llFocal_focal);
        llFocalFilter = findViewById(R.id.llFocal_filter);

        btnFocalSubmit = findViewById(R.id.btn_Focal_submit);

        editTextFocalNum = findViewById(R.id.editText_Focal_num);

        btnFilterTop = findViewById(R.id.btn_filter_Top);
        btnFilterBottom = findViewById(R.id.btn_filter_Bottom);
    }

    /**
     * 绑定监听
     */
    public void definitionListener(){
        btnVision.setOnClickListener(this);
        btnLed.setOnClickListener(this);
        btnFocal.setOnClickListener(this);
        btnLamplight.setOnClickListener(this);

        btnFocalFocal.setOnClickListener(this);
        btnFocalFiltration.setOnClickListener(this);
        btnFocalMultiple.setOnClickListener(this);

        btnLightRgbUV1_submit.setOnClickListener(new submitOnClickListener());
        btnLightRgbUV1_return.setOnClickListener(this);
        btnFocalSubmit.setOnClickListener(this);

        btnArrowKeyTop.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnArrowKeyTop.getId()));
        btnArrowKeyLeft.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnArrowKeyLeft.getId()));
        btnArrowKeyRight.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnArrowKeyRight.getId()));
        btnArrowKeyBottom.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnArrowKeyBottom.getId()));
        btnFocalTop.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnFocalTop.getId()));
        btnFocalBottom.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnFocalBottom.getId()));
        btnFilterTop.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnFilterTop.getId()));
        btnFilterBottom.setOnTouchListener(new MyOnTouchListener(applicationUtil,btnFilterBottom.getId()));
    }

    class submitOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(!applicationUtil.getSocketIs()){
                Toast.makeText(context,"服务器未连接",Toast.LENGTH_SHORT).show();
                return;
            }
            if(!applicationUtil.getTcpSendIs()){
                Toast.makeText(context,"不允许发送指令",Toast.LENGTH_SHORT).show();
                return;
            }
            Thread sub = new Thread(){
                @Override
                public void run() {
                    //当前位置
                    int m=-1;//行
                    int n=-1;//列
                    m = applicationUtil.getM();
                    n = applicationUtil.getN();
                    if(n <=0 || m <= 0){
                        showToast("此区域没有灯！");
                        return;
                    }
                    //红色led选择判断
                    //分析单选框结果,不是第一个就是第二个
                    RadioButton rb1 = (RadioButton)radioGRLed.getChildAt(0);
                    int R_is;
                    if(rb1.isChecked()){
                        applicationUtil.sendMessageF("CMSRL"+m+"C"+n+"=0\r\n");//关闭
                        R_is = 0;
                    }else {
                        applicationUtil.sendMessageF("CMSRL"+m+"C"+n+"=1\r\n");//打开
                        R_is = 1;
                    }
                    Response rled1 = new Response(applicationUtil,150);
                    rled1.start();
                    while (true){
                        if (rled1.getIsGo() == 2){
                            showToast("指令响应失败");
                            return;
                        }else {
                            if(rled1.getIsGo() == 1){
                                System.out.println("指令响应成功");
                                applicationUtil.setRgbIndex(m-1,n-1,0,R_is);
                                break;
                            }
                        }
                        try {
                            sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //绿色led选择判断
                    //分析单选框结果,不是第一个就是第二个
                    RadioButton rb2 = (RadioButton)radioGGLed.getChildAt(0);
                    int G_is;
                    if(rb2.isChecked()){
                        applicationUtil.sendMessageF("CMSGL"+m+"C"+n+"=0\r\n");//关闭
                        G_is = 0;
                    }else {
                        applicationUtil.sendMessageF("CMSGL"+m+"C"+n+"=1\r\n");//打开
                        G_is = 1;
                    }
                    Response rled2 = new Response(applicationUtil,150);
                    rled2.start();
                    while (true){
                        if (rled2.getIsGo() == 2){
                            showToast("指令响应失败");
                            return;
                        }else {
                            if(rled2.getIsGo() == 1){
                                System.out.println("指令响应成功");
                                applicationUtil.setRgbIndex(m-1,n-1,1,G_is);
                                break;
                            }
                        }
                        try {
                            sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //蓝色led选择判断
                    //分析单选框结果,不是第一个就是第二个
                    RadioButton rb3 = (RadioButton)radioGBLed.getChildAt(0);
                    int B_is;//更新app内的rgb数组数据
                    if(rb3.isChecked()){
                        applicationUtil.sendMessageF("CMSBL"+m+"C"+n+"=0\r\n");//关闭
                        B_is = 0;
                    }else {
                        applicationUtil.sendMessageF("CMSBL"+m+"C"+n+"=1\r\n");//打开
                        B_is = 1;
                    }
                    Response rled3 = new Response(applicationUtil,150);
                    rled3.start();
                    while (true){
                        if (rled3.getIsGo() == 2){
                            showToast("指令响应失败");
                            return;
                        }else {
                            if(rled3.getIsGo() == 1){
                                System.out.println("指令响应成功");
                                applicationUtil.setRgbIndex(m-1,n-1,2,B_is);
                                break;
                            }
                        }
                        try {
                            sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //白色led选择判断
                    if (n>=6){
                        n=5;
                    }
                    //分析单选框结果,不是第一个就是第二个
                    RadioButton rb4 = (RadioButton)radioGWLed.getChildAt(0);
                    int L_is=0;
                    if(rb4.isChecked()){
                        applicationUtil.sendMessageF("CMSWL"+m+"C"+n+"=0\r\n");//关闭
                        L_is = 0;
                    }else {
                        applicationUtil.sendMessageF("CMSWL"+m+"C"+n+"=1\r\n");//打开
                        L_is = 1;
                    }
                    Response rled4 = new Response(applicationUtil,150);
                    rled4.start();
                    while (true){
                        if (rled4.getIsGo() == 2){
                            showToast("指令响应失败");
                            return;
                        }else {
                            if(rled4.getIsGo() == 1){
                                applicationUtil.setLED((m-1)*5+(n),L_is);
                                System.out.println("指令响应成功");
                                break;
                            }
                        }
                        try {
                            sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //紫外灯1选择判断
                    //分析单选框结果,不是第一个就是第二个
                    if(m >= 4){//第4行没有紫外灯
                        m =3;
                    }
                    if (n>=6){
                        n=5;
                    }
                    RadioButton rb5 = (RadioButton)radioGUV1Led.getChildAt(0);
                    int UV1_is;
                    if(rb5.isChecked()){
                        applicationUtil.sendMessageF("CMSZL"+m+"C"+n+"=0\r\n");//关闭
                        UV1_is = 0;
                    }else {
                        applicationUtil.sendMessageF("CMSZL"+m+"C"+n+"=1\r\n");//打开
                        UV1_is = 1;
                    }
                    Response rled5 = new Response(applicationUtil,150);
                    rled5.start();
                    while (true){
                        if (rled5.getIsGo() == 2){
                            showToast("指令响应失败");
                            return;
                        }else {
                            if(rled5.getIsGo() == 1){
                                System.out.println("指令响应成功");
                                applicationUtil.setUV1((m-1)*5+n,UV1_is);
                                break;
                            }
                        }
                        try {
                            sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //紫外灯2选择判断
                    RadioButton rb6 = (RadioButton)radioGUV2Led.getChildAt(0);
                    int UV2_is;
                    if(rb6.isChecked()){
                        applicationUtil.sendMessageF("CMSKL"+m+"C"+n+"=0\r\n");//关闭
                        UV2_is = 0;
                    }else {
                        applicationUtil.sendMessageF("CMSKL"+m+"C"+n+"=1\r\n");//打开
                        UV2_is = 1;
                    }
                    Response rled6 = new Response(applicationUtil,150);
                    rled6.start();
                    while (true){
                        if (rled6.getIsGo() == 2){
                            showToast("指令响应失败");
                            return;
                        }else {
                            if(rled6.getIsGo() == 1){
                                System.out.println("指令响应成功");
                                applicationUtil.setUV2((m-1)*5+n,UV2_is);
                                break;
                            }
                        }
                        try {
                            sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            sub.start();
            llLamplightControl.setVisibility(View.GONE);
            llVisionControl.setVisibility(VISIBLE);

        }

    }
    /**
     * 截图按钮
     * @param view
     */
    public void printScreen(View view) {
        //获取屏幕的大小
        Printscreen printscreen = new Printscreen();
        //图片的比例是4/3，运算截图点
        int x = (int)((width-width1)/2)-(int)(((height*4)/3)/2);
        int xw = (int)((height*4)/3);
        Bitmap bitmap = printscreen.getBitmap(this,x,0,xw ,height);
        //传递给applicationUtil
        applicationUtil.setAllBitmap(bitmap);
        Thread thread  = (new Thread(){
            @Override
            public void run() {
                Intent it=new Intent(getApplicationContext(), PrintscreenActivity.class);//启动MainActivity
                startActivity(it);
                returnMain = false;
                finish();
            }
        });
        thread.start();

    }

    /**
     *返回
     * @param view
     */
    public void back(View view) {
        if(btnFocal.getVisibility() == View.GONE){
            llFocalFocal.setVisibility(INVISIBLE);
            llFocalFilter.setVisibility(View.GONE);
            llFocalMultiple.setVisibility(View.GONE);
            llVision.setVisibility(INVISIBLE);
            btnScreenshot.setVisibility(VISIBLE);
            btnLamplight.setVisibility(VISIBLE);
            btnVision.setVisibility(VISIBLE);
            //btnLed.setVisibility(VISIBLE);
            btnFocal.setVisibility(VISIBLE);
            btnFocalFiltration.setVisibility(View.GONE);
            btnFocalFocal.setVisibility(View.GONE);
            btnFocalMultiple.setVisibility(View.GONE);
        }else {
            returnMain = true;
            finish();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFocal://微调
                if(btnFocal.getVisibility() == VISIBLE){//如果是隐藏占位就
                    // 改成显示
                    llFocalFilter.setVisibility(View.GONE);
                    llVision.setVisibility(VISIBLE);
                    btnLamplight.setVisibility(View.GONE);
                    btnVision.setVisibility(View.GONE);
                    btnLed.setVisibility(View.GONE);
                    btnScreenshot.setVisibility(View.GONE);
                    btnFocal.setVisibility(View.GONE);
                    btnFocalFiltration.setVisibility(VISIBLE);
                    btnFocalFocal.setVisibility(VISIBLE);
                    btnFocalMultiple.setVisibility(VISIBLE);
                }
                break;
            case R.id.btn_lamplight://灯光
                if(llLamplightControl.getVisibility() == View.GONE){
                    llLamplightControl.setVisibility(VISIBLE);
                    llVisionControl.setVisibility(View.GONE);
                }else {
                    llLamplightControl.setVisibility(View.GONE);
                    llVisionControl.setVisibility(VISIBLE);
                }

                break;
            case R.id.btnVision ://坐标
                Thread thread  = (new Thread(){
                    @Override
                    public void run() {
                        Intent it=new Intent(getApplicationContext(), SiteSelection.class);//启动SiteSelection
                        startActivity(it);
                        returnMain = false;
                        finish();
                    }
                });
                thread.start();
                break;
            case R.id.btnLed://总灯光按钮
                Thread threadBtnLed  = (new Thread(){
                    @Override
                    public void run() {
                        Intent it=new Intent(getApplicationContext(), LightControl.class);//启动MainActivity
                        startActivity(it);
                    }
                });
                threadBtnLed.start();
                break;
            case R.id.btn_light_RGB_UV1_return://灯光的取消
                llLamplightControl.setVisibility(View.GONE);
                llVisionControl.setVisibility(VISIBLE);
                break;
            case R.id.btn_Focal_focal:
                if(llFocalFocal.getVisibility() == View.GONE){
                    llFocalFocal.setVisibility(VISIBLE);
                    llFocalFilter.setVisibility(View.GONE);
                }else {
                    llFocalFocal.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_Focal_filtration:
                if(llFocalFilter.getVisibility() == View.GONE){
                    llFocalFilter.setVisibility(VISIBLE);
                    llFocalFocal.setVisibility(View.GONE);
                }else {
                    llFocalFilter.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_Focal_multiple:
                if(llFocalMultiple.getVisibility() == INVISIBLE){
                    llFocalMultiple.setVisibility(VISIBLE);
                }else {
                    llFocalMultiple.setVisibility(INVISIBLE);
                }
                break;
            case R.id.btn_Focal_submit:
                if(!editTextFocalNum.getText().toString().trim().equals("")){
                    try {
                        int m = Integer.parseInt(editTextFocalNum.getText().toString().trim());
                        if(m < 1){
                            Toast.makeText(this,"请输入一个大于零的正整数",Toast.LENGTH_SHORT).show();
                            break;
                        }else {
                            applicationUtil.setMultiple(m);
                            llFocalMultiple.setVisibility(View.GONE);
                            Toast.makeText(this,"设置成功！",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(this,"请输入一个大于零的正整数",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(this,"请输入一个大于零的正整数",Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }


    /**
     * 由Application调用更新图片
     * @param bitmap
     */
    public void updateImage(Bitmap bitmap){
        runOnUiThread(new MyRnnable(bitmap){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                Bitmap b = getBitmap();
                ViewGroup.LayoutParams lp;
                lp= imageView12.getLayoutParams();
                lp.width=height*4/3;
                lp.height=height;
                imageView12.setLayoutParams(lp);
                imageView12.setBackground(new BitmapDrawable(b));
            }
        });
    }


    /**
     * 重写返回方法（系统返回键执行该方法）
     */
    @Override
    public void finish(){
        //停止接收图片数据
        //跳到其他页面都不用关闭消息流的读取
        applicationUtil.stopUpdateFullImg(false);
        if(returnMain){
            Thread thread  = (new Thread(){
                @Override
                public void run() {
                    Intent it=new Intent(getApplicationContext(), MainActivity.class);//启动MainActivity
                    startActivity(it);
                }
            });
            //跳到主页面需要关闭消息流读取，因为等下会启动另一条，不然会有冲突
            applicationUtil.stopUpdateFullImg(false);
            thread.start();
        }
        super.finish();
    }
    /**
     * 提示框
     * @param msg
     */
    public void showToast(String msg){
        Looper.prepare();
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
