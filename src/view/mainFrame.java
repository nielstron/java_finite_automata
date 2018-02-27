package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.FiniteAutomaton;
import model.algorithms.RabinScott;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
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
							FiniteAutomaton<String, String> nfa =  new GraphConverter().stringToFA(new FileInputStream(inputfile));
							FiniteAutomaton<Set<String>, String> dfa = new RabinScott<String, String>().constructDNF(nfa);
							String result = new GraphConverter().NFAtoString(dfa);
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
		txtrDigraphFinitestatemachine.setText("digraph {\r\n    \r\n    qi [shape = point];\r\n    node [shape = circle];\r\n    X; 0; 1; 2;\r\n    3 [shape = doublecircle];\r\n    \r\n    qi -> X;\r\n    X -> X [label = \"1\"];\r\n    X -> X [label = \"0\"];\r\n    X -> 0 [label = \"1\"];\r\n    0 -> 1 [label = \"1\"];\r\n    0 -> 1 [label = \"0\"];\r\n    1 -> X [label = \"&epsilon;\"];\r\n    1 -> 2 [label = \"0\"];\r\n    2 -> 3 [label = \"1\"];\r\n    2 -> 3 [label = \"0\"];\r\n\r\n}");
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
