package jeuxDames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

//******************************************
//faire un bord pour le damier de 40 pixels
//******************************************

public class Fenetre extends JFrame implements ActionListener,KeyListener {

	protected Plateau plateau = new Plateau();
	protected int cmpt=0;//pour les boutons
	protected int cmpt2=1;//pour le clavier
	protected int numJ=1;
	protected int Ind[] = new int[4];
	protected Jeton T[][] = new Jeton[10][10];
	protected Vector<Jeton> vect = new Vector<Jeton>();
	protected boolean activeClavier;
	protected boolean activeSouris;
	protected JLabel jlArbitre = new JLabel();
	protected JLabel jlj1 = new JLabel();
	protected JLabel jlj2 = new JLabel();
	protected boolean mange=false;
	protected static Tableau tab;
	
	private JMenuBar menu = new JMenuBar();
	private JMenu fich = new JMenu("Fichier");
	private JMenu apropos = new JMenu("a propos");
	JMenuItem newgame;
	JMenuItem charger;
	public int click=0;
	protected JPanel jpGrille = new JPanel();


	
public Fenetre() throws IOException{
	this.setTitle("JEUX DE DAMES");
	this.setSize(808, 808);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    
    tab = new Tableau();
    initDamier();
   	initLayout();
   	this.setVisible(true);
    	
}

public void initDamier()//initialisation du damier
{
	jpGrille.setLayout(new GridLayout(10,10));
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			if((i+j)%2!=0)	//les cases qui ont des pions noir
				{
				T[i][j] = new Jeton(0);
				T[i][j].setCoor(i, j);
				jpGrille.add(T[i][j]);
				T[i][j].addActionListener(this);
				T[i][j].addKeyListener(this);
				}			
			else	//les cases qui ont des pions blanc
				{
				T[i][j] = new Jeton(-2);
				T[i][j].setCoor(i, j);
				jpGrille.add(T[i][j]);
				T[i][j].addActionListener(this);
				T[i][j].addKeyListener(this);
				}
			}
		}
}

public void initMenu(Joueur j1,Joueur j2)//réglage de la barre de menu
{
	newgame = new JMenuItem("Nouvelle partie");
	JMenuItem sauvegarder = new JMenuItem("Sauvegarder partie");
	charger = new JMenuItem("Charger partie");
	JMenuItem classement = new JMenuItem("Classement");
	JMenuItem quit = new JMenuItem("Quitter");
	
	this.fich.add(sauvegarder);
	this.fich.add(classement);
	this.fich.add(quit);
	
	this.menu.add(fich);
	this.setJMenuBar(menu);   	
	
	 sauvegarder.addActionListener(new ActionListener(){
 	      public void actionPerformed(ActionEvent event){
 	    	try {
				Sauvegarder(j1,j2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	      }
 	    });
	 classement.addActionListener(new ActionListener(){
  	      public void actionPerformed(ActionEvent event){
  	    	try {
				tab = new Tableau();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	tab.setVisible(true);
  	      }
  	    });
	
	 quit.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent event){
	    	String[] d1 = {"Oui","Non"};
  	  	    int option = JOptionPane.showOptionDialog(null, "Voulez-vous quitter la partie ?","Quitter",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,d1,d1[1]);
	  	    d1[0]="Oui";
	  	    d1[1]="Non";
	  	    if (option == 0)
	  	    {
	  	    	int option2 = JOptionPane.showOptionDialog(null, "Voulez-vous sauvergarder avant de quitter ?","Sauvergarder",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,d1,d1[1]);
	  	    	if (option2 == 0)
					try {
						Sauvegarder(j1,j2);
					} catch (IOException e) {
						e.printStackTrace();
					}
	  	    	System.exit(0);
	  	    }
	        
	      }
	    });   	
	
	activeClavier=true;
	activeSouris=false;
}

void initLayout()//mise en place des layout et des borders
{
	//couleur de police
	jlj1.setForeground(Color.BLACK);
	jlj2.setForeground(Color.BLACK);
	jlArbitre.setForeground(Color.BLACK);
	
	 JLayeredPane jpEnsemble = new JLayeredPane();
	 jpEnsemble.setOpaque(false);
        jpEnsemble.add(jpGrille);
        jpGrille.setOpaque(false);
        jpGrille.setBounds(30, 38, 553, 560);
        //label1
        JPanel jpLabelj1 = new JPanel();
        jpLabelj1.setLayout(new BoxLayout(jpLabelj1,BoxLayout.PAGE_AXIS));
        jpLabelj1.setOpaque(false);
        jpLabelj1.add(jlj1);
        jpEnsemble.add(jpLabelj1);
        jpLabelj1.setBounds(0,627,150,100);
		//arbitre
        JPanel arbitre = new JPanel();
        arbitre.setLayout(new BorderLayout());
        jpEnsemble.add(arbitre);
        arbitre.add(jlArbitre,BorderLayout.CENTER);
        jlArbitre.setHorizontalAlignment(JLabel.CENTER);
        arbitre.setBounds(150,627,300,100);
        arbitre.setOpaque(false);
        //label2
        JPanel jpLabelj2 = new JPanel();
        jpLabelj2.setLayout(new BoxLayout(jpLabelj2,BoxLayout.PAGE_AXIS));
        jpLabelj2.setOpaque(false);
        jpLabelj2.add(jlj2);
        jpEnsemble.add(jpLabelj2);
        jpLabelj2.setBounds(460,627,150,100);
        //label vide pour qu'il n'y ai pas de problï¿½mes de vue
        JPanel jpLabel4= new JPanel();
        jpLabel4.setOpaque(false);
        
      //Les bords
        Border titleJ1 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "");
        ((TitledBorder) titleJ1).setTitleJustification(TitledBorder.CENTER);
        Border titleJ2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "");
        ((TitledBorder) titleJ2).setTitleJustification(TitledBorder.CENTER);
        
        jpLabelj1.setBorder(titleJ1);
        jpEnsemble.add(jpLabel4);
        jpLabel4.setBounds(600,600,100,100);
        jpLabelj2.setBorder(titleJ2);
        
        JLabel contentPane = new JLabel();
    	contentPane.setIcon( new ImageIcon("cases/fond.png"));
    	contentPane.setLayout( new BorderLayout() );
        
    	this.setContentPane(contentPane);
    	this.getContentPane().add(jpEnsemble);
}

public void changeDamier(int tableau[][])
{
	plateau.t=tableau;
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			if((i+j)%2==0)
			{
				T[i][j].setC(-2);
				T[i][j].setCoor(i, j);
			}
			else
			{
				T[i][j].setC(tableau[i][j]);
			}
			
			if(tableau[i][j]==2)	//les cases qui ont des pions noir
				{
				T[i][j].setC(2);
				T[i][j].setCoor(i, j);
				}			
			else if(tableau[i][j]==1)	//les cases qui ont des pions blanc
				{    				
				T[i][j].setC(1);
				T[i][j].setCoor(i, j);
				}
			else if((i+j)%2==0)
			{
				T[i][j].setC(-2);
				T[i][j].setCoor(i, j);
			}
			else
			{
				T[i][j].setC(0);
				T[i][j].setCoor(i, j);
			}
			}
		}
	
}

public void initBord()//rÃ©initialisation des Boders
{
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			T[i][j].setBord(0);//on ne voit pas le border (qui est vert a la base)
		}
	}
}

public void affichepion()
{
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
			if(vect.contains(T[i][j]))
			{
				T[i][j].setBord(1);
			}
		}
	}
}

public void actionPerformed(ActionEvent arg0) {

	if(activeSouris)
	{
		if(cmpt==0)
	    {
			Jeton jeton= (Jeton) arg0.getSource();
			if(vect.contains(jeton))
			{
	    	Ind[0]=jeton.getLigne();
	    	Ind[1]=jeton.getColonne();
	    	vect.removeAllElements();
	    	initBord();
	    	cmpt++;
			}
	    }
	    else
	    {
	    	Jeton jeton2 = (Jeton) arg0.getSource();
	    	if(vect.contains(jeton2))
	    	{
	    	    Ind[2]=jeton2.getLigne();
	    	    Ind[3]=jeton2.getColonne();
		    	int coul = T[Ind[0]][Ind[1]].getC();
		    	T[Ind[0]][Ind[1]].setC(0);
		    	plateau.t[Ind[0]][Ind[1]]=0;
		    	jeton2.setC(coul);
		    	plateau.t[jeton2.getLigne()][jeton2.getColonne()]=coul;
		        initBord();
		        vect.removeAllElements();
		    	cmpt=0;
	    	
	    	}
	     }
	}
    
  }     

void changeCouleur(int n)
{
	for(int i=0;i<10;i++)
	{
		for(int j=0;j<10;j++)
		{
				T[i][j].choixPions(n);
		}
	}
}

@Override
public void keyTyped(KeyEvent e) {}

@Override
public void keyPressed(KeyEvent e) {
if(e.getKeyCode()==32 && activeClavier==true)//le code de la touche espace est 10
{
	if(cmpt2!=4)
	{
		cmpt2++;
	}
	else cmpt2=1;
	changeCouleur(cmpt2);
}	
	
else if(e.getKeyCode()==10 && activeClavier==true)//le code de la touche entrï¿½e est 10
	{
		activeClavier=false;
		activeSouris=true;
	}
}

@Override
public void keyReleased(KeyEvent e) {}

public void Sauvegarder(Joueur j1,Joueur j2) throws IOException
{
	File f = new File ("partie.txt"); // mettre le chemin absolu du fichier si il le trouve pas
	PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f))); //ouverture du fichier en ecriture avec suppression de l'ancien contenu.
	int i=0;
	int j=0;
	for(i=0;i<10;i++)
	{
		for(j=0;j<10;j++)
		{
			if(plateau.t[i][j]<5)
			{
				pw.print(plateau.t[i][j]+"  ");
			}			
			else pw.print(plateau.t[i][j]+" ");
		}
		pw.println();
	}	
	pw.println(j1.pseudo);
	pw.println(j1.statut);
	pw.println(j1.nombrePion);
	pw.println(j1.nombreDame);
	pw.println(j1.CouleurPions);
	pw.println(j1.niveau);
	
	pw.println(j2.pseudo);
	pw.println(j2.statut);
	pw.println(j2.nombrePion);
	pw.println(j2.nombreDame);
	pw.println(j2.CouleurPions);
	pw.println(j2.niveau);
	pw.println(numJ);
	pw.close();		
}

public void finalize(){}

}














