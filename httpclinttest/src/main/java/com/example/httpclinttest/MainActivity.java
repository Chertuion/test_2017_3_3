package com.example.httpclinttest;

import android.app.usage.NetworkStatsManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static boolean FLAG = true;
    private static String VERIFYURL = "http://172.18.4.243/CheckCode.aspx?";  //图片验证码的url
    private static String VERIFYURL_TEST = "http://usr.005.tv/User/verify.html?"; //另外的一个图片验证码url  测试用的
    private static String LOGINURL = "http://172.18.4.243/default2.aspx";    //登录post的url
    private static String MAINBODYHTML = "";   //获取页面的HTML  即 页面内容


    private EditText et_login_username;
    private EditText et_login_psd;
    private EditText et_login_verify;
    private ImageView iv_login_verify;
    private Button bt_login_login;
    private Context mContext;
    private Bitmap bitmapVerify;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();

        //通過Handler 與 Message 來對UI進行修改
         handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0:
                        iv_login_verify.setImageBitmap(bitmapVerify);
                        break;
                    case 1:
                        Toast.makeText(mContext, "验证码获取失败，请检查网络或刷新!", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        iv_login_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVerity();
            }
        });

        bt_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin(et_login_username.getText().toString().trim(), et_login_psd.getText().toString().trim(), et_login_verify.getText().toString().trim());
            }
        });


    }

    //初始化控件
    private void initView() {
        et_login_username = (EditText)findViewById(R.id.et_login_username);
        et_login_psd = (EditText)findViewById(R.id.et_login_psd);
        et_login_verify = (EditText)findViewById(R.id.et_login_verify);
        iv_login_verify = (ImageView)findViewById(R.id.iv_login_verify);
        bt_login_login = (Button) findViewById(R.id.bt_login_login);
    }



    private void getVerity(){

        if(FLAG){
            Log.e("是否调用了getVerity", "true");
        }

        //网络判断
        if(NetUtil.isNetworkConnected(mContext)){   //有网络连接时返回true

            if(FLAG){
                Log.e("Chertuion", "网络连接了");
            }

            new Thread(new Runnable() {
                @Override
                public void run() {

                    HttpPost httpPost = new HttpPost(VERIFYURL);
                    HttpClient httpClient = new DefaultHttpClient();
                    try{
                        HttpResponse httpResponse = httpClient.execute(httpPost);
                        byte [] bytes = new byte[1024];
                        bytes = EntityUtils.toByteArray(httpResponse.getEntity()); // 将返回(httpResponse)的数据用byteArray保存起来
                        bitmapVerify = BitmapFactory.decodeByteArray(bytes, 0, bytes.length); //将保存起来的数据读取出来
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    if(bitmapVerify == null){ //判断是否加载了验证码
                        Message msg_IsGetVerify = handler.obtainMessage(1);
                        handler.sendMessage(msg_IsGetVerify);
                    }
                    Message msg = handler.obtainMessage(0);
                    handler.sendMessage(msg);
                }
            }).start();

        }else{
            if(FLAG){
                Log.e("Chertuion", "网络连接失败");
            }
            Message msg_isConnected = handler.obtainMessage(1);
            handler.sendMessage(msg_isConnected);
        }


    }

    //當程序加載時調用獲取驗證碼的方法
    @Override
    protected void onResume() {
        super.onResume();
        getVerity();
    }

    private void doLogin(final String username, final String psd, final String verify){

        new Thread(new Runnable() {
            @Override
            public void run() {
                DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(LOGINURL);

                HttpResponse httpResponse;

                //BaseNameValuePair implements NameValuePair         ********************************
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("__VIEWSTATE", "dDwyODE2NTM0OTg7Oz730t/wjAamQ7OisS0+gLZW0xCUVw=="));
                params.add(new BasicNameValuePair("Button1", ""));
                params.add(new BasicNameValuePair("hidPdrs", ""));
                params.add(new BasicNameValuePair("hidsc", ""));
                params.add(new BasicNameValuePair("lbLanguage", ""));
                params.add(new BasicNameValuePair("RadioButtonList1", "%D1%A7%C9%FA"));
                params.add(new BasicNameValuePair("TextBox2", psd));
                params.add(new BasicNameValuePair("txtSecretCode", verify));
                params.add(new BasicNameValuePair("txtUserName", username));

                try {
                    //对post的数据进行编码
                    httpPost.setEntity(new UrlEncodedFormEntity(params, "utf_8"));
                    //
                    httpResponse = defaultHttpClient.execute(httpPost);

                    if(FLAG){
                        Log.e("状态码", String.valueOf(httpResponse.getStatusLine().getStatusCode()));
                    }

                    if(httpResponse.getStatusLine().getStatusCode() == 200){

                        StringBuffer sb = new StringBuffer();  // *********************

                        HttpEntity entity = httpResponse.getEntity();
                        MAINBODYHTML = EntityUtils.toString(entity);

                       if(FLAG){
                           Log.e("MAINBODYHTML", MAINBODYHTML);
                       }
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

}
