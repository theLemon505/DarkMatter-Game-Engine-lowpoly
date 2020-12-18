package CoreEngine.Objects.Scenes;

import CoreEngine.Components.Camera;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import DarkMatterEditor.EditorCamera;
import imgui.ImGui;
import imgui.ImVec2;

public class EditorSceneInitializer extends SceneInitializer{
    private Node levelEditorStuff;

    public EditorSceneInitializer() {

    }

    @Override
    public void init(Scene scene) {
        levelEditorStuff = scene.createGameObject("LevelEditor");
        levelEditorStuff.setNoSerialize();

        levelEditorStuff.addComponent(new Camera(new Vector3f(0,0,0), new Vector3f(0,0,0)));
        //levelEditorStuff.addComponent(new GizmoSystem(gizmos));
        scene.addGameObjectToScene(levelEditorStuff);
    }

    @Override
    public void loadResources(Scene scene) {

    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor Stuff");
        levelEditorStuff.imgui();
        ImGui.end();

        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        

        ImGui.end();
    }
}
