import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuGUI extends JFrame{
    private JButton graphGoruntulemeButton;
    private JPanel panel1;
    private JButton kelimeIleTweetBulButton;
    private JButton hashtagIleTweetBulButton;
    private JButton graphUzerindeAnalizButton;
    private JButton a2KullaniciIleAnalizButton;

    public MainMenuGUI(MyHashMap<String, User> hashMap, MyGraph ourGraph) {
        add(panel1);
        setTitle("Ana Menü");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        graphGoruntulemeButton.setPreferredSize(new Dimension(10, 60));
        kelimeIleTweetBulButton.setPreferredSize(new Dimension(10,60));
        hashtagIleTweetBulButton.setPreferredSize(new Dimension(10, 60));
        graphUzerindeAnalizButton.setPreferredSize(new Dimension(10, 60));
        a2KullaniciIleAnalizButton.setPreferredSize(new Dimension(10, 60));


        graphGoruntulemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GraphVisualizerGUI GUI = new GraphVisualizerGUI(hashMap);
                        GUI.setVisible(true);
                        GUI.setLocationRelativeTo(null);
                    }
                });
            }
        });

        kelimeIleTweetBulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        WordAnalyzeGUI GUI = new WordAnalyzeGUI(ourGraph);
                        GUI.setVisible(true);
                        GUI.setLocationRelativeTo(null);
                    }
                });
            }
        });

        hashtagIleTweetBulButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        HashtagAnalyzeGUI GUI = new HashtagAnalyzeGUI(ourGraph);
                        GUI.setVisible(true);
                        GUI.setLocationRelativeTo(null);
                    }
                });
            }
        });

        graphUzerindeAnalizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GraphAnalyzeGUI GUI = new GraphAnalyzeGUI(ourGraph, hashMap);
                        GUI.setVisible(true);
                        GUI.setLocationRelativeTo(null);
                    }
                });
            }
        });

        a2KullaniciIleAnalizButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        TwoUserAnalyze GUI = new TwoUserAnalyze(ourGraph, hashMap);
                        GUI.setVisible(true);
                        GUI.setLocationRelativeTo(null);
                    }
                });
            }
        });
    }
}
