package CoreEngine.Components;

import CoreEngine.Maths.Vector3f;
import CoreEngine.Objects.Node;
import imgui.ImGui;
import imgui.type.ImInt;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Component {
    private static int ID_COUNTER = 0;
    private int uid = -1;

    public Node getGameObject() {
        return gameObject;
    }

    public void setGameObject(Node gameObject) {
        this.gameObject = gameObject;
    }

    public transient Node gameObject = null;

    public void start() {
    }
    public abstract void update(float dt);

    public void editorUpdate(float dt) {

    }

    public void imgui() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field field : fields) {
                boolean isTransient = Modifier.isTransient(field.getModifiers());
                if (isTransient) {
                    continue;
                }

                boolean isPrivate = Modifier.isPrivate(field.getModifiers());
                if (isPrivate) {
                    field.setAccessible(true);
                }
                boolean isPublic = Modifier.isPublic(field.getModifiers());
                if (isPublic) {
                    field.setAccessible(true);
                }

                Class type = field.getType();
                Object value = field.get(this);
                String name = field.getName();

                if (type == int.class) {
                    int val = (int)value;
                    field.set(this, ImGui.dragInt(name, new int[]{val}));
                }
                else if (type == float.class) {
                    float val = (float)value;
                    if(ImGui.dragFloat(name, new float[]{val})){
                        field.set(this, val);
                    }
                }
                else if (type == float.class) {
                    double val = (double)value;
                    if(ImGui.dragFloat(name, new float[]{(float)val})){
                        val = val;
                    }
                }
                else if (type == boolean.class) {
                    boolean val = (boolean)value;
                    if (ImGui.checkbox(name + ": ", val)) {
                        field.set(this, !val);
                    }
                }
                else if (type == TexturedModel.class){
                    TexturedModel val = (TexturedModel)value;
                    ImInt v = new ImInt(val.getRawModel().getVaoID());
                    float[] col = new float[]{val.getMat().getColor().getX(), val.getMat().getColor().getY(), val.getMat().getColor().getZ()};
                    if(ImGui.colorPicker3("Color", col)){
                        val.getMat().setColor(new Vector3f(col[0], col[1], col[2]));
                    }
                    if(ImGui.inputInt("vaoID: ", v)){
                        ImGui.treeNode("vaoID: " + v);
                    }
                    ImInt a = new ImInt(val.getMat().getTexture().getID());
                    if(ImGui.inputInt("textureID: ", a)){
                        ImGui.treeNode("textureID: " + a);
                    }
                }
                else if (type == Vector3f.class) {
                    Vector3f val = (Vector3f)value;
                    float[] imVec = {val.getX(), val.getY(), val.getZ()};
                    if(name != "rotation") {
                        if (ImGui.dragFloat3(name + ": ", imVec)) {
                            val.set(imVec[0], imVec[1], imVec[2]);
                        }
                    }
                    if(name == "rotation"){
                        if(ImGui.dragFloat3(name + ": ", imVec)){
                            this.gameObject.getComponent(Transform.class).setRotation(new Vector3f(imVec[0], imVec[1], imVec[2]));
                        }
                    }
                }
                if (isPrivate) {
                    field.setAccessible(false);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void generateId() {
        if (this.uid == -1) {
            this.uid = ID_COUNTER++;
        }
    }

    public void destroy() {

    }

    public int getUid() {
        return this.uid;
    }

    public static void init(int maxId) {
        ID_COUNTER = maxId;
    }
}
