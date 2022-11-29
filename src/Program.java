
import java.awt.EventQueue;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager;
import views.Pizarra;
import views.Pizarra2;

/**
 *
 * @author Honorio Acosta Ruiz
 */
public class Program {

	public static void main(String[] args) {

		/* Look and feel */
		try {

			for (var info : UIManager.getInstalledLookAndFeels()) {

				if ("Nimbus".equals(info.getName())) {

					UIManager.setLookAndFeel(info.getClassName());
					break;

				}
			}
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {

		}

		EventQueue.invokeLater(new Pizarra2());
	}

}
