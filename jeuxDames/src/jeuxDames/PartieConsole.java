package jeuxDames;
import jeuxDames.Plateau;
import jeuxDames.Classement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class PartieConsole{
	protected static Joueur j1=new Joueur();
	protected static Joueur j2=new Joueur();

	
	
	public PartieConsole(){
		j1=null;
		j2=null;
	}

//Determine le gagnant de la partie :2=J2 a gagn�, 1 J1 a gagn�, 3 �galit� c est le nombre de tour sans qu'un pion ne soit boug�
	public static int Gagnant(int c, int d){
		int nP1,nD1,nP2,nD2;
		nP1=j1.nombrePion;
		nD1=j1.nombreDame;
		nP2=j2.nombrePion;
		nD2=j2.nombreDame;
		if(d>=25){return 3;}
		if(nP1==0 && nD1==0) return 2;
		if(nP2==0 && nD2==0) return 1;
		if((nD1==1 && nD2==2) || (nD1==2 && nD2==1) || (nD1==1 && nD2==1) && c>=5) return 3;
		if(nD1==1 && nP1==0 && c>=32)
			{if(nD2==3 || (nD2==2 && nP2==1) || (nD2==1 && nP2==2))		//Sil n 'y a plus que trois dames, deux dames et un pion, ou une dame et deux pions contre une dame, la fin de partie sera consid�r�e comme �gale lorsque les deux joueurs auront encore jou� chacun 16 coups au maximum.
				return 3;}
		if(nD2==1 && nP2==0 && c>=32)
			{if(nD1==3 || (nD1==2 && nP1==1) || (nD1==1 && nP1==2))
				return 3;}
		if(c==50)
			return 3;
		return 0;
	}
	
	public static void Sauvegarder(Plateau p, int cj) throws IOException
	{
		File f = new File ("partie.txt"); // mettre le chemin absolu du fichier si il le trouve pas
		PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f))); //ouverture du fichier en ecriture avec suppression de l'ancien contenu.
		int i=0;
		int j=0;
		for(i=0;i<10;i++)
		{
			for(j=0;j<10;j++)
			{
				if(p.t[i][j]<5)
				{
					pw.print(p.t[i][j]+"  ");
				}			
				else pw.print(p.t[i][j]+" ");
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
		pw.println(cj);
		
		pw.close();		
	}
	
	public static int Charger(Plateau p)
	{
		int cj=0;
		try
		{
			File f = new File ("partie.txt"); // mettre le chemin absolu du fichier si il le trouve pas
			Scanner scanner = new Scanner (new BufferedReader (new FileReader (f)));	
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
								p.t[i][j]=scanner.nextInt();
							}
						}							 
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
					   cj=scanner.nextInt();
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
		    System.out.println ("Le fichier n'a pas �t� trouv�");
		}
		return cj;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
	Scanner sc = new Scanner(System.in);
	int b; int c=0;int d=0;int e=0; int cj=1; int n=0; int type=0; int f=0;
	Vector<Integer> acc=new Vector<Integer>(); 
	acc.add(0);acc.add(0);acc.add(0);acc.add(0);
	boolean rejoue=true;
	Classement c1= new Classement();
	while(rejoue)
	{
		Plateau partieDame=new Plateau();
		System.out.println("voulez vous charger une partie ?");
		System.out.println("1. oui");
		System.out.println("2. non");
		e=sc.nextInt();
		if (e==1)
		{
			cj=Charger(partieDame);
		}
		else
		{
			while ((type != 1) && (type != 2))
		    {
				System.out.println("Choisissez le type de partie :");
				System.out.println("1. Jouer � 2 joueurs r�els");
				System.out.println("2. Jouer contre l'ordinateur");
				type = sc.nextInt();
				if ((type != 1) && (type != 2))
				    System.out.println("Erreur, entrez 1 ou 2");
		    }
			if (type==1)
			{
				j1.saisie(1);
				j2.saisie(2);
			}
			else
			{
				j1.saisie(1);
				j2.saisie2(2);
			}	
		}
		while(n==0)
		{
			partieDame.affiche();
			if(j1.nombrePion==acc.elementAt(0) && j1.nombreDame==acc.elementAt(1) && j2.nombrePion==acc.elementAt(2) && j2.nombreDame==acc.elementAt(3)){c+=1;}
			else{c=0; acc=new Vector<Integer>(); acc.add(j1.nombrePion); acc.add(j1.nombreDame); acc.add(j2.nombrePion); acc.add(j2.nombreDame);}
			if((cj==2)&&(j2.getstatut()==2)){partieDame.JeuVirtuel(j1,j2,cj);}else{if((cj==1)&&(j1.getstatut()==2)){partieDame.JeuVirtuel(j1,j2,cj);}
			else{partieDame.Jeu(cj,j1,j2);}}
			if(cj==1)cj+=1;else{cj=1;}
			System.out.println("voulez vous sauvegarder et quitter la partie ?");
			System.out.println("1. oui");
			System.out.println("2. non");
			f=sc.nextInt();
			if (f==1)
			{
				Sauvegarder(partieDame, cj);
				 System.exit (0);
			}
			n=Gagnant(c,partieDame.dm);
			if(n!=3 && n!=0)
			{
				partieDame.affiche();
				if(n==1) System.out.println(j1.pseudo+ " gagne la partie");
				else if (n==2) System.out.println(j2.pseudo+ " gagne la partie");
				else if(n==3)System.out.println("�galit�");
			}
		}
		String pseudo1=j1.pseudo;
		String pseudo2=j2.pseudo;
						
		if(n==1)
		{
			c1.ajoutscore(pseudo1, 0);
			if (j2.statut != 2) c1.ajoutscore(pseudo2, 1);
		}
		else if(n==2)
		{
			if (j2.statut != 2) c1.ajoutscore(pseudo2, 0);
			c1.ajoutscore(pseudo1, 1);
		}
		else if(n==3)
		{
			c1.ajoutscore(pseudo1, 2);
			if (j2.statut != 2) c1.ajoutscore(pseudo2, 2);
		}
		System.out.println("voulez vous afficher le classement ?");
		System.out.println("1. oui");
		System.out.println("2. non");
		d=sc.nextInt();
		if (d==1)
		{
			c1.affiche();
		}
		System.out.println("voulez vous rejouer ?");
		System.out.println("1. oui");
		System.out.println("2. non");
		b=sc.nextInt();
		rejoue=(b==1);
		if(b==1){c=0; cj=1; n=0;}
		if(b==2)sc.close();
		}
		System.out.println("Partie termin�e");
	}
}
