package CoreEngine.Objects.Scenes;

import CoreEngine.Components.Camera;
import CoreEngine.Components.Component;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.Window;
import DarkMatterEditor.EditorCamera;
import imgui.ImGui;
import imgui.ImVec2;

public class EditorSceneInitializer extends SceneInitializer{
    public static Node levelEditorStuff;
    public EditorSceneInitializer() {

    }

    @Override
    public void init(Scene scene) {
        Camera cam = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,0));
        levelEditorStuff = scene.createGameObject("LevelEditor");
        levelEditorStuff.setNoSerialize();

        levelEditorStuff.addComponent(new EditorCamera(cam));
        //levelEditorStuff.addComponent(new GizmoSystem(gizmos));
        scene.addGameObjectToScene(levelEditorStuff);
        scene.camera = cam;
        Window.get().loop();
    }
    @Override
    public void loadResources(Scene scene) {

    }

    @Override
    public void imgui() {
        levelEditorStuff.getComponent(EditorCamera.class).update(1);
        ImGui.begin("Level Editor Stuff");
        levelEditorStuff.imgui();

        ImGui.end();

    }
}
