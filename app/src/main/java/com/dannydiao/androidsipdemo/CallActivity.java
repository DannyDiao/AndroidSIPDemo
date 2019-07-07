package com.dannydiao.androidsipdemo;

import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.github.faucamp.simplertmp.RtmpHandler;

import com.laifeng.sopcastsdk.stream.sender.rtmp.RtmpSender;
import com.laifeng.sopcastsdk.ui.CameraLivingView;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.IOException;
import java.net.SocketException;



public class CallActivity extends AppCompatActivity implements RtmpHandler.RtmpListener, SrsEncodeHandler.SrsEncodeListener, SrsRecordHandler.SrsRecordListener {

    private RtmpSender rtmpSender;
    private Button callButton;
    private Button stopButton;
    private CameraLivingView cameraLivingView;
    public String RtmpPushUrl = "";
    public String RtmpPullUrl = "";
    private EditText editText;
    String ID = "";
    private FloatingActionButton switch_1;
    private Button incall;

    StandardGSYVideoPlayer videoPlayer;
    OrientationUtils orientationUtils;

    String accout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        callButton = findViewById(R.id.call_button);
        stopButton = findViewById(R.id.stop_button);
        editText = findViewById(R.id.id_input);
        switch_1 = findViewById(R.id.switch_1);
        incall = findViewById(R.id.incall);

        Intent intent = getIntent();




        //声明推流摄像头展示界面对象
        SrsPublisher mPublisher = new SrsPublisher((SrsCameraView)
                findViewById(R.id.surfaceViewEx));
        //设置编码消息处理
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        //设置RTMP消息处理
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        //设置记录消息处理
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        //设置展示界面大小
        mPublisher.setPreviewResolution(1080, 1920);
        //设置横屏推流 1为竖屏 2为横屏
        mPublisher.setScreenOrientation(1);
        //设置输出界面大小
        mPublisher.setOutputResolution(640, 480);
        //打开摄像头
        mPublisher.startCamera();
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = editText.getText().toString();

                if (ID.equals("1")) {
                    RtmpPushUrl = "rtmp://148.70.157.68:2333/ji_tong/1";
                    RtmpPullUrl = "rtmp://148.70.157.68:2333/ji_tong/2";

                } else {
                    RtmpPushUrl = "rtmp://148.70.157.68:2333/ji_tong/2";
                    RtmpPullUrl = "rtmp://148.70.157.68:2333/ji_tong/1";

                }

                mPublisher.switchToHardEncoder();
                //开始推流
                mPublisher.startPublish(RtmpPushUrl);
                mPublisher.startCamera();

            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublisher.stopPublish();
                mPublisher.stopRecord();
            }
        });
        switch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPublisher.switchCameraFace((mPublisher.getCameraId() + 1) % Camera.getNumberOfCameras());
            }
        });
        incall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

    }

    private void init() {
        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.video_player);

        String source1 = RtmpPullUrl;
        videoPlayer.setUp(source1, true, "Main");
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);

        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        videoPlayer.startPlayLogic();
    }



    @Override
    public void onRtmpAudioStreaming() {

    }

    @Override
    public void onNetworkWeak() {

    }

    @Override
    public void onNetworkResume() {

    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRecordPause() {

    }

    @Override
    public void onRecordResume() {

    }

    @Override
    public void onRecordStarted(String msg) {

    }

    @Override
    public void onRecordFinished(String msg) {

    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRecordIOException(IOException e) {

    }

    @Override
    public void onRtmpStopped() {

    }

    @Override
    public void onRtmpDisconnected() {

    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {

    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpSocketException(SocketException e) {

    }

    @Override
    public void onRtmpIOException(IOException e) {

    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {

    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {

    }

    @Override
    public void onRtmpVideoStreaming() {
        Log.d("RTMP", "onRtmpVideoStreaming");
    }

    @Override
    public void onRtmpConnected(String msg) {
        Log.d("RTMP", "onRtmpConnected");

    }

    @Override
    public void onRtmpConnecting(String msg) {
        Log.d("RTMP", "onRtmpConnecting");
    }


}
