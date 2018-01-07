package com.jimmt.spinner;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class SpinnerSprite extends Sprite {
	private ArrayList<Body> circles;
    private TripleEffect effect;
	private Body anchor;

	private float scale = 1 / 2f, initialRotOffset;

	public SpinnerSprite(Texture tex, World world) {
		super(tex);
		circles = new ArrayList<Body>();
		initAnchor(world);
		float mag = 355f / 2f * Constants.PIX_TO_BOX;
		circles.add(initCircle(world, new Vector2(0, mag)));
		circles.add(initCircle(world, new Vector2(300, -171).scl(Constants.PIX_TO_BOX * scale)));
		circles.add(initCircle(world, new Vector2(-300, -171).scl(Constants.PIX_TO_BOX * scale)));
		for (int i = 0; i < circles.size(); i++) {
			anchor(circles.get(i), world);
		}
		connectCircles(0, 1, world);
		connectCircles(0, 2, world);
		connectCircles(1, 2, world);

		setSize(getWidth() * Constants.PIX_TO_BOX * scale, getHeight() * Constants.PIX_TO_BOX * scale);
		setOrigin(getWidth() / 2f, getHeight() / 2f);
		initialRotOffset = anchor.getPosition().sub(circles.get(0).getPosition()).angle();
        
        effect = new TripleEffect(circles, anchor);
	}

	public void spin(float angle) {
//		float magnitude = 0;
//		if (Math.abs(magX) > Math.abs(magY)) {
//			magnitude = magX;
//		} else {
//			magnitude = magY;
//		}
//		System.out.println(magX + " " + magY + " " + magnitude);

	    circles.get(0).setAngularVelocity(angle * 2);
	}

	private void initAnchor(World world) {
		BodyDef anchorBD = new BodyDef();
		anchorBD.type = BodyType.StaticBody;
		anchor = world.createBody(anchorBD);
		FixtureDef anchorFD = new FixtureDef();
		anchorFD.filter.groupIndex = -1;
		CircleShape circle = new CircleShape();
		circle.setRadius(150f * scale * Constants.PIX_TO_BOX);
		anchorFD.shape = circle;
		anchor.createFixture(anchorFD);
	}

	private Body initCircle(World world, Vector2 position) {
		BodyDef bd = new BodyDef();
		bd.fixedRotation = false;
		bd.type = BodyType.DynamicBody;
		bd.angularDamping = 0.2f;
		bd.position.set(position);
		Body body = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.density = 0.1f;
		// fd.filter.groupIndex = -1;
		CircleShape circle = new CircleShape();
		circle.setRadius(200f * scale * Constants.PIX_TO_BOX);
		fd.shape = circle;
		body.createFixture(fd);
		return body;
	}

	private void anchor(Body body1, World world) {
		RevoluteJoint rj = new RevoluteJoint(world, 0);
		RevoluteJointDef jd = new RevoluteJointDef();
		jd.initialize(body1, anchor, Vector2.Zero);
		rj = (RevoluteJoint) world.createJoint(jd);
	}

	private void connectCircles(int body1, int body2, World world) {
		DistanceJoint dj = new DistanceJoint(world, 1);
		DistanceJointDef jd = new DistanceJointDef();
		jd.initialize(circles.get(body1), circles.get(body2), circles.get(body1).getWorldCenter(),
				circles.get(body2).getWorldCenter());
		dj = (DistanceJoint) world.createJoint(jd);
	}

	public ArrayList<Body> getCircles() {
		return circles;
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);
		Vector2 v2 = circles.get(0).getPosition().sub(anchor.getPosition());
		this.setRotation(initialRotOffset + v2.angle());
		effect.draw(batch);
		// this.setPosition(body.getPosition().x - getWidth() *
		// Constants.PIX_TO_BOX / 2f,
		// body.getPosition().y - getHeight() * Constants.PIX_TO_BOX / 2f);

	}
}
