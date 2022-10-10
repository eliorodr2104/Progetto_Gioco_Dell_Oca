import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.IOException;

public class GestioneJson {
    public String directory;

    /**
     * Metodo costruttore(), che inizializza tutte le variabili
     * @param directory richiede la directory del file json a manipolare
     */
    public GestioneJson(String directory){
        this.directory = directory;
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

}
