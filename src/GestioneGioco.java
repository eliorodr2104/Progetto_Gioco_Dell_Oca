import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Scanner;

public class GestioneGioco {

    private final String directory, caselle;
    public ArrayList<Giocatore> giocatoreArrayList;
    private int scelta;

    public GestioneGioco(){
        this.directory = "json/caselle.json";
        this.caselle = "caselle";
        this.scelta = 0;
    }

    public void creaGiocatori(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Quanti giocatori giocheranno?");

        do {
            try {
                scelta = Integer.parseInt(scanner.next());

            }catch (NumberFormatException e){
                System.out.println("Non puoi inserire lettere");
            }

            if (scelta < 0)
                System.out.println("Non puoi inserire un numero minore di zero");

            else if (scelta == 1)
                System.out.println("Non puoi fare una partita con un solo giocatore");

        }while (scelta <= 1);

        for (int i = 0; i < scelta; i++){
            giocatoreArrayList.add(new Giocatore(i, 0, 1500, true));
        }
    }

    public void andareAvantiCasella(int risultatoDado, int casellaAttualeGiocatore){
        GestioneJson gestioneJson = new GestioneJson(directory);

        JSONArray caselleArray = gestioneJson.totaleProdotti(caselle);

        int casellaAggiornata = risultatoDado + casellaAttualeGiocatore;

        System.out.println(caselleArray.get(casellaAggiornata - 1));
    }
}
