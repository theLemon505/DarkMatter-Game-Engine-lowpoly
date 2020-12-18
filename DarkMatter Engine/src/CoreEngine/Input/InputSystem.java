package CoreEngine.Input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class InputSystem {
    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private static double mousex, mousey;

    public double getMousex() {
        return mousex;
    }

    public double getMousey() {
        return mousey;
    }

    public GLFWKeyCallback getKeyboard() {
        return Keyboard;
    }

    public GLFWCursorPosCallback getMousePos() {
        return MousePos;
    }

    public GLFWMouseButtonCallback getMouseButton() {
        return MouseButton;
    }

    private GLFWKeyCallback Keyboard;
    private GLFWCursorPosCallback MousePos;
    private GLFWMouseButtonCallback MouseButton;


    public InputSystem(){
        Keyboard = new GLFWKeyCallback() {
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };
        MousePos = new GLFWCursorPosCallback() {
            public void invoke(long window, double xpos, double ypos) {
                mousex = xpos;
                mousey = ypos;
            }
        };
        MouseButton = new GLFWMouseButtonCallback() {
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };
    }
    public void destroy(){
        Keyboard.free();
        MouseButton.free();
        MousePos.free();
    }
    public static boolean isKeyDown(int key){
        return keys[key];
    }
    public static boolean isButtonDown(int button){
        return buttons[button];
    }
}
