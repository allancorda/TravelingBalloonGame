package com.example.webdev.travelingballoon;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import static java.lang.Math.asin;


/**
 * Created by AllanCorda-PC on 2017-01-05.
 */

public class Player extends GameObject {
    private Bitmap spriteSheet;
    private int score;
    private double dya,dxa;     //Velocity or Acceleration
    private boolean switchDir = false;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;
    private int jump = 13;


    private boolean isJump = false;   //it is already jumping


    //setting up the player in game.
    public Player(Bitmap res, int w, int h, int numFrames){
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/2;
        dy = 0;
        dx = 0;
        score = 0;
        height = h;
        width = w;

        //we store the bitmap image of the player all 3 sprite frames
        Bitmap[] image = new Bitmap[numFrames];
        spriteSheet = res;

        //to loop through each section of the image 3 different picture of the helicopter in our case
        for(int i =0; i < image.length;i++){
            image[i] = Bitmap.createBitmap(spriteSheet, i*width, 0, width,height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    /*public void setUp(boolean b){ up = b; } */
    public void jumpPressed(){ isJump = true;}
    public void jumpPressedReleased(){ isJump = false;}

    // here is where were counting the score for the player.

    /*public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if(isJump){
            dy = (int)(dya-=jump);
            isJump = false;
        }
        else{
            dy = (int)(dya+=1.1);
        }

        if(dy>14)dy = 14;
        if(dy<-14)dy = -14;

        y += dy*2;
        dy = 0;
    }*/

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if(isJump){
            dxa = 0;
            if(switchDir){
                //Jumping to the left
                //dx = (int)(dxa-=jump);
            }else{
                //Jumping to the right
                dx = (int)(dxa+=jump);
            }

            isJump = false;
        }else{
            if(switchDir){
                //Moving to the Right
                //dx = (int)(dxa+=1.1);
            }else{
                //Moving to the Left
                dx = (int)(dxa-=1.1);
            }


        }

        if(dx>14)dx = 14;
        if(dx<-14)dx = -14;


        x += dx*2;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }


    public int getScore() { return score;}
    public boolean getPlaying() { return playing;}
    public void setPlaying(boolean p) {playing = p;}
    public void resetDXA(){dxa = 0;}
    public void resetDYA(){dya = 0;}
    public void resetScore() {score = 0;}

}
