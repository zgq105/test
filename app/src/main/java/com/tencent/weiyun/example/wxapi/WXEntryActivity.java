package com.tencent.weiyun.example.wxapi;

import com.example.test.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import com.tencent.weiyun.WeiyunSDK;
import com.tencent.weiyun.example.Constants;
import com.tencent.weiyun.example.Contexts;
import com.tencent.weiyun.example.DirActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	private IWXAPI api;

	private TextView tvVersion;
	private TextView tvContent;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wx);

		tvVersion = (TextView)findViewById(R.id.textView);
		tvVersion.setText(getString(R.string.sdk_name) + " v" + WeiyunSDK.getInstance().version());

		tvContent = (TextView)findViewById(R.id.contentView);

		api = WXAPIFactory.createWXAPI(this, Constants.WX_APP_ID, false);
		api.registerApp(Constants.WX_APP_ID);

		try {
			api.handleIntent(getIntent(), this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		setIntent(intent);

		try {
			api.handleIntent(intent, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onReq(BaseReq req) {

	}

	@Override
	public void onResp(final BaseResp resp) {

		WXEntryActivity.this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tvContent.setText("Type = " + resp.getType() + "\nCode = " + resp.errCode + "\nMsg = " + resp.errStr);
			}
		});

		if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						String sUrl = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
								Constants.WX_APP_ID, Constants.WX_APP_SECRET, ((SendAuth.Resp)resp).code);

						URL url = new URL(sUrl);
						HttpURLConnection connection = (HttpURLConnection)url.openConnection();
						connection.setRequestMethod("GET");
						connection.setRequestProperty("Content-length", "0");
						connection.setReadTimeout(60000);
						connection.setConnectTimeout(60000);
						connection.setUseCaches(false);
						connection.setAllowUserInteraction(false);
						connection.connect();

						final int httpCode = connection.getResponseCode();
						if (httpCode == HttpURLConnection.HTTP_OK) {
							BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
							StringBuilder sb = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
								sb.append(line+"\n");
							}
							br.close();

							JSONObject jsonObject = new JSONObject(sb.toString());
							final String accessToken = jsonObject.getString("access_token");
							final String openID = jsonObject.getString("openid");

							Contexts.getInstance().appID = Constants.WX_APP_ID;
							Contexts.getInstance().uid = openID;
							Contexts.getInstance().openID = openID;
							Contexts.getInstance().accessToken = accessToken;

							WXEntryActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									tvContent.setText("AppID = " + Constants.WX_APP_ID + "\nOpenID = " + openID + "\nAccessToken = " + accessToken);
								}
							});

							Intent itent = new Intent();
							itent.setClass(WXEntryActivity.this, DirActivity.class);
							startActivity(itent);
						} else {
							WXEntryActivity.this.runOnUiThread(new Runnable() {
								@Override
								public void run() {
									tvContent.setText("Code = " + ((SendAuth.Resp)resp).code + "\nHttpCode = " + String.valueOf(httpCode));
								}
							});
						}

						connection.disconnect();
					} catch (Exception e) {
						final String msg = e.getMessage();
						WXEntryActivity.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								tvContent.setText("Exception = " + msg);
							}
						});
					}
				}
			}).start();
		}
	}
}