package CoreEngine.Graphics;
import CoreEngine.Maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.opengl.Texture;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class Material {
    private String path;
    private Texture texture;
    private float width, height;
    private int textureID;
    private Vector3f color = new Vector3f(0,0,0);
    public Material(String path) {
        this.path = path;
    }
    public Material(){
        path = "/Resources/Assets/Original.png";
        return;
    }
    public void create() {
        try {
            texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL11.GL_FASTEST);
            width = texture.getWidth();
            height = texture.getHeight();
            textureID = texture.getTextureID();
        } catch (IOException e) {
            System.err.println("Can't find texture at " + path);
        }
    }

    public void setColor(Vector3f color){
        this.color = color;
    }
    public void destroy() {
        GL13.glDeleteTextures(textureID);
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
