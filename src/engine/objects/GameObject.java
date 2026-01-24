package engine.objects;

import engine.graphics.Mesh;
import engine.maths.Vector3f;

public class GameObject {
    private double temp = 0;
    private Vector3f position , rotation , scale;
    private Mesh mesh;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void update() {
        temp+= 0.02;
        position.setX((float) Math.sin(temp) + -2.0f);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
