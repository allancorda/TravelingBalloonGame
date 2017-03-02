package com.example.webdev.travelingballoon;

import android.graphics.Bitmap;
import android.provider.Settings;

/**
 * Created by AllanCorda-PC on 2017-01-05.
 */

public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames){
        //19::19
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void setDelay(long d){ delay = d;}
    public void setFrame(int i){currentFrame = i;}

    //to update any/all animation
    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed > delay){
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            currentFrame =0;
            playedOnce = true;
        }
    }

    //What the player class will be drawing is implemented by this method
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    public int getFrame(){return  currentFrame;}
    public boolean playedOnce() {return playedOnce;}

}
