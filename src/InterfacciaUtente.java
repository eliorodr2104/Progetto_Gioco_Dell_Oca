import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author E.Rodriguez, A.Duta
 * @date 12/10/2022
 * @version Java 19
 * @description Classe InterfacciaUtente la quale rende il programma capibile per una persona
 */

public class InterfacciaUtente {
    GestioneGioco gestioneGioco = new GestioneGioco();

    private int numCaselle;
    private int scelta;
    private String sceltaSalvataggio;

    /**
     * Metodo costruttore()
     */
    public InterfacciaUtente(){
        this.numCaselle = 0;
        this.scelta = 0;
        this.sceltaSalvataggio = "";

    }

    /**
     * Metodo startMenu(), che avvia il primo menu del progetto
     */
    public void startMenu(){
        Scanner s1 = new Scanner(System.in);
        GestioneTimer gestioneTimer = new GestioneTimer(1, 10);

        System.out.println("           ,adPPYb,d8  ,adPPYba,   ,adPPYba,  ,adPPYba,  ,adPPYba,  ");
        System.out.println("          a8\"    `Y88 a8\"     \"8a a8\"     \"8a I8[    \"\" a8P_____88  ");
        System.out.println("          8b       88 8b       d8 8b       d8  `\"Y8ba,  8PP\"\"\"\"\"\"\"  ");
        System.out.println("          \"8a,   ,d88 \"8a,   ,a8\" \"8a,   ,a8\" aa    ]8I \"8b,   ,aa  ");
        System.out.println("           `\"YbbdP\"Y8  `\"YbbdP\"'   `\"YbbdP\"'  `\"YbbdP\"'  `\"Ybbd8\"'  ");
        System.out.println("           aa,    ,88                                               ");
        System.out.println("            \"Y8bbdP\"      \n");

        System.out.println("|-------------------------------------------------------------------------|");
        System.out.println("|                                 BENVENUTI                               |");
        System.out.println("|                                    al                                   |");
        System.out.println("|                              Gioco dell'oca                             |");
        System.out.println("|-------------------------------------------------------------------------|\n");

        do {
            System.out.println("0. Continua partita\n" + "1. Nuova partita");
            do {
                try{
                    scelta = Integer.parseInt(s1.next());

                    if (scelta < 0 || scelta > 1)
                        System.out.println("Valori inseriti non validi");

                }catch (NumberFormatException e){
                    System.out.println("Non puoi inserire lettere");
                }
            }while (scelta < 0 || scelta > 1);

            switch (scelta){
                case 0 -> {
                    System.out.println("\nCaricamento");
                    numCaselle = (int) gestioneGioco.caricaPartita();
                    gestioneTimer.timer();

                    if (numCaselle != -1)
                        partita();

                }
                case 1 -> {
                    inizializzaPartita();
                    partita();
                }

            }

            if (numCaselle == -1)
                System.out.println("Non puoi caricare una partita dato che non ci sono partite salvate!!");
        }while (numCaselle == -1);


    }

    /**
     * Metodo inizializzaPartita(), che setta la quantità massima di caselle e crea i giocatori
     */
    public void inizializzaPartita(){
        Scanner s2 = new Scanner(System.in);

        gestioneGioco.creaGiocatori();

        System.out.println("Inserire con quante caselle si vuole giocare: ");
        do {
            try {
                numCaselle = Integer.parseInt(s2.next());
                if(numCaselle <= 0 || numCaselle > 90){
                    System.out.println("Il numero di caselle non puo essere minore di 0 o maggiore di 90!!!!!");
                    System.out.println("Reinserire:");
                }
            }catch (NumberFormatException e){
                System.out.println("Non puoi inserire lettere");
            }

        }while (numCaselle <= 12 || numCaselle > 90);
    }

    /**
     * Metodo stampaResoconto(), che stampa il resoconto alla fine di ogni turno
     * @param id del giocatore
     */
    public void stampaResoconto(int id){
        System.out.println("Il giocatore N^: " + id + "\n" +
                "Ti trovi nella casella: " + gestioneGioco.giocatoreArrayList.get(id).getPosizioneGiocatore() + "\n");
    }

    /**
     * Metodo partita(), che esegue la partita e stampa ogni aspetto di essa
     */
    public void partita(){
        int contatore = 0;

        do {
            for (int i = 0; i < gestioneGioco.giocatoreArrayList.size(); i++){
                if (!gestioneGioco.controlloPartitaFinita(numCaselle)){
                    if (gestioneGioco.giocatoreArrayList.get(i).isRimanereBloccato()){
                        System.out.println("Giocatore N^: " + gestioneGioco.giocatoreArrayList.get(i).getId() + " sei bloccato, devi aspettare che arriva un altro giocatore nella casella: " + gestioneGioco.giocatoreArrayList.get(i).getPosizioneGiocatore());

                    }else{
                        if (gestioneGioco.giocatoreArrayList.get(i).getRimanereFermo() > 0){
                            System.out.println("L'utente deve rimanere fermo per: " + gestioneGioco.giocatoreArrayList.get(i).getRimanereFermo());
                            gestioneGioco.giocatoreArrayList.get(i).setRimanereFermo(gestioneGioco.giocatoreArrayList.get(i).getRimanereFermo() - 1);

                        }else {
                            System.out.println("|-------------------------|");
                            System.out.println("|Turno del giocatore N^: " + i + "|");
                            System.out.println("|-------------------------|");
                            gestioneGioco.spostareGiocatore(i, gestioneGioco.tiraDado(), gestioneGioco.giocatoreArrayList.get(i).getPosizioneGiocatore(), numCaselle);
                            stampaResoconto(i);
                        }

                    }
                }
            }
            contatore++;

            if (contatore == 10 && !gestioneGioco.controlloPartitaFinita(numCaselle))
                contatore = salvaPartita();

        }while (!gestioneGioco.controlloPartitaFinita(numCaselle) && !gestioneGioco.tuttiGiocatoriBloccati());

        if (gestioneGioco.tuttiGiocatoriBloccati())
            System.out.println("La partita è finita perchè tutti i giocatori sono bloccati nelle caselle speciali");

    }

    /**
     * Metodo salvaPartita(), che chiede all'utente se desidera salvare la partita
     * @return zero per resettare il contatore del metodo partita()
     */
    public int salvaPartita(){
        GestioneJson gestioneJson = new GestioneJson("json/partitaSalvata.json");
        Scanner s2 = new Scanner(System.in);

        System.out.println();
        System.out.println("|---------------------|");
        System.out.println("| Vuoi salvare ?? y/n |");
        System.out.println("|---------------------|");
        System.out.println();

        do {
            sceltaSalvataggio = s2.nextLine();

            if (!sceltaSalvataggio.equals("y") && !sceltaSalvataggio.equals("n"))
                System.out.println("Errore reinserire!!");

        }while (!sceltaSalvataggio.equals("y") && !sceltaSalvataggio.equals("n"));

        if (sceltaSalvataggio.equals("y")){
            try {
                gestioneJson.salvarePartitaJson(gestioneGioco.giocatoreArrayList, numCaselle);
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return 0;
    }
}
