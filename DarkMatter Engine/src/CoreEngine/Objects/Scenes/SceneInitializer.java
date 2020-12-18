package CoreEngine.Objects.Scenes;

import CoreEngine.Objects.Scene;

public abstract class SceneInitializer {
    public abstract void init(Scene scene);
    public abstract void loadResources(Scene scene);
    public abstract void imgui();
}
