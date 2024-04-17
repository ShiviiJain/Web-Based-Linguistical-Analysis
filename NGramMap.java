package ngordnet.ngrams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import edu.princeton.cs.algs4.In;
/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    private HashMap<String, TimeSeries> mappings;
    private TimeSeries times;

    public NGramMap(String wordsFilename, String countsFilename) {
        mappings = new HashMap<>();
        times = new TimeSeries();
        In word = new In(wordsFilename);
        In count = new In(countsFilename);
        while (!(word.isEmpty())) {
            String w = word.readString();
            Integer y = Integer.valueOf(word.readString());
            Double d = Double.valueOf(word.readString());
            if (!(mappings.containsKey(w))) {
                mappings.put(w, new TimeSeries());
            }
            mappings.get(w).put(y, d);
            word.readLine();
        }

        while (!(count.isEmpty())) {
            String[] arrstr = count.readString().split(",");
            Integer ys = Integer.valueOf(arrstr[0]);
            Double tn = Double.valueOf(arrstr[1]);
            times.put(ys, tn);
            count.readLine();
        }
    }
    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        TimeSeries temp = new TimeSeries();
        if (mappings.containsKey(word)) {
            ArrayList<Integer> keys = new ArrayList<>(mappings.get(word).keySet());
            for (int i = 0; i < mappings.get(word).keySet().size(); i++) {
                temp.put(keys.get(i), mappings.get(word).get(keys.get(i)));
            }
        }
        return temp;
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if (mappings.get(word) != null) {
            return new TimeSeries(mappings.get(word), startYear, endYear);
        }
        return null;
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        TimeSeries temp = new TimeSeries();
        ArrayList<Integer> t = new ArrayList<>(times.years());
        for (int i = 0; i < times.size(); i++) {
            temp.put(t.get(i), times.get(t.get(i)));
        }
        return temp;
    }


    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        TimeSeries temp = countHistory(word);
        return temp.dividedBy(totalCountHistory());
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        return countHistory(word, startYear, endYear).dividedBy(times);
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries temp = new TimeSeries();
        for (String i : words) {
            TimeSeries temp2 = countHistory(i);
            if (temp2 != null) {
                if (!temp.isEmpty()) {
                    temp = temp.plus(weightHistory(i));
                }
                if (temp.isEmpty()) {
                    temp = weightHistory(i);
                }
            }
        }
        return temp;
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries temp = summedWeightHistory(words);
        if (temp != null) {
            return new TimeSeries(temp, startYear, endYear);
        }
        return null;
    }


}
