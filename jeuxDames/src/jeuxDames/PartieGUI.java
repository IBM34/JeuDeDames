package jeuxDames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PartieGUI {

	private static int T[][] = new int[10][10];
	protected static Joueur j1;
	protected static Joueur j2;
	protected static Fenetre f;
	static int n;
	static boolean newGame;
	static ZDialog zd1 = new ZDialog(null, "réglage des joueurs", true,0);
	static ZDialog zd2 = new ZDialog(null, "réglage des joueurs", true,1);
	

	public static void main(String[] args) throws InterruptedException, IOException {

		boolean rejoue=true;
		while(rejoue)
		{
			j1 = new Joueur();
			j2 = new Joueur();
			rejoue=false;
			f = new Fenetre();
			newGame = true;
			f.initMenu(j1, j2);
			//initMenu();
			n=0;
			String[] d1 = {"nouvelle partie","charger partie"};
			JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();	
		    int option = jop.showOptionDialog(null, "séléctionnez votre choix","charger partie?",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,d1,d1[1]);
		    if(option==0)
		    {
		    	newPartie();
		    }
		    else
		    {
		    	Charger();
		    	dameGui();
		    }
		    
		    f.jlArbitre.setText("<html>Apuyez sur Espace pour changer de pions<br>Appuyez sur Entree quand vous aurez fait votre choix</html>");
	 	    while(f.activeClavier){System.out.print("");}
		
	 	    Font font2 = new Font("Arial",Font.BOLD,18);
	 	    f.jlArbitre.setFont(font2);
	 	    
		    while(n==0 && newGame)
		    {
		    	deroulementPartie();
		    }
		    
		    f.jlj1.setText("<html>"+j1.getpseudo()+"<br>"+j1.getnombrePion()+" jetons"+"<br>"+j1.getnombreDame()+" dames </html>");
	        f.jlj2.setText("<html>"+j2.getpseudo()+"<br>"+j2.getnombrePion()+" jetons"+"<br>"+j2.getnombreDame()+" dames </html>");	
	        if(f.numJ==1)
	        {
	        	f.jlArbitre.setText("<html>Au tour de "+j1.getpseudo()+"</html>");
	        }
	        else
		   	{
		   		f.jlArbitre.setText("<html>Au tour de "+j2.getpseudo()+"</html>");
		   	}
		   
		    if(n==1)
		    {
		    	if (j1.statut != 2) f.tab.mod.c.ajoutscore(j1.pseudo, 0);
				if (j2.statut != 2) f.tab.mod.c.ajoutscore(j2.pseudo, 1);
		    	f.jlArbitre.setForeground(new Color(186,48,48));
		    	f.jlArbitre.setText("<html>"+j1.pseudo+" a gagné la partie!"+"<br>Bravo!!!");
		    }
		    else if(n==2)
		    {
				if (j2.statut != 2) f.tab.mod.c.ajoutscore(j2.pseudo, 0);
				if (j1.statut != 2) f.tab.mod.c.ajoutscore(j1.pseudo, 1);
		    	f.jlArbitre.setForeground(new Color(186,48,48));
		    	f.jlArbitre.setText("<html>"+j2.pseudo+" a gagné la partie!"+"<br>Bravo!!!");
		    }
		    else if(n==3)
		    {
		    	if (j1.statut != 2) f.tab.mod.c.ajoutscore(j1.pseudo, 2);
				if (j2.statut != 2) f.tab.mod.c.ajoutscore(j2.pseudo, 2);
		    	f.jlArbitre.setForeground(Color.BLUE);
		    	f.jlArbitre.setText("<html>match nul"+"<br>pfff!!!");
		    }
		    Thread.sleep(1300);
		    
		    d1[0]="oui";
		    d1[1]="non";
		    int option4 = jop.showOptionDialog(null, "Voulez-vous rejouer?", "Rejouer?", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE, null, d1, d1[1]);
		    if(option4==0)
		    {
		    	rejoue=true;
		    	f.dispose();
		    }
		    else
		    {
		    	System.exit(0);
		    }
		}
	}

	
	public static void deroulementPartie() throws InterruptedException
	{
		int c=0;
		Vector<Integer> acc=new Vector<Integer>(); 
		acc.add(0);
		acc.add(0);
		acc.add(0);
		acc.add(0);
		if(j1.nombrePion==acc.elementAt(0) && j1.nombreDame==acc.elementAt(1) && j2.nombrePion==acc.elementAt(2) && j2.nombreDame==acc.elementAt(3))
    	{
    		c+=1;
    	}
    	else
    	{
			 c=0; 
			 acc=new Vector<Integer>(); 
			 acc.add(j1.nombrePion); 
			 acc.add(j1.nombreDame); 
			 acc.add(j2.nombrePion); 
			 acc.add(j2.nombreDame);
		 }
		f.jlj1.setText("<html>"+j1.getpseudo()+"<br>"+j1.getnombrePion()+" jetons"+"<br>"+j1.getnombreDame()+" dames </html>");
        f.jlj2.setText("<html>"+j2.getpseudo()+"<br>"+j2.getnombrePion()+" jetons"+"<br>"+j2.getnombreDame()+" dames </html>");	
        if(f.numJ==1)
        {
        	f.jlArbitre.setText("<html>Au tour de "+j1.getpseudo()+"</html>");
        }
        else
	   	{
	   		f.jlArbitre.setText("<html>Au tour de "+j2.getpseudo()+"</html>");
	   	}
        
			joue();
	
    	if(f.numJ==1)
    	{
    		f.numJ=2;
    	}
    	else f.numJ=1;
    	n=Gagnant(c,f.plateau.dm);
	}
	
	/*public static void initMenu()
	{
		f.newgame.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent event){
		    	//initMenu();
		    	//newGame=false;
		    	  //f.plateau.reinitPlateau();
		    	  //f.changeDamier(f.plateau.t);
		    	//newPartie2();

		    	  Fenetre newF = new Fenetre();
		    	  f.dispose();
		    	  f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		    	  newF.pack();
		    	  newF.setVisible(true);
		    	  newPartie2();
		      }
		    });
	}*/
	
	public static void newPartie()
	{
		f.vect.removeAllElements();
		//initMenu();
		f.activeClavier=true;
		f.activeSouris=false;
		f.plateau.reinitPlateau();
		f.vect.removeAllElements();
		f.initBord();
		String[] d1 = {"1 VS 1","1 VS IA"};
		JOptionPane jop = new JOptionPane();
		T=f.plateau.t;
    	f.changeDamier(T);
	    int option2 = jop.showOptionDialog(null, "mode de jeu","mode de jeu",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,d1,d1[1]);
	    if(option2==0)
        {
	    	j1.setNiveau(0);
	    	j2.setNiveau(0);
	    	j1.setStatut(1);
	    	j2.setStatut(1);
	    	j1.setCouleurPions(1);
	    	j2.setCouleurPions(2);
        	zd1.setVisible(true);
        	
            j1.setPseudo(zd1.pseudo1.getText());
            j2.setPseudo(zd1.pseudo2.getText());
            f.jlj1.setText("<html>"+j1.getpseudo()+"<br>"+j1.getnombrePion()+" jetons"+"<br>"+j1.getnombreDame()+" dames </html>");
            f.jlj2.setText("<html>"+j2.getpseudo()+"<br>"+j2.getnombrePion()+" jetons"+"<br>"+j2.getnombreDame()+" dames </html>");
        }
        else if(option2 == 1)
        {
        	j1.setStatut(1);
	    	j2.setStatut(2);
	    	j1.setCouleurPions(1);
	    	j2.setCouleurPions(2);
        	zd2.setVisible(true);
        	j1.setPseudo(zd2.pseudo1.getText());
        	j2.setPseudo("IA");
        	if((String)zd2.niveau.getSelectedItem()=="faible"){j2.setNiveau(1);}
        	else if((String)zd2.niveau.getSelectedItem()=="moyen"){j2.setNiveau(2);}
        	else {j2.setNiveau(3);}
        	f.jlj1.setText("<html>"+j1.getpseudo()+"<br>"+j1.getnombrePion()+" jetons"+"<br>"+j1.getnombreDame()+" dames </html>");
            f.jlj2.setText("<html>"+j2.getpseudo()+" "+(String)zd2.niveau.getSelectedItem()+"<br>"+j2.getnombrePion()+" jetons"+"<br>"+j2.getnombreDame()+" dames </html>");
            j2.setPseudo(j2.getpseudo()+"_"+(String)zd2.niveau.getSelectedItem());
        }  
	}
	
	public static int Gagnant(int c,int d){
		int nP1,nD1,nP2,nD2;
		nP1=j1.nombrePion;
		nD1=j1.nombreDame;
		nP2=j2.nombrePion;
		nD2=j2.nombreDame;
		if(d>=25) return 3;
		if((nP1==0 && nD1==0) || f.plateau.bloque==1) return 2;
		if((nP2==0 && nD2==0) || f.plateau.bloque==2) return 1;
		if((nD1==1 && nD2==2) || (nD1==2 && nD2==1) || (nD1==1 && nD2==1) && c>=5) return 3;
		if(nD1==1 && nP1==0 && c>=32)
			{if(nD2==3 || (nD2==2 && nP2==1) || (nD2==1 && nP2==2))		//SÂ’il n 'y a plus que trois dames, deux dames et un pion, ou une dame et deux pions contre une dame, la fin de partie sera considï¿½rï¿½e comme ï¿½gale lorsque les deux joueurs auront encore jouï¿½ chacun 16 coups au maximum.
				return 3;}
		if(nD2==1 && nP2==0 && c>=32)
			{if(nD1==3 || (nD1==2 && nP1==1) || (nD1==1 && nP1==2))
				return 3;}
		if(c==50)
			return 3;
		return 0;
	}
	
	public static void Charger()
	{
		try
		{
			File fi = new File ("partie.txt"); // mettre le chemin absolu du fichier si il le trouve pas
			Scanner scanner = new Scanner (new BufferedReader (new FileReader (fi)));	
			while(true)
			{
				 try                        
			        {
					 	int i=0;
						int j=0;
						for(i=0;i<10;i++)
						{
							for(j=0;j<10;j++)
							{
								T[i][j]=scanner.nextInt();
							}
						}	
						f.changeDamier(T);
										  
					   j1.pseudo=scanner.next();          
					   j1.statut=scanner.nextInt();  
					   j1.nombrePion=scanner.nextInt();  
					   j1.nombreDame=scanner.nextInt();  
					   j1.CouleurPions=scanner.nextInt();  
					   j1.niveau=scanner.nextInt();  
					   
					   j2.pseudo=scanner.next();          
					   j2.statut=scanner.nextInt();  
					   j2.nombrePion=scanner.nextInt();  
					   j2.nombreDame=scanner.nextInt();  
					   j2.CouleurPions=scanner.nextInt();  
					   j2.niveau=scanner.nextInt();  
					   f.numJ=scanner.nextInt();
			        }
				 catch (NoSuchElementException exception)
			        {
			            break;
			        }
			}	
			scanner.close();
		}
		catch (FileNotFoundException exception)
		{
		    System.out.println ("Le fichier n'a pas ï¿½tï¿½ trouvï¿½");
		}
	}
	
	public static void dameGui()
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				if(f.plateau.t[i][j]==10)
				{
					f.T[i][j].setDame(true); 
					f.T[i][j].setC(10); 
				}
				if(f.plateau.t[i][j]==20)
				{
					f.T[i][j].setDame(true); 
					f.T[i][j].setC(20);
				}
			}
		}
		
	 }
	
	public static void joue() throws InterruptedException{
		boolean virtuel;
		int n,m = 0;
		boolean amange=false;								//Bool pour savoir si on fait une rafle
		Vector<Integer> p=new Vector<Integer>();			//Coordonnï¿½es du pion choisit initialement
		Vector<Integer> p2=new Vector<Integer>();			//Coordonnï¿½es de la case d'arrivï¿½e
		Vector<Integer> q=new Vector<Integer>();			//Pions eligibles
		if (f.numJ==1)
		{
			if (j1.statut==1)virtuel=false;
			else virtuel=true;
		}
		else
		{
			if(j2.statut==1)virtuel=false;
			else virtuel=true;
		}
		q=f.plateau.DoitJouer(f.numJ);
		if(!(q.isEmpty())){
		if(f.cmpt==0)
		{
			for(int i=0;i<q.size();i+=2)
			{
				Jeton j = f.T[q.elementAt(i)][q.elementAt(i+1)];
				f.vect.add(j);
			}
			
		}
		if (!(virtuel))
		{
			for(int i=0;i<f.vect.size();i++)
			{
				f.vect.elementAt(i).setBord(1);
			}
		while(f.cmpt==0){System.out.print("");}
		p.add(f.Ind[0]);
		p.add(f.Ind[1]);			
		}
		
		else 
		{
			Thread.sleep(300);
			f.vect.removeAllElements();
	    	f.initBord();
	    	f.cmpt++;
	    	if(f.numJ==2){
				switch(j2.getNiveau()){
				case 1: 
					n=f.plateau.ChoixIA1(q,0,true,f.numJ);
					break;
				case 3:
					n=f.plateau.ChoixIA3(q,0,true,f.numJ);
					break;
				default:
					n=f.plateau.ChoixIA2(q); 
					break;
					}
				}
				else{
				switch(j1.getNiveau()){
				case 1: n=f.plateau.ChoixIA1(q,0,true,f.numJ);
				case 3: n=f.plateau.ChoixIA3(q,0,true,f.numJ);
				default:n=f.plateau.ChoixIA2(q);}}
			f.Ind[0]=q.elementAt(n);
			f.Ind[1]=q.elementAt(n+1);
			p.add(f.Ind[0]);
			p.add(f.Ind[1]);
		}
		//Le joueur en choisit un, la fonction renvoit le tableau de deux entiers correspondant a ses coordonnï¿½es.
		q=new Vector<Integer>();
		do{
			if(amange)f.cmpt=1;
			q=f.plateau.CasesAJouer(p.elementAt(0),p.elementAt(1),f.numJ);
			if(f.cmpt==1)
			{
				for(int i=0;i<q.size();i+=2)
				{
					Jeton j = f.T[q.elementAt(i)][q.elementAt(i+1)];
					f.vect.add(j);
				}					
			}
			if (!(virtuel))
			{
				for(int i=0;i<f.vect.size();i++)
				{
					f.vect.elementAt(i).setBord(1);
				}
			while(f.cmpt==1){System.out.print("");}	
			f.plateau.affiche();
			p2.addElement(f.Ind[2]);
			p2.addElement(f.Ind[3]);
			}
			else
			{
				Thread.sleep(300);
				if(f.numJ==2)
				{
				switch(j2.getNiveau()){
				case 1: {m=f.plateau.ChoixIA1(q,0,false,f.numJ); break;}
				case 3:{ m=f.plateau.ChoixIA3(q,0,false,f.numJ); break;}
				default: m=f.plateau.ChoixIA2(q);}}
				else{switch(j1.getNiveau()){
				case 1: { m=f.plateau.ChoixIA1(q,0,false,f.numJ); break;}
				case 3: { m=f.plateau.ChoixIA3(q,0,false,f.numJ); break;}
				default: m=f.plateau.ChoixIA2(q);}
				}
				f.Ind[2]=q.elementAt(m);
				f.Ind[3]=q.elementAt(m+1);
				p2.addElement(f.Ind[2]);
				p2.addElement(f.Ind[3]);
				Jeton jeton2 = new Jeton(0);
				jeton2.setCoor(f.Ind[2],f.Ind[3]);
				int coul = f.T[f.Ind[0]][f.Ind[1]].getC();
		    	f.T[f.Ind[0]][f.Ind[1]].setC(0);
		    	f.plateau.t[f.Ind[0]][f.Ind[1]]=0;
		    	jeton2.setC(coul);
		    	f.plateau.t[jeton2.getLigne()][jeton2.getColonne()]=coul;
		    	f.T[f.Ind[2]][f.Ind[3]].setC(coul);
		        f.initBord();
		        f.vect.removeAllElements();
		    	f.cmpt=0;
			}
			amange=f.plateau.PionMange(p.elementAt(0),p.elementAt(1),p2.elementAt(0),p2.elementAt(1),f.numJ,j1,j2);		
            f.mange=amange;
			if(amange){
				f.T[f.plateau.ircopie][f.plateau.jrcopie].setC(0);
				f.Ind[0]=f.Ind[2];
				f.Ind[1]=f.Ind[3];
			}
			p=p2;
			p2=new Vector<Integer>();}
		while(f.plateau.PeutManger(p.elementAt(0),p.elementAt(1),f.numJ) && amange);				//On refait cette boucle si le joueur a mangï¿½ un pion et on continue tant que le pion selectionnï¿½ peut manger un pion adverse (tant que PeutManger() retourne true)
		f.plateau.DameCreee(p.elementAt(0),p.elementAt(1),f.numJ,j1,j2);
		dameGui();
		}
		else f.plateau.bloque=f.numJ;
			}

}


