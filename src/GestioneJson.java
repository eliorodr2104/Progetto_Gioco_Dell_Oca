import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

public class GestioneJson {
    public String directory;

    private final String[] tipiOggetti = new String[5]; //Array di tipo String

    /**
     * Metodo costruttore(), che inizializza tutte le variabili
     * @param directory richiede la directory del file json a manipolare
     */
    public GestioneJson(String directory){
        this.directory = directory;
        this.tipiOggetti[0] = "id";
        this.tipiOggetti[1] = "dimensione";
        this.tipiOggetti[2] = "peso";
        this.tipiOggetti[3] = "posizione";
        this.tipiOggetti[4] = "uscitoMagazzino";
    }

    /**
     * Metodo aggiungereProdottoJson(), che aggiunge dei prodotti al file json
     * @param informazione richiede un'Array di json con tutti gli oggetti dentro
     * @param prodotto richiede il tipo di prodotto che si deve ricercare
     * @throws IOException eccezione
     * @throws ParseException eccezione parse
     */
    public void aggiungereProdottoJson(JSONArray informazione, String prodotto) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(directory);

            Object obj = jsonParser.parse(fileReader); //Oggetto con l'informazione del file json

            JSONObject jsonObject = (JSONObject) obj; //Oggetto di tipo Json con l'informazione dell'oggetto precedente

            JSONArray arrayOggettoSalvare = (JSONArray) jsonObject.get(prodotto); //Array di oggetti json

            for (Object o : informazione) { //For-each che prende ogni oggetto e lo salva su una variabile temporanea
                JSONObject oggettoSalvare = new JSONObject();

                System.out.println(informazione);

                String[] infoSplit = ((String) o).split(" "); //Array di String che splitta l'informazione dell'oggetto

                Object[] infoConvertita = new Object[5];

                //Converto le stringhe nei tipi di variabili utilizzati prima di salvarlo nel file json
                infoConvertita[0] = Integer.parseInt(infoSplit[0]);
                infoConvertita[1] = infoSplit[1];
                infoConvertita[2] = Double.parseDouble(infoSplit[2]);
                infoConvertita[3] = infoSplit[3];
                infoConvertita[4] = Boolean.parseBoolean(infoSplit[4]);

                for (int j = 0; j < tipiOggetti.length; j++) //For che salva l'informazione dentro l'oggetto json
                    oggettoSalvare.put(tipiOggetti[j], infoConvertita[j]);


                arrayOggettoSalvare.add(oggettoSalvare);//Inserisco l'oggetto json dentro l'Array

            }

            jsonObject.put(prodotto, arrayOggettoSalvare);//Inserisco l'Array di jsonObject dentro un oggetto

            FileWriter file = new FileWriter(directory);

            file.write(jsonObject.toJSONString());//Scrive l'oggetto dentro il file json
            file.flush();
            file.close();//Chiude il fileWriter
        } catch (FileNotFoundException ignored){} catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Metodo togliProdotti(), metodo che legge il file json e togli un prodotto
     * @param prodotti richiede una variabile di tipo JSONObject
     */
    public void togliProdotti(JSONObject prodotti){
        try {
            FileWriter fileWriter = new FileWriter(directory);

            fileWriter.write(prodotti.toJSONString());//Scrive il prodotto nel file json
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo numeroProdotti(), che cerca i prodotti dentro il file json
     * @param prodotto variabile di tipo String che il tipo di prodotto ricercato
     * @return la quantità di prodotti che ci sono dentro il dile json
     */
    public int numeroProdotti(String prodotto){
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(directory);

            // parsing file
            JSONObject obj = (JSONObject) jsonParser.parse(fileReader);

            JSONArray prodotti = (JSONArray) obj.get(prodotto);

            return prodotti.size();//Quantità di prodotti

        } catch (IOException | org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo totaleProdotti(), che restituisce tutti i prodotti dentro il file json
     * @param prodotto variabile di tipo String che il tipo di prodotto ricercato
     * @return Array con tutti gli oggetti dentro il file json
     */
    public JSONArray totaleProdotti(String prodotto) {
        JSONParser jsonParser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(directory);

            // parsing file
            JSONObject obj = (JSONObject) jsonParser.parse(fileReader);

            return (JSONArray) obj.get(prodotto);

        } catch (IOException | org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
