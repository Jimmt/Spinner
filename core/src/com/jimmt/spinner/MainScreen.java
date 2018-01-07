package com.jimmt.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class MainScreen implements Screen {
    private OrthographicCamera camera;
    private World world;
    private SpriteBatch batch;
    private SpinnerSprite spinner;
    private Box2DDebugRenderer renderer;
    private SpinnerInput spinnerInput;
    private ShapeRenderer sr;
    private ScalingViewport viewport;

    public MainScreen() {
        viewport = new ScalingViewport(Scaling.fit, 600 * Constants.PIX_TO_BOX,
                                       960 * Constants.PIX_TO_BOX);
        camera = (OrthographicCamera) viewport.getCamera();

        batch = new SpriteBatch();
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
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        world.step(1 / 60f, 3, 3);
        camera.update();
        batch.begin();
        spinner.draw(batch);
        batch.end();

        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        renderer.render(world, camera.combined);

        // if (spinnerInput.angle1 != null) {
//         sr.setProjectionMatrix(camera.combined);
//         sr.setColor(Color.RED);
//         sr.begin(ShapeType.Line);
//         sr.line(spinner.getX() + spinner.getOriginX(), spinner.getY() +
//         spinner.getOriginY(), spinnerInput.worldLast.x, spinnerInput.worldLast.y);
//         sr.line(spinner.getX() + spinner.getOriginX(), spinner.getY() +
//                 spinner.getOriginY(), spinnerInput.worldCurrent.x, spinnerInput.worldCurrent.y);
//         sr.end();
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
