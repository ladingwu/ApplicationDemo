package com.example.lading.applicationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;

public class RxConnetActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private Button bntNormol;
    private TextView mEdit;
    private Subscription mSubscription=null;
    private Integer [] integer={1,2,3,4,5,6};
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
        bntNormol= (Button) findViewById(R.id.button_cancal);
        mBtn.setText("正常情况下");

        bntNormol.setText("connect模式");

        mEdit.setText("Observable发送事件1-6，两个观察者同时观察这个Observable \n要求：每发出一个事件，观察者A和观察者都会收到，而不是先把所有的时间发送A,然后再发送给B  \n\n" );
        mBtn.setOnClickListener(this);
        mText.setOnClickListener(this);
        mEdit.setOnClickListener(this);
        bntNormol.setOnClickListener(this);

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
                normol();
                break;
            case R.id.button_cancal:
                mText.setText("");
                start();
                break;

        }
    }

    private void normol() {
        Observable  observable= Observable.from(integer);
        Action1 a1=new Action1<Integer>(){
            @Override
            public void call(Integer o) {
                mText.append("观察者A  收到:  "+o+"\n");
            }
        };
        Action1 a2=new Action1<Integer>(){
            @Override
            public void call(Integer o) {
                mText.append("观察者B  收到:  "+o+"\n");
            }
        };

        observable.subscribe(a1);
        observable.subscribe(a2);

    }

    private void start() {

        ConnectableObservable  observable= Observable.from(integer)
                                                    .publish();//将一个Observable转换为一个可连接的Observable

        Action1 a1=new Action1<Integer>(){
            @Override
            public void call(Integer o) {
                mText.append("观察者A  收到:  "+o+"\n");
            }
        };
        Action1 a2=new Action1<Integer>(){
            @Override
            public void call(Integer o) {
                mText.append("观察者B  收到:  "+o+"\n");
            }
        };

        observable.subscribe(a1);
        observable.subscribe(a2);
        observable.connect();

    }
}
