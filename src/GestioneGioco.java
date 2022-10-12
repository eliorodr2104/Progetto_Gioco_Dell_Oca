import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GestioneGioco {

    private final String directory, caselle;
    public ArrayList<Giocatore> giocatoreArrayList;
    private int scelta;

    public GestioneGioco(){
        this.directory = "json/caselle.json";
        this.caselle = "caselle";
        this.giocatoreArrayList = new ArrayList<>();
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

            if (scelta <= 0)
                System.out.println("Non puoi inserire un numero minore di zero");

            else if (scelta == 1)
                System.out.println("Non puoi fare una partita con un solo giocatore");

        }while (scelta <= 1);

        for (int i = 0; i < scelta; i++){
            giocatoreArrayList.add(new Giocatore(i, 0, 0, false));
        }
    }

    public void spostareGiocatore(int id, int risultatoDado, long casellaAttualeGiocatore, int numeroCaselle){
        long casellaAggiornata = risultatoDado + casellaAttualeGiocatore;

        if (casellaAggiornata > numeroCaselle)
            casellaAggiornata = casellaAggiornata - numeroCaselle;

        if (controllaCasellaSpeciale(casellaAggiornata)) {
            JSONObject casellaSpeciale = trovaCasellaSpeciale(casellaAggiornata);

            if ((boolean) casellaSpeciale.get("RimanereFermo")){
                giocatoreArrayList.get(id).setRimanereFermo(Math.toIntExact((long) casellaSpeciale.get("TurniRimanereFermo")));

            }else if ((boolean) casellaSpeciale.get("rimanereBloccato")){
                if (trovaUtenteCasellaBloccata(id) < 0){
                    giocatoreArrayList.get(id).setRimanereBloccato(true);

                }else{
                    giocatoreArrayList.get(id).setRimanereBloccato(true);
                    giocatoreArrayList.get(trovaUtenteCasellaBloccata(id)).setRimanereBloccato(false);
                }

            } else if ((boolean) casellaSpeciale.get("vaiDritto")) {
                casellaAggiornata = (long) casellaSpeciale.get("CasellaSpostamento");

            }else if ((boolean) casellaSpeciale.get("vaiAvanti")){
                casellaAggiornata = (int) (casellaAggiornata + (Long) casellaSpeciale.get("caselleDaMuoversi"));

            }else {
                casellaAggiornata = (int) (casellaAggiornata - (Long) casellaSpeciale.get("caselleDaMuoversi"));
            }

            if (casellaAggiornata > numeroCaselle)
                casellaAggiornata = casellaAggiornata - numeroCaselle;

            if (casellaAggiornata < 0)
                casellaAggiornata = 0;
        }

        if (!controlloPartitaFinita(numeroCaselle)) {
            if (!verificaCasellaLibera(casellaAggiornata)) {
                System.out.println(stampaDescrizioneCasellaSpeciale(casellaAggiornata));
                giocatoreArrayList.get(id).setPosizioneGiocatore(casellaAggiornata);

            } else {
                System.out.println(stampaDescrizioneCasellaSpeciale(casellaAggiornata));
                giocatoreArrayList.get(prendereIdGiocatoreCasella(casellaAttualeGiocatore)).setPosizioneGiocatore(casellaAttualeGiocatore);
                giocatoreArrayList.get(id).setPosizioneGiocatore(casellaAggiornata);
            }
        }

        if (controlloPartitaFinita(numeroCaselle))
            System.out.println("Il giocatore N^" + id + " Ha vinto la partita!!\n" + "Congratulazioni!!!");

    }

    public JSONObject trovaCasellaSpeciale(long casellaGiocatore) {
        GestioneJson gestioneJson = new GestioneJson(directory);

        JSONArray caselleArray = gestioneJson.totaleCaselle(caselle);
        JSONObject casellaSpeciale = new JSONObject();

        for (Object o : caselleArray) {
            JSONObject casellaCapitata = (JSONObject) o;

            Long casella = (Long) casellaCapitata.get("idCasella");

            if (casellaGiocatore == casella){
                casellaSpeciale = casellaCapitata;
            }
        }

        return casellaSpeciale;
    }

    public boolean controllaCasellaSpeciale(long casellaGiocatore){
        boolean controlloCasella = false;
        GestioneJson gestioneJson = new GestioneJson(directory);

        JSONArray caselleArray = gestioneJson.totaleCaselle(caselle);

        for (Object o : caselleArray) {
            JSONObject casellaCapitata = (JSONObject) o;

            Long casella = (Long) casellaCapitata.get("idCasella");

            if (casellaGiocatore == casella){
                controlloCasella = true;
            }
        }

        return controlloCasella;
    }

    public int trovaUtenteCasellaBloccata(int idGiocatoreArrivato){
        int idUtenteTrovato = -1;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (giocatore.isRimanereBloccato())
                if (giocatore.getId() != idGiocatoreArrivato)
                    idUtenteTrovato = giocatore.getId();
        }

        return idUtenteTrovato;
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

    public String stampaDescrizioneCasellaSpeciale(long casellaCorrente){
        String descrizioneCasella = "";
        GestioneJson gestioneJson = new GestioneJson(directory);

        JSONArray caselleArray = gestioneJson.totaleCaselle(caselle);

        for (Object o : caselleArray) {
            JSONObject caselleSpeciali = (JSONObject) o;

            Long idCasella = (Long) caselleSpeciali.get("idCasella");

            if (casellaCorrente == idCasella)
                descrizioneCasella = (String) caselleSpeciali.get("descrizioneCasella");

        }

        return descrizioneCasella;
    }

    public boolean verificaCasellaLibera(long posizioneGiocatore){
        boolean giocatoreTrovato = false;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (posizioneGiocatore == giocatore.getPosizioneGiocatore()) {
                giocatoreTrovato = true;
                break;
            }
        }

        return giocatoreTrovato;
    }

    public int prendereIdGiocatoreCasella(long posizioneGiocatore){
        int idGiocatoreCasella = 0;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (posizioneGiocatore == giocatore.getPosizioneGiocatore()) {
                 idGiocatoreCasella = giocatore.getId();
            }
        }

        return idGiocatoreCasella;
    }

    public int tiraDado(){
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci 1 per tirare il dado");


        try {
            do {
                scelta = Integer.parseInt(scanner.next());

                if (scelta != 1)
                    System.out.println("Valore inserito no valido");

            }while (scelta != 1);

        }catch (NumberFormatException e){
            System.out.println("Non puoi inserire una lettera");
        }

        return random.nextInt(12+1);
    }
}
