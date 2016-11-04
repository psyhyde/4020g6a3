package applets;

import irs.PrepareData;
import irs.PrepareProcess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ViewMain {

	int i = 1;
	int length = 0;
	static String content = " ";

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewMain window = new ViewMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ViewMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(1250, 600));
		frame.setName("frame");
		frame.setResizable(false);
		frame.setBackground(Color.DARK_GRAY);
		BorderLayout borderLayout = (BorderLayout) frame.getContentPane()
				.getLayout();
		borderLayout.setHgap(6);
		borderLayout.setVgap(9);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton btnNewButton = new JButton("Run");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PrepareProcess.build(null);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					PrepareData.build(null);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JPanel panel = new JPanel();
				panel.setAlignmentX(Component.RIGHT_ALIGNMENT);
				frame.getContentPane().add(panel, BorderLayout.SOUTH);

				int length = new File("./indexes").listFiles().length;
				for (i = 1; i < length - 1; i++) {
					JButton b = new JButton(String.valueOf(i));
					b.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String n = ((JButton) e.getSource()).getText();
							try {
								content = readFile("./indexes/index" + n
										+ ".txt", Charset.defaultCharset())
										+ " ";
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							changetext(content);

						}
					});
					panel.add(b);
				}
				SwingUtilities.updateComponentTreeUI(frame);

			}
		});
		frame.getContentPane().add(btnNewButton, BorderLayout.NORTH);
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	public void changetext(String E) {
		JTextArea textArea = new JTextArea();
		textArea.setDoubleBuffered(true);
		textArea.setDragEnabled(false);
		textArea.setEditable(false);
		textArea.setLineWrap(true);

		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		textArea.setText(content);
		textArea.setCaretPosition(0);
		JScrollPane scr = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scr);
	}

}
