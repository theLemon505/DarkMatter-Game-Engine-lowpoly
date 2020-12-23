package CoreEngine.EngineUtils;

import CoreEngine.Maths.Vector3f;
import CoreEngine.RenderingUtils.TextureUtil.ModelTexture;

public class Material {
    private Vector3f color;

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }

    private float shineDamper = 1;
    private float reflectivity = 1;
    public Material(Vector3f color, ModelTexture texture) {
        this.color = color;
        this.texture = texture;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }

    private ModelTexture texture;
}
