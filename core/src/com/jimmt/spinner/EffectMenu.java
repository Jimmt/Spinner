package com.jimmt.spinner;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class EffectMenu extends Group {
    Image bg;
    Array<Image> effects;
    Table table;

    public EffectMenu(float w, float h, final SpinnerSprite spinner) {
        bg = new Image(new Texture(Gdx.files.internal("menubg.png")));
        bg.setSize(w, h);
        this.setSize(w, h);

        effects = new Array<Image>();

        FileHandle dirHandle;
        if (Gdx.app.getType() == ApplicationType.Android) {
            dirHandle = Gdx.files.internal("");
        } else {
            dirHandle = Gdx.files.internal("./bin/");
        }
        table = new Table();
        table.setFillParent(true);
        // table.setPosition(300, 480);

        addActor(bg);
        addActor(table);

        TextButtonStyle textStyle = new TextButtonStyle();
        textStyle.font = new BitmapFont(Gdx.files.internal("gotham_medium.fnt"));
        TextButton button = new TextButton("none", textStyle);

        for (final FileHandle entry: dirHandle.list("p")) {
            if (entry.nameWithoutExtension().equals("none")) {
                continue;
            }
            button = new TextButton(entry.name(), textStyle);
            table.add(button).center();
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    spinner.getTripleEffect().loadEffects(entry.name());
                }

            });
        }
    }
}
