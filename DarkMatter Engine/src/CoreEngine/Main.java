package CoreEngine;

import CoreEngine.DefaultShapes.Cube;
import CoreEngine.Objects.Scene;
import CoreEngine.Shaders.Shader;
import CoreEngine.Input.InputSystem;

import CoreEngine.Graphics.Renderer;
import DarkMatterEditor.EditorMain;
import DarkMatterEditor.Gui.ImGuiLayer;
import org.lwjgl.glfw.GLFW;

public class Main {


    public static void main(String[] args) {
        Window window = Window.get();
        window.run();
    }
}
