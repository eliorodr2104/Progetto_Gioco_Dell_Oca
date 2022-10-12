
/**
 * @author E.Rodriguez, A.Duta
 * @date 12/10/2022
 * @version Java 19
 * @description Classe Giocatore con l'oggetto Giocatore creato
 */

public class Giocatore {

    public int id, rimanereFermo;
    public long posizioneGiocatore;
    public boolean rimanereBloccato;

    /**
     * Metodo costruttore()
     * @param id del giocatore
     * @param posizioneGiocatore del giocatore
     * @param rimanereFermo del giocatore
     * @param rimanereBloccato del giocatore
     */
    public Giocatore(int id, int posizioneGiocatore, int rimanereFermo, boolean rimanereBloccato){
        this.id = id;
        this.posizioneGiocatore = posizioneGiocatore;
        this.rimanereFermo = rimanereFermo;
        this.rimanereBloccato = rimanereBloccato;
    }

    /**
     * Metodo setId()
     * @param id setta l'id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metodo setPosizioneGiocatore()
     * @param posizioneGiocatore setta posizioneGiocatore
     */
    public void setPosizioneGiocatore(long posizioneGiocatore) {
        this.posizioneGiocatore = posizioneGiocatore;
    }

    /**
     * Metodo setRimanereFermo()
     * @param rimanereFermo setta rimanereFermo
     */
    public void setRimanereFermo(int rimanereFermo) {
        this.rimanereFermo = rimanereFermo;
    }

    /**
     * Metodo setRimanereBloccato()
     * @param rimanereBloccato setta rimanereBloccato
     */
    public void setRimanereBloccato(boolean rimanereBloccato) {
        this.rimanereBloccato = rimanereBloccato;
    }

    /**
     * Metodo getId()
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Metodo getPosizioneGiocatore()
     * @return posizioneGiocatore
     */
    public long getPosizioneGiocatore() {
        return posizioneGiocatore;
    }

    /**
     * Metodo getRimanereFermo()
     * @return rimanereFermo
     */
    public int getRimanereFermo() {
        return rimanereFermo;
    }

    /**
     * Metodo isRimanereBloccato()
     * @return rimanereBloccato
     */
    public boolean isRimanereBloccato() {
        return rimanereBloccato;
    }
}
