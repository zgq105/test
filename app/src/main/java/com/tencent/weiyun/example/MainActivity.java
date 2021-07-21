package com.tencent.weiyun.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.R;
import com.tencent.weiyun.WeiyunSDK;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class MainActivity extends Activity {
    private IWXAPI api;

    private TextView tvVersion;
    private Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tvVersion = (TextView)findViewById(R.id.textView);
        tvVersion.setText(getString(R.string.sdk_name) + " v" + WeiyunSDK.getInstance().version());

        btLogin = (Button) this.findViewById(R.id.buttonLogin);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "com.tencent.weiyun.example";
                api.sendReq(req);
            }
        });

        api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
        api.registerApp(Constants.WX_APP_ID);
    }
}
