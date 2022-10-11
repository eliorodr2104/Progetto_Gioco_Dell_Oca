public class Giocatore {

    public int id, posizioneGiocatore, rimanereFermo;
    public double soldiRimanenti;

    public Giocatore(int id, int posizioneGiocatore, double soldiRimanenti, int rimanereFermo){
        this.id = id;
        this.posizioneGiocatore = posizioneGiocatore;
        this.soldiRimanenti = soldiRimanenti;
        this.rimanereFermo = rimanereFermo;
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

    public void setRimanereFermo(int rimanereFermo) {
        this.rimanereFermo = rimanereFermo;
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

    public int getRimanereFermo() {
        return rimanereFermo;
    }
}
