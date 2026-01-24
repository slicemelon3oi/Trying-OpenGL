package engine.maths;

import java.util.Objects;

public class Vector3f {
    private float x , y , z;

    public Vector3f(float x , float y , float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(float x , float y , float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public static Vector3f add(Vector3f v1 , Vector3f v2) {
        return new Vector3f(v1.getX() + v2.getX(), v1.getY() + v2.getY() , v1.getZ() + v2.getZ());
    }

    public static Vector3f subtract(Vector3f v1 , Vector3f v2) {
        return new Vector3f(v1.getX() - v2.getX(), v1.getY() - v2.getY() , v1.getZ() - v2.getZ());
    }

    public static Vector3f multiply(Vector3f v1 , Vector3f v2) {
        return new Vector3f(v1.getX() * v2.getX(), v1.getY() * v2.getY() , v1.getZ() * v2.getZ());
    }

    public static Vector3f divide(Vector3f v1 , Vector3f v2) {
        return new Vector3f(v1.getX() / v2.getX(), v1.getY() / v2.getY() , v1.getZ() / v2.getZ());
    }

    public static float length(Vector3f vector) {
        return (float) Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY() + vector.getZ()*vector.getZ());
    }

    public static Vector3f normalize(Vector3f vector) {
        float len = Vector3f.length(vector);
        return Vector3f.divide(vector , new Vector3f(len,len,len));
    }

    public static float dot(Vector3f v1 , Vector3f v2) {
        // -ve means dissimilar direction of each other
        // 0 means perpendicular
        // +ve means similar direction of each other
        return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ() * v2.getZ();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        return Float.compare(x, vector3f.x) == 0 && Float.compare(y, vector3f.y) == 0 && Float.compare(z, vector3f.z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public float getZ() {
        return z;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void set(float x , float y , float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
