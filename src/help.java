import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class help extends JFrame {
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					help frame = new help();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public help() {
		setResizable(false);
		setAlwaysOnTop(true);
		setTitle("HELP");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 516, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea txtrAdd = new JTextArea();
		txtrAdd.setBounds(5, 5, 490, 245);
		txtrAdd.setText("Add Vertex - add vertex to the graph \r\nAdd Edge- add edge to the graph \r\nRemove Vertex - remove vertex from the graph\r\nRemove Edge - remove edge from the graph\r\nMove Vertex - reposition the selected vertex\r\n\r\nAdd All Edges - connect all vertices with one another\r\nConnected Components - show all components in the graph\r\nShow Cut Vertices - highlights the vertices that will create separate components when \t\t\t\t\tremoved\r\nHelp - instructions how to run GUI\r\n");
		contentPane.add(txtrAdd);
	}

}