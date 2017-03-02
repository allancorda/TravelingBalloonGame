package com.example.webdev.travelingballoon;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by webdev on 11/01/2017.
 * The Purpose of this calls is to be able to add any object into the game and attach it to the
 * player. can be glitter to the balloon or it can be on fire etc
 */

public class PlayerObjectAddon extends GameObject{
    public int r;

    public PlayerObjectAddon(int x, int y){
        r = 5;
        super.x = x;
        super.y = y;
    }

    public void update(){
        y -= 10;
    }

    //We are drawing our own Smoke object here
    public void draw(Canvas canvas){
        //Our paint class to get colour for the game
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);

        //Drawing a simple circle and filling paint in there
        //Drawing 3 circles
        canvas.drawCircle(x-r,y-r,r,paint);
        canvas.drawCircle(x-r-2,y-r+2,r,paint);
        canvas.drawCircle(x-r+1,y-r+4,r,paint);

    }
}
