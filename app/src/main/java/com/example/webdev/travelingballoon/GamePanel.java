package com.example.webdev.travelingballoon;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by AllanCorda-PC on 2017-01-04.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    //the dimension of the image to reset
    public static final int WIDTH = 720;
    public static final int HEIGHT = 1280;
    public static final int MOVESPEED = -3;
    private long smokeStartTimer;
    private long missileStartTimer;
    private long missileElapsed;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<PlayerObjectAddon> smoke;
    private ArrayList<MapObjects> missiles;
    //the array list for the Left side border
    private ArrayList<Border> leftBorder;
    //the array list for the Right side border
    private ArrayList<Border> rightBorder;
    //Max Border length
    private final int maxBorderWidth = 1280;
    //increase to slow down difficulty progression, decrease to speed up difficulty progression
    private int progressDenom = 20;
    //Random variable for missile
    private Random rand = new Random();

    public GamePanel (Context context)
    {
        super(context);

        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while (retry && counter < 1000){
            counter++;
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
            }catch (InterruptedException ix){
                ix.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //Loading the background screen
        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.skybg1));
        //Note width is needed for the 2 parameter for this player to animate. The width of each individual frame
        //Note height is needed for the 3 parameter for this player to animate. The height of each individual frame
        //Note the Last one is the number of image/frame used for the animation
        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.helicopter),65,25,3);
        smoke = new ArrayList<PlayerObjectAddon>();
        //comeout at a delay.. 1 at a time
        smokeStartTimer = System.nanoTime();
        //for the missiles the be created and into the game
        missiles = new ArrayList<MapObjects>();
        missileStartTimer = System.nanoTime();
        //Border for the Left Side created
        leftBorder = new ArrayList<Border>();

        //Border for the Right Side created
        rightBorder = new ArrayList<Border>();

        //this making the background image move left off the screen
        //to make this work in the gameboard to uncomment setVector
        //bg.setVector(-1);
        //We can safely start the game thread loop
        thread.setRunning(true);
        thread.start();

    }

    //Time 28:31
    //Player will move after we click on something
    //OnTouch event goes here
    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()){
                player.setPlaying(true);
            }else{
                player.jumpPressed();
            }
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_UP){
            player.jumpPressedReleased();
            return true;
        }
        return super.onTouchEvent(event);
    }

    public boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getRectangle(),b.getRectangle()))
        {
            return true;
        }
        return false;
    }

    public void update(){
        //Only update the background if the player is flying logic
        //Only update the player if the player is flying logic
        if(player.getPlaying()){
            bg.update();
            player.update();


            //update the missiles for our game
            //add missiles on timer
            long missilesElapsed = (System.nanoTime() - missileStartTimer)/1000000;
            //The higher the player score is the less the delay to spawn the missiles
            //less delay time for each missiles
            if(missilesElapsed > (2000 - player.getScore()/4)){
                //have the first one go down in the middle
                //Rest is random spots
                if(missiles.size() == 0){
                    missiles.add(new MapObjects(BitmapFactory.decodeResource(getResources(),R.drawable.
                            missile),WIDTH/2, -10, 45, 15, player.getScore(), 13));
                }else if(missiles.size() < 5){
                    //Rest is random spots
                    missiles.add(new MapObjects(BitmapFactory.decodeResource(getResources(),R.drawable.missile),
                            (int)(rand.nextDouble()*(WIDTH)), -10,45,15, player.getScore(),13));
                }

            }

            //Update Missiles for collision detection
            //loop through every missile and check collision and remove
            for(int i = 0; i<missiles.size();i++)
            {
                //update missile
                missiles.get(i).update();

                if(collision(missiles.get(i),player))
                {
                    missiles.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //remove missile if it is way off the screen
                if(missiles.get(i).getY()> 1300  ||  missiles.get(i).getX()<= 0 ||  missiles.get(i).getX() >= WIDTH)
                {
                    //System.out.println("delete missile" + missiles.get(i).getX());
                    missiles.remove(i);
                    break;
                }
            }

            //update the smokepuffs for our game
            long elapsed = (System.nanoTime()- smokeStartTimer)/1000000;
            //after 120 milli seconds then we add a smoke
            if(elapsed > 120){
                //establishing where the smoke object will be created and placed
                smoke.add(new PlayerObjectAddon(player.getX()+10,player.getY()));
                //we are now starting the time for the smoke
                smokeStartTimer = System.nanoTime();
            }

            //Our for loop to remove all the smoke puff from the game
            for(int i = 0; i < smoke.size(); i++){
                smoke.get(i).update();
                //We are check here if the smoke puff is off the screen then remove it
                if(smoke.get(i).getY() < -10){
                    smoke.remove(i);
                }
            }
        }

    }

    public void updateLeftBorder()
    {

    }
    public void updateRightBorder()
    {

    }

    //Everything that we see visually goes here
    @Override
    public void draw(Canvas canvas){
        //to scale the whole game to a bigger screen
        //Scale factor


        //final float scaleFactorX = getWidth()/(WIDTH*1.f);
        //final float scaleFactorY = getHeight()/(HEIGHT*1.f);


        if(canvas != null){
            //Need to initial state so we can go back to prevent it from growing

            final int savedState = canvas.save();
            //canvas.scale(scaleFactorX,scaleFactorY);

            bg.draw(canvas);
            player.draw(canvas);
            //Iterate through each PlayerObjectAddon for the smoke array and draw it
            for(PlayerObjectAddon sp: smoke){
                sp.draw(canvas);
            }

            //loop through all the Mapobject and draw missiles
            for(MapObjects m: missiles)
            {
                m.draw(canvas);
            }
            //Draw the Left Border
            for(Border lb: leftBorder)
            {
                lb.draw(canvas);
            }

            //draw Right Brder
            for(Border rb: rightBorder)
            {
                rb.draw(canvas);
            }

            //to return back to normal.. otherwise it will scale again and get bigger and bigger
            canvas.restoreToCount(savedState);

        }
    }

}
