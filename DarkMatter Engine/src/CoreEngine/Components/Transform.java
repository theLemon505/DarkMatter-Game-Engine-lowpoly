package CoreEngine.Components;

import CoreEngine.Maths.Vector3f;

public class Transform extends Component{
    public Vector3f position;
    public Vector3f rotation;

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
    public float getTotalScale(){
        return (scale.x + scale.y + scale.z) / 3;
    }
    public Vector3f scale;
    public Transform copy() {
        return new Transform(position, rotation, scale);
    }
    public void update(float dt){

    }
    public Transform(Vector3f position, Vector3f rotation, Vector3f scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }
    public Transform(){
        this.position = new Vector3f(0,0,0);
        this.rotation = new Vector3f(0,0,0);
        this.scale = new Vector3f(1,1,1);
    }
}
