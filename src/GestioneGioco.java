import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author E.Rodriguez, A.Duta
 * @date 12/10/2022
 * @version Java 19
 * @description Classe GestioneGioco la quale gestisce tutta la logica del gioco
 */

public class GestioneGioco {
    private final String directory, caselle;
    public ArrayList<Giocatore> giocatoreArrayList;
    private int scelta;

    /**
     * Metodo costruttore()
     */
    public GestioneGioco(){
        this.directory = "json/caselle.json";
        this.caselle = "caselle";
        this.giocatoreArrayList = new ArrayList<>();
        this.scelta = 0;
    }

    /**
     * Metodo creaGiocatori(), che crea i giocatori
     */
    public void creaGiocatori(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nQuanti giocatori giocheranno?");
        do {
            try {
                scelta = Integer.parseInt(scanner.next());

                if (scelta <= 0)
                    System.out.println("Non puoi inserire un numero minore di zero");

                else if (scelta > 4)
                    System.out.println("Non puoi fare una partita con più di 4 giocatori");

                else if (scelta == 1)
                    System.out.println("Non puoi fare una partita con un solo giocatore");

            }catch (NumberFormatException e){
                System.out.println("Non puoi inserire lettere");
            }

        }while (scelta <= 1 || scelta > 4);

        for (int i = 0; i < scelta; i++){
            giocatoreArrayList.add(new Giocatore(i, 0, 0, false));
        }
    }

    /**
     * Metodo caricaPartita(), che prende l'informazione da un file json e fa giocare una partita salvata in precedenza
     * @return il numero di caselle massime che hanno scelto nella partita scorsa
     */
    public long caricaPartita(){
        long caselleMax = 0, idUtente, posizioneGiocatore, rimanereFermo;

        GestioneJson gestioneJson = new GestioneJson("json/partitaSalvata.json");

        JSONArray statoPartita = gestioneJson.partiteSalvateLeggere("partite");

        if (!statoPartita.isEmpty()){
            for (int i = 0; i < statoPartita.size(); i++){
                JSONObject jsonObject = (JSONObject) statoPartita.get(i);

                caselleMax = (long) jsonObject.get("caselleMax" + i);
                idUtente = (long) jsonObject.get("id" + i);
                posizioneGiocatore = (long) jsonObject.get("posizioneGiocatore" + i);
                rimanereFermo = (long) jsonObject.get("rimanereFermo" + i);

                giocatoreArrayList.add(new Giocatore((int) idUtente, (int) posizioneGiocatore, (int) rimanereFermo, (Boolean) jsonObject.get("rimanereBloccato" + i)));
            }

        }else
            caselleMax = -1;

        return caselleMax;
    }

    /**
     * Metodo spostareGiocatore(), che prende il giocatore che ha tirato i dadi e lo fa spostare controllando anche
     * le caselle speciali
     * @param id del giocatore che sta giocando il turno
     * @param risultatoDado dado tirato dal giocatore
     * @param casellaAttualeGiocatore la casella dove si trova il giocatore al tirare il dado
     * @param numeroCaselle il numero di caselle massime che stanno giocando
     */
    public void spostareGiocatore(int id, int risultatoDado, long casellaAttualeGiocatore, int numeroCaselle){
        int restoSottrazioneCasella;
        long casellaAggiornata = risultatoDado + casellaAttualeGiocatore;

        if (casellaAggiornata > numeroCaselle){
            restoSottrazioneCasella = (int) (casellaAggiornata - numeroCaselle);

            casellaAggiornata = casellaAggiornata - restoSottrazioneCasella;
        }


        if (controllaCasellaSpeciale(casellaAggiornata)) {
            JSONObject casellaSpeciale = trovaCasellaSpeciale(casellaAggiornata);

            if ((boolean) casellaSpeciale.get("RimanereFermo")){
                System.out.println(trovaDescrizioneCasellaSpeciale(casellaAggiornata));
                giocatoreArrayList.get(id).setRimanereFermo(Math.toIntExact((long) casellaSpeciale.get("TurniRimanereFermo")));

            }else if ((boolean) casellaSpeciale.get("rimanereBloccato")){
                System.out.println(trovaDescrizioneCasellaSpeciale(casellaAggiornata));
                if (trovaUtenteCasellaBloccata(id) < 0){
                    giocatoreArrayList.get(id).setRimanereBloccato(true);

                }else{
                    giocatoreArrayList.get(id).setRimanereBloccato(true);
                    giocatoreArrayList.get(trovaUtenteCasellaBloccata(id)).setRimanereBloccato(false);
                }

            } else if ((boolean) casellaSpeciale.get("vaiDritto")) {
                System.out.println(trovaDescrizioneCasellaSpeciale(casellaAggiornata));
                casellaAggiornata = (long) casellaSpeciale.get("CasellaSpostamento");

            }else if ((boolean) casellaSpeciale.get("vaiAvanti")){
                System.out.println(trovaDescrizioneCasellaSpeciale(casellaAggiornata));
                casellaAggiornata = (int) (casellaAggiornata + (Long) casellaSpeciale.get("caselleDaMuoversi"));

            }else {
                System.out.println(trovaDescrizioneCasellaSpeciale(casellaAggiornata));
                casellaAggiornata = (int) (casellaAggiornata - (Long) casellaSpeciale.get("caselleDaMuoversi"));
            }

            if (casellaAggiornata > numeroCaselle)
                casellaAggiornata = casellaAggiornata - numeroCaselle;

            if (casellaAggiornata < 0)
                casellaAggiornata = 0;
        }

        if (!verificaCasellaLibera(casellaAggiornata, id)) {
            giocatoreArrayList.get(id).setPosizioneGiocatore(casellaAggiornata);

        } else {
            giocatoreArrayList.get(prendereIdGiocatoreCasella(casellaAttualeGiocatore, id)).setPosizioneGiocatore(casellaAttualeGiocatore);
            giocatoreArrayList.get(id).setPosizioneGiocatore(casellaAggiornata);
        }

        if (controlloPartitaFinita(numeroCaselle))
            System.out.println("Il giocatore N^" + id + " Ha vinto la partita!!\n" + "Congratulazioni!!!");

    }

    /**
     * Metodo controlloCasellaSpeciale(), verifica se il giocatore è arrivato in una casella speciale
     * @param casellaGiocatore la casella dov'è arrivato il giocatore
     * @return true se il giocatore è arrivato in una casella speciale, altrimenti false
     */
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

    /**
     * Metodo trovaCasellaSpeciale(), che cerca la casella speciale dov'è capitato il giocatore
     * @param casellaGiocatore la casella dov'è il giocatore
     * @return la casellaSpeciale
     */
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

    /**
     * Metodo trovaUtenteCasellaBloccata(), che verifica se dentro la casella dove arriva il giocatore c'è una persona
     * bloccata
     * @param idGiocatoreArrivato l'id del giocatore ch'è arrivato
     * @return l'id dell'utente dentro alla casella
     */
    public int trovaUtenteCasellaBloccata(int idGiocatoreArrivato){
        int idUtenteTrovato = -1;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (giocatore.isRimanereBloccato())
                if (giocatore.getId() != idGiocatoreArrivato && giocatore.getPosizioneGiocatore() == giocatoreArrayList.get(idGiocatoreArrivato).getPosizioneGiocatore())
                    idUtenteTrovato = giocatore.getId();
        }

        return idUtenteTrovato;
    }

    /**
     * Metodo controlloPartitaFinita(), che controlla se un'utente ha raggiunto la casella finale
     * @param numeroCaselle il numero della casella finale
     * @return true se l'utente ha raggiunto l'ultima casella, altrimenti false
     */
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

    /**
     * Metodo trovaDescrizioneCasellaSpeciale(), che trova la descrizione della casella speciale nella quale è
     * arrivato l'utente
     * @param casellaCorrente la casella nella quale è arrivato l'utente
     * @return la descrizione della casella
     */
    public String trovaDescrizioneCasellaSpeciale(long casellaCorrente){
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

    /**
     * Metodo vericaCasellaLibera(), che cerca se nella casella nella quale è arrivato l'utente c'è già qualcuno
     * @param posizioneGiocatore la posizione del giocatore arrivato
     * @param id del giocatore arrivato
     * @return true se c'era qualcuno, altrimenti false
     */
    public boolean verificaCasellaLibera(long posizioneGiocatore, int id){
        boolean giocatoreTrovato = false;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (posizioneGiocatore == giocatore.getPosizioneGiocatore() && id != giocatore.getId()) {
                giocatoreTrovato = true;
                break;
            }
        }

        return giocatoreTrovato;
    }

    /**
     * Metodo prendereIdGiocatoreCasella(), che cerca l'id del giocatore ch'è dentro la casella
     * @param posizioneGiocatore la posizione del giocatore
     * @return id del giocatore dentro la casella
     */
    public int prendereIdGiocatoreCasella(long posizioneGiocatore, int id){
        int idGiocatoreCasella = 0;

        for (Giocatore giocatore : giocatoreArrayList) {
            if (posizioneGiocatore == giocatore.getPosizioneGiocatore() && id != giocatore.getId()) {
                 idGiocatoreCasella = giocatore.getId();
            }
        }

        return idGiocatoreCasella;
    }

    /**
     * Metodo tiraDado(), che genera un numero random con la classe Random
     * @return il valore random
     */
    public int tiraDado(){
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Inserisci 1 per tirare il dado");


        do {
            try {
                scelta = Integer.parseInt(scanner.next());

                if (scelta != 1)
                    System.out.println("Valore inserito no valido");

            }catch (NumberFormatException e){
                System.out.println("Non puoi inserire una lettera");
            }

        }while (scelta != 1);

        return random.nextInt(12+1);
    }

    /**
     * Metodo tuttiGiocatoriBloccati(), che controlla se tutti i giocatori sono bloccati
     * @return true se tutto i giocatori sono bloccati, altrimenti false
     */
    public boolean tuttiGiocatoriBloccati(){
        int contaBloccati = 0;

        for (Giocatore giocatore : giocatoreArrayList)
            if (giocatore.isRimanereBloccato())
                contaBloccati++;

        return contaBloccati == giocatoreArrayList.size();
    }
}
