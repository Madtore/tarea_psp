package tarea_psp;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class MainInterface extends JFrame {

	private JTextField urlField;
	private JList<String> urlList;
	private DefaultListModel<String> listModel;
	private UrlManager urlManager;
	private BrowserLauncher browserLauncher;
	private String regex = "https?://[\\w-]+(\\.[\\w-]+)+[/#?]?.*$";

	public MainInterface() {

		urlManager = new UrlManager();
		browserLauncher = new BrowserLauncher();
		listModel = new DefaultListModel<>();
		
		urlManager.initializeFile();

		urlList = new JList<>(listModel);
		urlList.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		urlList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton btnEliminaUrl = new JButton("-");
		btnEliminaUrl.setBounds(104, 0, 72, 23);
		btnEliminaUrl.setEnabled(false);

		btnEliminaUrl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				urlManager.eliminaUrl(urlList.getSelectedValue());
				loadUrlList();
			}
		});

		urlList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					if (urlList.getModel().getSize() > 0)
						btnEliminaUrl.setEnabled(true);
				}
			}
		});

		JButton openAppButtonLeft = new JButton("App1");
		openAppButtonLeft.setBounds(98, 71, 92, 59);
		JButton openAppButtonCenter = new JButton("App2");
		openAppButtonCenter.setBounds(251, 71, 88, 59);
		JButton openAppButtonRigth = new JButton("App3");
		openAppButtonRigth.setBounds(393, 71, 97, 59);

		JPanel panel_Button = new JPanel();
		panel_Button.setBounds(0, 0, 786, 200);
		panel_Button.setPreferredSize(new Dimension(600, 200));
		panel_Button.setLayout(null);
		panel_Button.add(openAppButtonLeft);
		panel_Button.add(openAppButtonCenter);
		panel_Button.add(openAppButtonRigth);

		loadUrlList();
		getContentPane().setLayout(null);

		JPanel panel_navigacion = new JPanel();
		panel_navigacion.setBounds(0, 200, 786, 87);
		panel_navigacion.setLayout(new BoxLayout(panel_navigacion, BoxLayout.Y_AXIS));

		urlField = new JTextField(30);
		urlField.setLocation(104, 41);
		urlField.setSize(375, 20);

		JButton openUrlButton = new JButton("Abrir URL");
		openUrlButton.setBounds(324, 0, 153, 29);

		openUrlButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String url = urlField.getText();
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher("https://" + url);

				if (url.isEmpty() || !matcher.matches()) {
					JOptionPane.showMessageDialog(MainInterface.this, "URL no válida", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				browserLauncher.openBrowserWithUrl(url);
				urlManager.saveUrl(url);
				loadUrlList();
				urlField.setText("");
			}
		});

		JPanel panelUrl = new JPanel();
		panelUrl.setLayout(null);

		panelUrl.add(urlField);
		panelUrl.add(openUrlButton);

		panel_navigacion.add(panelUrl);
		
				JButton btnSelecionaDirectorio = new JButton("Seleciona Directorio");
				btnSelecionaDirectorio.setBounds(104, 0, 153, 29);
				panelUrl.add(btnSelecionaDirectorio);
				btnSelecionaDirectorio.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JFileChooser selecionaDirectorio = new JFileChooser();
						selecionaDirectorio.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						selecionaDirectorio.setDialogTitle("Seleccionar un archivo");

						int risultado = selecionaDirectorio.showOpenDialog(MainInterface.this);

						if (risultado == JFileChooser.APPROVE_OPTION) {
							File directorioSelecionado = selecionaDirectorio.getSelectedFile();
							urlManager.setUrl(directorioSelecionado);
						}
					}

				});

		JScrollPane jscrollPane = new JScrollPane(urlList);
		jscrollPane.setBounds(104, 34, 374, 231);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!jscrollPane.getBounds().contains(e.getPoint())) {
					urlList.clearSelection();
					btnEliminaUrl.setEnabled(false);
				}
			}
		});

		getContentPane().add(panel_navigacion);
		getContentPane().add(panel_Button);
		
		JLabel lblNewLabel = new JLabel("Gestión de Aplicaciones");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblNewLabel.setBounds(98, 11, 349, 49);
		panel_Button.add(lblNewLabel);

		JPanel panel_textArea = new JPanel();
		panel_textArea.setBounds(0, 287, 786, 276);
		panel_textArea.setLayout(null);
		panel_textArea.add(jscrollPane);
		panel_textArea.add(btnEliminaUrl);
		getContentPane().add(panel_textArea);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 630);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	private void loadUrlList() {
		listModel.clear();
		Set<String> urls = urlManager.loadUrls();
		urls.forEach(element -> listModel.addElement(element));
	}

	public static void main(String[] args) {
		new MainInterface();
	}
}
