package com.example.lading.applicationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
/*
RxBinding这个库是基于RxJava的对于Android原生组件的绑定，是RxJava风格的，
相当于代替了OnClick,Listener这些东西，
 */

public class RxBindingActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private EditText mEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);
        initView();
    }

    private void initView() {
        mText= (TextView) findViewById(R.id.text1);
        mEdit= (EditText) findViewById(R.id.edit1);
        mBtn= (Button) findViewById(R.id.button);
        mEdit.setHint("输入含有1的数字，下方才会出现提示");
        mText.setText("提示数据：\n");

        mBtn.setOnClickListener(this);
        mText.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        //用来监听edittext输入，同时匹配输入数数据来提示
        RxTextView.textChanges(mEdit)
                //在一次事件发生后的一段时间内没有新操作，则发出这次事件
                .debounce(500,TimeUnit.MILLISECONDS)
                //转换线程
                .observeOn(Schedulers.newThread())
                //通过输入的数据，来匹配"数据库"中的数据从而提示。。
                .map(new Func1<CharSequence, List<String>>() {
                    List<String> list=new ArrayList<String>();
                    @Override
                    public List<String> call(CharSequence charSequence) {

                        if (charSequence.toString().contains("1")){
                            for (int i=0;i<5;i++){
                                list.add("11"+i);
                            }
                        }
                        return list;
                    }
                })
                //由于我不想要listl列表，所以使用了flatMap来分解成一个一个的数据发送
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {

                        return Observable.from(strings);
                    }
                })
                //这里切换成主线程，不然没法操作组件
                .observeOn(AndroidSchedulers.mainThread())
                //这里做一些过滤动作
                .filter(new Func1<String, Boolean>() {
                    @Override
                    public Boolean call(String s) {
                        return !mText.getText().toString().contains(s);
                    }
                })
                //订阅
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        //这里展示提示数据
                        mText.append(s + "\n");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.w("DDDDDDDD",throwable.getMessage().toString());
                    }
                });
        mBtn.setText("连续点击防误触");
        RxView.clicks(mBtn)
                //防误触（设定点击后500ms内丢弃新事件，或者说点击后500ms毫秒无响应）
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    //这就相当于OnClickListener中的OnClick方法回调
                    @Override
                    public void call(Void aVoid) {
                       mText.append("\n 防误触 测试  \n");
                    }
                });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text1:
                break;
            case R.id.edit1:
                break;
            case R.id.button:
                mText.append("lalal\n");
                break;
        }
    }
}
