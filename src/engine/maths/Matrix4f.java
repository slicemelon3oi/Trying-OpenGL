package engine.maths;

public class Matrix4f {
    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    // [i,j] Matrix 4x4
    // [0,0] [0,1] [0,2] [0,3]
    // [1,0] [1,1] [1,2] [1,3]
    // [2,0] [2,1] [2,2] [2,3]
    // [3,0] [3,1] [3,2] [3,3]
    // 4x4 --> SIZE = 4

    public static Matrix4f identity() {
        // 1 0 0 0
        // 0 1 0 0
        // 0 0 1 0
        // 0 0 0 1

        Matrix4f result = new Matrix4f();
        for (int i = 0 ; i < SIZE ; i++) {
            for (int j = 0 ; j < SIZE ; j++) {
                result.set(i , j , 0);
            }
        }

        result.set(0,0,1);
        result.set(1,1,1);
        result.set(2,2,1);
        result.set(3,3,1);
        return result;
    }

    public static Matrix4f translate(Vector3f translate) {
        // 1 0 0 x
        // 0 1 0 y
        // 0 0 1 z
        // 0 0 0 1

        Matrix4f result = Matrix4f.identity();
        result.set(0,3,translate.getX());
        result.set(1,3,translate.getY());
        result.set(2,3,translate.getZ());
        return result;
    }

    public static Matrix4f rotate(float angle , Vector3f axis) {
        // (x*x)(1-cos(angle))+cos(angle) (x*y)(1-cos(angle))-z*sin(angle) (x*z)(1-cos(angle))+y*sin(angle) X
        // (x*y)(1-cos(angle))+z*sin(angle) (y*y)(1-cos(angle))+cos(angle) (y*z)(1-cos(angle))-x*sin(angle) Y
        // (x*z)(1-cos(angle))-y*sin(angle) (y*z)(1-cos(angle))+x*sin(angle) (z*z)(1-cos(angle))+cos(angle) Z
        //                0                                 0                               0               1

        float x = axis.getX();
        float y = axis.getY();
        float z = axis.getZ();
        float sin = (float) Math.sin(Math.toRadians(angle));
        float cos = (float) Math.cos(Math.toRadians(angle));
        float c = (1 - cos);

        Matrix4f result = Matrix4f.identity();
        result.set(0,0,(((x * x) * c) + cos));
        result.set(0,1,(((x * y) * c) - (z * sin)));
        result.set(0,2,(((x * z) * c) + (y * sin)));
        result.set(1,0,(((x * y) * c) + (z * sin)));
        result.set(1,1,(((y * y) * c) + cos));
        result.set(1,2,(((y * z) * c) - (x * sin)));
        result.set(2,0,(((x * z) * c) - (y * sin)));
        result.set(2,1,(((y * z) * c) + (x * sin)));
        result.set(2,2,(((z * z) * c) + cos));

        return result;
    }

    public static Matrix4f scale(Vector3f scaler) {
        // x 0 0 0
        // 0 y 0 0
        // 0 0 z 0
        // 0 0 0 1

        Matrix4f result = Matrix4f.identity();
        result.set(0,0,scaler.getX());
        result.set(1,1,scaler.getY());
        result.set(2,2,scaler.getZ());
        return result;
    }

    public static Matrix4f multiply(Matrix4f matrix0 , Matrix4f matrix1) {
        Matrix4f result = Matrix4f.identity();

        for (int i = 0 ; i < SIZE ; i++) {
            for (int j = 0 ; j < SIZE ; j++) {
                result.set(i , j ,
                        matrix0.get(i,0) * matrix1.get(0,j) +
                        matrix0.get(i,1) * matrix1.get(1,j) +
                        matrix0.get(i,2) * matrix1.get(2,j) +
                        matrix0.get(i,3) * matrix1.get(3,j));
            }
        }

        return result;
    }

    public static Matrix4f transform(Vector3f position , Vector3f rotation , Vector3f scale) {
        Matrix4f result;

        Matrix4f translationMatrix = Matrix4f.translate(position);
        Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(),new Vector3f(1.0f , 0.0f , 0.0f));
        Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(),new Vector3f(0.0f , 1.0f , 0.0f));
        Matrix4f rotZMatrix = Matrix4f.rotate(rotation.getZ(),new Vector3f(0.0f , 0.0f , 1.0f));
        Matrix4f scaleMatrix = Matrix4f.scale(scale);

        Matrix4f rotationMatrix = Matrix4f.multiply(rotXMatrix , Matrix4f.multiply(rotYMatrix , rotZMatrix));

        result = Matrix4f.multiply(rotationMatrix , Matrix4f.multiply(scaleMatrix , translationMatrix));

        return result;
    }

    public static Matrix4f projection(float fov, float aspect , float near , float far) {
        Matrix4f result = Matrix4f.identity();

        float tanFOV = (float) Math.tan(Math.toRadians(fov/2));
        float range = (far - near);
        result.set(0,0, 1.0f/(aspect*tanFOV));
        result.set(1,1, 1.0f/tanFOV);
        result.set(2,2, -((far+near)/range));
        result.set(3,2, -1.0f);
        result.set(2,3, -((2*far*near)/range));
        result.set(3,3, 0.0f);

        return result;
    }

    public static Matrix4f view(Vector3f position , Vector3f rotation) {
        Matrix4f result = Matrix4f.identity();

        Vector3f negative = new Vector3f(-position.getX() , -position.getY() , -position.getZ());
        Matrix4f translationMatrix = Matrix4f.translate(negative);
        Matrix4f rotXMatrix = Matrix4f.rotate(rotation.getX(),new Vector3f(1.0f , 0.0f , 0.0f));
        Matrix4f rotYMatrix = Matrix4f.rotate(rotation.getY(),new Vector3f(0.0f , 1.0f , 0.0f));
        Matrix4f rotZMatrix = Matrix4f.rotate(rotation.getZ(),new Vector3f(0.0f , 0.0f , 1.0f));

        Matrix4f rotationMatrix = Matrix4f.multiply(rotXMatrix , Matrix4f.multiply(rotYMatrix , rotZMatrix));

        result = Matrix4f.multiply(rotationMatrix , translationMatrix);

        return result;
    }

    public float get(int j , int i) {
        return elements[j * SIZE + i];
    }

    public void set(int j , int i , float value) {
        elements[j * SIZE + i] = value;
    }

    public float[] getAll() {
        return elements;
    }
}
