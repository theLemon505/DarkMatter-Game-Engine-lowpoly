package CoreEngine.Objects;

import CoreEngine.Components.*;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Scenes.SceneInitializer;
import CoreEngine.Shaders.Shader;
import CoreEngine.Window;
import DarkMatterEditor.EditorRenderer.DebugRenderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Scene {
    public static Camera camera;
    private boolean isRunning;
    private List<Node> gameObjects;
    private Shader defaultShader;
    private SceneInitializer sceneInitializer;
    public Scene(SceneInitializer sceneInitializer) {
        this.sceneInitializer = sceneInitializer;
        this.gameObjects = new ArrayList<>();
        this.isRunning = false;
    }

    public void init() {
        this.sceneInitializer.loadResources(this);
        this.sceneInitializer.init(this);
        for (int i=0; i < gameObjects.size(); i++) {
            for (Component com:gameObjects.get(i).getAllComponents()
                 ) {
                com.start();
            }
        }

    }
    public void createRenderer(){

    }
    public void start() {
        for (int i=0; i < gameObjects.size(); i++) {
            Node go = gameObjects.get(i);
            go.start();
        }
        isRunning = true;
    }

    public void addGameObjectToScene(Node go) {
        if (!isRunning) {
            gameObjects.add(go);
        } else {
            gameObjects.add(go);
            go.start();
        }
    }

    public void destroy() {
        for (Node go : gameObjects) {
            go.destroy();
        }
    }

    public List<Node> getGameObjects() {
        return this.gameObjects;
    }

    public Node getGameObject(int gameObjectId) {
        Optional<Node> result = this.gameObjects.stream()
                .filter(gameObject -> gameObject.getUid() == gameObjectId)
                .findFirst();
        return result.orElse(null);
    }

    public void editorUpdate(float dt) {
        DebugRenderer.addLine(new Vector3f(0,0,1), new Vector3f(1,1,1));

        for (int i=0; i < gameObjects.size(); i++) {
            Node go = gameObjects.get(i);
            go.editorUpdate(dt);

            if (go.isDead()) {
                gameObjects.remove(i);
                i--;
            }
        }
    }

    public void update(float dt) {
        for (int i=0; i < gameObjects.size(); i++) {
            Node go = gameObjects.get(i);
            go.update(dt);

            if (go.isDead()) {
                gameObjects.remove(i);
                i--;
            }
        }
    }

    public void render() {

    }

    public Camera camera() {
        return this.camera;
    }

    public void imgui() {
        this.sceneInitializer.imgui();
    }

    public Node createGameObject(String name) {
        Node go = new Node(name);
        go.addComponent(new Transform());
        return go;
    }

    public void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(Node.class, new GameObjectDeserializer())
                .create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            List<Node> objsToSerialize = new ArrayList<>();
            for (Node obj : this.gameObjects) {
                if (obj.doSerialization()) {
                    objsToSerialize.add(obj);
                }
            }
            writer.write(gson.toJson(objsToSerialize));
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Component.class, new ComponentDeserializer())
                .registerTypeAdapter(Node.class, new GameObjectDeserializer())
                .create();

        String inFile = "";
        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!inFile.equals("")) {
            int maxGoId = -1;
            int maxCompId = -1;
            Node[] objs = gson.fromJson(inFile, Node[].class);
            for (int i=0; i < objs.length; i++) {
                addGameObjectToScene(objs[i]);

                for (Component c : objs[i].getAllComponents()) {
                    if (c.getUid() > maxCompId) {
                        maxCompId = c.getUid();
                    }
                }
                if (objs[i].getUid() > maxGoId) {
                    maxGoId = objs[i].getUid();
                }
            }

            maxGoId++;
            maxCompId++;
            Node.init(maxGoId);
            Component.init(maxCompId);
        }
    }
}
