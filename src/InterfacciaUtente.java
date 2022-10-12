
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class InterfacciaUtente {

    GestioneGioco gestioneGioco = new GestioneGioco();

    private int numCaselle;
    private int scelta;
    private String sceltaSalvataggio;

    public InterfacciaUtente(){

        this.numCaselle = 0;
        this.scelta = 0;
        this.sceltaSalvataggio = "";

    }
    public void startMenu(){
        int ngiocatori = 0;
        Scanner s1 = new Scanner(System.in);

        System.out.println("--------------");
        System.out.println("Gioco dell'Oca");
        System.out.println("--------------");

        System.out.println("0. Continua partita");
        System.out.println("1. Nuova partita");
        scelta = s1.nextInt();

        switch (scelta){

            case 0 -> System.out.println("Caricamento");
            case 1 -> partita();

        }


    }

    public void stampaResoconto(int id){
        System.out.println("Il giocatore N^: " + id + "\n" +
                "Avanazato alla casella: " + gestioneGioco.giocatoreArrayList.get(id).getPosizioneGiocatore() + "\n");
    }

    public void partita(){
        GestioneJson gestioneJson = new GestioneJson("json/partitaSalvata.json");

        int contatore = 0;

        Scanner s2 = new Scanner(System.in);


        gestioneGioco.creaGiocatori();

        do {
            System.out.println("Inserire con quante caselle si vuole giocare: ");
            numCaselle = s2.nextInt();
            if(numCaselle <= 0 || numCaselle > 90){
                System.out.println("Il numero di caselle non puo essere minore di 0 o maggiore di 90!!!!!");
                System.out.println("Reinserire:");
            }
        }while (numCaselle <= 12 || numCaselle > 90);

        do {
            for (int i = 0; i < gestioneGioco.giocatoreArrayList.size(); i++){
                if (gestioneGioco.giocatoreArrayList.get(i).isRimanereBloccato()){
                    System.out.println("Sei bloccato, devi aspettare che arriva un altro giocatore nella casella: " + gestioneGioco.giocatoreArrayList.get(i).getPosizioneGiocatore());

                }else{
                    if (gestioneGioco.giocatoreArrayList.get(i).getRimanereFermo() > 0){
                        System.out.println("L'utente deve rimanere fermo per: " + gestioneGioco.giocatoreArrayList.get(i).getRimanereFermo());
                        gestioneGioco.giocatoreArrayList.get(i).setRimanereFermo(gestioneGioco.giocatoreArrayList.get(i).getRimanereFermo() - 1);

                    }else {
                        gestioneGioco.spostareGiocatore(i, gestioneGioco.tiraDado(), gestioneGioco.giocatoreArrayList.get(i).getPosizioneGiocatore(), numCaselle);
                        stampaResoconto(i);
                    }

                }


            }
            contatore++;

            if (contatore == 5){
                contatore = 0;

                System.out.println("Vuoi salvare ?? y/n");
                do {
                    sceltaSalvataggio = s2.nextLine();

                    if (!sceltaSalvataggio.equals("y") || !sceltaSalvataggio.equals("n"))
                        System.out.println("Errore reinserire!!");

                }while (!sceltaSalvataggio.equals("y") || !sceltaSalvataggio.equals("n"));

                if (sceltaSalvataggio.equals("y")){
                    try {
                        gestioneJson.salvarePartitaJson(gestioneGioco.giocatoreArrayList);
                    } catch (IOException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }while (!gestioneGioco.controlloPartitaFinita(numCaselle));


    }
}
