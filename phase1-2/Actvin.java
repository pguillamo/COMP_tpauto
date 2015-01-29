public class Actvin {

	private static class Chauffeur {
		public int numchauf, bj, bg, ordin ;
		public String name;
		public SmallSet magdif ;
		public Chauffeur(int numchauf, String name, int bj,int bg,int ordin,SmallSet magdif) {
			this.numchauf = numchauf ; this.name = name ; this.bj = bj ; this.bg = bg ; 
			this.ordin = ordin ; this.magdif = magdif.clone() ;
		}
		public Chauffeur copie() {return new Chauffeur(numchauf, name, bj,bg,ordin,magdif);}
	} // class Chauffeur

	public static final int[][] action = {
		/* état        BJ    BG   IDENT  NBENT   ,     ;     /  AUTRES  */
		/* 0 */      { -1,   -1,    1,    -1,   -1,   -1,    9,   -1   },
		/* 1 */      {  6,    7,    3,     2,   -1,   -1,    9,   -1   },
		/* 2 */      {  6,    7,    3,    -1,   -1,   -1,    9,   -1   },
		/* 3 */      { -1,   -1,    3,    -1,   -1,   -1,    9,   -1   },
		/* 4 */      { -1,   -1,   -1,     4,   -1,   -1,    9,   -1   },
		/* 5 */      { -1,   -1,   -1,     3,    5,    8,    9,   -1   },
		/* 6 */      { -1,   -1,   -1,    -1,   -1,   -1,    9,   -1   }
	} ;	       

	private static final int FATALE = 0 , NONFATALE = 1 ;

	private static final int MAXCHAUF = 10 , MAXLGID = 20, MAXMAG = 10 ;
	
	private static final int VIN_BJ = 0, VIN_BG = 1, VIN_ORDIN = 2 ;
	
	private static Chauffeur[] tabchauf = new Chauffeur[MAXCHAUF] ;
	
	private static String[] tabmag = new String[MAXMAG];
	
	private static int curChauf = -1;
	private static int curMag = -1;
	private static int curVin = -1;
	private static int volume_citerne = -1;
	private static int volume_restant = -1;
	private static int nbFiches = 0;
	
	private static void attenteSurLecture(String mess) {
		String tempo ;
		System.out.println("") ;
		System.out.print(mess + " pour continuer tapez entrée ") ;
		tempo = Lecture.lireString() ;
	} // attenteSurLecture

	private static void erreur(int te,String messerr) {
		attenteSurLecture(messerr) ;
		switch (te) {
		case FATALE    : Vin.errcontr = true ; break ;
		case NONFATALE : Vin.etat = Autovin.ETATERR ; break ;
		default : attenteSurLecture("paramètre incorrect pour erreur") ;
		}
	} // erreur

	private static String chaineCadrageGauche(String ch) {
		/* en entrée : ch est une chaine de longueur quelconque
	   délivre la chaine ch cadrée à gauche sur MAXLGID caractères
		 */
		int lgch = Math.min(MAXLGID,ch.length()) ;
		String chres = ch.substring(0,lgch) ;
		for (int k = lgch ;k < MAXLGID ; k++) chres = chres + " " ;
		return chres ;
	} // chaineCadrageGauche

	public static void executer(int numact) {
		switch (numact) {
		case -1  :	break ;
		case  0  : // initialisations
			nbFiches = 0;
			break ;
		case  1  : // chauffeur
			curChauf++;
			nbFiches++;
			tabchauf[curChauf] = new Chauffeur(curChauf, Lexvin.repId(Lexvin.numId), 0, 0, 0, new SmallSet());
			volume_restant = volume_citerne = 100;
			curVin = VIN_ORDIN;
			curMag = -1;
			break ;
		case  2  : // volume citerne
			if (Lexvin.valNb >= 100 && Lexvin.valNb <= 200)
				volume_citerne = Lexvin.valNb;
			else
				volume_citerne = 100;
			volume_restant = volume_citerne;
			break ;
		case  3  : // magasin
			String magName = Lexvin.repId(Lexvin.numId);
			int i;
			curMag = -1;
			for (i=0; i<MAXMAG; i++) {
				if (tabmag[i].equals(magName))
					curMag = i;
			} 
			if (curMag == -1) {
				if (i >= MAXMAG)
					erreur(NONFATALE, "Nombre maximum de magasins atteint.");
				else {
					tabmag[i] = magName;
					curMag = i;
				}
			}
			if (curMag > -1) {
				tabchauf[curChauf].magdif.add(curMag);
			}
			
			break ;
		case  4  : // quantité
			int qty = Lexvin.valNb;
			if (qty > volume_restant) {
				erreur(NONFATALE, "Volume livré supérieur au volume restant.");
			}
			else if (qty == 0) {
				erreur(NONFATALE, "Volume livré nul.");
			}
			else {
				volume_restant -= qty;
				switch (curVin) {
				case VIN_ORDIN:
					tabchauf[curChauf].ordin += qty;
					break;
				case VIN_BJ:
					tabchauf[curChauf].bj += qty;
					break;
				case VIN_BG:
					tabchauf[curChauf].bg += qty;
					break;
				default:
					erreur(NONFATALE, "Type de vin inconnu.");	
				}
			}
			break ;
		case  5  : // fin de livraison
			curVin = VIN_ORDIN;
			volume_restant = volume_citerne;
			break ;
		case  6  : // Beaujolais
			curVin = VIN_BJ;
			break ;
		case  7  : // Bourgogne
			curVin = VIN_BG;
			break ;
		case  8  : // fin de fiche
			System.out.println("à la fin de la fiche n°"+nbFiches+" :");
			System.out.println("\t"+chaineCadrageGauche("CHAUFFEUR")+"BJ BG ORD NBMAG");
			break ;
		case  9  : // fin
			break ;
		default : attenteSurLecture("action " + numact + " non prévue") ;
		}
	} // executer
	
	public static void main(String[] args) {
		System.out.println("la classe Actvin ne possède pas de 'main'") ;
	} // main

} // class Actvin
