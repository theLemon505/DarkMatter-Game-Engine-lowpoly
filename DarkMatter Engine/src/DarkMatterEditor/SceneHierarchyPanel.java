package DarkMatterEditor;

import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.Observers.MouseListener;
import CoreEngine.Window;
import DarkMatterEditor.Gui.ImGuiLayer;
import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class SceneHierarchyPanel {
    private Node activeGameObject = null;
    private float debounce = 0.2f;
    public void update(float dt, Scene currentScene) {
        debounce -= dt;

        if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
            int x = (int)MouseListener.getScreenX();
            int y = (int)MouseListener.getScreenY();
            int gameObjectId = 0;
            Node pickedObj = currentScene.getGameObject(gameObjectId);
            if (pickedObj != null) {
                activeGameObject = pickedObj;
                onClickSelect(activeGameObject);
            } else if (pickedObj == null && !MouseListener.isDragging()) {
                activeGameObject = null;
            }
            this.debounce = 0.2f;
        }
    }

    public void imgui(){
        ImGui.begin("Hierarchy");
        for (Node node: Window.currentScene.getGameObjects()
             ) {
            if(node.name != "levelEditorStuff") {
                ImGui.treeNodeEx(node.name, ImGuiTreeNodeFlags.NoTreePushOnOpen);
                if (ImGui.isItemClicked()) {
                    ImGuiLayer.propertiesWindow.setActiveGameObject(node);
                }
            }
        }
        if (ImGui.beginPopupContextWindow("ComponentAdder")) {
            if (ImGui.menuItem("add Node")) {
                Node n = Window.currentScene.createGameObject("Node");
                Window.currentScene.addGameObjectToScene(n);
            }
            ImGui.endPopup();
        }
        ImGui.end();
    }
    public void onClickSelect(Node node){
        node.imgui();
    }
}
