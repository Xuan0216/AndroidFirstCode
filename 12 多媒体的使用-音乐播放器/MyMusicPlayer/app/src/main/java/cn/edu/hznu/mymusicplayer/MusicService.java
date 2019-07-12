package cn.edu.hznu.mymusicplayer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {

    public static final String SERVICE_ACTION = "cn.edu.hznu.Service";
    private int mNewMusic;
    private Music mMusic;
    private MediaPlayer mPlayer = new MediaPlayer();
    private int state = 0x11;//0x11:第一次播放歌曲  0x12:暂停 0x13:继续播放
    private int mCurPosition, mDuration;

    @Override
    public void onCreate() {
        super.onCreate();

        //注册广播
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter(SERVICE_ACTION);
        registerReceiver(receiver, filter);

        //歌曲播放完成监听
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent = new Intent(MainActivity.ACTIVITY_ACTION);
                intent.putExtra("over", true);
                sendBroadcast(intent);
                mCurPosition = 0;
                mDuration = 0;
            }
        });
    }

    public MusicService() {}

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mNewMusic = intent.getIntExtra("newMusic", -1);//接收是否是新歌曲
            if (mNewMusic != -1) {
                mMusic = (Music) intent.getSerializableExtra("music");//获得歌曲对象
                if (mMusic != null) {
                    playMusic(mMusic);
                    state = 0x12;
                }
            }

            int isPlay = intent.getIntExtra("isPlay", -1);
            if (isPlay != -1) {
                switch (state) {
                    case 0x11://第一次播放
                        mMusic = (Music) intent.getSerializableExtra("music");//获得歌曲对象
                        playMusic(mMusic);
                        state = 0x12;
                        break;
                    case 0x12://暂停播放
                        mPlayer.pause();
                        state = 0x13;
                        break;
                    case 0x13://继续播放
                        mPlayer.start();
                        state = 0x12;
                        break;
                }
            }

            //进度条位置
            int progress = intent.getIntExtra("progress", -1);
            if (progress != -1) {
                mCurPosition = (int) (((progress * 1.0) / 100) * mDuration);
                mPlayer.seekTo(mCurPosition);
            }

            //停止播放
            int isStop=intent.getIntExtra("isStop",-1);
            if(isStop != -1){
                mPlayer.reset();
                state = 0x11;
            }

            Intent intentActivity = new Intent(MainActivity.ACTIVITY_ACTION);
            intentActivity.putExtra("state", state);
            sendBroadcast(intentActivity);//将当前状态发送给activity
        }
    }

    //播放音乐
    public void playMusic(Music music) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.reset();
            try {
                mPlayer.setDataSource(music.getPath());
                mPlayer.prepare();
                mPlayer.start();
                mDuration = mPlayer.getDuration();//获得歌曲时长
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        while (mCurPosition < mDuration) {
                            try {
                                sleep(1000);
                                mCurPosition = mPlayer.getCurrentPosition();//获得当前进度位置
                                Intent intent = new Intent(MainActivity.ACTIVITY_ACTION);
                                intent.putExtra("curPosition", mCurPosition);
                                intent.putExtra("duration", mDuration);
                                sendBroadcast(intent);//将当前位置和总时长发送给activity
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver receiver) {
        super.unregisterReceiver(receiver);
        unregisterReceiver(receiver);
    }
}
