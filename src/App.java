import javax.swing.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
/*
 School Scheduel Helper, First Made in Python By Sam Heiligmann only, then remade in java with The Help of Zach Millisor
*/

public class App {
    public static List<String> names = new ArrayList<>();
    public static List<String> classNames = new ArrayList<>();
    public static List<Long> startTimes = new ArrayList<>();
    public static List<Long> endTimes = new ArrayList<>();

    public static boolean changeTime = false;
    public static void main(String[] args) throws Exception {

        TellTime time = new TellTime();
        JSONParser parser = new JSONParser();
        try{

            JSONObject obj = (JSONObject) parser.parse(new FileReader("src/schedule.json"));
            JSONArray arr = (JSONArray) obj.get("assignments");
            for(Object o: arr) {
                JSONObject getName = (JSONObject) o;
                String name = (String) getName.get("nameOf");
                String className = (String) getName.get("className");
                Long startTimes = (Long) getName.get("startTime");
                Long endTimes  = (Long)getName.get("endTime");
                classNames.add(className);
                names.add(name);
                App.startTimes.add(startTimes);
                App.endTimes.add(endTimes);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        gui loadGui = new gui();
        loadGui.setVisible(true);
        while(true){
            time.update();
            if(changeTime){
                loadGui.guiActionArea.remove(0);
                loadGui.initActionArea();
                changeTime = false;
            }
        }
    }
}
//This Class is The Window that Can be opened From the first
class readsched extends JFrame{
    JPanel actionArea = new JPanel();
    readsched(){
        initGui();
    }

    private void initGui(){
        createMenuBar();
        setTitle("Edit Schedule");
        setSize(320,320);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(actionArea);
    }
    private void createMenuBar(){
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem eMenuItem = new JMenuItem("Exit");

        eMenuItem.setToolTipText("Exit App");

        fileMenu.add(eMenuItem);
        menubar.add(fileMenu);

        setJMenuBar(menubar);

    }

}
//For the Clock Label
class TellTime{
   static int hour,minutes,seconds;
   static String time24;
   static boolean canChange = true;

    public TellTime(){

        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minutes = cal.get(Calendar.MINUTE);
        seconds = cal.get(Calendar.SECOND);
        SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm aa");
        Date dat = cal.getTime();
        time24 = sdf12.format(dat);

    }

    public void update(){
        Calendar cal = Calendar.getInstance();
        seconds = cal.get(Calendar.SECOND);
        if(hour < App.startTimes.get(0)){
            gui.startIdx = 0;
        }
        if(seconds == 0 && canChange){
            hour = cal.get(Calendar.HOUR_OF_DAY);
            minutes = cal.get(Calendar.MINUTE);
            SimpleDateFormat sdf12 = new SimpleDateFormat("hh:mm aa");
            Date dat = cal.getTime();
            time24 = sdf12.format(dat);
            App.changeTime = true;
            canChange = false;

            if(hour > App.startTimes.get(gui.startIdx) && hour <= App.endTimes.get(gui.startIdx)){
               gui.AssignmentLabel.setText(App.names.get(gui.startIdx) + " For " + App.classNames.get(gui.startIdx));
            }else if(hour > App.startTimes.get(gui.startIdx)){
                if(gui.startIdx <= App.startTimes.size()){
                    gui.startIdx++;
                }
            }
        }
        if(seconds == 1) {
            canChange = true;
        }
    }
}
//The First Window 
class gui extends JFrame {
    readsched reader = new readsched();
    public static JPanel guiActionArea = new JPanel(null);
    public static JLabel AssignmentLabel = new JLabel();
    public static Integer startIdx = 0;

    gui() {
        initGui();
    }

    private void initGui() {
        createMenuBar();
        setTitle("Schedule Helper");
        setSize(1280,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initActionArea();
    }
    public void initActionArea(){
      setContentPane(guiActionArea);
        TellTime clock = new TellTime();
      JLabel TimeLabel = new JLabel(clock.time24);
      TimeLabel.setBounds(500,50,650,300);
      TimeLabel.setFont(TimeLabel.getFont().deriveFont(90.0f));
      AssignmentLabel.setFont(AssignmentLabel.getFont().deriveFont(30.0f));
      AssignmentLabel.setBounds(500,250,650,200);
      guiActionArea.add(TimeLabel);
      guiActionArea.add(AssignmentLabel);
      App.changeTime = false;






    }
    private void createMenuBar(){
        JMenuBar menubar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        ImageIcon exitIcon =  new ImageIcon("src/resources/exit.png");
        JMenu editMenu =  new JMenu("Edit");
        

        JMenuItem eMenuItem = new JMenuItem("Exit", exitIcon);
        eMenuItem.setToolTipText("Exit App");
        eMenuItem.addActionListener((event) -> System.exit(1));
        JMenuItem edMenuItem = new JMenuItem("Add");
        edMenuItem.setToolTipText("Set Things");
        edMenuItem.addActionListener((event) -> reader.setVisible(true));
        

        fileMenu.add(eMenuItem);
        editMenu.add(edMenuItem);
        menubar.add(fileMenu);
        menubar.add(editMenu);
        

        setJMenuBar(menubar);
        

    }
}