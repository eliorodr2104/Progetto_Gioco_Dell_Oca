
import java.util.Scanner;

public class InterfacciaUtente {

    GestioneGioco gestioneGioco = new GestioneGioco();

    private int numCaselle;
    public void startMenu(){
        int ngiocatori = 0;
        Scanner s1 = new Scanner(System.in);

        gestioneGioco.creaGiocatori();

        do {
            System.out.println("Inserire con quante caselle si vuole giocare: ");
            numCaselle = s1.nextInt();
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
        }while (!gestioneGioco.controlloPartitaFinita(numCaselle));

    }

    public void stampaResoconto(int id){
        System.out.println("Il giocatore N^: " + id + "\n" +
                "Avanazato alla casella: " + gestioneGioco.giocatoreArrayList.get(id).getPosizioneGiocatore() + "\n");
    }

}
