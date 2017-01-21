package jeuxDames;
import java.util.*;

public class Plateau {
	protected Scanner sc=new Scanner(System.in);
	protected int t[][]=new int[10][10];		//Matrice du plateau initialisïṡẄe totalement ïṡẄ 0.
	protected int ircopie,jrcopie,dm;
	protected int bloque=0;

	//Constructeur du damier
	public Plateau(){
		for(int i=0;i<10;i++)
		{for(int j=0;j<10;j++)
		{if((i+j)%2!=0 & i<4)	//les cases qui ont des pions noir
		{t[i][j]=2;}			
		if((i+j)%2!=0 & i>5)	//les cases qui ont des pions blanc
		{t[i][j]=1;}}}
	}


	//Affiche le damier
	public void affiche(){
		for(int i=0;i<10;i++)
		{for(int j=0;j<10;j++)
		{if(t[i][j]<5){System.out.print(t[i][j]+"  ");}
		else System.out.print(t[i][j]+" ");}
		System.out.println(" ");
		System.out.println(" ");}
	}


	//Test si un pion peut manger un pion adverse
	public boolean PeutManger(int i, int j, int cj){
		int a,k,b,a2,b2; 
		boolean n=true,o=true,p=true,q=true;
		a=1; k=2; b=2; a2=10; b2=20;
		if(cj==2) {a=2; b=1; a2=20; b2=10;}			//Test quel joueur joue et determine si les jeton adverses sont les 1 ou les 2.
		if(t[i][j]==a)			
		{if(i>=2 && j>=2){if((t[i-1][j-1]==b || t[i-1][j-1]==b2) && (t[i-2][j-2]==0)){return true;}}
		if(i>=2 && j<8){if((t[i-1][j+1]==b || t[i-1][j+1]==b2) && (t[i-2][j+2]==0)){return true;}}
		if(i<8 && j<8){if((t[i+1][j+1]==b || t[i+1][j+1]==b2) && (t[i+2][j+2]==0)){return true;}}
		if(i<8 && j>=2){if((t[i+1][j-1]==b || t[i+1][j-1]==b2) && (t[i+2][j-2]==0)){return true;}}}   //Test pour chaque pion si il a des pions adverses a portïṡẄe.
		else {if(t[i][j]==a2){	
			while(k<10 && (n || o || p || q)) 					//Si le pion est une dame on teste toutes ses diagonales en entier.
			{if(appartientPlateau(i+k,j+k) && n)  {if(t[i+k-1][j+k-1]==cj || t[i+k-1][j+k-1]==cj*10)  {n=false;}   else{   if(t[i+k-1][j+k-1]==b || t[i+k-1][j+k-1]==b2){if(t[i+k][j+k]==0){return true;}else{n=false;}}}}
			if(appartientPlateau(i+k,j-k) && o)   {if(t[i+k-1][j-k+1]==cj || t[i+k-1][j-k+1]==cj*10)  {o=false;}   else{   if(t[i+k-1][j-k+1]==b || t[i+k-1][j-k+1]==b2){if(t[i+k][j-k]==0){return true;}else{o=false;}}}}
			if(appartientPlateau(i-k,j+k) && p)   {if(t[i-k+1][j+k-1]==cj || t[i-k+1][j+k-1]==cj*10)  {p=false;}   else{   if(t[i-k+1][j+k-1]==b || t[i-k+1][j+k-1]==b2){if(t[i-k][j+k]==0){return true;}else{p=false;}}}}
			if(appartientPlateau(i-k,j-k) && q)   {if(t[i-k+1][j-k+1]==cj || t[i-k+1][j-k+1]==cj*10)  {q=false;}   else{   if(t[i-k+1][j-k+1]==b || t[i-k+1][j-k+1]==b2){if(t[i-k][j-k]==0){return true;}else{q=false;}}}}		//Une fois la dame dans le tableau des pions qui ont une proie a portïṡẄe il faut arreter de chercher si elle peut manger sur ses diagonales.
			k+=1;} return false;
		}}
		return false;
	}


	public boolean PeutBouger(int i, int j, int cj){
		boolean b=false;
		if(PeutManger(i,j,cj)) return true;
		else{
			int a,a2;
			a=1; a2=10;
			if(cj==2) {a=2; a2=20;}			//Test quel joueur joue et determine si les jeton adverses sont les 1 ou les 2.
			if(t[i][j]==a)			
			{if(i>=1 && j>=1 && cj==1){if(t[i-1][j-1]==0){b=true;}}
			if(i>=1 && j<9 && cj==1){if(t[i-1][j+1]==0){b=true;}}
			if(i<9 && j<9 && cj==2){if(t[i+1][j+1]==0){b=true;}}
			if(i<9 && j>=1 && cj==2){if(t[i+1][j-1]==0){b=true;}}}	//Test pour chaque pion si il a de la place
			if(t[i][j]==a2)		//pour les dames
			{if(i>=1 && j>=1){if(t[i-1][j-1]==0){b=true;}}
			if(i>=1 && j<9){if(t[i-1][j+1]==0){b=true;}}
			if(i<9 && j<9){if(t[i+1][j+1]==0){b=true;}}
			if(i<9 && j>=1){if(t[i+1][j-1]==0){b=true;}}}}
		return b;}


	//Cette fonction calcule parmis les pions pouvant manger un autre pion ceux ayant le chemin potentiel avec le plus de prises et renvoit leurs coordonnïṡẄes dans un tableau.
	public Vector<Integer> DoitJouer(int cj){
		Vector<Integer> ti=new Vector<Integer>();				//Ce tableau sera le nombre de prises maximum pour chaque pions
		Vector<Integer> nul=new Vector<Integer>();
		int i,j,s2=0,s=0;
		for(i=0; i<10; i++)
		{ for(j=0; j<10; j++)		//Parcours du plateau.
		{if(t[i][j]==cj || t[i][j]==cj*10)
		{if(PeutBouger(i,j,cj))			//Test pour chaque pion si il peut manger un adversaire.
		{nul=new Vector<Integer>(); s2=MaxPrises(i,j,nul,(t[i][j]>9),cj);	//Test le nombre maximum que le pion va manger
		if(s2>s){ti=new Vector<Integer>(); ti.add(i); ti.add(j); s=s2;}  //Si ce nombre est superieur au prïṡẄcedent max, il remplace tout le tableau par les coordonnïṡẄes du nouveau pion
		else if(s2==s){ti.add(i); ti.add(j);}}}
		}}		//Sinon si il est ïṡẄgal a l'ancien maximum, on ajoute ses coordonnïṡẄes au tableau.
		return ti;
	}


	//Cette fonction determine le nombre de prises maximum pour un jeton donnïṡẄ.

public int MaxPrises(int i, int j, Vector<Integer> ni,Boolean dame, int cj){
	int k=1,k2=1,ja,a,b,c,d=0; a=b=c=d;
	Boolean p,q,r,s=true; p=q=r=s;Boolean amange=false;
	if(cj==1) ja=2; else ja=1; 
	while(appartientPlateau(i+k,j+k) && p){
				if(t[i+k][j+k]==cj || t[i+k][j+k]==cj*10){p=false;}
				if(t[i+k][j+k]==ja || t[i+k][j+k]==ja*10){
					if(ni.contains(i+k) && ni.contains(j+k)){p=false;}
					else{k2=1;
					if((appartientPlateau((i+k)+k2,(j+k+k2)))&&!(t[i+k+k2][j+k+k2]==cj|| t[(i+k)+k2][(j+k)+k2]==cj*10 || t[(i+k)+k2][(j+k)+k2]==ja || t[(i+k)+k2][(j+k)+k2]==ja*10)){amange=true;}
						while(appartientPlateau((i+k)+k2,(j+k)+k2) && p){
								if(t[(i+k)+k2][(j+k)+k2]==cj || t[(i+k)+k2][(j+k)+k2]==cj*10 || t[(i+k)+k2][(j+k)+k2]==ja || t[(i+k)+k2][(j+k)+k2]==ja*10){p=false;}
								if(t[(i+k)+k2][(j+k)+k2]==0){
									ni.add(i+k); ni.add(j+k);
									a=max(a,MaxPrises((i+k)+k2,(j+k)+k2,ni,dame,cj));
									if(!dame)k2+=11; else k2++;
									ni.remove(ni.size()-1); ni.remove(ni.size()-1);
								}
						}
						if(amange){a++;}
					}
				}
				if(!dame)k=11; else k++;
	}
	k=1;amange=false;
	while(appartientPlateau(i+k,j-k) && q){
				if(t[i+k][j-k]==cj || t[i+k][j-k]==cj*10){q=false;}
				if(t[i+k][j-k]==ja || t[i+k][j-k]==ja*10){
					if(ni.contains(i+k) && ni.contains(j-k)){q=false;}//a modifier***********
					else{k2=1;
					if((appartientPlateau((i+k)+k2,(j-k)-k2))&&!(t[(i+k)+k2][(j-k)-k2]==cj || t[(i+k)+k2][(j-k)-k2]==cj*10 || t[(i+k)+k2][(j-k)-k2]==ja || t[(i+k)+k2][(j-k)-k2]==ja*10)){amange=true;}
						while(appartientPlateau((i+k)+k2,(j-k)-k2) && q){
								if(t[(i+k)+k2][(j-k)-k2]==cj || t[(i+k)+k2][(j-k)-k2]==cj*10 || t[(i+k)+k2][(j-k)-k2]==ja || t[(i+k)+k2][(j-k)-k2]==ja*10){q=false;}
								if(t[(i+k)+k2][(j-k)-k2]==0){
									ni.add(i+k); ni.add(j-k);
									b=max(b,MaxPrises((i+k)+k2,(j-k)-k2,ni,dame,cj));
									if(!dame)k2+=11; else k2++;
									ni.remove(ni.size()-1); ni.remove(ni.size()-1);
								}
						}
						if(amange){b++;}
					}
				}
				if(!dame)k=11; else k++;
	}
	k=1;amange=false;
	while(appartientPlateau(i-k,j+k) && r){
				if(t[i-k][j+k]==cj || t[i-k][j+k]==cj*10){r=false;}
				if(t[i-k][j+k]==ja || t[i-k][j+k]==ja*10){
					if(ni.contains(i-k) && ni.contains(j+k)){r=false;}
					else{k2=1;
					if((appartientPlateau((i-k)-k2,(j+k)+k2))&&!(t[(i-k)-k2][(j+k)+k2]==cj || t[(i-k)-k2][(j+k)+k2]==cj*10 || t[(i-k)-k2][(j+k)+k2]==ja || t[(i-k)-k2][(j+k)+k2]==ja*10)){amange=true;}
						while(appartientPlateau((i-k)-k2,(j+k)+k2) && r){
								if(t[(i-k)-k2][(j+k)+k2]==cj || t[(i-k)-k2][(j+k)+k2]==cj*10 || t[(i-k)-k2][(j+k)+k2]==ja || t[(i-k)-k2][(j+k)+k2]==ja*10){r=false;}
								if(t[(i-k)-k2][(j+k)+k2]==0){
									ni.add(i-k); ni.add(j+k);
									c=max(c,MaxPrises((i-k)-k2,(j+k)+k2,ni,dame,cj));
									if(!dame)k2+=11; else k2++;
									ni.remove(ni.size()-1); ni.remove(ni.size()-1);
								}
						}
						if(amange){c++;}
					}
				}
				if(!dame)k=11; else k++;
	}
	k=1;amange=false;
	while(appartientPlateau(i-k,j-k) && s){
				if(t[i-k][j-k]==cj || t[i-k][j-k]==cj*10){s=false;}
				if(t[i-k][j-k]==ja || t[i-k][j-k]==ja*10){
					if(ni.contains(i-k) && ni.contains(j-k)){s=false;}
					else{ k2=1;
					if((appartientPlateau((i-k)-k2,(j-k)-k2))&&!(t[(i-k)-k2][(j-k)-k2]==cj || t[(i-k)-k2][(j-k)-k2]==cj*10 || t[(i-k)-k2][(j-k)-k2]==ja || t[(i-k)-k2][(j-k)-k2]==ja*10))amange=true;
						while(appartientPlateau((i-k)-k2,(j-k)-k2) && s){
								if(t[(i-k)-k2][(j-k)-k2]==cj || t[(i-k)-k2][(j-k)-k2]==cj*10 || t[(i-k)-k2][(j-k)-k2]==ja || t[(i-k)-k2][(j-k)-k2]==ja*10){s=false;}
								if(t[(i-k)-k2][(j-k)-k2]==0){
									ni.add(i-k); ni.add(j-k);
									d=max(d,MaxPrises((i-k)-k2,(j-k)-k2,ni,dame,cj));
									if(!dame)k2+=11; else k2++;
									ni.remove(ni.size()-1); ni.remove(ni.size()-1);
								}
						}
						if(amange){d++;}
					}
				}
				if(!dame)k=11; else k++;
	}
	return max(a,max(b,max(c,d)));
}

	public int max(int m, int n){
		if(m>=n) return m;
		else return n;
	};


	//Determine si une case est bien definie dans le plateau
	public boolean appartientPlateau(int i,int j){
		if(i>=0 && i<10 && j>=0 && j<10)
			return true;
		else return false;
	}


	//Ensuite voici l'algo qui a partir d'un pion choisit par le joueur, renvoit le tableau de boolïṡẄen des quatres cases, si T[i]=1: i est une des cases ïṡẄ jouer. Si T[i]=0 la case n'est pas accessible ou n'est pas le chemin le plus long.
	//Sachant que pour moi les cases a,b,c et d par rapport a un pion 1 sont :  a       b   (Ca me parait juste plus clair si on les appelle comme ca plutot que T[i-2][j-2] etc...)
	//																			  2   2
	//																			    1
	//																			  2   2
	//																			d       c
	public Vector<Integer> CasesAJouer(int i, int j, int cj){
		int k2=1,k=2,a=0,b=0,c=0,d=0,s=0,ja; 
		if(cj==1)
			ja=2;
		else ja=1;
		boolean n=true;
		Vector<Integer> nul=new Vector<Integer>();
		Vector<Integer> max=new Vector<Integer>();

		if(PeutManger(i,j,cj)){
			if(t[i][j]<10)		//Au cas ou on a un pion
			{
				if((i+k<=9 && j-k>=0))
				{
					if((t[i+k-1][j-k+1]==ja || t[i+k-1][j-k+1]==ja*10)&&t[i+k][j-k]==0)
					{
						nul.add(i+k-1); nul.add(j-k+1); 
						a=1+MaxPrises(i+k,j-k,nul,(t[i][j]>9),cj);
					}
				}
			if((i+k<=9 && j+k<=9))
			{
				if((t[i+k-1][j+k-1]==ja || t[i+k-1][j+k-1]==ja*10)&&t[i+k][j+k]==0)
				{
					nul.add(i+k-1); 
					nul.add(j+k-1);
					b=1+MaxPrises(i+k,j+k,nul,(t[i][j]>9),cj);
				}
			}
			if((i-k>=0 && j+k<=9 ))
			{
				if((t[i-k+1][j+k-1]==ja || t[i-k+1][j+k-1]==ja*10)&&t[i-k][j+k]==0)
				{
					nul.add(i-k+1); 
					nul.add(j+k-1);
					c=1+MaxPrises(i-k,j+k,nul,(t[i][j]>9),cj);
				}
			}
			if((i-k>=0 && j-k>=0))
			{
				if((t[i-k+1][j-k+1]==ja || t[i-k+1][j-k+1]==ja*10)&&t[i-k][j-k]==0)
				{
					nul.add(i-k+1); 
					nul.add(j-k+1);
					d=1+MaxPrises(i-k,j-k,nul,(t[i][j]>9),cj);
				}
			}
			s=max(a,max(b,max(c,d)));
			if((i+k<=9 && j-k>=0))
			{
				if(a==s && (t[i+k-1][j-k+1]==ja || t[i+k-1][j-k+1]==ja*10) && t[i+k][j-k]==0) 
				{
					max.add(i+k);
					max.add(j-k);
				}
			}
			if((i+k<=9 && j+k<=9))
			{
				if(b==s && (t[i+k-1][j+k-1]==ja || t[i+k-1][j+k-1]==ja*10) && t[i+k][j+k]==0) 
				{
					max.add(i+k);
					max.add(j+k);
			
				}
			}
			if((i-k>=0 && j+k<=9))
			{
				if(c==s && (t[i-k+1][j+k-1]==ja || t[i-k+1][j+k-1]==ja*10) && t[i-k][j+k]==0) 
				{
					max.add(i-k);
					max.add(j+k);
				}
			}
			if((i-k>=0 && j-k>=0))
			{
				if(d==s && (t[i-k+1][j-k+1]==ja || t[i-k+1][j-k+1]==ja*10) && t[i-k][j-k]==0) 
				{
					max.add(i-k);
					max.add(j-k);
				}
			}
		}
			//*********pour les dames**********
			else 
			{
				k=1;
				
				s=MaxPrises(i,j,nul,true,cj);
				
				nul= new Vector<Integer>();
				if(s>0){
			while(appartientPlateau(i+k,j+k) && n)
			{k2=1;
					if((t[i+k][j+k]==ja)||(t[i+k][j+k]==ja*10)){
							while(appartientPlateau(i+k+k2,j+k+k2)&&t[i+k+k2][j+k+k2]==0)
					{
						nul.add(i+k);
						nul.add(j+k);
						a=max(a,MaxPrises(i+k+k2,j+k+k2,nul,true,cj));
						nul=new Vector<Integer>();
						nul.add(i+k);
						nul.add(j+k);
						n=false;
						k2++;
					}
							if(!(appartientPlateau(i+k+1,j+k+1)&&t[i+k+1][j+k+1]==0))n=false;else a++;}
				else if(t[i+k][j+k]==cj||t[i+k][j+k]==cj*10)
					{
						n=false;}
						else{k++;}}
			if(a==s){
				k++;
				while(appartientPlateau(i+k,j+k)&&t[i+k][j+k]==0){
					if (MaxPrises(i+k,j+k,nul,true,cj)==s-1)
					{
					max.add(i+k);
					max.add(j+k);
					}
					k++;
					}}
				n=true;
				k=1;
				nul=new Vector<Integer>();
				while(appartientPlateau(i+k,j-k) && n)
				{k2=1;
						if((t[i+k][j-k]==ja)||(t[i+k][j-k]==ja*10)){
							while(appartientPlateau(i+k+k2,j-k-k2)&&t[i+k+k2][j-k-k2]==0)
						{
							nul.add(i+k);
							nul.add(j-k);			
							b=max(b,MaxPrises(i+k+k2,j-k-k2,nul,true,cj));		
							nul=new Vector<Integer>();
							nul.add(i+k);
							nul.add(j-k);
							n=false;
							k2++;
						}
								if(!(appartientPlateau(i+k+1,j-k-1)&&t[i+k+1][j-k-1]==0)) n=false;else b++;}
					else if(t[i+k][j-k]==cj||t[i+k][j-k]==cj*10)
						{
							n=false;}
							else{k++;}}
				if(b==s){
					k++;
					while(appartientPlateau(i+k,j-k)&&t[i+k][j-k]==0){
						if (MaxPrises(i+k,j-k,nul,true,cj)==s-1)
						{
						max.add(i+k);
						max.add(j-k);
						}
						k++;
						}}
					n=true;
					k=1;
					nul=new Vector<Integer>();
					while(appartientPlateau(i-k,j+k) && n)
					{k2=1;
							if((t[i-k][j+k]==ja)||(t[i-k][j+k]==ja*10)){
								while(appartientPlateau((i-k)-k2,j+k+k2)&&t[(i-k)-k2][j+k+k2]==0)
							{
								nul.add(i-k);
								nul.add(j+k);
								c=max(c,MaxPrises((i-k)-k2,(j+k)+k2,nul,true,cj));
								nul=new Vector<Integer>();
								nul.add(i-k);
								nul.add(j+k);
								n=false;
								k2++;
							}
							if(!(appartientPlateau((i-k)-1,j+k+1)&&t[(i-k)-1][j+k+1]==0)) n=false;else c++;}
						else if(t[i-k][j+k]==cj||t[i-k][j+k]==cj*10)
							{
								n=false;}
								else{k++;}}
					if(c==s){
						k++;
						while(appartientPlateau(i-k,j+k)&&t[i-k][j+k]==0){
							if (MaxPrises(i-k,j+k,nul,true,cj)==s-1)
							{
							max.add(i-k);
							max.add(j+k);
							}
							k++;
							}}
						n=true;
						k=1;
						nul=new Vector<Integer>();
						while(appartientPlateau(i-k,j-k) && n)
						{k2=1;
								if((t[i-k][j-k]==ja)||(t[i-k][j-k]==ja*10)){
									while(appartientPlateau(i-k-k2,j-k-k2)&&t[i-k-k2][j-k-k2]==0)
								{
									nul.add(i-k);
									nul.add(j-k);
									d=max(d,MaxPrises(i-k-k2,j-k-k2,nul,true,cj));
									nul=new Vector<Integer>();
									nul.add(i-k);
									nul.add(j-k);
									n=false;
									k2++;
								}
								if(!(appartientPlateau(i-k-1,j-k-1)&&t[i-k-1][j-k-1]==0)) n=false; else d++;}
							else if(t[i-k][j-k]==cj||t[i-k][j-k]==cj*10)
								{
									n=false;}
									else{k++;}}
						if(d==s){
							k++;
							while(appartientPlateau(i-k,j-k)&&t[i-k][j-k]==0){
								if (MaxPrises(i-k,j-k,nul,true,cj)==s-1)
								{
								max.add(i-k);
								max.add(j-k);
								}
								k++;
								}}}
			else{
				while(appartientPlateau(i+k,j+k)&&t[i+k][j+k]==0){
					max.add(i+k);max.add(j+k);
					k++;
				}
				k=1;
				while(appartientPlateau(i+k,j-k)&&t[i+k][j-k]==0){
					max.add(i+k);max.add(j-k);
					k++;
				}
				k=1;
				while(appartientPlateau(i-k,j+k)&&t[i-k][j+k]==0){
					max.add(i-k);max.add(j+k);
					k++;
				}
				k=1;
				while(appartientPlateau(i-k,j-k)&&t[i-k][j-k]==0){
					max.add(i-k);max.add(j-k);
					k++;
				}
			}}
			return max;}
		else{return CasesDispo(i,j,cj);}
	}

	// Retourne les cases disponible pour un pion qui ne peut pas manger d'adversaire.
	public Vector<Integer> CasesDispo(int i, int j, int cj) {
		boolean n = true, o = true, p = true, q = true;
		int k;
		if (cj == 1)
			k = -1;
		else 
			k = 1;
		Vector<Integer> m = new Vector<Integer>();
		if (t[i][j] > 2) { // Pour les dames
			while (k < 10 && k > -10) {
				if (i + k < 10 && j + k < 10 && i + k >= 0 && j + k >= 0) {
					if (t[i + k][j + k] == 0 && n) {
						m.add(i + k);
						m.add(j + k);
					} else {
							n = false;
					}
				}
				if (i + k < 10 && j - k > 0 && i + k >= 0 && j - k < 10) {
					if (t[i + k][j - k] == 0 && o) {
						m.add(i + k);
						m.add(j - k);
					} else {
							o = false;
					}
				}
				if (i - k > 0 && j + k < 10 && i - k < 10 && j + k >= 0) {
					if (t[i - k][j + k] == 0 && p) {
						m.add(i - k);
						m.add(j + k);
					} else {
							p = false;
					}
				}
				if (i - k > 0 && j - k > 0 && i - k < 10 && j - k < 10) {
					if (t[i - k][j - k] == 0 && q) {
						m.add(i - k);
						m.add(j - k);
					} else {
							q = false;
					}
				}
				if (cj == 1) {
					k--;
				} else {
					k++;
				}
			}
		} else {
			if (cj == 1) {
				if (j + 1 < 10 && t[i + k][j + 1] == 0) {
					m.add(i + k);
					m.add(j + 1);
				} // Pour les pions du joueur 1
				if (j - 1 >= 0 && t[i + k][j - 1] == 0) {
					m.add(i + k);
					m.add(j - 1);
				}
			} else {
				if (j + 1 < 10 && t[i + k][j + 1] == 0) {
					m.add(i + k);
					m.add(j + 1);
				} // et du joueur 2
				if (j - 1 >= 0 && t[i + k][j - 1] == 0) {
					m.add(i + k);
					m.add(j - 1);
				}
			}
		}
		return m;
	}

	//Execute le pion mangïṡẄ en fonction de la case de depart d'un pion et de sa case d'arrivïṡẄe et determine si le pion a bien mangïṡẄ
	public boolean PionMange(int i,int j, int i2, int j2,int cj, Joueur joueur1, Joueur joueur2){
		int ir,jr;
		int k=1,ja;
		if(cj==1){ja=2;}else{ja=1;}
		if(t[i2][j2]==cj){				//Pour les pions
			if(i2-1==i || i2+1==i){
				return false;}
			else{
				if(i2>i)
					ir=i2-1;
				else ir=i2+1;
				if(j2>j)
					jr=j2-1;
				else jr=j2+1;
				if ((cj==2) && (joueur2.statut==2)) System.out.println("L'ordinateur mange le pion: "+ir+" "+jr);
				else System.out.println("Vous mangez le pion: "+ir+" "+jr);	
				ircopie=ir;
				jrcopie=jr;
				if(t[ir][jr]<10)if(cj==1)joueur2.nombrePion-=1;
				else joueur1.nombrePion-=1;
				else if(cj==1)joueur2.nombreDame-=1;
				else joueur1.nombreDame-=1;
				t[ir][jr]=0;
				return true;}}
		else{ k=1;							//Pour les dames
			{while(i2-k>i && j2-k>j)
				{if(t[i2-k][j2-k]==ja || t[i2-k][j2-k]==ja*10)
				{if(t[i2-k][j2-k]<10){if(cj==1)joueur2.nombrePion-=1; else joueur1.nombrePion-=1;} else {if(cj==1)joueur2.nombreDame-=1; else joueur1.nombreDame-=1;} 
				t[i2-k][j2-k]=0;ircopie=i2-k;jrcopie=j2-k;if ((cj==2) && (joueur2.statut==2)) System.out.println("L'ordinateur a mangez le pion: "+(i2-k)+" "+(j2-k)); else System.out.println("Vous mangez le pion: "+(i2-k)+" "+(j2-k));return true;}k++;}}
			{while(i2-k>i && j2+k<j)
				{if(t[i2-k][j2+k]==ja || t[i2-k][j2+k]==ja*10)
				{if(t[i2-k][j2+k]<10){if(cj==1)joueur2.nombrePion-=1; else joueur1.nombrePion-=1;} else {if(cj==1)joueur2.nombreDame-=1; else joueur1.nombreDame-=1;} 
				t[i2-k][j2+k]=0;ircopie=i2-k;jrcopie=j2+k; if ((cj==2) && (joueur2.statut==2)) System.out.println("L'ordinateur a mangez le pion: "+(i2-k)+" "+(j2+k)); else System.out.println("Vous mangez le pion: "+(i2-k)+" "+(j2+k));return true;}k++;}}
			{while(i2+k<i && j2-k>j)
				{if(t[i2+k][j2-k]==ja || t[i2+k][j2-k]==ja*10)
				{if(t[i2+k][j2-k]<10){if(cj==1)joueur2.nombrePion-=1; else joueur1.nombrePion-=1;} else {if(cj==1)joueur2.nombreDame-=1; else joueur1.nombreDame-=1;} 
				t[i2+k][j2-k]=0;ircopie=i2+k;jrcopie=j2-k;if ((cj==2) && (joueur2.statut==2)) System.out.println("L'ordinateur a mangez le pion: "+(i2+k)+" "+(j2-k)); else System.out.println("Vous mangez le pion: "+(i2+k)+" "+(j2-k));return true;}k++;}}
			{while(i2+k<i && j2+k<j)
				{if(t[i2+k][j2+k]==ja || t[i2+k][j2+k]==ja*10)
				{if(t[i2+k][j2+k]<10){if(cj==1)joueur2.nombrePion-=1; else joueur1.nombrePion-=1;} else {if(cj==1)joueur2.nombreDame-=1; else joueur1.nombreDame-=1;} 
				t[i2+k][j2+k]=0;ircopie=i2+k;jrcopie=j2+k; if ((cj==2) && (joueur2.statut==2)) System.out.println("L'ordinateur a mangez le pion: "+(i2+k)+" "+(j2+k)); else System.out.println("Vous mangez le pion: "+(i2+k)+" "+(j2+k));return true;}k++;}}}
			
			return false;}


	//Creer une dame
	public void DameCreee(int i, int j, int cj, Joueur joueur1, Joueur joueur2){
		if((i==0 && cj==1)&&(t[i][j]!=10))
		{t[i][j]=10; joueur1.nombreDame+=1; joueur1.nombrePion-=1; System.out.println("La dame en "+i+" "+j+" a ïṡẄtïṡẄ crïṡẄïṡẄe.");}
		if((i==9 && cj==2)&&(t[i][j]!=20))
		{t[i][j]=20; joueur2.nombreDame+=1; joueur2.nombrePion-=1; System.out.println("La dame en "+i+" "+j+" a ïṡẄtïṡẄ crïṡẄïṡẄe.");}
	}


	//Affiche les choix de pions a jouer ou les cases disponibles a partir d'un pion
	public void AfficheChoix(Vector<Integer> q,int cj, String pseudo1, String pseudo2){
		int i=0;
		if (cj==1)
		{
			System.out.println(pseudo1 + ", voici les pions/cases que vous pouvez jouer:");
		}
		else
		{
			System.out.println(pseudo2 +", voici les pions/cases que vous pouvez jouer:");
		}
		while(i<q.size())
		{System.out.print(q.elementAt(i)); i=i+1; if(i%2==0)System.out.print(";"); else System.out.print(" ");}
		System.out.println("");
	}

	//Renvoit le pion/la case choisie par le joueur
	public Vector<Integer> Choix(Vector<Integer> q){
		Vector<Integer> pm=new Vector<Integer>(); int m=10,n=10;
		System.out.print("entrez votre choix de pion/case (la ligne puis la colonne):");
		while(!(q.contains(m)) || !(q.contains(n))){
			m=sc.nextInt();
			n=sc.nextInt();
			if(!(q.contains(m)) || !(q.contains(n)))
			{System.out.println("Erreur, entrez des coordonnïṡẄes valides");}}
		pm.add(m); pm.add(n);
		return pm;}
	
	public boolean EnDanger(int i, int j, int cj){ //Test si le pion va ÃẂtre mangÃ©
		int k=1,ja=2; if(cj==2)ja=1; boolean b=false,r=true;
		if(appartientPlateau(i+1,j+1) && appartientPlateau(i-1,j-1) && t[i+1][j+1]==ja && t[i-1][j-1]==0){b=true;} 
		if(appartientPlateau(i-1,j+1) && appartientPlateau(i+1,j-1) && t[i-1][j+1]==ja && t[i+1][j-1]==0){b=true;}
		if(appartientPlateau(i+1,j-1) && appartientPlateau(i-1,j+1) && t[i+1][j-1]==ja && t[i-1][j+1]==0){b=true;}
		if(appartientPlateau(i-1,j-1) && appartientPlateau(i+1,j+1) && t[i-1][j-1]==ja && t[i+1][j+1]==0){b=true;}
		else while(k<10 && r)
		{if(appartientPlateau(i+k,j+k)){if(t[i+k][j+k]!=ja*10 || t[i+k][j+k]!=0){r=false;}else {if(t[i+k][j+k]!=ja*10 && t[i-1][j-1]==0){b=true;}}}
		if(appartientPlateau(i-k,j+k)){if(t[i-k][j+k]!=ja*10 || t[i-k][j+k]!=0){r=false;}else {if(t[i-k][j+k]!=ja*10 && t[i+1][j-1]==0){b=true;}}}
		if(appartientPlateau(i+k,j-k)){if(t[i+k][j-k]!=ja*10 || t[i+k][j-k]!=0){r=false;}else {if(t[i+k][j-k]!=ja*10 && t[i-1][j+1]==0){b=true;}}}
		if(appartientPlateau(i-k,j-k)){if(t[i-k][j-k]!=ja*10 || t[i-k][j-k]!=0){r=false;}else {if(t[i-k][j-k]!=ja*10 && t[i+1][j+1]==0){b=true;}}}}
		return b;
	}
	
	public int ChoixIA1(Vector<Integer> q,int acc, boolean w,int cj){ //met ses pions partout oÃṗ ils peuvent ÃẂtre manger
		Vector<Integer> l=new Vector<Integer>();
		int n;
		n=(int)(Math.random()*(q.size()));
		if(n%2==1)n-=1;
		if(acc>=q.size()/2){return n;}
		if(w){l=CasesAJouer(q.elementAt(n),q.elementAt(n+1),cj);}
		else{l=q;}
		for(int i=0;i<l.size();i+=2)
		{if(!(EnDanger(l.elementAt(i),l.elementAt(i+1),cj))){n=ChoixIA1(q,acc+1,w,cj);}}
		return n;}
	
	public int ChoixIA2(Vector<Integer> q){ //random
		int n;
		n=(int)(Math.random()*(q.size()));
		if(n%2==1)n-=1;
		return n;}
	
	public int ChoixIA3(Vector<Integer> q,int acc, boolean w,int cj){ //Si il a le choix, il Ã©vite de mettre ses pions la oÃṗ ils vont ÃẂtre mangÃ©
		Vector<Integer> l=new Vector<Integer>();
		int n;
		n=(int)(Math.random()*(q.size()));
		if(n%2==1)n-=1;
		if(acc>=q.size()/2){return n;}
		if(w){l=CasesAJouer(q.elementAt(n),q.elementAt(n+1),cj);}
		else{l=q;}
		for(int i=0;i<l.size();i+=2)
		{if(EnDanger(l.elementAt(i),l.elementAt(i+1),cj)){n=ChoixIA3(q,acc+1,w,cj);}}
		return n;}

	public void JeuVirtuel(Joueur joueur1, Joueur joueur2,int cj) throws InterruptedException{
		boolean amange=false; int n,m;
		Vector<Integer> p=new Vector<Integer>();
		Vector<Integer> p2=new Vector<Integer>();
		Vector<Integer> q=new Vector<Integer>();
		q=DoitJouer(cj);
		if(!(q.isEmpty())){ Thread.sleep(300);
			if(cj==2){
			switch(joueur2.getNiveau()){
			case 1: n=ChoixIA1(q,0,true,cj); break;
			case 3: n=ChoixIA3(q,0,true,cj); break;
			default:n=ChoixIA2(q);}}
			else{
			switch(joueur1.getNiveau()){
			case 1: n=ChoixIA1(q,0,true,cj); break;
			case 3: n=ChoixIA3(q,0,true,cj); break;
			default:n=ChoixIA2(q);}}
		p.add(q.elementAt(n));
		p.add(q.elementAt(n+1));	
		q=new Vector<Integer>();
		if(t[p.elementAt(0)][p.elementAt(1)]>=10){dm++;}else{dm=0;}
		do{	q=CasesAJouer(p.elementAt(0),p.elementAt(1),cj);	
		if(!(q.isEmpty())){if(cj==2){
			switch(joueur2.getNiveau()){
			case 1: m=ChoixIA1(q,0,false,cj); break;
			case 3: m=ChoixIA3(q,0,false,cj); break;
			default: m=ChoixIA2(q);}}
			else{switch(joueur1.getNiveau()){
			case 1: m=ChoixIA1(q,0,false,cj); break;
			case 3: m=ChoixIA3(q,0,false,cj); break;
			default: m=ChoixIA2(q);}}
			p2.add(q.elementAt(m));
			p2.add(q.elementAt(m+1));
			t[p2.elementAt(0)][p2.elementAt(1)]=t[p.elementAt(0)][p.elementAt(1)];
			t[p.elementAt(0)][p.elementAt(1)]=0;
			amange=PionMange(p.elementAt(0),p.elementAt(1),p2.elementAt(0),p2.elementAt(1),cj,joueur1,joueur2);
			System.out.println("Le joueur virtuel a joué le pion :"+p.elementAt(0)+" "+p.elementAt(1)+" ïṡẄ la case :"+p2.elementAt(0)+" "+p2.elementAt(1));
			p=p2;
			p2=new Vector<Integer>();}
			else amange=false;}
		while(PeutManger(p.elementAt(0),p.elementAt(1),2) && amange);
		DameCreee(p.elementAt(0),p.elementAt(1),cj,joueur1,joueur2);}}


	//La fonction de jeu principale, appelée par le main de la classe Partie
	public void Jeu(int cj,Joueur joueur1, Joueur joueur2){
		boolean amange=false;								//Bool pour savoir si on fait une rafle
		Vector<Integer> p=new Vector<Integer>();			//CoordonnïṡẄes du pion choisit initialement
		Vector<Integer> p2=new Vector<Integer>();			//CoordonnïṡẄes de la case d'arrivïṡẄe
		Vector<Integer> q=new Vector<Integer>();			//Pions eligibles
		q=DoitJouer(cj); 										//DoitJouer(PeuventManger()) determine les pions qui peuvent jouer								
		System.out.println(joueur1.pseudo + " a: "+joueur1.nombrePion+" pions et "+joueur1.nombreDame+" dames");
		System.out.println(joueur2.pseudo + " a: "+joueur2.nombrePion+" pions et "+joueur2.nombreDame+" dames");
		System.out.println();
		//Au moins un pion peut manger un adversaire
		AfficheChoix(q,cj,joueur1.pseudo, joueur2.pseudo);									//Propose au joueur les pions possibles
		if(!(q.isEmpty())){
			p=Choix(q);											//Le joueur en choisit un, la fonction renvoit le tableau de deux entiers correspondant a ses coordonnïṡẄes.
			q=new Vector<Integer>();
			if(t[p.elementAt(0)][p.elementAt(1)]>=10){dm++;}else{dm=0;}
			do{	affiche();
			q=CasesAJouer(p.elementAt(0),p.elementAt(1),cj);							//CasesAJouer() determine dans quelles cases le pion peut aller
			AfficheChoix(q,cj,joueur1.pseudo, joueur2.pseudo);								//Propose au joueur les cases qu'il peut jouer
			p2=Choix(q);										//Le joueur en choisit une, la fonction renvoit le tableau de ses coordonïṡẄes.
			t[p2.elementAt(0)][p2.elementAt(1)]=t[p.elementAt(0)][p.elementAt(1)];
			t[p.elementAt(0)][p.elementAt(1)]=0;
			amange=PionMange(p.elementAt(0),p.elementAt(1),p2.elementAt(0),p2.elementAt(1),cj,joueur1,joueur2);				//PionMange() effectue les eliminations des pions et determine si un pion a ïṡẄtïṡẄ mangïṡẄ
			p=p2;
			p2=new Vector<Integer>();}
			while(PeutManger(p.elementAt(0),p.elementAt(1),cj) && amange);				//On refait cette boucle si le joueur a mangïṡẄ un pion et on continue tant que le pion selectionnïṡẄ peut manger un pion adverse (tant que PeutManger() retourne true)
			DameCreee(p.elementAt(0),p.elementAt(1),cj,joueur1,joueur2);}
		else bloque=cj;
				
	}								//DameCreee() test a la fin de chaque tour si le pion s'est ARRETE sur une case de la derniere ligne, on le transforme alors en dame

	
	public void reinitPlateau()
	{
		for(int i=0;i<10;i++)
		{
			for(int j=0;j<10;j++)
			{
				if((i+j)%2!=0 & i<4)	//les cases qui ont des pions noir
				{
					t[i][j]=2;
				}			
				else if((i+j)%2!=0 & i>5)	//les cases qui ont des pions blanc
				{
					t[i][j]=1;
				}
				else t[i][j]=0;
			}
			
		}
		
	}

}