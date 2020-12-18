package DarkMatterEditor;

import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Scene;


public class EditorMain {
    public static EditorCamera camera = new EditorCamera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));
    private Scene scene = new Scene();
    public void start(){
        scene.init();
    }

    public void update(){
        camera.update();
    }
    public void render(float deltaTime){
        scene.render();
    }

    public void end(){
    }
}
