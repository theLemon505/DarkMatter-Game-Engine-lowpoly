package CoreEngine.Models;

import CoreEngine.Components.Component;
import CoreEngine.RenderingUtils.TextureUtil.ModelTexture;

public class TexturedModel extends Component {
    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel model, ModelTexture texture){
        this.rawModel =model;
        this.texture = texture;
    }


    @Override
    public void update(float dt) {

    }
}
