package com.example.lenovo.qqdemos;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperatorInterface1Activity extends Activity implements View.OnFocusChangeListener {

    private EditText editText1 = null;
    private EditText editText2 = null;
    private Button myButton = null;
    private TextView textView1 = null;
    private TextView textView2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_interface1);

        editText1 = (EditText) findViewById(R.id.textView1);
        editText2 = (EditText) findViewById(R.id.textView2);
        myButton = (Button) findViewById(R.id.button);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);

        editText1.setOnFocusChangeListener(this);
        editText2.setOnFocusChangeListener(this);

//      editText1.setOnClickListener(new OnClickListener());

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Text1 = editText1.getText().toString();

                if(Text1.equals(""))
                {
                    Toast.makeText(OperatorInterface1Activity.this, "账户名不为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //检查账号密码合法性
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(Text1);
                if (!m.matches()) {
                    Toast.makeText(OperatorInterface1Activity.this, "账户名请输入0-9的数字", Toast.LENGTH_SHORT).show();
                    editText1.setText("");
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(OperatorInterface1Activity.this, OperatorInterface2Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText textView = (EditText)v;
        String hint;
        if(hasFocus){
            hint = textView.getHint().toString();
            textView.setTag(hint);
            textView.setHint("");
        }else{
            hint = textView.getTag().toString();
            textView.setHint(hint);
        }
    }
//    private class OnClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            if(v.getId() == R.id.textView1){
//                editText1.setVisibility(View.GONE);
//            }
//        }
//  }
}
