package engine.graphics;

import engine.io.Window;
import engine.maths.Matrix4f;
import engine.objects.Camera;
import engine.objects.GameObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL46;

public class Renderer {
    private Shader shader;
    private Window window;

    public Renderer(Window window, Shader shader) {
        this.shader = shader;
        this.window = window;
    }

    public void renderObject (GameObject object , Camera camera) {
        Mesh mesh = object.getMesh();
        GL46.glBindVertexArray(mesh.getVAO()); //Enable VAO
        GL46.glEnableVertexAttribArray(0); //Enable VBO 0
        GL46.glEnableVertexAttribArray(1); //Enable VBO 1
        GL46.glEnableVertexAttribArray(2); //Enable VBO 2
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER , mesh.getIBO()); //Enable IBO
        GL46.glActiveTexture(GL46.GL_TEXTURE0); //Loading Texture0
        GL46.glBindTexture(GL11.GL_TEXTURE_2D , mesh.getMaterial().getTextureID()); //Binding Texture
        GL46.glTexParameteri(GL11.GL_TEXTURE_2D , GL11.GL_TEXTURE_MIN_FILTER , GL11.GL_NEAREST);
        GL46.glTexParameteri(GL11.GL_TEXTURE_2D , GL11.GL_TEXTURE_MAG_FILTER , GL11.GL_NEAREST);
        GL46.glGenerateMipmap(GL46.GL_TEXTURE_2D);
        shader.bind();
        shader.setUniform("model" , Matrix4f.transform(object.getPosition() , object.getRotation() , object.getScale()));
        shader.setUniform("view" , Matrix4f.view(camera.getPosition() , camera.getRotation()));
        shader.setUniform("projection" , this.window.getProjectionMatrix());

        GL46.glDrawElements(GL46.GL_TRIANGLES , mesh.getIndices().length , GL46.GL_UNSIGNED_INT , 0); //Draws element
        //GL46.glPolygonMode(GL46.GL_FRONT_AND_BACK , GL46.GL_LINE);

        shader.unbind();
        GL46.glBindBuffer(GL46.GL_ELEMENT_ARRAY_BUFFER , 0); //Disable IBO
        GL46.glDisableVertexAttribArray(2); //Disable VBO 2
        GL46.glDisableVertexAttribArray(1); //Disable VBO 1
        GL46.glDisableVertexAttribArray(0); //Disable VBO 0
        GL46.glBindVertexArray(0); //Disable VAO
    }
}
