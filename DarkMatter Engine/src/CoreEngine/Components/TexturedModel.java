package CoreEngine.Components;

import CoreEngine.Components.Component;
import CoreEngine.EngineUtils.Material;
import CoreEngine.Models.RawModel;
import CoreEngine.RenderingUtils.TextureUtil.ModelTexture;

public class TexturedModel extends Component {
    public RawModel getRawModel() {
        return rawModel;
    }

    public Material getMat() {
        return mat;
    }

    private Material mat;

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel model, Material mat){
        this.rawModel =model;
        this.mat = mat;
        this.texture = mat.getTexture();
    }


    @Override
    public void update(float dt) {

    }
}
