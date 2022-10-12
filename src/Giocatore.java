public class Giocatore {

    public int id, rimanereFermo;
    public long posizioneGiocatore;
    public boolean rimanereBloccato;

    public Giocatore(int id, int posizioneGiocatore, int rimanereFermo, boolean rimanereBloccato){
        this.id = id;
        this.posizioneGiocatore = posizioneGiocatore;
        this.rimanereFermo = rimanereFermo;
        this.rimanereBloccato = rimanereBloccato;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosizioneGiocatore(long posizioneGiocatore) {
        this.posizioneGiocatore = posizioneGiocatore;
    }

    public void setRimanereFermo(int rimanereFermo) {
        this.rimanereFermo = rimanereFermo;
    }

    public void setRimanereBloccato(boolean rimanereBloccato) {
        this.rimanereBloccato = rimanereBloccato;
    }

    public int getId() {
        return id;
    }

    public long getPosizioneGiocatore() {
        return posizioneGiocatore;
    }

    public int getRimanereFermo() {
        return rimanereFermo;
    }

    public boolean isRimanereBloccato() {
        return rimanereBloccato;
    }
}
