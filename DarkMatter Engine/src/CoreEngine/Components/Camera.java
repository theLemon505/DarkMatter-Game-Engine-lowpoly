package CoreEngine.Components;

import CoreEngine.Maths.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera extends Component {
    private Vector3f position, rotation;
    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public void update() {

    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }
}
