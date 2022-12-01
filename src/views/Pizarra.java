package views;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
						//listFiguras.setListData(listModel.toArray());
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
