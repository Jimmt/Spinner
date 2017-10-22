package com.jimmt.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class SpinnerInput implements InputProcessor {
    private OrthographicCamera camera;
    private SpinnerSprite spinner;
    public float lastX, lastY;
    public boolean ignore = false;
    public Vector3 worldLast = Vector3.Zero, worldCurrent = Vector3.Zero;

    public SpinnerInput(SpinnerSprite spinner, OrthographicCamera camera) {
        this.spinner = spinner;
        this.camera = camera;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!ignore) {
            float dy = screenY - lastY;
            float dx = screenX - lastX;
            float spinnerCenterX = spinner.getX() + spinner.getWidth() / 2;
            float spinnerCenterY = spinner.getY() + spinner.getHeight() / 2;
            worldLast = camera.unproject(new Vector3(lastX, lastY, 0));
            worldCurrent = camera.unproject(new Vector3(screenX, screenY, 0));
            float startAngle = (float) Math.atan2(worldLast.y - spinnerCenterY,
                                                  worldLast.x - spinnerCenterX);
            float endAngle = (float) Math.atan2(worldCurrent.y
                - spinnerCenterY, worldCurrent.x - spinnerCenterX);
            float angle = (float) Math.toDegrees(endAngle - startAngle);
            if (Math.abs(angle) < 275)
                spinner.spin(angle);
        }
        lastY = screenY;
        lastX = screenX;
        ignore = false;
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer,
        int button) {
//        ignore = false;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        ignore = true;
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
