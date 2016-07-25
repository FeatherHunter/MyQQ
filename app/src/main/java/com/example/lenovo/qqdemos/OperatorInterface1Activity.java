package com.example.lenovo.qqdemos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperatorInterface1Activity extends Activity implements View.OnFocusChangeListener {

    private EditText userEditText = null;//账号
    private EditText passwdEditText = null;//密码
    private Button logButton = null;//登陆
    private TextView infoTextView = null;//无法登陆
    private TextView sign_upTextView = null;//新用户注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_interface1);

        userEditText = (EditText) findViewById(R.id.user_edittext);
        passwdEditText = (EditText) findViewById(R.id.passwd_edittext);
        logButton = (Button) findViewById(R.id.login_button);
        infoTextView = (TextView) findViewById(R.id.info_textview);
        sign_upTextView = (TextView) findViewById(R.id.sign_up_textview);

        userEditText.setOnFocusChangeListener(this);
        passwdEditText.setOnFocusChangeListener(this);

//      userEditText.setOnClickListener(new OnClickListener());

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Text1 = userEditText.getText().toString();

                if (Text1.equals("")) {
                    Toast.makeText(OperatorInterface1Activity.this, "账户名不为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //检查账号密码合法性
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(Text1);
                if (!m.matches()) {
                    Toast.makeText(OperatorInterface1Activity.this, "账户名请输入0-9的数字", Toast.LENGTH_SHORT).show();
                    userEditText.setText("");
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(OperatorInterface1Activity.this, QQService.class);
//              startActivity(intent);
                startService(intent);
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
//            if(v.getId() == R.id.infoTextView){
//                userEditText.setVisibility(View.GONE);
//            }
//        }
//  }
}
