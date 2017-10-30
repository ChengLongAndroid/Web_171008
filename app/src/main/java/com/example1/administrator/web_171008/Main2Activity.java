package com.example1.administrator.web_171008;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    static private Camera camera;
    private TextView textView1,textView2;
    private Button button,button2,button3;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent=getIntent();
        String sun1=intent.getStringExtra("aa")+intent.getStringExtra("bb");
        textView1= (TextView) findViewById(R.id.textView1);
        textView2= (TextView) findViewById(R.id.textView2);
        textView1.setText(sun1);
        button= (Button) findViewById(R.id.led);
        button2= (Button) findViewById(R.id.led2);
        button3= (Button) findViewById(R.id.vib3);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLight();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeLight();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {100,400,100,400}; // 停止 开启 停止 开启
                vibrator.vibrate(pattern,0); //重复两次上面的pattern 如果只想震动一次，index设为-1
            }

        });



    }
    protected static void openLight() {
        try {
            camera = Camera.open();//开启摄像头只需透过Camera.Open就可以简单开启，开启后取得Camera.Parameters就可以设定参数
            //int textureId = 0;
            //camera.setPreviewTexture(new SurfaceTexture(textureId));
            camera.startPreview();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(parameters.FLASH_MODE_TORCH);//开灯
            camera.setParameters(parameters);
        } catch (Exception e) {
            Log.i("打开闪光灯失败：",e.toString()+"");
        }
    }

    protected static void closeLight() {

        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);//关灯
            camera.setParameters(parameters);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
    public void onStop(){
        super.onStop();

        vibrator.cancel();
    }
}

