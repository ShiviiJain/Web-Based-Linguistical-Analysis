package ngordnet.ngrams;
import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */

public class TimeSeries extends TreeMap<Integer, Double> {
    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        clear();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        for (int i = startYear; i <= endYear; i++) {
            if (ts.containsKey(i)) {
                put(i, ts.get(i));
            }
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        ArrayList<Integer> keys = new ArrayList<>();
        for (int i : this.keySet()) {
            keys.add(i);
        }
        return keys;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        ArrayList<Double> vals = new ArrayList<>();
        for (Double i : this.values()) {
            vals.add(i);
        }
        return vals;
    }

    /**
     * Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries temp = new TimeSeries();
        if (!(ts.isEmpty()) && !(this.isEmpty())) {
            for (int i : ts.keySet()) { //KeySet more efficient than list
                if (!(this.containsKey(i))) {
                    temp.put(i, ts.get(i));
                } else {
                    temp.put(i, this.get(i) + ts.get(i));
                }
            }
            for (int i : this.keySet()) {
                if (!(ts.containsKey(i))) {
                    temp.put(i, this.get(i));
                }
            }
        }
        return temp;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries temp = new TimeSeries();
        for (int i : this.keySet()) {
            if (ts.isEmpty() || (!ts.containsKey(i))) {
                throw new IllegalArgumentException();
            } else {
                temp.put(i, this.get(i) / ts.get(i));
            }
        }
        return temp;
    }
}

