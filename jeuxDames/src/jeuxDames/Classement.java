package jeuxDames;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/* Cette classe permet de faire un classement des joueurs en fonction du nombre de partie gagn�es, perdues, egalite.
 * Le joueur qui a gagn� le plus de partie est premier.
 * Si 2 joueurs on le meme nombre de partie gagn�es, celui qui en a perdu le moins est premier.
 * Si 2 joueurs ont le meme nombre de parties gagn�es et de partie perdues , celui qui a le plus d'egalit�s est premier.
 * Appel a la classe : ajoutscore(string pseudo, int resultat)
 * resultat = 0 si le joueur a gagn�
 * resultat = 1 si il a perdu
 * resultat = 2 si il a fait egalit�
 */

public class Classement{
	protected File f = new File ("classement.txt"); // mettre le chemin absolu du fichier si il le trouve pas
	protected ArrayList<Score> scores= new ArrayList<Score>();
	
	
Classement() throws FileNotFoundException 
{	
	try
	{
		Scanner scanner = new Scanner (new BufferedReader (new FileReader (f)));	
		String pseudo;
		int gagne, perdu, egalite, total = 0;
		while(true)
		{
			 try                        // On lit toutes les valeurs du fichier et on les stockent dans les vecteurs.
		        {
				   pseudo = scanner.next(); 
				   gagne = scanner.nextInt();
				   perdu = scanner.nextInt();
				   egalite = scanner.nextInt();
				   total = scanner.nextInt();
				 
				   scores.add(new Score(pseudo, gagne, perdu, egalite, total));
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
}

public void affiche() throws IOException
{
	FileReader fr = new FileReader (f);
	BufferedReader br = new BufferedReader (fr);		
	 String line = br.readLine();
	 
	 System.out.println("|  Pseudo  |  Gagn�es  |  Perdues  |  Egalit�s  |  Total  | \n");
        while (line != null)
        {
            System.out.println (line);    //On affiche le contenu du fichier topscores lignes par lignes.
            line = br.readLine();
        } 
        br.close();
        fr.close();
}
public void echange(int j, int i) //Fonction qui echange la position de deux joueurs du classement.
{
   Score scoretemp= new Score();   
   scoretemp=scores.get(j); 
   scores.set(j, scores.get(i));
   scores.set(i, scoretemp);
}

public void ajoutscore(String p, int resultat) throws IOException
{
	
	int i=0;
	int j=0;		
	boolean bool=false;
	
	for (i=0; i<scores.size(); i++)
	{
		if (scores.get(i).getPseudo().equals(p))
		{
			j= i;
			bool=true;
		}
	}
	
	if (bool == false)
	{		
		scores.add(new Score(p, 0, 0, 0, 0));		
		j= scores.size()-1;		
	}
	if (resultat==0) // cas ou le joueur gagne
	{
		scores.get(j).setTotal((scores.get(j).getTotal())+1);
		scores.get(j).setGagne((scores.get(j).getGagne())+1);
	}
	if (resultat == 1)	//cas ou le joueur a perdu
	{
		scores.get(j).setTotal((scores.get(j).getTotal())+1);
		scores.get(j).setPerdu((scores.get(j).getPerdu())+1);
	}

	if (resultat == 2)	//cas ou le joueur a fait egalite
	{
		scores.get(j).setTotal((scores.get(j).getTotal())+1);
		scores.get(j).setEgalite((scores.get(j).getEgalite())+1);
	}
	for (i=0; i<j; i++)
	{
	  if (scores.get(j).getGagne()>scores.get(i).getGagne())
		 {	
		   echange(j, i);
		  
		 }
	  if (scores.get(j).getGagne()==scores.get(i).getGagne())
	  { 
		  if (scores.get(j).getPerdu()==scores.get(i).getPerdu())
		  { 
			  if(scores.get(j).getEgalite()>scores.get(i).getEgalite())
			  {
				  echange(j, i);			  
			  }
		  }
		  else if(scores.get(j).getPerdu()<scores.get(i).getPerdu())
		  {
			  echange(j, i);				  
		  }
	  }	 
	  if (resultat == 1)
		{
		  for (i=j+1; i<scores.size(); i++)
		  {
			  if (scores.get(j).getGagne()==scores.get(i).getGagne())
			  { 
				  if (scores.get(j).getPerdu()==scores.get(i).getPerdu())
				  { 
					  if(scores.get(j).getEgalite()<scores.get(i).getEgalite())
					  {
						  echange(j, i);			  
					  }
					  
				  }
				  else if(scores.get(j).getPerdu()>scores.get(i).getPerdu())
				  {
					  echange(j, i);				  
				  }
			  }	 
		  }
		}
	}		
	
	PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f))); //ouverture du fichier en ecriture avec suppression de l'ancien contenu.
	i=0;
	while(i!=scores.size())
		{
		pw.println(scores.get(i).getPseudo() + " " + scores.get(i).getGagne() + " "+ scores.get(i).getPerdu() + " " + scores.get(i).getEgalite() + " " +  scores.get(i).getTotal());
		i++;
		}
	pw.close();
	}		
}

	
