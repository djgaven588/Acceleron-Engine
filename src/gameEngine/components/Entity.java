package gameEngine.components;

import org.joml.*;

import game.models.TexturedMesh;

public class Entity extends GameObject {

	public Entity(Vector3d pos, Vector3d rot, Vector3d sca) {
		super(pos, rot, sca);
		transform = new Transform(pos, rot, sca);
	}
}
