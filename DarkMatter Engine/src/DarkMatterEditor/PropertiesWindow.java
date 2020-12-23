package DarkMatterEditor;

import CoreEngine.Components.Light;
import CoreEngine.Components.Transform;
import CoreEngine.EngineUtils.Colors;
import CoreEngine.EngineUtils.Material;
import CoreEngine.Components.TexturedModel;
import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.RenderingUtils.Loader;
import CoreEngine.RenderingUtils.TextureUtil.ModelTexture;
import CoreEngine.Window;
import imgui.ImGui;

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
                    Material mat = new Material(Colors.white, texturei);
                    activeGameObject.addComponent(new TexturedModel(Window.model, mat));
                }
                if (ImGui.menuItem("Add Light")) {
                    Light light = new Light(getActiveGameObject().getComponent(Transform.class).position,getActiveGameObject().getComponent(Transform.class).rotation, 10);
                    activeGameObject.addComponent(light);
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
