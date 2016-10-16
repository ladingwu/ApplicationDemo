package com.example.lading.applicationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/*
    同步情况下了解Rxjava的运行
 */
public class NormalRxActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private TextView mEdit;
    static String str ="一二三四五\n 上山打老虎\n 老虎一发威\n 武松就发怵\n";
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
        mEdit.setText(str);


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
                if(mText.getText().toString()!=null ||mText.getText().toString().length()>0){
                    mText.setText("");
                }
                start();
                break;
        }
    }

    private void start() {
        //创建被观察者
        Observable observable=createObservable();
        //创建观察者
        Subscriber subscriber=createSubscriber();

        mText.append("开始订阅，准备观察...\n");
        //事实上，observable不止可以订阅subscriber，也可以订阅ActionX()
        observable.subscribe(subscriber);

        //就像现在这样
//        observable.subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                //Action1也就意味着，只能传入一个参数 ----> String s,同理Action0，Action2....,
//                //在这个call方法中传入了onNext()的参数，相当于代替了onNext方法，但是就不能监听onComplete,onError方法了
//                mText.append("执行观察者中的onNext()...\n");
//                mText.append(s+"...\n");
//            }
//        });
    }

    private Subscriber createSubscriber() {
        //创建观察者
        Subscriber subscriber=new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mText.append("执行观察者中的onCompleted()...\n");
                mText.append("订阅完毕，结束观察...\n");
            }

            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String s) {
                mText.append("执行观察者中的onNext()...\n");
                mText.append(s+"...\n");
            }

        };
        return  subscriber;
    }

    private Observable createObservable(){
        //创建被观察者，这是最正常的创建方法
        Observable observable=Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("一二三四五");
                subscriber.onNext("上山打老虎");
                subscriber.onNext("老虎一发威");
                subscriber.onNext("武松就发怵");
                subscriber.onCompleted();

            }
        });
        //想要图方便，可以这样创建
        //from(T[])
//        String [] kk={"一二三四五","上山打老虎","老虎一发威","武松就发怵"};
//        Observable observable=Observable.from(kk);

        //或者这样
        //just(T...)
//        Observable observable=Observable.just("一二三四五","上山打老虎","老虎一发威","武松就发怵");

        return observable;
    }
}
