import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class FenetreJouer extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ApplicationMenu fenMenu;
	private FenetreOption fenOption;
	
	public FenetreJouer(ApplicationMenu fenMenu, FenetreOption fenOption) {
		this.fenMenu = fenMenu;
		this.fenOption = fenOption;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 40, 1100, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);
}
}
