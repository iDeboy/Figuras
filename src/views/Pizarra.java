package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import models.*;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class Pizarra extends JFrame implements Runnable {

	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
	}

	public Pizarra() {
		initComponents();
	}

	private void initComponents() {
		leftPanel = new JPanel();
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanelTitle = new JLabel();

		buttonGroup = new ButtonGroup();
		btnTriangulo = new JToggleButton();
		btnCuadrilatero = new JToggleButton();
		btnCirculo = new JToggleButton();

		canvasPanel = new CanvasPanel();
		scrollLogger = new JScrollPane();
		logger = new JTextArea();

		rightPanelTitle = new JLabel();
		scrollFiguras = new JScrollPane();
		listFiguras = new JList<>(new ListFigurasModel());

		setTitle("Pizarra");
		setResizable(false);
		setLayout(new BorderLayout());

		// Si la ventana pierde el foco del teclado, recuperarlo
		KeyboardFocusManager.
						getCurrentKeyboardFocusManager().
						addPropertyChangeListener("focusOwner", (PropertyChangeEvent e) -> {
							//System.out.println(e.toString());
							requestFocusInWindow();
						});

		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e) {

				FiguraModel selectedModel = listFiguras.getSelectedValue();

				// Quitar la seleccion de la figura
				if (e.getExtendedKeyCode() == VK_ESCAPE && selectedModel != null) {
					logger.append(selectedModel.getNombre() + " deseleccionado.");
					listFiguras.clearSelection();
					return;
				}

				if (selectedModel == null || !selectedModel.canDraw()) {
					return;
				}

				switch (e.getExtendedKeyCode()) {
					case VK_W -> {
						// Up
						selectedModel.getPuntos().up(5);
						logger.append("[W ↑] " + selectedModel.getNombre() + "\n");
					}
					case VK_S -> {// Down
						selectedModel.getPuntos().down(5);
						logger.append("[S ↓] " + selectedModel.getNombre() + "\n");
					}
					case VK_A -> {// Left
						selectedModel.getPuntos().left(5);
						logger.append("[A ←] " + selectedModel.getNombre() + "\n");
					}
					case VK_D -> {// Right
						selectedModel.getPuntos().right(5);
						logger.append("[D →] " + selectedModel.getNombre() + "\n");
					}
					case VK_Q -> {// Rotate left
						selectedModel.getPuntos().rotateLeft(90);
						logger.append("[Q ↶] " + selectedModel.getNombre() + "\n");
					}
					case VK_E -> {// Rotate right
						selectedModel.getPuntos().rotateRight(90);
						logger.append("[E ↷] " + selectedModel.getNombre() + "\n");
					}
					case VK_SHIFT -> { // Zoom in
						selectedModel.getPuntos().zoomIn(2);
						logger.append("[Shift ←█→] " + selectedModel.getNombre() + "\n");
					}
					case VK_CONTROL -> {// Zoom out
						selectedModel.getPuntos().zoomOut(2);
						logger.append("[Ctrl →|←] " + selectedModel.getNombre() + "\n");
					}
				}

				repaint();
			}

		});

		/* leftPanel components & design */
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		leftPanelTitle.setText("Figuras");
		leftPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));

		btnTriangulo.setText("Triángulo");
		btnTriangulo.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnTriangulo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Se va a crear un triángulo.\n");

			btnTriangulo.setEnabled(false);
			btnCuadrilatero.setEnabled(true);
			btnCirculo.setEnabled(true);

			currentFigura = new TrianguloModel();
		});

		btnCuadrilatero.setText("Cuadrilatero");
		btnCuadrilatero.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnCuadrilatero.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Se va a crear un cuadrilatero.\n");

			btnCuadrilatero.setEnabled(false);
			btnTriangulo.setEnabled(true);
			btnCirculo.setEnabled(true);

			currentFigura = new CuadrilateroModel();
		});

		btnCirculo.setText("Circulo");
		btnCirculo.setFont(new Font("Montserrat", Font.BOLD, 16));
		btnCirculo.addActionListener((ActionEvent e) -> {

			listModel.checkForIncompletes();
			repaint();

			logger.append("Se va a crear un circulo.\n");

			btnCirculo.setEnabled(false);
			btnCuadrilatero.setEnabled(true);
			btnTriangulo.setEnabled(true);

			currentFigura = new CirculoModel();
		});

		buttonGroup.add(btnTriangulo);
		buttonGroup.add(btnCuadrilatero);
		buttonGroup.add(btnCirculo);

		leftPanel.add(leftPanelTitle);
		leftPanel.add(btnTriangulo);
		leftPanel.add(btnCuadrilatero);
		leftPanel.add(btnCirculo);

		/* centerPanel components & design */
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);

		canvasPanel.setBackground(centerPanel.getBackground());
		canvasPanel.setBorder(new LineBorder(Color.BLACK, 2));
		canvasPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if (currentFigura == null) {
					return;
				}

				for (int i = 0; i < currentFigura.getPuntos().size(); i++) {

					if (currentFigura.getPuntos().getValueAt(i) == null) {

						currentFigura.getPuntos().setValueAt(i, e.getPoint());
						logger.append("P" + (i + 1) + " (" + e.getX() + ", " + e.getY() + ")\n");

						listModel.addElement(currentFigura);
						canvasPanel.setModel(listModel);
						repaint();
						break;
					}
				}

				if (currentFigura.canDraw()) {
					try {
						logger.append(currentFigura.getNombre() + " creado.\n");
						currentFigura = (FiguraModel) currentFigura.getClass().getConstructors()[0].newInstance(new Object[0]);
					} catch (Exception ex) {

					}
				}

			}

		});

		logger.setRows(10);
		logger.setLineWrap(true);
		logger.setEditable(false);

		scrollLogger.setViewportView(logger);

		centerPanel.add(canvasPanel, BorderLayout.CENTER);
		centerPanel.add(scrollLogger, BorderLayout.PAGE_END);

		/* rightPanel components & design */
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		rightPanelTitle.setText("Vista y edición");
		rightPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));

		listModel = (ListFigurasModel) listFiguras.getModel();
		listFiguras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listFiguras.addListSelectionListener((ListSelectionEvent e) -> {

			listModel.unselectAll();

			var selectedModel = listFiguras.getSelectedValue();

			if (selectedModel == null) {
				repaint();
				return;
			}

			selectedModel.setSelected(true);
			logger.append(selectedModel.getNombre() + " seleccionado.\n");
			repaint();
		});

		//canvasPanel.setModel(listModel);
		scrollFiguras.setViewportView(listFiguras);

		rightPanel.add(rightPanelTitle);
		rightPanel.add(scrollFiguras);

		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);

	}

	/* Variables declaration */
	private FiguraModel currentFigura;

	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JLabel leftPanelTitle;

	/* leftPanel components */
	private ButtonGroup buttonGroup;
	private JToggleButton btnTriangulo;
	private JToggleButton btnCuadrilatero;
	private JToggleButton btnCirculo;

	/* centerPanel components */
	private CanvasPanel canvasPanel;
	private JScrollPane scrollLogger;
	private JTextArea logger;

	/* rightPanel components */
	private JLabel rightPanelTitle;
	private JScrollPane scrollFiguras;
	private JList<FiguraModel> listFiguras;
	private ListFigurasModel listModel;

}
