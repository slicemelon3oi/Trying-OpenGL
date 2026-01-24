package engine.maths;

import java.util.Objects;

public class Vector2f {
    private float x , y;

    public Vector2f(float x , float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2f add(Vector2f v1 , Vector2f v2) {
        return new Vector2f(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    public static Vector2f subtract(Vector2f v1 , Vector2f v2) {
        return new Vector2f(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }

    public static Vector2f multiply(Vector2f v1 , Vector2f v2) {
        return new Vector2f(v1.getX() * v2.getX(), v1.getY() * v2.getY());
    }

    public static Vector2f divide(Vector2f v1 , Vector2f v2) {
        return new Vector2f(v1.getX() / v2.getX(), v1.getY() / v2.getY());
    }

    public static float length(Vector2f vector) {
        return (float) Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY());
    }

    public static Vector2f normalize(Vector2f vector) {
        float len = Vector2f.length(vector);
        return Vector2f.divide(vector , new Vector2f(len,len));
    }

    public static float dot(Vector2f v1 , Vector2f v2) {
        // -ve means dissimilar direction of each other
        // 0 means perpendicular
        // +ve means similar direction of each other
        return v1.getX() * v2.getX() + v1.getY() * v2.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        return Float.compare(x, vector2f.x) == 0 && Float.compare(y, vector2f.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
