package DarkMatterEditor;

import CoreEngine.Input.InputSystem;
import CoreEngine.Maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class EditorCamera {
    InputSystem input = new InputSystem();
    private Vector3f position, rotation;
    private float moveSpeed = 0.05f, mouseSensitivity = 0.15f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;

    public EditorCamera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {
        newMouseX = -input.getMousex();
        newMouseY = -input.getMousey();

        float x = (float) Math.sin(Math.toRadians(rotation.getY())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(rotation.getY())) * moveSpeed;
        float y = moveSpeed;
            if (input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z, 0, x));
            if (input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(z, 0, -x));
            if (input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(-x, 0, -z));
            if (input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(x, 0, z));
            if (input.isKeyDown(GLFW.GLFW_KEY_E)) position = Vector3f.add(position, new Vector3f(-0, y, -0));
            if (input.isKeyDown(GLFW.GLFW_KEY_Q)) position = Vector3f.add(position, new Vector3f(0, -y, 0));
            if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
            if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
                position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));
        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);
        if(input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_2)) {
            rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSensitivity, -dx * mouseSensitivity, 0));
        }
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
