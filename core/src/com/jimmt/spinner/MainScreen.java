package com.jimmt.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

public class MainScreen implements Screen {
    private OrthographicCamera camera;
    private Stage uiStage;
    private World world;
    private SpriteBatch batch;
    private SpinnerSprite spinner;
    private Box2DDebugRenderer renderer;
    private SpinnerInput spinnerInput;
    private ShapeRenderer sr;
    private ScalingViewport viewport, uiViewport;
    private ImageButton effectsButton;
    private InputMultiplexer multiplexer;
    private EffectMenu menu;

    public MainScreen() {
        viewport = new ScalingViewport(Scaling.fit, 600 * Constants.PIX_TO_BOX,
                                       960 * Constants.PIX_TO_BOX);
        uiViewport = new ScalingViewport(Scaling.fit, 600, 960);
        camera = (OrthographicCamera) viewport.getCamera();

        uiStage = new Stage(uiViewport);
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

        sr = new ShapeRenderer();

        setupMenu();

        ImageButtonStyle style = new ImageButtonStyle();
        Image img = new Image(new Texture(Gdx.files.internal("button.png")));
        img.scaleBy(Constants.PIX_TO_BOX);
        style.up = img.getDrawable();
        effectsButton = new ImageButton(style);
        uiStage.addActor(effectsButton);
        effectsButton.setPosition(uiStage.getWidth() - 64 - 10,
                                  uiStage.getHeight() - 64 - 32);
        addListeners();

        multiplexer = new InputMultiplexer(uiStage, spinnerInput);
        Gdx.input.setInputProcessor(multiplexer);

        // uiStage.addActor(new Test(uiViewport.getWorldWidth(),
        // uiViewport.getWorldHeight(), spinner));
    }

    private void setupMenu() {
        menu = new EffectMenu(uiViewport.getWorldWidth(),
                              uiViewport.getWorldHeight(), spinner);
        menu.setVisible(false);
        uiStage.addActor(menu);
    }

    private void addListeners() {
        effectsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.setVisible(!menu.isVisible());
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        uiStage.act(delta);
        world.step(1 / 60f, 3, 3);
        camera.update();
        batch.begin();
        spinner.draw(batch);
        batch.end();
        uiStage.draw();

        // uiBatch.begin();
        // float margin = 0f;
        // effectsButton.setPosition(uiViewport.getWorldWidth() - 64 - 10,
        // uiViewport.getWorldHeight() - 64 - 32);
        // effectsButton.draw(uiBatch, 1.0f);
        // uiBatch.end();

        // sr.setProjectionMatrix(uiStage.getCamera().combined);
        // sr.setColor(Color.RED);
        // sr.begin(ShapeType.Line);
        // menu.setPosition(200, 200);
        // sr.circle(menu.bg.getX(), menu.bg.getY(), 100);
        // sr.circle(menu.getX(), menu.getY(), 100);
        // sr.end();
        // sr.circle(uiViewport.getWorldWidth(), uiViewport.getWorldHeight(),
        // 50);
        // sr.end();
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
