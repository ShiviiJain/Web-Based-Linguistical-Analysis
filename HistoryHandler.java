package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler {
    private NGramMap mapping;

    public HistoryHandler(NGramMap map) {
        mapping = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        TimeSeries parabola = new TimeSeries();
        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (String w : q.words()) {
            labels.add(w);
            parabola = mapping.weightHistory(w, q.startYear(), q.endYear());
            lts.add(parabola);
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);
        return encodedImage;
    }
}
