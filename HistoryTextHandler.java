package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private NGramMap m;
    public HistoryTextHandler(NGramMap map) {
        m = map;
    }
    public String handle(NgordnetQuery map) {
        List<String> words = map.words();
        int startYear = map.startYear();
        int endYear = map.endYear();
        String response = "";
        for (String w : words) {
            response += (w + ": " + (m.weightHistory(w, startYear, endYear)).toString() + "\n");
        }
        return response;
    }
}
