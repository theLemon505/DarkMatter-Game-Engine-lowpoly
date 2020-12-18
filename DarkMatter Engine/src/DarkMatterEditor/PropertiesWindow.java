package DarkMatterEditor;

import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.Observers.MouseListener;
import imgui.ImGui;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {
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
            } else if (pickedObj == null && !MouseListener.isDragging()) {
                activeGameObject = null;
            }
            this.debounce = 0.2f;
        }
    }

    public void imgui() {
        if (activeGameObject != null) {
            ImGui.begin("Properties");

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (ImGui.menuItem("Add Rigidbody")) {

                }

                if (ImGui.menuItem("Add Box Collider")) {

                }

                if (ImGui.menuItem("Add Circle Collider")) {

                }

                ImGui.endPopup();
            }

            activeGameObject.imgui();
            ImGui.end();
        }
    }

    public Node getActiveGameObject() {
        return this.activeGameObject;
    }

    public void setActiveGameObject(Node go) {
        this.activeGameObject = go;
    }
}
