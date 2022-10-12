import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GestioneJson {
    public String directory;

    private String[] tipiOggetti = new String[4];

    /**
     * Metodo costruttore(), che inizializza tutte le variabili
     * @param directory richiede la directory del file json a manipolare
     */
    public GestioneJson(String directory){
        this.directory = directory;
        this.tipiOggetti[0] = "id";
        this.tipiOggetti[1] = "posizioneGiocatore";
        this.tipiOggetti[2] = "rimanereFermo";
        this.tipiOggetti[3] = "rimanereBloccato";
    }

    /**
     * Metodo totaleCaselle(), che restituisce tutti i prodotti dentro il file json
     * @param tipoOggetto variabile di tipo String che il tipo di prodotto ricercato
     * @return Array con tutti gli oggetti dentro il file json
     */
    public JSONArray totaleCaselle(String tipoOggetto) {
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(directory);

            // parsing file
            JSONObject obj = (JSONObject) jsonParser.parse(fileReader);

            return (JSONArray) obj.get(tipoOggetto);

        } catch (IOException | org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo aggiungereProdottoJson(), che aggiunge dei prodotti al file json
     * @param giocatoreArrayList richiede un'Array di json con tutti gli oggetti dentro
     * @throws IOException eccezione
     * @throws ParseException eccezione parse
     */
    public void salvarePartitaJson(ArrayList<Giocatore> giocatoreArrayList) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(directory);

            Object obj = jsonParser.parse(fileReader); //Oggetto con l'informazione del file json

            JSONObject jsonObject = (JSONObject) obj; //Oggetto di tipo Json con l'informazione dell'oggetto precedente

            JSONArray arrayOggettoSalvare = (JSONArray) jsonObject.get("partite"); //Array di oggetti json

            for (int i = 0; i < giocatoreArrayList.size(); i++) { //For-each che prende ogni oggetto e lo salva su una variabile temporanea
                JSONObject oggettoSalvare = new JSONObject();

                Object[] infoConvertita = new Object[5];

                //Converto le stringhe nei tipi di variabili utilizzati prima di salvarlo nel file json
                infoConvertita[0] = giocatoreArrayList.get(i).getId();
                infoConvertita[1] = giocatoreArrayList.get(i).getPosizioneGiocatore();
                infoConvertita[2] = giocatoreArrayList.get(i).getRimanereFermo();
                infoConvertita[3] = giocatoreArrayList.get(i).isRimanereBloccato();

                for (int j = 0; j < tipiOggetti.length; j++) //For che salva l'informazione dentro l'oggetto json
                    oggettoSalvare.put(tipiOggetti[j] + i, infoConvertita[j]);


                arrayOggettoSalvare.add(oggettoSalvare);//Inserisco l'oggetto json dentro l'Array

            }

            jsonObject.put("partite", arrayOggettoSalvare);//Inserisco l'Array di jsonObject dentro un oggetto

            FileWriter file = new FileWriter(directory);

            file.write(jsonObject.toJSONString());//Scrive l'oggetto dentro il file json
            file.flush();
            file.close();//Chiude il fileWriter
        } catch (FileNotFoundException ignored){}

    }

}
