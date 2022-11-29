package views;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import models.FiguraModel;
import models.ListFigurasModel;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class Pizarra2 extends JFrame implements Runnable {

	@Override
	public void run() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public Pizarra2(){
		initComponents();
	}
	
	private void initComponents(){
		leftPanel = new JPanel();
		centerPanel = new JPanel();
		rightPanel = new JPanel();
		leftPanelTitle = new JLabel();
		buttonGroup = new ButtonGroup();
		btnTriangulo = new JToggleButton();
		btnCuadrilatero = new JToggleButton();
		btnCirculo = new JToggleButton();
		
		rightPanelTitle = new JLabel();
		scrollFiguras = new JScrollPane();
		listFiguras = new JList<>(new ListFigurasModel());
		
		setTitle("Pizarra");
		setResizable(false);
		
		/* leftPanel components & design */
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		leftPanelTitle.setText("Figuras");
		leftPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));
		
		btnTriangulo.setText("Triángulo");
		btnTriangulo.setFont(new Font("Montserrat", Font.BOLD, 16));
		
		btnCuadrilatero.setText("Cuadrilatero");
		btnCuadrilatero.setFont(new Font("Montserrat", Font.BOLD, 16));
		
		btnCirculo.setText("Circulo");
		btnCirculo.setFont(new Font("Montserrat", Font.BOLD, 16));
		
		buttonGroup.add(btnTriangulo);
		buttonGroup.add(btnCuadrilatero);
		buttonGroup.add(btnCirculo);
		
		leftPanel.add(leftPanelTitle);
		leftPanel.add(btnTriangulo);
		leftPanel.add(btnCuadrilatero);
		leftPanel.add(btnCirculo);
		
		/* centerPanel components & design */
		centerPanel.setBackground(Color.WHITE);
		
		/* rightPanel components & design */
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		rightPanelTitle.setText("Vista y edición");
		rightPanelTitle.setFont(new Font("Montserrat", Font.BOLD, 24));
		
		listModel = (ListFigurasModel) listFiguras.getModel();
		
		scrollFiguras.setViewportView(listFiguras);
		
		rightPanel.add(rightPanelTitle);
		rightPanel.add(scrollFiguras);
		
		add(leftPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
	}
	
	/* Variables declaration */
  private JPanel leftPanel;
	private JPanel centerPanel;
  private JPanel rightPanel;
  private JLabel leftPanelTitle;
  private ButtonGroup buttonGroup;
	private JToggleButton btnTriangulo;
	private JToggleButton btnCuadrilatero;
	private JToggleButton btnCirculo;
	
	private JLabel rightPanelTitle;
	private JScrollPane scrollFiguras;
	private JList<FiguraModel> listFiguras;
	private ListFigurasModel listModel;
	

}
