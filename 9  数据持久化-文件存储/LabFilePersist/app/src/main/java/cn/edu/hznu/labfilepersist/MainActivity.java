package cn.edu.hznu.labfilepersist;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private Button Save;

    private Button Load;

    private EditText file;

    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file=(EditText)findViewById(R.id.file);
        edit=(EditText)findViewById(R.id.edit);
        Save=(Button)findViewById(R.id.Save);
        Load=(Button)findViewById(R.id.Load);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName=file.getText().toString();
                String inputText=edit.getText().toString();
                if (fileName.isEmpty()){
                    check();
                }else{
                    save(fileName,inputText);
                    edit.setText("");
                    Toast.makeText(MainActivity.this,
                            "Data have been saved.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName=file.getText().toString();
                String inputText=load(fileName);
                if (fileName.isEmpty()){
                    check();
                }else {
                    if(!TextUtils.isEmpty(inputText)){
                        edit.setText(inputText);
                        edit.setSelection(inputText.length());
                        Toast.makeText(MainActivity.this,
                                "Data have been loaded.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void save(String fileName,String inputText) {
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=openFileOutput(fileName,Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String load(String fileName){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder content=new StringBuilder();
        try{
            in=openFileInput(fileName);
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while((line=reader.readLine())!=null){
                content.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public void check(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("文件名不能为空");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
         });
        builder.show();
    }
}