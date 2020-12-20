package CoreEngine;

import CoreEngine.Components.MeshComponent;
import CoreEngine.DefaultShapes.Cube;
import CoreEngine.Graphics.FrameBuffer;
import CoreEngine.Graphics.Mesh;
import CoreEngine.Graphics.Renderer;
import CoreEngine.Input.InputSystem;
import CoreEngine.Maths.Matrix4f;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Node;
import CoreEngine.Objects.Scene;
import CoreEngine.Objects.Scenes.EditorSceneInitializer;
import CoreEngine.Objects.Scenes.SceneInitializer;
import CoreEngine.Observers.Events.Event;
import CoreEngine.Shaders.Shader;
import DarkMatterEditor.ConsolePanel;
import DarkMatterEditor.Gui.ImGuiLayer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjglx.Sys;

import java.awt.event.MouseListener;

import static CoreEngine.Observers.Events.EventType.*;
import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    public Shader defaultShader;
    private Renderer renderer;
    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;
    private InputSystem input = new InputSystem();
    private FrameBuffer framebuffer;
    private static Matrix4f projection;
    //private PickingTexture pickingTexture;
    private boolean runtimePlaying = false;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "DarkMatter Engine v0.1";
        projection = Matrix4f.projection(70.0f, (float) width / (float) height, 0.1f, 1000.0f);
    }

    public static void changeScene(SceneInitializer sceneInitializer) {
        if (currentScene != null) {
            currentScene.destroy();
        }

        //getImguiLayer().getPropertiesWindow().setActiveGameObject(null);
        currentScene = new Scene(sceneInitializer);
        currentScene.load();
        currentScene.init();
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
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
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

        this.imguiLayer = new ImGuiLayer(glfwWindow);
        this.imguiLayer.initImGui();

        Window.changeScene(new EditorSceneInitializer());

    }

    public void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = -1.0f;
        defaultShader = new Shader("src/CoreEngine/Shaders/MainVertex.glsl", "src/CoreEngine/Shaders/MainFragmentShader.glsl");
        //Shader pickingShader = AssetPool.getShader("assets/shaders/pickingShader.glsl");
        defaultShader.create();
        renderer = new Renderer(defaultShader);
        Cube cube = new Cube();
        Mesh mesh = cube.mesh;
        Node obj = new Node("default");
        obj.addComponent(new MeshComponent(new Vector3f(0,0,0), new Vector3f(0,0,0), new Vector3f(1,1,1), mesh));
        currentScene.addGameObjectToScene(obj);
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
            //currentScene.render();

            //pickingTexture.disableWriting();
            glEnable(GL_BLEND);

            // Render pass 2. Render actual game
            drawGrid();
            renderer.renderMesh();
            this.framebuffer.bind();
            glClearColor(0.5f, 0.5f, 0.5f, 1);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                if (runtimePlaying) {
                    currentScene.update(dt);
                } else {
                    currentScene.editorUpdate(dt);
                }
                //currentScene.render();
            }
            this.framebuffer.unbind();

            this.imguiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow);

            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
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
    public void drawGrid() {
        for (int i = 0; i < 100; i++) {
            glPushMatrix();
            if (i < 50) {
                glTranslatef(0, i, 0);
            }
            if (i >= 50) {
                glTranslatef(i - 20, 0, 0);
                glRotatef(-90, 0, 1, 0);
            }
            glBegin(GL_LINES);
            glColor3f(1, 1, 1);
            glLineWidth(1);
            glVertex3f(0, -0.1f, 0);
            glVertex3f(19, -0.1f, 0);
            glEnd();
            glPopMatrix();
            System.out.println("renderingGrid");
            ConsolePanel.log("rendering Grid ");
        }
    }
}
