import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    public void spostareGiocatore(int id, int risultatoDado, int casellaAttualeGiocatore, int numeroCaselle){
        GestioneJson gestioneJson = new GestioneJson(directory);

        JSONArray caselleArray = gestioneJson.totaleCaselle(caselle);

        int casellaAggiornata = risultatoDado + casellaAttualeGiocatore;

        if (casellaAggiornata > numeroCaselle)
            casellaAggiornata = casellaAggiornata - numeroCaselle;

        for (Object o : caselleArray) {
            JSONObject casellaCapitata = (JSONObject) o;

            if (casellaAggiornata == (int) casellaCapitata.get("idCasella")) {
                if ((boolean) casellaCapitata.get("vaiAvanti"))
                    casellaAggiornata = casellaAggiornata + (int) casellaCapitata.get("caselleDaMuoversi");

                else
                    casellaAggiornata = casellaAggiornata - (int) casellaCapitata.get("caselleDaMuoversi");

                if (casellaAggiornata > numeroCaselle)
                    casellaAggiornata = casellaAggiornata - numeroCaselle;

            }
        }

        if (casellaAggiornata != numeroCaselle) {
            if (!verificaCasellaLibera(casellaAggiornata)) {
                giocatoreArrayList.get(id).setPosizioneGiocatore(casellaAggiornata);

            } else {
                giocatoreArrayList.get(prendereIdGiocatoreCasella(casellaAttualeGiocatore)).setPosizioneGiocatore(casellaAttualeGiocatore);
                giocatoreArrayList.get(id).setPosizioneGiocatore(casellaAggiornata);
            }

        }else
            System.out.println("Il giocatore N^" + id + " Ha vinto la partita!!\n" + "Congratulazioni!!!");
    }

    public boolean controlloPartitaFinita(int numeroCaselle){
        boolean partitaFinita = false;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (giocatore.getPosizioneGiocatore() == numeroCaselle) {
                partitaFinita = true;
                break;
            }
        }

        return partitaFinita;
    }

    public String stampaDescrizioneCasellaSpeciale(int casellaCorrente){
        String descrizioneCasella = "";
        GestioneJson gestioneJson = new GestioneJson(directory);

        JSONArray caselleArray = gestioneJson.totaleCaselle(caselle);

        for (Object o : caselleArray) {
            JSONObject caselleSpeciali = (JSONObject) o;

            if (casellaCorrente == (int) caselleSpeciali.get("idCasella"))
                descrizioneCasella = (String) caselleSpeciali.get("descrizioneCasella");

        }

        return descrizioneCasella;
    }

    public boolean verificaCasellaLibera(int posizioneGiocatore){
        boolean giocatoreTrovato = false;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (posizioneGiocatore == giocatore.getPosizioneGiocatore()) {
                giocatoreTrovato = true;
                break;
            }
        }

        return giocatoreTrovato;
    }

    public int prendereIdGiocatoreCasella(int posizioneGiocatore){
        int idGiocatoreCasella = 0;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (posizioneGiocatore == giocatore.getPosizioneGiocatore()) {
                 idGiocatoreCasella = giocatore.getId();
            }
        }

        return idGiocatoreCasella;
    }
}
