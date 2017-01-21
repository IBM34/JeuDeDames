package jeuxDames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class modele extends AbstractTableModel {
    private List<Score> scores = new ArrayList<Score>();
    Classement c;
 
    private final String[] entetes = {"Pseudo", "Gagnées", "Perdues", "Egalitées", "Total"};
 
    public modele() throws IOException {
        super();
        c = new Classement();
        for (int i=0; i< c.scores.size(); i++)
        {
        	 scores.add(new Score(c.scores.get(i).getPseudo(), c.scores.get(i).getGagne(), c.scores.get(i).getPerdu(), c.scores.get(i).getEgalite() , c.scores.get(i).getTotal()));             
        }
    }
       
 
    public int getRowCount() {
        return scores.size();
    }
 
    public int getColumnCount() {
        return entetes.length;
    }
 
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return scores.get(rowIndex).getPseudo();
            case 1:
                return scores.get(rowIndex).getGagne();
            case 2:
                return scores.get(rowIndex).getPerdu();
            case 3:
                return scores.get(rowIndex).getEgalite();
            case 4:
                return scores.get(rowIndex).getTotal();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
 
    public void addScore(Score s) {
        scores.add(s);
 
        fireTableRowsInserted(scores.size() -1, scores.size() -1);
    }
 
    public void removeAmi(int rowIndex) {
        scores.remove(rowIndex);
 
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
}
