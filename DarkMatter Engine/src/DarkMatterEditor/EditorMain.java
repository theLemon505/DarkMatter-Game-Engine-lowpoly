package DarkMatterEditor;

import CoreEngine.Components.MeshComponent;
import CoreEngine.DefaultShapes.Cube;
import CoreEngine.Graphics.Mesh;
import CoreEngine.Graphics.Renderer;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.Window;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.glEnd;


public class EditorMain {
    public static EditorCamera camera = new EditorCamera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));
    public void start(){
    }

    public void update(){
        camera.update();
    }
    public void render(float deltaTime){

    }

    public void end(){
    }
}
