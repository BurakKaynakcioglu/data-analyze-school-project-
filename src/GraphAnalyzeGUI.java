import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphAnalyzeGUI extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JButton sonucuGosterButton;

    public GraphAnalyzeGUI(MyGraph ourGraph, MyHashMap<String, User> hashMap) {
        add(panel1);
        setTitle("Graph Üzerinde Analiz");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        sonucuGosterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField1.getText();

                if (hashMap.get(input) == null) {
                    JOptionPane.showMessageDialog(null,"Geçersiz kullanıcı adı!");
                } else {
                    Node startNode = ourGraph.getNodeByName(input);
                    ourGraph.analyzeGraph(startNode);
                    JOptionPane.showMessageDialog(null, "Girdiğiniz kullanıcadan daha fazla takipçiye sahip\n"
                                    + "kullanıcıların bulunduğu JSON dosyası oluşturuldu.\n" +
                            "Dosyayı şimdi görüntüleyebilirsiniz.");
                }
            }
        });
    }
}
