import java.io.* ; import java.awt.* ;
public class Lexvin {

    public static final int // codage des items
	BEAUJOLAIS = 0, BOURGOGNE = 1, IDENT = 2, NBENTIER = 3, 
        VIRGULE = 4, PTVIRG = 5, BARRE = 6, AUTRES = 7 ;
    public static final String [] item = {
	"BEAUJ", "BOURG", "IDENT", "NBENT", "  ,  ", "  ;  ", "  /  ", "AUTRE" 
    } ;

    public static int valNb, numId ; // attributs lexicaux

    private static InputStream f ; // fichier logique d'entr�e
    private static TextArea fen ; // fen�tre d'entr�e en cours d'analyse
    private static char carlu ; // caract�re courant
    private static final int NBRES = 2 ; // nombre de mots r�serv�s
    private static final int MAXID = 200 ; // nombre maximum d'ident
    private static String[] tabid = new String[MAXID+NBRES] ; 
                             // table des mots r�serv�s suivis des ident
    private static int itab ; // indice de remplissage de tabid

    private static void attenteSurLecture(String mess) {
	String tempo ;
	System.out.println("") ;
	System.out.print(mess + " pour continuer tapez entr�e ") ;
	tempo = Lecture.lireString() ;
    } // attenteSurLecture

    public static void debutAnalyse(TextArea fenentree) {
	String nomfich ;
	fen = fenentree ;
	carlu = ' ' ; itab = NBRES-1 ;
	tabid[0] = "BEAUJOLAIS" ; tabid[1] = "BOURGOGNE" ;
	System.out.print("nom du fichier d'entr�e : ") ;
	nomfich = Lecture.lireString() ;
	f = Lecture.ouvrir(nomfich) ;
	if (f == null) {
	    attenteSurLecture("fichier " + nomfich + " inexistant") ;
	    System.exit(0) ;
	}
    } // debutAnalyse

    public static void finAnalyse() {
	Lecture.fermer(f) ;
	attenteSurLecture("fin d'analyse") ;
    } // finAnalyse

    private static void lirecar() {
	carlu = Lecture.lireChar(f) ; 
	if (carlu == '\r') carlu = ' ' ;
	fen.append("" + carlu) ; // la valeur carlu est transform�e en cha�ne 
	if (Character.isWhitespace(carlu)) carlu = ' ' ;
	else carlu = Character.toUpperCase(carlu) ;
    } // lirecar

    public static String repId(int nid) {
	if (nid <= itab)	return  tabid[nid];
	else return ("erreur de num�ro d'identifiant")
    } // repId

    public static int liresymb() {
    	while (carlu == ' ' || carlu == "/n") lirecar();
    	String mot = carlu; 
    	
    	// cas de la virgule 
    	if (mot.equals(";")) return VIRGULE;
    	
    	
       	// cas du point-virgule
    	if (mot.equals(";")) return PTVIRG;
    	
    	
    	// cas de la barre
    	if (mot.equals("/")) return PTVIRG;    	
    	
    	// cas des nombres entier
    	if (carlu >= "0" && carlu <= "9"){
    		lirecar();
    		while carlu(carlu >= "0" && carlu <= "9"){
    			mot = mot + carlu;
    			lirecar();
    		}
	    		valNb= Integer.parseInt(mot);
    			return NBENTIER;
    	}
    	
    	// cas de Beaujolais, Bourgogne et ITEMS
    	if (carlu >= "A" && carlu <= "Z"){
    		lirecar();
    		while (carlu <= "A" && carlu >= "Z") {
    			mot = mot + carlu;
    			lirecar;
    		}
    		
    		if (mot.equals(BOURGOGNE)) return BOURGOGNE;
    		if (mot.equals(BEAUJOLAIS)) return BEAUJOLAIS;
    		
    		
    		int i=NBRES;
    		while (i <= itab){
    				if (mot.equals(tabid[i])) { 
    					numid = i;
    					return IDENT;
    				}
    				else i++;
    		}
    		
    		if (itab <= MAXID -1 ) {
    			itab ++;
    			tabid[itab]= mot;
    			numid = itab;
    			return IDENT;
    		}
    	}
    	
    	else return AUTRE;
    	}
    		
    		    		
    		
	return IDENT ;
    } // liresymb

    public static void main(String[] args) {
	System.out.println("la classe Lexvin ne poss�de pas de 'main'") ;
    } // main
} // class Lexvin
