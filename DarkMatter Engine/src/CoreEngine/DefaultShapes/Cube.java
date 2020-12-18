package CoreEngine.DefaultShapes;

import CoreEngine.EngineUtils.Colors;
import CoreEngine.Graphics.*;
import CoreEngine.Maths.Vector2f;
import CoreEngine.Maths.Vector3f;
import CoreEngine.Components.MeshComponent;

public class Cube {
    public float xpos;
    public float ypos;
    public float zpos;
    public Mesh mesh = new Mesh(new Vertex[] {
            //Back face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), Colors.white, new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), Colors.white,new Vector2f(0.0f, 0.5f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), Colors.white,new Vector2f(0.5f, 0.5f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), Colors.white,new Vector2f(0.5f, 0.0f)),

            //Front face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), Colors.white,new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), Colors.white,new Vector2f(0.0f, 0.5f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.5f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.0f)),

            //Right face
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), Colors.white,new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), Colors.white,new Vector2f(0.0f, 0.5f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.5f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.0f)),

            //Left face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), Colors.white,new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), Colors.white,new Vector2f(0.0f, 0.5f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.5f)),
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.0f)),

            //Top face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), Colors.white,new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), Colors.white,new Vector2f(0.0f, 0.5f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), Colors.white,new Vector2f(0.5f, 0.5f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.0f)),

            //Bottom face
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), Colors.white,new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), Colors.white,new Vector2f(0.0f, 0.5f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), Colors.white,new Vector2f(0.5f, 0.5f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), Colors.white,new Vector2f(0.5f, 0.0f)),
    }, new int[] {
            //Back face
            0, 1, 3,
            3, 1, 2,

            //Front face
            4, 5, 7,
            7, 5, 6,

            //Right face
            8, 9, 11,
            11, 9, 10,

            //Left face
            12, 13, 15,
            15, 13, 14,

            //Top face
            16, 17, 19,
            19, 17, 18,

            //Bottom face
            20, 21, 23,
            23, 21, 22
    }, new Material("/Resources/Assets/Capture4.png"));

    public MeshComponent cube = new MeshComponent(new Vector3f(xpos, ypos, xpos), new Vector3f(0,0,0), new Vector3f(1,1,1), mesh);
}
