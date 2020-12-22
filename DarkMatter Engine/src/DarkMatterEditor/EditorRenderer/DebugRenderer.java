package DarkMatterEditor.EditorRenderer;

import CoreEngine.Maths.Vector3f;
import CoreEngine.Shaders.Shader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class DebugRenderer {
    private static int MAX_LINES = 500;
    private static List<Line> lines = new ArrayList<>();

    private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
    private static Shader shader = new Shader("src/CoreEngine/Shaders/debugLineVertex.glsl", "src/CoreEngine/Shaders/debugLineFragment.glsl");
    private static int vaoID;
    private static int vboID;

    private static boolean started = false;

    public static void start(){
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0,3,GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1,3,GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(0);
    }

    public static void beginFrame(){
        if(!started){
            start();
            started = true;
        }

        for(int i = 0; i <lines.size(); i++){
            if(lines.get(i).beginFrame() < 0){
                lines.remove(i);
                i--;
            }
        }
    }

    public static void draw(){
        if(lines.size() <= 0) return;
        int index = 0;
        for(Line line: lines){
            for(int i  = 0; i < 2; i++){
                Vector3f position = i == 0 ? line.getFrom() : line.getTo();
                Vector3f color = line.getColor();

                vertexArray[index] = position.getX();
                vertexArray[index + 1] = position.getX();
                vertexArray[index + 2] = position.getX();

                vertexArray[index + 3] = color.getX();
                vertexArray[index + 4] = color.getY();
                vertexArray[index + 5] = color.getZ();
                index += 6;
            }
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));
        shader.create();

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawArrays(GL_LINES, 0, lines.size());
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        shader.destroy();
    }

    public static void addLine(Vector3f from, Vector3f to){
        addLine(from, to, new Vector3f(1,1,1), 1);
    }
    public static void addLine(Vector3f from, Vector3f to, Vector3f color){
        addLine(from, to, color, 1);
    }
    public static void addLine(Vector3f from, Vector3f to, Vector3f color, int lifetime)
    {
        if(lines.size() >= MAX_LINES) return;
        DebugRenderer.lines.add(new Line(from, to, color, lifetime));
    }
}
