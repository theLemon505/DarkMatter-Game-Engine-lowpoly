package CoreEngine.Maths;

public class TransformPos {
    private static Vector3f position;
    private static Vector3f rotation;

    public static Vector3f getPosition() {
        return position;
    }

    public static void setPosition(Vector3f position) {
        TransformPos.position = position;
    }

    public static Vector3f getRotation() {
        return rotation;
    }

    public static void setRotation(Vector3f rotation) {
        TransformPos.rotation = rotation;
    }

    public static Vector3f getScale() {
        return scale;
    }

    public static void setScale(Vector3f scale) {
        TransformPos.scale = scale;
    }

    private static Vector3f scale;

    public TransformPos(Vector3f position, Vector3f rotation, Vector3f scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
    public TransformPos(){
        this.position = new Vector3f(0,0,0);
        this.rotation = new Vector3f(0,0,0);
        this.scale = new Vector3f(1,1,1);
    }
}
