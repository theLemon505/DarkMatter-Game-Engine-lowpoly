package DarkMatterEditor;

import CoreEngine.Components.Camera;
import CoreEngine.Components.Component;
import CoreEngine.Input.InputSystem;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Observers.MouseListener;
import CoreEngine.Window;
import DarkMatterEditor.Gui.ImGuiLayer;
import org.lwjgl.glfw.GLFW;

public class EditorCamera extends Component {
    private InputSystem input = new InputSystem();
    private Vector3f position;
    private Vector3f rotation;
    private float moveSpeed = 0.05f, mouseSensitivity = 0.15f;
    private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;
    private Camera cam;
    public EditorCamera(Camera cam) {
        this.cam = cam;
        position = cam.position;
        rotation = cam.rotation;
    }
    @Override
    public void update(float dt) {
        newMouseX = -input.getMousex();
        newMouseY = -input.getMousey();

        float x = (float) Math.sin(Math.toRadians(cam.getRotation().getY())) * moveSpeed;
        float z = (float) Math.cos(Math.toRadians(cam.getRotation().getY())) * moveSpeed;
        float y = moveSpeed;
            if (input.isKeyDown(GLFW.GLFW_KEY_A)) {
                cam.position = Vector3f.add(cam.position, new Vector3f(-z, 0, x));
                System.out.println("A");
            }
            if (input.isKeyDown(GLFW.GLFW_KEY_D)) cam.position.set(z, 0, -x);
            if (input.isKeyDown(GLFW.GLFW_KEY_W)) cam.position.set(-x, 0, -z);
            if (input.isKeyDown(GLFW.GLFW_KEY_S)) cam.position.set(x, 0, z);
            if (input.isKeyDown(GLFW.GLFW_KEY_E)) cam.position.set(-0, y, -0);
            if (input.isKeyDown(GLFW.GLFW_KEY_Q)) cam.position.set(0, -y, 0);
            if (input.isKeyDown(GLFW.GLFW_KEY_SPACE)) cam.position.set(0, moveSpeed, 0);
            if (input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT))
                cam.position = Vector3f.add(cam.position, new Vector3f(0, -moveSpeed, 0));
        float dx = (float) (newMouseX - oldMouseX);
        float dy = (float) (newMouseY - oldMouseY);

        if(MouseListener.mouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_2)) {
            System.out.println("key");
            cam.rotation.set(-dy * mouseSensitivity, -dx * mouseSensitivity, 0);
        }
        oldMouseX = newMouseX;
        oldMouseY = newMouseY;
    }

    public Vector3f getPosition() {
        return cam.position;
    }

    public Vector3f getRotation() {
        return cam.rotation;
    }
}
