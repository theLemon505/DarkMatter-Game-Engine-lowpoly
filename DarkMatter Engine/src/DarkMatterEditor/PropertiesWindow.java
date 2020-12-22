package DarkMatterEditor;

import CoreEngine.Models.TexturedModel;
import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.Observers.MouseListener;
import CoreEngine.RenderingUtils.Loader;
import CoreEngine.RenderingUtils.TextureUtil.ModelTexture;
import CoreEngine.Window;
import imgui.ImGui;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {
    private static Node activeGameObject = null;
    private float debounce = 0.2f;
    Node pickedObj;

    public void update(float dt, Scene currentScene) {
        debounce -= dt;


    }

    public void imgui() {
        if (activeGameObject != null) {
            ImGui.begin("Properties");

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if (ImGui.menuItem("Add Mesh")) {
                    Loader loader = new Loader();
                    int texture = loader.loadTexture("Original");
                    ModelTexture texturei = new ModelTexture(texture);
                    activeGameObject.addComponent(new TexturedModel(Window.model, texturei));
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
        activeGameObject = go;
    }
}
