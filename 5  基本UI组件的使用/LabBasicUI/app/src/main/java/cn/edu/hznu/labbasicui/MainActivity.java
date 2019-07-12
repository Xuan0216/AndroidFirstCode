package cn.edu.hznu.labbasicui;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressBar progressBar;
    private EditText txtNumber;
    private Button btnChange;
    private int value;
    private Button btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取布局中的组件
        progressBar=(ProgressBar)findViewById(R.id.progressBar1);
        txtNumber=(EditText)findViewById(R.id.txt_number);
        btnChange=(Button)findViewById(R.id.btn_change);
        btnChange.setOnClickListener(this);
        btnView=(Button)findViewById(R.id.btn_view);
        btnView.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id=v.getId();
        switch(id){
            case R.id.btn_change://点击“修改”按钮
                String strValue=txtNumber.getText().toString();
                if((strValue != null) && !strValue.equals("")){
                    value=Integer.parseInt(strValue);
                    if(value>=0&&value<=100){
                        progressBar.setProgress(value);//修改进度条的当前值
                    }else{
                        //弹出消息提示框
                        AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("Indication");

                        dialog.setMessage("输入的数字不合法！");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog.show();//显示提示对话框

                    }
                }
                txtNumber.setText("");
                break;
            case R.id.btn_view://点击“查看”按钮
                int progress=progressBar.getProgress();
                AlertDialog.Builder prog=new AlertDialog.Builder(MainActivity.this);
                prog.setTitle("Progress Value");
                prog.setMessage("当前值"+progress);
                prog.setCancelable(false);
                prog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                prog.show();//显示提示对话框
            default:
                break;
        }
    }
}
