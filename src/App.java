import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        var loadGui = new gui();
        loadGui.setVisible(true);
    }
}
class readsched extends JFrame{
    readsched(){
        
    }
}
class gui extends JFrame {
    gui() {
        initGui();
    }

    private void initGui() {
        createMenuBar();

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
        

        fileMenu.add(eMenuItem);
        editMenu.add(edMenuItem);
        menubar.add(fileMenu);
        menubar.add(editMenu);
        

        setJMenuBar(menubar);
        

    }
}