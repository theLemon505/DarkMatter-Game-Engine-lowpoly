package DarkMatterEditor;

import imgui.ImGui;
import org.lwjglx.Sys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsolePanel {
    private static float lengthPerLine = 160;
    static String text = "";
    static String nextText = "";
    public void imgui(){
        ImGui.begin("Console");
        ImGui.text(text);
        ImGui.end();
    }
    public static void log(String input){
        nextText = input;
        text = text + nextText;
        if(text.length() >= lengthPerLine){
            text = "";
        }
    }
}
