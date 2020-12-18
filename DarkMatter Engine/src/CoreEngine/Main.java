package CoreEngine;

import CoreEngine.DefaultShapes.Cube;
import CoreEngine.Objects.Scene;
import CoreEngine.Shaders.Shader;
import CoreEngine.Input.InputSystem;

import CoreEngine.Graphics.Renderer;
import DarkMatterEditor.EditorMain;
import DarkMatterEditor.Gui.ImGuiLayer;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {
    public Thread game;
    private ImGuiLayer imGuiLayer;
    public EditorMain editor = new EditorMain();
    public static DisplayMAnager window;
    public static Renderer renderer;
    public static Shader shader;
    public final int WIDTH = 1280, HEIGHT = 760;
    private static Scene scene = new Scene();
    private Cube cube = new Cube();

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window = new DisplayMAnager(WIDTH, HEIGHT, "DarkMatetr Engine");
        shader = new Shader("src/CoreEngine/Shaders/MainVertex.glsl", "src/CoreEngine/Shaders/MainFragmentShader.glsl");
        renderer = new Renderer( shader);
        window.setBackgroundColor(0.5f, 0.5f, 0.5f);
        window.create();
        shader.create();
        editor.start();
        imGuiLayer = new ImGuiLayer(window.getWindow());
        imGuiLayer.initImGui();
        scene.init();
        cube.cube.getMesh().create();
    }

    public void run() {
        init();
        while (!window.shouldClose() && !InputSystem.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            render();
            update();
            if (InputSystem.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            if (InputSystem.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
        }
        close();
    }

    private void update() {
        editor.update();
        imGuiLayer.update(System.currentTimeMillis(), scene);
        window.update();
    }

    private void render() {
        DisplayMAnager.frameBuffer.bind();
        renderer.renderMesh(cube.cube, scene.editorCamera);
        DisplayMAnager.frameBuffer.unbind();
        editor.render(System.currentTimeMillis());
        this.imGuiLayer.update(System.currentTimeMillis(), scene);
        scene.render();
        window.swapBuffers();
    }

    private void close() {
        editor.end();
        window.destroy();
        shader.destroy();
        cube.cube.getMesh().destroy();
    }
    public static void main(String[] args) {
        new Main().start();
    }
}
