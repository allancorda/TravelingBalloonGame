package com.example.webdev.travelingballoon;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by AllanCorda-PC on 2017-01-14.
 */

public class MapObjects extends GameObject {
    private int score;
    private int speed;
    //generate the speed of the object falling
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public MapObjects(Bitmap res, int x, int y, int w, int h, int s, int numFrames ){
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        score = s;

        //speed of the falling object
        speed = 7 + (int) (rand.nextDouble()*score/30);

        //cap missile speed
        if(speed>40)speed = 40;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i = 0; i<image.length;i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i*height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100-speed);

    }

    public void update()
    {
        y+=speed;
        animation.update();
    }
    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){}
    }

    @Override
    public int getWidth(){
        //Overriding from the super class game object
        //Offset slightly for more realistic collision detection
        return width -10;
    }
}
