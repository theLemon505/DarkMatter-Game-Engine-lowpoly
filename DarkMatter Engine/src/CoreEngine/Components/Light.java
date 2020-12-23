package CoreEngine.Components;

import CoreEngine.Maths.Vector3f;

public class Light extends Component{
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    private Vector3f position;

    public float getIntensity() {
        return Intensity;
    }

    public void setIntensity(float intensity) {
        Intensity = intensity;
    }

    public float Intensity;
    public Light(Vector3f position, Vector3f color, float intensity) {
        this.position = position;
        this.color = color;
        this.Intensity = intensity;
    }

    private Vector3f color;
    @Override
    public void update(float dt) {

    }
}
