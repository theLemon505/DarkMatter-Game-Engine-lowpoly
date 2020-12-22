package CoreEngine.RenderingUtils;

import CoreEngine.Components.Camera;
import CoreEngine.Components.Transform;
import CoreEngine.DefaultShapes.Maths;
import CoreEngine.Models.RawModel;
import CoreEngine.Models.TexturedModel;
import CoreEngine.Objects.Node;
import CoreEngine.Shaders.Shader;
import CoreEngine.Shaders.StaticShader;
import CoreEngine.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;

import java.util.List;

public class Renderer {
    private Matrix4f projectionMatrix;
    private StaticShader shader;
    public void prepare(StaticShader shader) {
        this.shader = shader;
        createProjectionMatrix();
        shader.loadProjectionMatrix(projectionMatrix);
    }

    public void render(Node obj) {
        TexturedModel model = obj.getComponent(TexturedModel.class);
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(obj.getComponent(Transform.class).position.x, obj.getComponent(Transform.class).position.y, obj.getComponent(Transform.class).position.z),
                obj.getComponent(Transform.class).rotation.x, obj.getComponent(Transform.class).rotation.y, obj.getComponent(Transform.class).rotation.z, obj.getComponent(Transform.class).getTotalScale());
        shader.loadTransformationMatrix(transformationMatrix);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getMat().getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
    private void createProjectionMatrix(){
        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(Window.FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = Window.FAR_PLANE - Window.NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((Window.FAR_PLANE + Window.NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * Window.NEAR_PLANE * Window.FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
    public void renderAll(List<Node> nodes,Shader shader) {
        for (Node obj : nodes
        ) {
            if (obj.getComponent(TexturedModel.class) != null) {
                render(obj);
            }
        }
    }
}