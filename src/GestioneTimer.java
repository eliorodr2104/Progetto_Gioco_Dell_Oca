import java.time.LocalTime;

/**
 * @author E.Rodriguez, A.Duta
 * @date 12/10/2022
 * @version Java 19
 * @description Classe Timer la quale esegue un timer
 */

public class GestioneTimer{
    private int contatore; //Variabile dichiarata di tipo int
    private final int secondi, cicli; //Variabili dichiarate di tipo int

    /**
     * Metodo costruttore(), che inizializza le variabili
     * @param secondi parametro che chiede i secondi del timer
     * @param cicli parametro che chiede in quanti cicli dovrà ripetersi
     */
    public GestioneTimer(int secondi, int cicli) {
        this.contatore = 0;
        this.secondi = secondi;
        this.cicli = cicli;
    }

    public void timer(){
        LocalTime inizio = LocalTime.now(); //Momento nel quale si inizia il ciclo

        while (contatore < cicli) {
            LocalTime localTime = LocalTime.now(); //Momento nel quale si chiede il tempo trascorso

            if (localTime.toSecondOfDay() - inizio.toSecondOfDay() >= secondi) {
                contatore++;
                inizio = LocalTime.now(); //Inizia un nuovo ciclo
                System.out.print('-');

            }
        }

        System.out.println("Operazione conclusa!!----------\n");
    }
}
