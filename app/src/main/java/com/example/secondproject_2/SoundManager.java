package com.example.secondproject_2;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class SoundManager {
    private static MediaPlayer backgroundSound;
    private static MediaPlayer clickSound;
    private static MediaPlayer winSound;
    private static boolean isBackgroundSoundPlaying = false;

    public static void playBackgroundSound(Context context) {
        int soundResourceId = R.raw.backgroundsound;

        if (backgroundSound == null) {
            backgroundSound = MediaPlayer.create(context, soundResourceId);
            backgroundSound.start();
            isBackgroundSoundPlaying = true;
        } else {
            backgroundSound.stop();
            backgroundSound.release();
            backgroundSound = MediaPlayer.create(context, soundResourceId);
            backgroundSound.start();
        }

        Log.d("SoundManager", "playBackgroundSound called");
    }




    public static void playClickSound(Context context) {
        Log.d("SoundManager", "playClickSound called");


        if (clickSound == null) {
            clickSound = MediaPlayer.create(context, R.raw.clicksound);
        } else {
            clickSound.stop();
            clickSound.release();
            clickSound = MediaPlayer.create(context, R.raw.clicksound);
            clickSound.start();
        }


    }

    public static void playWinSound(Context context) {
        Log.d("SoundManager", "playWinSound called");

        if (winSound == null) {
            winSound = MediaPlayer.create(context, R.raw.winsound);
            winSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopBackgroundSound();
                }
            });
        } else {
            winSound.stop();
            winSound.release();
            winSound = MediaPlayer.create(context, R.raw.winsound);
            winSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopBackgroundSound();
                }
            });
        }
        winSound.start();
    }

    public static void stopBackgroundSound() {
        if (backgroundSound != null && isBackgroundSoundPlaying) {
            backgroundSound.stop();
            isBackgroundSoundPlaying = false;
        }
    }
}