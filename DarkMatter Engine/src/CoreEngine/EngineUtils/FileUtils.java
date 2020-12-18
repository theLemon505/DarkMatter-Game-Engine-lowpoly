package CoreEngine.EngineUtils;


import org.lwjgl.system.Callback;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;


public class FileUtils {
    private ByteBuffer image;

    private int w;
    private int h;
    private int comp;

    private long window;
    private int  ww;
    private int  wh;

    private boolean ctrlDown;

    private int scale;

    private Callback debugProc;
    private List<Integer> textures = new ArrayList<Integer>();
    private void Texture(String imagePath) {

    }
    public static String loadAsString(String path) {
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        return shaderSource.toString();
    }

}
