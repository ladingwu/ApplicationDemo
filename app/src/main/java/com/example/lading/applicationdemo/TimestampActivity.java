package com.example.lading.applicationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.schedulers.Timestamped;

public class TimestampActivity extends AppCompatActivity implements View.OnClickListener{

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
        mBtn.setText("开始");
//        mBtnCancal= (Button) findViewById(R.id.button_cancal);
        mEdit.setText("为给定数据列表：1,3,5,2,34,7,5,86,23,43中每一个数据加上一个时间戳   \n\ntimestamp() :为每个事件加上一个时间戳" );
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
                mText.setText("");
                start();
                break;

        }
    }

    private void start() {
        //
//        Observable.from(words)
//                  .toSortedList()
//                   .flatMap(new Func1<List<Integer>, Observable<Integer>>() {
//                       @Override
//                       public Observable<Integer> call(List<Integer> strings) {
//                           return Observable.from(strings);
//                       }
//                   })
//                  .subscribe(new Action1<Integer>() {
//                      @Override
//                      public void call(Integer strings) {
//                          mText.append(strings+"\n");
//                      }
//                  });
        Observable.from(words)
                .timestamp()
//                .timestamp(Schedulers.io()) 可指定线程环境，如果指定到子线程，请在最后切换成主线程
                .subscribe(new Action1<Timestamped<Integer>>() {
                    @Override
                    public void call(Timestamped<Integer> integerTimestamped) {

                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                        mText.append("value: "+integerTimestamped.getValue()+"       time:   ");
                        mText.append(sdf.format(new Date(integerTimestamped.getTimestampMillis()))+"\n");

                    }
                });
    }
}
