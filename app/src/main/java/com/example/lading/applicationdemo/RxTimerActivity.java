package com.example.lading.applicationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxTimerActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private Button mBtnCancal;
    private TextView mEdit;
    private Subscription mSubscription=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        initView();
    }

    private void initView() {
        mText= (TextView) findViewById(R.id.text1);
        mEdit= (TextView) findViewById(R.id.edit1);
        mBtn= (Button) findViewById(R.id.button);
        mBtnCancal= (Button) findViewById(R.id.button_cancal);
        mEdit.setText("定时器，每一秒发送打印一个数字   \n\ninterval(1, TimeUnit.SECONDS)  创建一个每隔一秒发送一次事件的对象");
        mBtn.setOnClickListener(this);
        mText.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        mBtnCancal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text1:
                break;
            case R.id.edit1:
                break;
            case R.id.button:
                start();
                break;
            case R.id.button_cancal:
                    //取消订阅
                     if (mSubscription!=null && !mSubscription.isUnsubscribed()){
                         mSubscription.unsubscribe();
                     }
                    break;
        }
    }

    private void start() {
        //interval（）是运行在computation Scheduler线程中的，因此需要转到主线程
        mSubscription=Observable.interval(1, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Action1<Long>() {
                      @Override
                      public void call(Long aLong) {
                          mText.setText(aLong+"");
                      }
                  });
    }
}
