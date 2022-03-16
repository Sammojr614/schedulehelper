import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.util.*;

public class App {
    public static List<String> names = new ArrayList<>();
    public static List<String> classNames = new ArrayList<>();
    public static List<String> StartTimes = new ArrayList<>();

    public static void main(String[] args) throws Exception {
      
        gui loadGui = new gui();
        loadGui.setVisible(true);
    }
}
class readsched extends JFrame{

    readsched(){
        initGui();
    }
    private void readFile(){
        JSONParser parser = new JSONParser();
        JLabel label =  new JLabel();
            try{

                JSONObject obj = (JSONObject) parser.parse(new FileReader("src/schedule.json"));
                JSONArray arr = (JSONArray) obj.get("assigments");

                for(Object o: arr){
                    JSONObject getName = (JSONObject) o;
                    String name = (String) getName.get("nameOf");
                    App.names.add(name);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
    }
    private void initGui(){
        createMenuBar();
        setTitle("Edit Schedule");
        setSize(320,320);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private void createMenuBar(){
        var menubar = new JMenuBar();
        var fileMenu = new JMenu("File");
        var eMenuItem = new JMenuItem("Exit");

        eMenuItem.setToolTipText("Exit App");

        fileMenu.add(eMenuItem);
        menubar.add(fileMenu);

        setJMenuBar(menubar);

    }

}
class guiActionArea extends JPanel{
    gui mainGui = new gui();
    guiActionArea(){
    initActionArea();
    }
    private void initActionArea(){
        setSize(mainGui.getWidth(),mainGui.getHeight());
    }
}
class gui extends JFrame {
    readsched reader = new readsched();
    guiActionArea actionArea = new guiActionArea();
    gui() {
        initGui();
    }

    private void initGui() {
        createMenuBar();
        actionArea.setVisible(true);
        setTitle("Schedule Helper");
        setSize(320,320);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private void createMenuBar(){
        var menubar = new JMenuBar();
        var fileMenu = new JMenu("File");
        var exitIcon =  new ImageIcon("src/resources/exit.png");
        var editMenu =  new JMenu("Edit");
        

        var eMenuItem = new JMenuItem("Exit", exitIcon);
        eMenuItem.setToolTipText("Exit App");
        eMenuItem.addActionListener((event) -> System.exit(1));
        var edMenuItem = new JMenuItem("Add");
        edMenuItem.setToolTipText("Set Things");
        edMenuItem.addActionListener((event) -> reader.setVisible(true));
        

        fileMenu.add(eMenuItem);
        editMenu.add(edMenuItem);
        menubar.add(fileMenu);
        menubar.add(editMenu);
        

        setJMenuBar(menubar);
        

    }
}