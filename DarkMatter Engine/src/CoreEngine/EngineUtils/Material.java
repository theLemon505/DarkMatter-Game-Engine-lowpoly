package CoreEngine.EngineUtils;

import CoreEngine.Maths.Vector3f;
import CoreEngine.RenderingUtils.TextureUtil.ModelTexture;

public class Material {
    private Vector3f color;

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
