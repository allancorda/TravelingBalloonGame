package com.example.webdev.travelingballoon;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by webdev on 05/01/2017.
 */

public class Background {

    private Bitmap image;
    private int x,y,dx,dy;

    public Background(Bitmap res){

        image = res;
        dy = GamePanel.MOVESPEED;
    }

    //is to scroll the background screen
    public void update(){
        y+=dy;  //to move the background image
        //if the background image is completely off the screen to reset it back to 0
        if(y<-GamePanel.HEIGHT){
            y=0;
        }
    }

    public void draw(Canvas canvas){
        //display the image to draw on the screen
        canvas.drawBitmap(image,x,y,null);
        //to take up the rest of the space the moved off the screen
        //so we draw a second image in front of the other image
        if(y <0){
            canvas.drawBitmap(image,x,y+GamePanel.HEIGHT,null);
        }
    }
    /*
    public void setVector(int dy){
        this.dy=dy;
    }
    */
}
