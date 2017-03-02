package com.example.webdev.travelingballoon;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by AllanCorda-PC on 2017-01-14.
 */



public class Border extends GameObject{
    private Bitmap image;

    public Border(Bitmap res, int x, int y, int h)
    {
        height = h;
        width = 20;

        this.x = x;
        this.y = y;

        dx = GamePanel.MOVESPEED;
        image = Bitmap.createBitmap(res, 0, 0, width, height);
    }
    public void update()
    {
        y+=dy;
    }
    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(image,x,y,null);
        }catch(Exception ex){

        };
    }

}
