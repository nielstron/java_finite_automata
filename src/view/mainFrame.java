package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.algorithms.RabinScott;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;

public class mainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				if(args.length > 0){
					if(args[0] == "--help"){
						System.out.println("To parse a graphviz formatted graph from a file:");
						System.out.println(" -i <filename> ");
						System.exit(0);
					}
					if(args[0] == "-i"){
						try {
							String inputfile = args[1];
							String result = new GraphConverter().NFAtoString(new RabinScott<String, String>().constructDNF( new GraphConverter().stringToFA(new FileInputStream(inputfile))));
							System.out.println(result);
						} catch (FileNotFoundException e) {
							System.err.println("File not found: " + e.getMessage());
						} catch (ArrayIndexOutOfBoundsException e){
							System.err.println("No File given.");
						}
					}
				}
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 444, 748);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow][][grow]"));
		
		JLabel lblInsertGraphIn = new JLabel("Insert Graph in GraphViz format here");
		contentPane.add(lblInsertGraphIn, "cell 0 0");
		
		JTextArea txtrDigraphFinitestatemachine = new JTextArea();
		txtrDigraphFinitestatemachine.setText("digraph finite_state_machine {\r\n    rankdir=LR;\r\n    size=\"8,5\"\r\n\r\n    S [shape = doublecircle];\r\n    qi [shape = point ]; \r\n\r\n    node [shape = circle];\r\n    qi -> S;\r\n    S  -> q1 [ label = \"a\" ];\r\n    S  -> S  [ label = \"a\" ];\r\n    q1 -> S  [ label = \"a\" ];\r\n    q1 -> q2 [ label = \"b\" ];\r\n    q2 -> q1 [ label = \"b\" ];\r\n    q2 -> q2 [ label = \"b\" ];\r\n}");
		contentPane.add(txtrDigraphFinitestatemachine, "cell 0 1,grow");
		
		JButton btnCreatePowersetConstrcution = new JButton("Create powerset constrcution");
		contentPane.add(btnCreatePowersetConstrcution, "cell 0 2,growx");
		
		JTextArea txtrOutput = new JTextArea();
		txtrOutput.setText("Output...");
		contentPane.add(txtrOutput, "cell 0 3,grow");
		
		btnCreatePowersetConstrcution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtrOutput.setText(new GraphConverter().NFAtoString(new RabinScott<String, String>().constructDNF( new GraphConverter().stringToFA(txtrDigraphFinitestatemachine.getText()))));
			}
		});
	}

}
