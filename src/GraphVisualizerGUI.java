import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GraphVisualizerGUI extends JFrame{
    private JPanel panel1;
    private JButton goruntuleButton;
    private JTextField textField1;
    private JCheckBox takipEdilenlerCheckBox;
    private JCheckBox takipcilerCheckBox;

    public GraphVisualizerGUI(MyHashMap<String, User> hashMap) {

        add(panel1);
        setTitle("Graph Görüntüle");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ButtonGroup group = new ButtonGroup();
        group.add(takipEdilenlerCheckBox);
        group.add(takipcilerCheckBox);

        goruntuleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = textField1.getText();
                User user = hashMap.get(userInput);

                if (user == null) {
                    JOptionPane.showMessageDialog(null, "Geçersiz kullanıcı adı!");
                } else if (!takipEdilenlerCheckBox.isSelected() && !takipcilerCheckBox.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Lütfen kutucuklardan birini seçin.");
                } else {
                    System.setProperty("org.graphstream.ui", "swing");
                    Graph graph = new SingleGraph("LargeDirectedGraph");

                    Node node1 = graph.addNode(user.getKullaniciAdi());
                    node1.setAttribute("ui.label", user.getKullaniciAdi());
                    for (int i = 0; i < user.getTakipEdilenler().size(); i++) {
                        Node node2 = graph.addNode(user.getTakipEdilenler().get(i));
                        node2.setAttribute("ui.label", user.getTakipEdilenler().get(i));
                    }

                    if (takipEdilenlerCheckBox.isSelected()) {
                        for (int i = 0; i < user.getTakipEdilenler().size(); i++) {
                            graph.addEdge("Edge" + i, user.getKullaniciAdi(), user.getTakipEdilenler().get(i), true);
                        }
                    } else if (takipcilerCheckBox.isSelected()) {
                        for (int i = 0; i < user.getTakipEdilenler().size(); i++) {
                            graph.addEdge("Edge" + i, user.getTakipEdilenler().get(i), user.getKullaniciAdi(), true);
                        }
                    }

                    String styleSheet = "node {" +
                            "   size: 30px, 30px;" + // Düğüm boyutu
                            "   text-size: 25px;" + // Metin boyutu
                            "   fill-color: lightblue;" + // Dolgu rengi
                            "   text-color: black;" + // Metin rengi
                            "   shape: circle;" + // Düğüm şekli
                            "}";
                    graph.setAttribute("ui.stylesheet", styleSheet);

                    Viewer viewer = graph.display();
                    viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
                }

                textField1.setText(null);
            }
        });
    }
}
