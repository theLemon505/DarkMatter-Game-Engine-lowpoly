package CoreEngine.Maths;

public class Transform {
    private static Vector3f position;
    private static Vector3f rotation;

    public static Vector3f getPosition() {
        return position;
    }

    public static void setPosition(Vector3f position) {
        Transform.position = position;
    }

    public static Vector3f getRotation() {
        return rotation;
    }

    public static void setRotation(Vector3f rotation) {
        Transform.rotation = rotation;
    }

    public static Vector3f getScale() {
        return scale;
    }

    public static void setScale(Vector3f scale) {
        Transform.scale = scale;
    }

    private static Vector3f scale;

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
}
