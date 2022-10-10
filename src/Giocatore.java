public class Giocatore {

    public int id, posizioneGiocatore;
    public double soldiRimanenti;
    public boolean ancoraInVita;

    public Giocatore(int id, int posizioneGiocatore, double soldiRimanenti, boolean ancoraInVita){
        this.id = id;
        this.posizioneGiocatore = posizioneGiocatore;
        this.soldiRimanenti = soldiRimanenti;
        this.ancoraInVita = ancoraInVita;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosizioneGiocatore(int posizioneGiocatore) {
        this.posizioneGiocatore = posizioneGiocatore;
    }

    public void setSoldiRimanenti(double soldiRimanenti) {
        this.soldiRimanenti = soldiRimanenti;
    }

    public void setAncoraInVita(boolean ancoraInVita) {
        this.ancoraInVita = ancoraInVita;
    }

    public int getId() {
        return id;
    }

    public int getPosizioneGiocatore() {
        return posizioneGiocatore;
    }

    public double getSoldiRimanenti() {
        return soldiRimanenti;
    }

    public boolean isAncoraInVita() {
        return ancoraInVita;
    }
}
