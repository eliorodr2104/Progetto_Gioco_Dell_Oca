import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author E.Rodriguez, A.Duta
 * @date 12/10/2022
 * @version Java 19
 * @description Classe GestioneJson la quale gestisce i file json
 */

public class GestioneJson {
    public String directory;
    private final String[] tipiOggetti = new String[5];

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
        this.tipiOggetti[4] = "caselleMax";
    }

    /**
     * Metodo totaleCaselle(), che restituisce tutte le caselle dentro il file json
     * @param tipoOggetto variabile di tipo String che il tipo di oggetto ricercato
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
     * Metodo salvarePartitaJson(), che salva la partita dentro al file json
     * @param giocatoreArrayList richiede un'ArrayList con tutti i giocatori dentro
     * @throws IOException eccezione
     * @throws ParseException eccezione parse
     */
    public void salvarePartitaJson(ArrayList<Giocatore> giocatoreArrayList, int numeroCaselle) throws IOException, ParseException {
        try {
            JSONArray arrayOggettoSalvare = new JSONArray();
            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < giocatoreArrayList.size(); i++) { //For che prende ogni oggetto e lo salva su una variabile temporanea
                JSONObject oggettoSalvare = new JSONObject();

                Object[] infoConvertita = new Object[5];

                //Converto le stringhe nei tipi di variabili utilizzati prima di salvarlo nel file json
                infoConvertita[0] = giocatoreArrayList.get(i).getId();
                infoConvertita[1] = giocatoreArrayList.get(i).getPosizioneGiocatore();
                infoConvertita[2] = giocatoreArrayList.get(i).getRimanereFermo();
                infoConvertita[3] = giocatoreArrayList.get(i).isRimanereBloccato();
                infoConvertita[4] = numeroCaselle;

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

    /**
     * Metodo partiteSalvateLeggere(), che restituisce tutti i prodotti dentro il file json
     * @param tipoOggetto variabile di tipo String che il tipo di oggetto ricercato
     * @return Array con tutti gli oggetti dentro il file json
     */
    public JSONArray partiteSalvateLeggere(String tipoOggetto) {
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

}
