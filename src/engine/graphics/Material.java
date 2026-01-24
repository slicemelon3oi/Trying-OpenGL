package engine.graphics;

import org.lwjgl.opengl.GL46;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;
import java.util.Objects;

public class Material {
    private String path;
    private Texture texture;
    private float width , height;
    private int textureID;

    public Material(String path) {
        this.path = path;
    }

    public void create() {
        try {
            texture = TextureLoader.getTexture(path.split("[.]")[1] , Objects.requireNonNull(Material.class.getResourceAsStream(path)) , GL46.GL_LINEAR);
        } catch (IOException e) {
            System.err.println("Can't find Texture at " + path);
        }
        width = texture.getWidth();
        height = texture.getHeight();
        textureID = texture.getTextureID();
    }

    public Texture getTexture() {
        return texture;
    }

    public void destroy() {
        GL46.glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
