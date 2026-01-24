package engine.graphics;

import org.lwjgl.opengl.GL46;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
    private Vertex[] vertices;
    private int[] indices;
    private Material material;
    private int vao , pbo, cbo , tbo , ibo;

    public Mesh(Vertex[] vertices , int[] indices , Material material) {
        this.vertices = vertices;
        this.indices = indices;
        this.material = material;
    }

    public void create() {
        material.create();

        vao = GL46.glGenVertexArrays();
        GL46.glBindVertexArray(vao);

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0 ; i < vertices.length ; i++) {
            positionData[(i * 3)    ] = vertices[i].getPosition().getX();
            positionData[(i * 3) + 1] = vertices[i].getPosition().getY();
            positionData[(i * 3) + 2] = vertices[i].getPosition().getZ();
        }
        positionBuffer.put(positionData).flip();
        pbo = storeData(positionBuffer , 0 , 3);

        FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] colorData = new float[vertices.length * 3];
        for (int i = 0 ; i < vertices.length ; i++) {
            colorData[i * 3] = vertices[i].getColor().getX();
            colorData[(i * 3) + 1] = vertices[i].getColor().getY();
            colorData[(i * 3) + 2] = vertices[i].getColor().getZ();
        }
        colorBuffer.put(colorData).flip();
        cbo = storeData(colorBuffer , 1 , 3);

        FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
        float[] textureData = new float[vertices.length * 2];
        for (int i = 0 ; i < vertices.length ; i++) {
            textureData[i * 2] = vertices[i].getTextureCoord().getX();
            textureData[(i * 2) + 1] = vertices[i].getTextureCoord().getY();
        }
        textureBuffer.put(textureData).flip();
        tbo = storeData(textureBuffer , 2 , 2);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();
        ibo = GL46.glGenBuffers();
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER , ibo);
        GL46.glBufferData(GL46.GL_ELEMENT_ARRAY_BUFFER , indicesBuffer , GL46.GL_STATIC_DRAW);
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER , 0);
        GL46.glBindVertexArray(0);
    }

    private int storeData(FloatBuffer buffer , int index , int size) {
        int bufferID = GL46.glGenBuffers(); // Generating VBO attribute pointer
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER , bufferID); //Binding VBO to GL ARRAY BUFFER -> Stores data like XYZ, RGB, etc.
        GL46.glBufferData(GL46.GL_ARRAY_BUFFER , buffer , GL46.GL_STATIC_DRAW); //Giving Data to GL ARRAY BUFFER (to GPU)
        GL46.glVertexAttribPointer(index , size , GL46.GL_FLOAT , false , 0 , 0); //Telling GPU to read data in this order
        GL46.glBindBuffer(GL46.GL_ARRAY_BUFFER , 0); //Unbinding VBO
        return bufferID;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVAO() {
        return vao;
    }

    public int getPBO() {
        return pbo;
    }

    public int getCBO() {
        return cbo;
    }

    public int getTBO() {
        return tbo;
    }

    public int getIBO() {
        return ibo;
    }

     public Material getMaterial() {
        return material;
     }

    public void destroy() {
        GL46.glDeleteBuffers(ibo);
        GL46.glDeleteBuffers(tbo);
        GL46.glDeleteBuffers(cbo);
        GL46.glDeleteBuffers(pbo);
        GL46.glDeleteVertexArrays(vao);
        material.destroy();
    }
}
