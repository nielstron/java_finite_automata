package view;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class mainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
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
		setBounds(100, 100, 450, 514);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblInputYourGraph = new JLabel("Input your graph in graphviz style here");
		contentPane.add(lblInputYourGraph);
		
		JTextArea txtrGraphinput = new JTextArea();
		contentPane.add(txtrGraphinput);
		
		
		JTextArea txtrOutput = new JTextArea();
		txtrOutput.setText("Output...");
		
		JButton btnPowersetconstruct = new JButton("Powerset-construct!");
		btnPowersetconstruct.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TextToGraph().parseFA(txtrGraphinput.getText());
			}
		});
		contentPane.add(btnPowersetconstruct);

		contentPane.add(txtrOutput);
		
		this.setVisible(true);
	}

}
