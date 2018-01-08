package com.jimmt.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class MainScreen implements Screen {
    private OrthographicCamera camera, uiCamera;
    private World world;
    private SpriteBatch batch;
    private SpriteBatch uiBatch;
    private SpinnerSprite spinner;
    private Box2DDebugRenderer renderer;
    private SpinnerInput spinnerInput;
    private ShapeRenderer sr;
    private ScalingViewport viewport, uiViewport;
    private ImageButton effectsButton;

    public MainScreen() {
        viewport = new ScalingViewport(Scaling.fit, 600 * Constants.PIX_TO_BOX,
                                       960 * Constants.PIX_TO_BOX);
        camera = (OrthographicCamera) viewport.getCamera();
        uiViewport = new ScalingViewport(Scaling.fit, 600, 960);
        uiCamera = (OrthographicCamera) uiViewport.getCamera();
        uiCamera.translate(uiViewport.getWorldWidth() / 2,
                           uiViewport.getWorldHeight() / 2);

        batch = new SpriteBatch();
        uiBatch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        Texture spinnerTex = new Texture(Gdx.files.internal("spinner.png"));
        spinnerTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        spinner = new SpinnerSprite(spinnerTex, world);
        spinner.setPosition(camera.viewportWidth / 2f * Constants.PIX_TO_BOX
            - spinner.getWidth() / 2f,
                            camera.viewportHeight / 2f * Constants.PIX_TO_BOX
                                - spinner.getHeight() / 2f);

        renderer = new Box2DDebugRenderer();

        spinnerInput = new SpinnerInput(spinner, camera);
        Gdx.input.setInputProcessor(spinnerInput);

        sr = new ShapeRenderer();

        ImageButtonStyle style = new ImageButtonStyle();
        Image img = new Image(new Texture(Gdx.files.internal("button.png")));
        img.scaleBy(Constants.PIX_TO_BOX);
        style.up = img.getDrawable();
        effectsButton = new ImageButton(style);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        uiBatch.setProjectionMatrix(uiCamera.combined);
        world.step(1 / 60f, 3, 3);
        camera.update();
        uiCamera.update();
        batch.begin();
        spinner.draw(batch);
        batch.end();

        uiBatch.begin();
        float margin = 0f;
        effectsButton.setPosition(uiViewport.getWorldWidth() - 64 - 10, uiViewport.getWorldHeight() - 64-32);
        effectsButton.draw(uiBatch, 1.0f);
        uiBatch.end();

        sr.setProjectionMatrix(uiCamera.combined);
        sr.setColor(Color.RED);
        sr.begin(ShapeType.Line);
        sr.circle(uiViewport.getWorldWidth(), uiViewport.getWorldHeight(), 50);
//        sr.setColor(Color.BLUE);
//        sr.line(0, 0, uiViewport.getScreenWidth(),
//                uiViewport.getScreenHeight());
        sr.end();

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiViewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // renderer.render(world, camera.combined);

        // if (spinnerInput.angle1 != null) {
        // sr.setProjectionMatrix(camera.combined);
        // sr.setColor(Color.RED);
        // sr.begin(ShapeType.Line);
        // sr.line(spinner.getX() + spinner.getOriginX(), spinner.getY() +
        // spinner.getOriginY(), spinnerInput.worldLast.x,
        // spinnerInput.worldLast.y);
        // sr.line(spinner.getX() + spinner.getOriginX(), spinner.getY() +
        // spinner.getOriginY(), spinnerInput.worldCurrent.x,
        // spinnerInput.worldCurrent.y);
        // sr.end();
        // sr.line(spinner.getX() + spinner.getOriginX(), spinner.getY() +
        // spinner.getOriginY(),
        // spinnerInput.lastAngle.x, spinnerInput.lastAngle.y);
        // sr.circle(spinner.getX() + spinner.getOriginX(), spinner.getY() +
        // spinner.getOriginY(), .25f);
        // sr.circle(spinnerInput.point.x, spinnerInput.point.y, .25f);
        // sr.end();
        // }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        uiViewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
