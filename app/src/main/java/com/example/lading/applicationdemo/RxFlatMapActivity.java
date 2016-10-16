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

public class RxFlatMapActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mText;
    private Button mBtn;
    private TextView mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);
        initView();
        initData();
    }
    private SchoolClass[] mSchoolClasses=new SchoolClass[2];

    private void initData() {
        Student[] student=new Student[5];
        for(int i=0;i<5;i++){
            Student s=new Student("二狗"+i,"17");
            student[i]=s;
        }
        mSchoolClasses[0]=new SchoolClass(student);

        Student[] student2=new Student[5];
        for(int i=0;i<5;i++){
            Student s=new Student("小明"+i,"27");
            student2[i]=s;
        }
        mSchoolClasses[1]=new SchoolClass(student2);

    }

    private void initView() {
        mText= (TextView) findViewById(R.id.text1);
        mEdit= (TextView) findViewById(R.id.edit1);
        mBtn= (Button) findViewById(R.id.button);

        mEdit.setText("打印一个学校所有班级所有学生姓名");
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
                strat();
                break;
        }
    }
    public SchoolClass[] getSchoolClass(){
        return  mSchoolClasses;
    }
    private void strat() {
        Observable.from(getSchoolClass())
                .flatMap(new Func1<SchoolClass, Observable<Student>>() {
                    @Override
                    public Observable<Student> call(SchoolClass schoolClass) {
                        //将Student列表使用from方法一个一个发出去
                        return Observable.from(schoolClass.getStudents());
                    }
                })
                .subscribe(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        mText.append("打印单个学生信息：\n");
                        mText.append("name:"+student.name+"    age: "+student.age+"\n");
                    }
                });
    }
}

class SchoolClass{
    Student[] stud;
    public SchoolClass(Student[] s){
        this.stud=s;
    }
    public Student[] getStudents(){
        return  stud;
    }
}

class Student{
    String name;
    String age;
    public Student(String name,String age){
        this.name=name;
        this.age=age;
    }
}