package gameEngine.common;

import org.joml.*;
import org.joml.Math;

import gameEngine.components.rendering.Camera;
import gameEngine.debug.Debug;

public class Maths {
	public static Matrix4f createTransformationMatrix(Vector3d translation, Vector3d rotation, Vector3d scale) {
		
		return new Matrix4f().
				translate((float)translation.x, (float)translation.y, (float)translation.z).
				rotate(fromEulerAngleFloat(rotation)).
				scale((float)scale.x, (float)scale.y, (float)scale.z);
	}
	
	public static Matrix4f createTransformationMatrixGui(Vector3d translation, Vector3d rotation, Vector2d size, Double width, Double height) {
		return new Matrix4f().
				translate((float)((translation.x * 2 - width)/ width), (float)((-translation.y * 2 + height) / height), (float)translation.z).
				rotate(fromEulerAngleFloat(rotation)).
				scale((float)(size.x / width), (float)(size.y / height), 1);
	}
	
	public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f matrix = new Matrix4f();
        
        Vector3d rot = camera.gameObject.transform.rotation;
        matrix.rotateX((float)Math.toRadians(rot.x));
        matrix.rotateY((float)Math.toRadians(rot.y));
        matrix.rotateZ((float)Math.toRadians(rot.z));
        Vector3d cameraPos = camera.gameObject.transform.position;
        Vector3f negativeCameraPos = new Vector3f((float)(-cameraPos.x),(float)(-cameraPos.y),(float)(-cameraPos.z));
        matrix.translate(negativeCameraPos);
        return matrix;
    }
	
	public static Quaterniond fromEulerAngle(Vector3d rot) {
		Quaterniond value = new Quaterniond();
		value.rotateY(-Math.toRadians(rot.y));
		value.rotateX(-Math.toRadians(rot.x));
		value.rotateZ(-Math.toRadians(rot.z));
		return value;
	}
	
	public static Quaternionf fromEulerAngleFloat(Vector3d rot) {
		Quaternionf value = new Quaternionf();
		value.rotateY((float) -rot.y * 3.14f / 180);
		value.rotateX((float) -rot.x * 3.14f / 180);
		value.rotateZ((float) -rot.z * 3.14f / 180);
		return value;
		//return new Quaternionf().rotateXYZ((float) -rot.y * 3.14f / 180, (float) -rot.x * 3.14f / 180, (float) -rot.z * 3.14f / 180);
	}
}
