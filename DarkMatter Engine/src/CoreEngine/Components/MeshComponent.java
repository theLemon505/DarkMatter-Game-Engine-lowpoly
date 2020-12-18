package CoreEngine.Components;

import CoreEngine.Graphics.Mesh;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Node;

import java.util.List;

public class MeshComponent extends Component {

    private Vector3f position, rotation, scale;
    private Mesh mesh;
    private double temp;
    private List<Node> nodes;
    public MeshComponent(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void update() {
        temp += 0.02;
    }
    public void imgui(){

    }
    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }
}
