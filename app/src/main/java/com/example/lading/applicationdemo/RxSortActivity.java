package com.example.lading.applicationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxSortActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private Button mBtnCancal;
    private TextView mEdit;
    private Subscription mSubscription=null;
    private Integer [] words={1,3,5,2,34,7,5,86,23,43};
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
        mBtn.setText("开始排序");
//        mBtnCancal= (Button) findViewById(R.id.button_cancal);
        mEdit.setText("为给定数据列表排序：1,3,5,2,34,7,5,86,23,43   \n\ntoSortedList() :为事件中的数据排序" );
        mBtn.setOnClickListener(this);
        mText.setOnClickListener(this);
        mEdit.setOnClickListener(this);
//        mBtnCancal.setOnClickListener(this);
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

        }
    }

    private void start() {
        //
        Observable.from(words)
                  .toSortedList()
                   .flatMap(new Func1<List<Integer>, Observable<Integer>>() {
                       @Override
                       public Observable<Integer> call(List<Integer> strings) {
                           return Observable.from(strings);
                       }
                   })
                  .subscribe(new Action1<Integer>() {
                      @Override
                      public void call(Integer strings) {
                          mText.append(strings+"\n");
                      }
                  });
    }
}
