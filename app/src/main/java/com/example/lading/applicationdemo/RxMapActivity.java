package com.example.lading.applicationdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxMapActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private TextView mEdit;
    private Integer [] number={1,2,3,4,5,6};
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
        mBtn.setText("判断数组中的小于3的数");
        mEdit.setText("输入Integer(int)：1,2,3,4,5,6 \n"+"\n"+"输出：type:true/false \n");

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
        mText.append("\n 输入参数： 1,2,3,4,5,6 \n");
        Observable.from(number)           //之前提到的创建Observable方法
                  .map(new Func1<Integer, Boolean>() {

                      @Override
                      public Boolean call(Integer integer) {
                          mText.append("\n\n map()  Integer--->Boolean");
                          return (integer<3);
                      }
                  })
                  .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        mText.append("\n观察到输出结果：\n");
                        mText.append(aBoolean.toString());
                    }
                });
    }

}
