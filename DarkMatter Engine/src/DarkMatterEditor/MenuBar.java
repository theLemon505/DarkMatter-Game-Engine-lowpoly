package DarkMatterEditor;

import CoreEngine.Observers.EventSystem;
import CoreEngine.Observers.Events.Event;
import CoreEngine.Observers.Events.EventType;
import imgui.ImGui;

public class MenuBar {
    public void imgui() {
        ImGui.beginMainMenuBar();

        if (ImGui.beginMenu("File")) {
            if (ImGui.menuItem("Save", "Ctrl+S")) {
                EventSystem.notify(null, new Event(EventType.SaveLevel));
            }

            if (ImGui.menuItem("Load", "Ctrl+O")) {
                EventSystem.notify(null, new Event(EventType.LoadLevel));
            }

            ImGui.endMenu();
        }

        ImGui.endMainMenuBar();
    }
}
