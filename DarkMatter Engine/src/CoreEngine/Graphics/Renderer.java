package CoreEngine.Graphics;

import CoreEngine.Components.Camera;
import CoreEngine.Maths.Matrix4f;
import CoreEngine.Objects.Node;
import DarkMatterEditor.EditorCamera;
import CoreEngine.Components.MeshComponent;
import CoreEngine.Shaders.Shader;
import org.lwjgl.opengl.*;
import CoreEngine.*;

import java.util.ArrayList;
import java.util.List;

import static DarkMatterEditor.EditorMain.camera;
import static org.lwjgl.opengl.GL11.*;

public class Renderer {
    private Shader shader;
    private Window window;
    public List<Node> nodes = new ArrayList<>();
    public Renderer(Shader shader) {
        this.shader = shader;
        this.window = Window.get();
    }
    public void renderMesh(MeshComponent object, EditorCamera camera) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL13.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    public void renderMesh(MeshComponent object, Camera camera) {
        GL30.glBindVertexArray(object.getMesh().getVAO());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL13.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getMesh().getIBO());
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getMesh().getMaterial().getTextureID());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(object.getPosition(), object.getRotation(), object.getScale()));
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
        GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
    public void renderMesh() {
        for (Node n: nodes
             ) {
            if (n.getComponent(MeshComponent.class) != null) {
                Node object = n;
                GL30.glBindVertexArray(object.getComponent(MeshComponent.class).getMesh().getVAO());
                GL30.glEnableVertexAttribArray(0);
                GL30.glEnableVertexAttribArray(1);
                GL30.glEnableVertexAttribArray(2);
                GL13.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
                GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, object.getComponent(MeshComponent.class).getMesh().getIBO());
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL13.glBindTexture(GL11.GL_TEXTURE_2D, object.getComponent(MeshComponent.class).getMesh().getMaterial().getTextureID());
                shader.bind();
                shader.setUniform("model", Matrix4f.transform(object.transform.getPosition(), object.transform.getRotation(), object.transform.getScale()));
                shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
                shader.setUniform("projection", window.getProjectionMatrix());
                GL11.glDrawElements(GL11.GL_TRIANGLES, object.getComponent(MeshComponent.class).getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
                shader.unbind();
                GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
                GL30.glDisableVertexAttribArray(0);
                GL30.glDisableVertexAttribArray(1);
                GL30.glDisableVertexAttribArray(2);
                GL30.glBindVertexArray(0);
            }

        }
    }
}
