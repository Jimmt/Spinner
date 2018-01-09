package com.jimmt.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Test extends Group {
    Image bg;
    public Test(float w, float h, SpinnerSprite spinner){
        bg = new Image(new Texture(Gdx.files.internal("menubg.png")));
        bg.setSize(w, h);
        
        TextButtonStyle textStyle = new TextButtonStyle();
        textStyle.font = new BitmapFont(Gdx.files.internal("gotham_medium.fnt"));
        TextButton button = new TextButton("none", textStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("click");
                // triEffect.loadEffects(effectPath);
            }

        });
        
        addActor(bg);
        addActor(button);
    }
}


