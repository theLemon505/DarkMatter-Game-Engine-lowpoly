package CoreEngine.RenderingUtils;

import CoreEngine.Components.Light;
import CoreEngine.Components.Transform;
import CoreEngine.DefaultShapes.Maths;
import CoreEngine.Maths.Matrix4f;
import CoreEngine.Models.RawModel;
import CoreEngine.Components.TexturedModel;
import CoreEngine.Objects.Node;
import CoreEngine.Shaders.Shader;
import CoreEngine.Window;
import org.lwjgl.opengl.*;
import org.lwjglx.util.vector.Vector3f;

import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Renderer {
    private Shader shader;
    public void init(Shader shader){
        this.shader = shader;
        shader.create();
    }
    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glEnable(GL_CULL_FACE);
        GL11.glCullFace(GL_BACK);
        glClearColor(0.5f, 0.5f, 0.5f, 1);
    }


    public void render(Map<TexturedModel, List<Node>> nodes){
        if(nodes.keySet() != null) {
            for (TexturedModel model : nodes.keySet()) {
                if (model != null) {
                    prepareTexturedModel(model);
                    List<Node> batch = nodes.get(model);
                    for (Node node : batch) {
                        prapareNode(node);
                        shader.bind();
                        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
                        shader.unbind();
                        unbindTexturedModel();
                    }
                }
            }
        }
    }
    private void prepareTexturedModel(TexturedModel model){
        if(model != null) {
            if (model.getRawModel() != null) {
                RawModel object = model.getRawModel();
                GL30.glBindVertexArray(object.getVaoID());
                GL20.glEnableVertexAttribArray(0);
                GL20.glEnableVertexAttribArray(1);
                GL20.glEnableVertexAttribArray(2);
                GL20.glEnableVertexAttribArray(3);
                shader.setUniform("shineDamper", model.getMat().getShineDamper());
                shader.setUniform("reflectivity", model.getMat().getReflectivity());
                shader.setUniform("color", model.getMat().getColor());
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMat().getTexture().getID());
            } else {
                return;
            }
        }
        else {
            return;
        }
    }
    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL20.glDisableVertexAttribArray(3);
        GL30.glBindVertexArray(0);

    }
    public void end(){
        shader.destroy();
    }
    private void prapareNode(Node node){
        org.lwjglx.util.vector.Matrix4f matMod = Maths.createTransformationMatrix(new Vector3f(node.getComponent(Transform.class).getPosition().x,node.getComponent(Transform.class).getPosition().y,node.getComponent(Transform.class).getPosition().z),
                node.getComponent(Transform.class).getRotation().x,node.getComponent(Transform.class).getRotation().y,node.getComponent(Transform.class).getRotation().z,node.getComponent(Transform.class).getTotalScale());

        shader.setUniform("model", matMod);
        shader.setUniform("view", Matrix4f.view(Window.currentScene.camera.getPosition(), Window.currentScene.camera.getRotation()));
        shader.setUniform("projection", Window.getProjectionMatrix());
        shader.setUniform("lightPosition", Window.light.getComponent(Transform.class).position);
        shader.setUniform("lightColor", Window.light.getComponent(Light.class).getColor());
        shader.setUniform("intensity", Window.light.getComponent(Light.class).getIntensity());
    }

}