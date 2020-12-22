package DarkMatterEditor.EditorRenderer;

import CoreEngine.Maths.Vector3f;

public class Line {
    private Vector3f from;

    public Vector3f getFrom() {
        return from;
    }

    public Vector3f getTo() {
        return to;
    }

    public Vector3f getColor() {
        return color;
    }
    public int beginFrame(){
        this.lifetime--;
        return this.lifetime;
    }

    private Vector3f to;

    public Line(Vector3f from, Vector3f to, Vector3f color, int lifetime) {
        this.from = from;
        this.to = to;
        this.color = color;
        this.lifetime = lifetime;
    }

    private Vector3f color;
    private int lifetime;
}
