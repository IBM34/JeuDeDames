package jeuxDames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class Tableau extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	modele mod;
    private JTable tableau;
 
    public Tableau() throws IOException {
        super();
        mod = new modele();
        setTitle("Classement"); 
        this.setPreferredSize(new Dimension(600, 275));
        tableau = new JTable(mod);
        centrerTable(tableau);
        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);
        pack();
    }
    
    private void centrerTable(JTable table) {     DefaultTableCellRenderer custom = new DefaultTableCellRenderer();
    custom.setHorizontalAlignment(JLabel.CENTER);
    for (int i=0 ; i<table.getColumnCount() ; i++)
    table.getColumnModel().getColumn(i).setCellRenderer(custom);
     }
}