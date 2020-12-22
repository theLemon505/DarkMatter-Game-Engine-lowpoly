package CoreEngine.RenderingUtils;

import CoreEngine.Components.Transform;
import CoreEngine.DefaultShapes.Maths;
import CoreEngine.Maths.Matrix4f;
import CoreEngine.Models.RawModel;
import CoreEngine.Models.TexturedModel;
import CoreEngine.Objects.Node;
import CoreEngine.Shaders.Shader;
import CoreEngine.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Vector3f;

public class Renderer {
    public void prepare(){

    }
    public void render(Node obj, Shader shader){
        shader.bind();
        RawModel model = obj.getComponent(TexturedModel.class).getRawModel();
        GL30.glBindVertexArray(model.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        shader.setUniform("model", Matrix4f.transform(obj.getComponent(Transform.class).getPosition(), obj.getComponent(Transform.class).getRotation(), obj.getComponent(Transform.class).getScale()));
        shader.setUniform("viewMat", Matrix4f.view(Window.currentScene.camera.getPosition(), Window.currentScene.camera.getRotation()));
        shader.setUniform("projection", Window.getProjectionMatrix());
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, obj.getComponent(TexturedModel.class).getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        shader.unbind();
    }
    public void renderAll( Shader shader){
        for (Node obj:Window.currentScene.getGameObjects()
             ) {
            if(obj.getComponent(TexturedModel.class) != null) {
                shader.bind();
                RawModel model = obj.getComponent(TexturedModel.class).getRawModel();
                GL30.glBindVertexArray(model.getVaoID());
                GL20.glEnableVertexAttribArray(0);
                GL20.glEnableVertexAttribArray(1);
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                shader.setUniform("model", Matrix4f.transform(obj.getComponent(Transform.class).getPosition(), obj.getComponent(Transform.class).getRotation(), obj.getComponent(Transform.class).getScale()));
                shader.setUniform("viewMat", Window.getProjectionMatrix());
                shader.setUniform("projection", Matrix4f.view(Window.currentScene.camera().getPosition(),Window.currentScene.camera().getRotation()));
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, obj.getComponent(TexturedModel.class).getTexture().getID());
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                GL20.glDisableVertexAttribArray(0);
                GL20.glDisableVertexAttribArray(1);
                GL30.glBindVertexArray(0);
                shader.unbind();
            }
            else {
                return;
            }
        }
    }
}
