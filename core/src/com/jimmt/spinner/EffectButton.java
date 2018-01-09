package com.jimmt.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EffectButton extends Button {
    ParticleEffect effect;
    Label label;
    String name;

    public EffectButton(final String effectPath, final TripleEffect triEffect) {
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal(effectPath), Gdx.files.internal(""));
        effect.scaleEffect(Constants.PIX_TO_BOX);
        effect.start();
        name = effectPath.replace(".p", "");

        LabelStyle style = new LabelStyle();
        style.font = new BitmapFont(Gdx.files.internal("gotham_medium.fnt"));
        label = new Label(name, style);
        setWidth(label.getPrefWidth());

        setSize(100, 100);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("click");
                triEffect.loadEffects(effectPath);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        effect.setPosition(100, 100);
        label.setPosition(getX() + getWidth() / 2 - label.getPrefWidth() / 2,
                          getY() + getHeight() / 2 - label.getPrefHeight() / 2);
        effect.draw(batch);
        label.draw(batch, parentAlpha);
    }

    @Override
    public float getPrefWidth() {
        return 100;
    }

    @Override
    public float getPrefHeight() {
        return 100;
    }
}
