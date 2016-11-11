package com.mygdx.game.MyBaseClasses;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GlobalClasses.Assets;
import com.mygdx.game.MyGdxGame;


/**
 * Created by tuskeb on 2016. 09. 30..
 */
abstract public class MyStage extends Stage implements InitableInterface {
    public final MyGdxGame game;
    private int x = (random(1,5));
    public boolean menue = true;
    public final Music menumusic = Assets.manager.get(Assets.MOOSE);
    public final Music gamemusic1 = Assets.manager.get(Assets.POPDANCE);
    public final Music gamemusic2 = Assets.manager.get(Assets.HAPPYROCK);
    public final Music gamemusic3 = Assets.manager.get(Assets.EXTREMEACTION);
    public final Music gamemusic4 = Assets.manager.get(Assets.DANCE);
    public final Music gamemusic5 = Assets.manager.get(Assets.BADASS);



    public int random(int a,int b){return (int)Math.round(Math.random()*(b-a+1)+a);}

    public void gamemusicgenerator(){
        int a = 0;
        if(!menue && !(gamemusic1.isPlaying() || gamemusic2.isPlaying() || gamemusic3.isPlaying() || gamemusic4.isPlaying() || gamemusic5.isPlaying())){
            gamemusic1.stop();
            gamemusic2.stop();
            gamemusic3.stop();
            gamemusic4.stop();
            gamemusic5.stop();
            while(true) {
                a = random(1,5);
                if(x!=a)break;
            }
        }
        if(a==1 && !menue){gamemusic1.play();x=a;}
        else if(a==2 && !menue){gamemusic2.play();x=a;}
        else if(a==3 && !menue){gamemusic3.play();x=a;}
        else if(a==4 && !menue){gamemusic4.play();x=a;}
        else if(a==5 && !menue){gamemusic5.play();x=a;}
        else if(menue) {
            gamemusic1.stop();
            gamemusic2.stop();
            gamemusic3.stop();
            gamemusic4.stop();
            gamemusic5.stop();
            menumusic.play();
        }
    }


    public MyStage(Viewport viewport, Batch batch, MyGdxGame game) {
        super(viewport, batch);
        this.game = game;
        setCameraResetToCenterOfScreen();
        menumusic.play();
        init();
    }

    public void addBackEventStackListener()    {
        addListener(new InputListener() {

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode== Input.Keys.BACK) {
                    game.setScreenBackByStackPop();
                }
                return true;
            }
        });
    }

    public Actor getLastAdded() {
        return getActors().get(getActors().size-1);
    }

    public void setCameraZoomXY(float x, float y, float zoom)
    {
        OrthographicCamera c = (OrthographicCamera)getCamera();
        c.zoom=zoom;
        c.position.set(x,y,0);
        c.update();
    }

    private float cameraTargetX = 0;
    private float cameraTargetY = 0;
    private float cameraTargetZoom = 0;
    private float cameraMoveSpeed = 0;

    public void setCameraMoveToXY(float x, float y, float zoom, float speed)
    {
        cameraTargetX = x;
        cameraTargetY = y;
        cameraTargetZoom = zoom;
        cameraMoveSpeed = speed;
    }

    public void setCameraResetToCenterOfScreen()
    {
        if(getViewport() instanceof ExtendViewport) {
            OrthographicCamera c = (OrthographicCamera) getCamera();
            ExtendViewport v = (ExtendViewport) getViewport();
            c.setToOrtho(false, getViewport().getWorldWidth(), getViewport().getWorldHeight());
            c.translate((v.getWorldWidth() - v.getMinWorldWidth() / 2) < 0 ? 0 : -((v.getWorldWidth() - v.getMinWorldWidth()) / 2),
                    ((v.getWorldHeight() - v.getMinWorldHeight()) / 2) < 0 ? 0 : -((v.getWorldHeight() - v.getMinWorldHeight()) / 2));
            c.update();
        }
    }
    public void setCameraResetToLeftBottomOfScreen(){
        OrthographicCamera c = (OrthographicCamera)getCamera();
        Viewport v = getViewport();
        setCameraZoomXY(v.getWorldWidth()/2, v.getWorldHeight()/2,1);
        c.update();

    }

    public void resize(int screenWidth, int screenHeight){
        getViewport().update(screenWidth, screenHeight, true);
        resized();
    }

    protected void resized(){
        setCameraResetToCenterOfScreen();
    };

    @Override
    public void act(float delta) {
        super.act(delta);
        OrthographicCamera c = (OrthographicCamera)getCamera();
        if (cameraTargetX!=c.position.x || cameraTargetY!=c.position.y || cameraTargetZoom!=c.zoom){
            if (Math.abs(c.position.x-cameraTargetX)<cameraMoveSpeed*delta) {
                c.position.x = (c.position.x + cameraTargetX) / 2;
            } else {
                if (c.position.x<cameraTargetX){
                    c.position.x += cameraMoveSpeed*delta;
                }else{
                    c.position.x -= cameraMoveSpeed*delta;
                }
            }
            if (Math.abs(c.position.y-cameraTargetY)<cameraMoveSpeed*delta) {
                c.position.y = (c.position.y + cameraTargetY) / 2;
            } else {
                if (c.position.y<cameraTargetY){
                    c.position.y += cameraMoveSpeed*delta;
                }else{
                    c.position.y -= cameraMoveSpeed*delta;
                }
            }
            if (Math.abs(c.zoom-cameraTargetZoom)<cameraMoveSpeed*delta) {
                c.zoom = (c.zoom + cameraTargetZoom) / 2;
            } else {
                if (c.zoom<cameraTargetZoom){
                    c.zoom += cameraMoveSpeed*delta;
                }else{
                    c.zoom -= cameraMoveSpeed*delta;
                }
            }
            c.update();

        }

    }


}
