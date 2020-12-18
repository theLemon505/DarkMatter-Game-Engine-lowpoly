package CoreEngine.Objects;

import CoreEngine.Components.MeshComponent;
import CoreEngine.Graphics.Renderer;
import CoreEngine.Main;
import DarkMatterEditor.EditorCamera;
import DarkMatterEditor.EditorMain;
import imgui.ImGui;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private Renderer renderer;
    private boolean isRunning = false;
    public List<Node> gameObjects = new ArrayList<>();
    public EditorCamera editorCamera = EditorMain.camera;
    protected MeshComponent activeGameObject = null;

    public void init(){
       renderer  = new Renderer(Main.shader);
    }
    public void sceneImgui(){
        if(activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }
        imgui();
    }
    public void render(){
        if(isRunning == false){
            //RENDER WITH EDITOR CAMERA!!!
        }
        if(isRunning == true){
            //RENDER WITH GAME CAMERA!!!
        }
    }
    public void imgui(){

    }
    public void addNodeToScene(MeshComponent object){
    }
}
