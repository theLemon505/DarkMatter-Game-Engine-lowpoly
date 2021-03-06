package CoreEngine;

import CoreEngine.Components.Light;
import CoreEngine.Components.Transform;
import CoreEngine.EngineUtils.Colors;
import CoreEngine.EngineUtils.Material;
import CoreEngine.EngineUtils.OBJLoader;
import CoreEngine.Graphics.FrameBuffer;
import CoreEngine.Input.InputSystem;
import CoreEngine.Maths.Matrix4f;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Components.TexturedModel;
import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.Objects.Scenes.EditorSceneInitializer;
import CoreEngine.Objects.Scenes.SceneInitializer;
import CoreEngine.Observers.Events.Event;
import CoreEngine.RenderingUtils.Loader;
import CoreEngine.Models.RawModel;
import CoreEngine.RenderingUtils.MasterRenderer;
import CoreEngine.RenderingUtils.Renderer;
import CoreEngine.RenderingUtils.TextureUtil.ModelTexture;
import CoreEngine.Shaders.Shader;
import DarkMatterEditor.EditorCamera;
import DarkMatterEditor.Gui.ImGuiLayer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private Loader loader = new Loader();
    private MasterRenderer renderer = new MasterRenderer();
    public Shader defaultShader;
    public static EditorCamera editorCam;
    private int width, height;
    public static Node light;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;
    private InputSystem input = new InputSystem();
    private FrameBuffer framebuffer;
    private static Matrix4f projection;
    //private PickingTexture pickingTexture;
    private boolean runtimePlaying = false;
    private static Window window = null;
    public static float FOV = 80;
    public static float NEAR_PLANE =  0.1f;
    public static float FAR_PLANE = 1000;
    public static Scene currentScene;
    public static RawModel model;
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "DarkMatter Engine v0.1";
    }

    public static void changeScene(SceneInitializer sceneInitializer) {
        if (currentScene != null) {
            currentScene.destroy();
        }
        //getImguiLayer().getPropertiesWindow().setActiveGameObject(null);
        currentScene = new Scene(sceneInitializer);
        currentScene.init();
        currentScene.createRenderer();
        currentScene.start();

    }
    public static Matrix4f getProjectionMatrix() {
        return projection;
    }
    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }

        return Window.window;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public void run() {

        init();
        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        projection = Matrix4f.projection(FOV, getTargetAspectRatio(), NEAR_PLANE, FAR_PLANE);
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, input.getMousePos());
        glfwSetMouseButtonCallback(glfwWindow, input.getMouseButton());
        glfwSetKeyCallback(glfwWindow, input.getKeyboard());
        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });
        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        this.framebuffer = new FrameBuffer(3840, 2160);
        //this.pickingTexture = new PickingTexture(3840, 2160);
        glViewport(0, 0, 3840, 2160);
        defaultShader = new Shader("src/CoreEngine/Shaders/MainVertex.glsl", "src/CoreEngine/Shaders/MainFragmentShader.glsl");
        this.imguiLayer = new ImGuiLayer(glfwWindow);
        this.imguiLayer.initImGui();//Shader pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");
        Window.changeScene(new EditorSceneInitializer());
    }

    public void loop() {
        defaultShader.create();
        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = -1.0f;
        Node obj = currentScene.createGameObject("default");
        currentScene.addGameObjectToScene(obj);

        model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texure = new ModelTexture(loader.loadTexture("stallTexture"));
        Material mat = new Material(Colors.darkGrey, texure);
        TexturedModel texturedModel = new TexturedModel(model, mat);
        Light lighting = new Light(new Vector3f(0,-10,0), Colors.white, 10);
        light = currentScene.createGameObject("light");
        Node n = currentScene.createGameObject("textNode");
        light.addComponent(lighting);
        n.addComponent(texturedModel);
        n.getComponent(Transform.class).setPosition(new Vector3f(0,0,-25));
        currentScene.addGameObjectToScene(n);
        currentScene.addGameObjectToScene(light);
        renderer.init();
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            // Render pass 1. Render to picking texture
            glDisable(GL_BLEND);
            //pickingTexture.enableWriting();
            glViewport(0, 0, 3840, 2160);
            glClearColor(0.5f, 0.5f, 0.5f, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            //Renderer.bindShader(pickingShader);

            //pickingTexture.disableWriting();
            glEnable(GL_BLEND);

            // Render pass 2. Render actual game
            this.framebuffer.bind();
            glClearColor(0.5f, 0.5f, 0.5f, 1);
            glClear(GL_COLOR_BUFFER_BIT);
            for(Node node:currentScene.getGameObjects()){
                renderer.processEntity(node);
            }
            renderer.render();
            if (dt >= 0) {
                if (runtimePlaying) {
                    currentScene.update(dt);
                } else {
                    currentScene.editorUpdate(dt);
                }
            }
            this.framebuffer.unbind();

            this.imguiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow);
            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        defaultShader.destroy();
        renderer.end();
        loader.cleanUp();
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public static FrameBuffer getFramebuffer() {
        return get().framebuffer;
    }

    public static float getTargetAspectRatio() {
        return 16.0f / 9.0f;
    }

    public static ImGuiLayer getImguiLayer() {
        return get().imguiLayer;
    }


    public void onNotify(Node object, Event event) {
        switch (event.type) {
            case GameEngineStartPlay:
                this.runtimePlaying = true;
                currentScene.save();
                break;
            case GameEngineStopPlay:
                this.runtimePlaying = false;
                Window.changeScene(new EditorSceneInitializer());
                break;
            case LoadLevel:
                Window.changeScene(new EditorSceneInitializer());
            case SaveLevel:
                currentScene.save();
        }
    }
}
