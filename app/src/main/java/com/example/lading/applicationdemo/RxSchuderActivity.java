package com.example.lading.applicationdemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxSchuderActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private TextView mEdit;
    private LinearLayout mLinearlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);
        initView();
    }

    private void initView() {
        mText= (TextView) findViewById(R.id.text1);
        mEdit= (TextView) findViewById(R.id.edit1);
        mBtn= (Button) findViewById(R.id.button);
        mLinearlayout= (LinearLayout) findViewById(R.id.linearlayout);
        mBtn.setText("从资源文件中获取图片，然后展示出来");
        mBtn.setOnClickListener(this);
        mText.setOnClickListener(this);
        mEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text1:
                break;
            case R.id.edit1:
                break;
            case R.id.button:
                if(mText.getText().toString().length()>0){
                    mText.setText("");
                }
                start();
                break;
        }
    }
static  StringBuffer sb=null;
    private void start() {
        sb=new StringBuffer();
        Observable.create(new Observable.OnSubscribe<Drawable>(){

            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
               sb.append(" Observable.create(): 线程: "+Thread.currentThread().getName()+"\n\n");
                Drawable dd=getResources().getDrawable(R.mipmap.gril);
                subscriber.onNext(dd);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(Schedulers.newThread())
          .map(new Func1<Drawable, ImageView>() {
              @Override
              public ImageView call(Drawable drawable) {
                  sb.append("map():  drawable -->imageview 的线程: "+Thread.currentThread().getName()+"\n\n");
                  ImageView img=new ImageView(RxSchuderActivity.this);
                  LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                  img.setLayoutParams(params);
                  img.setImageDrawable(drawable);
                  return img;
              }
          }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<ImageView>() {
                @Override
                public void call(ImageView imageView) {
                    sb.append("call(): 线程: "+Thread.currentThread().getName()+"\n");
                    mText.setText(sb);
                    mLinearlayout.addView(imageView);

                }
            });
    }
}
