package CoreEngine.RenderingUtils;

import CoreEngine.Components.TexturedModel;
import CoreEngine.Objects.Node;
import CoreEngine.Shaders.Shader;
import CoreEngine.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MasterRenderer {
    private Renderer renderer = new Renderer();

    private Map<TexturedModel, List<CoreEngine.Objects.Node>> nodes = new HashMap<TexturedModel, List<CoreEngine.Objects.Node>>();

    public void init(){
        renderer.init(Window.get().defaultShader);
    }
    public void end(){
        renderer.end();
    }
    public void processEntity(Node node){
        TexturedModel model = node.getComponent(TexturedModel.class);
        List<Node> batch = nodes.get(model);
        if(batch!=null){
            batch.add(node);
        }
        else{
            List<Node> newBatch = new ArrayList<Node>();
            newBatch.add(node);
            nodes.put(model, newBatch);
        }
    }
    public void render(){
        renderer.prepare();
        renderer.render(nodes);
    }
}
