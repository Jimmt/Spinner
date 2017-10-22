package com.jimmt.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class MouseTracker {
	private MouseJoint rj;
	private World world;
	private Body body;
	private OrthographicCamera camera;
	private int currentCircle = 0;

	public MouseTracker(World world, OrthographicCamera camera) {
		this.camera = camera;
		this.world = world;
		initBody();		body.setTransform(new Vector2(2, 2), 0);
	}

	private void initBody() {
		BodyDef bd = new BodyDef();
		bd.fixedRotation = false;
		bd.type = BodyType.DynamicBody;
		bd.position.set(getMouseWorldPos());
		body = world.createBody(bd);
		FixtureDef fd = new FixtureDef();
		fd.density = 100.0f;
		CircleShape circle = new CircleShape();
		circle.setRadius(50f * Constants.PIX_TO_BOX);
		fd.shape = circle;
		body.createFixture(fd);
	}

	private void initJoint(Body otherBody) {
		rj = new MouseJoint(world, 3);
		MouseJointDef jd = new MouseJointDef();
		jd.bodyA = body;
		jd.bodyB = otherBody;
		jd.target.set(body.getPosition());
		jd.collideConnected = false;
		jd.maxForce = 100f;
		rj = (MouseJoint) world.createJoint(jd);
	}

	private Vector2 getMouseWorldPos() {
		Vector3 point = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		point = camera.unproject(point);
		return new Vector2(point.x, point.y);
	}

	public void update(SpinnerSprite spinner) {
		body.setTransform(getMouseWorldPos(), 0);
		if(rj == null){
			initJoint(spinner.getCircles().get(0));
		} else {
			rj.setTarget(getMouseWorldPos());
		}
//		float minDist = Float.MAX_VALUE;
//
//		int index = 0;
//		for (int i = 0; i < spinner.getCircles().size(); i++) {
//			Vector2 dist = getMouseWorldPos().sub(spinner.getCircles().get(i).getPosition());
//			if (minDist > dist.len()) {
//				minDist = dist.len();
//				index = i;
//			}
//		}
//		if (index != currentCircle) {
//			if (rj != null)
//				world.destroyJoint(rj);
//			initJoint(spinner.getCircles().get(index));
//		}

	}
}
