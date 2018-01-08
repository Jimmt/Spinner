package com.jimmt.spinner;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public class TripleEffect {
    boolean enabled = true;
    float x, y, offsetX, offsetY;
    ParticleEffect[] effects = new ParticleEffect[3];
    ArrayList<ArrayList<RotationData>> rotData;

    ArrayList<Body> circles;
    Body anchor;

    class RotationData {
        float loMin, loMax;
        float hiMin, hiMax;

        public RotationData(float loMin, float loMax, float hiMin,
            float hiMax) {
            this.loMin = loMin;
            this.loMax = loMax;
            this.hiMin = hiMin;
            this.hiMax = hiMax;
        }
    }
    
    public void loadEffects(String path){
        for (int i = 0; i < 3; i++) {
            effects[i] = new ParticleEffect();
            effects[i].load(Gdx.files.internal(path),
                            Gdx.files.internal(""));
            effects[i].scaleEffect(Constants.PIX_TO_BOX);
            effects[i].start();

            ArrayList<RotationData> list = new ArrayList<RotationData>();
            rotData.add(list);

            for (ParticleEmitter emitter: effects[i].getEmitters()) {
                float hiMax = emitter.getAngle().getHighMax();
                float loMax = emitter.getAngle().getLowMax();
                float hiMin = emitter.getAngle().getHighMin();
                float loMin = emitter.getAngle().getLowMin();
                list.add(new RotationData(loMin, loMax, hiMin, hiMax));
            }
        }
    }

    public TripleEffect(ArrayList<Body> circles, Body anchor) {
        rotData = new ArrayList<ArrayList<RotationData>>();

        loadEffects("empty.p");

        this.circles = circles;
        this.anchor = anchor;
    }

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }
    
    public float calcAngle(int circleIndex){
        Body circle = circles.get(circleIndex);
        return circle.getPosition().sub(anchor.getPosition()).angle() + 90;
    }

    public void draw(Batch batch) {
        if (!enabled) {
            return;
        }
        for (int i = 0; i < 3; i++) {
            ParticleEffect pe = effects[i];
            Array<ParticleEmitter> emitters = effects[i].getEmitters();
            int index = 0;
            for (ParticleEmitter emitter: emitters) {
                float hiMax = rotData.get(i).get(index).hiMax + calcAngle(i);
                float loMax = rotData.get(i).get(index).loMax + calcAngle(i);
                float hiMin = rotData.get(i).get(index).hiMin + calcAngle(i);
                float loMin = rotData.get(i).get(index).loMin + calcAngle(i);
                emitter.getAngle().setHigh(hiMin, hiMax);
                emitter.getAngle().setLow(loMin, loMax);
                index++;
            }
            pe.setPosition(circles.get(i).getPosition().x,
                           circles.get(i).getPosition().y);
            pe.draw(batch, Gdx.graphics.getDeltaTime());
        }
    }
}
