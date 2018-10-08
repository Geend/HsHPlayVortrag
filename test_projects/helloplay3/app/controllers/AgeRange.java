package controllers;

import play.mvc.QueryStringBindable;

import java.util.Map;
import java.util.Optional;

public class AgeRange implements QueryStringBindable<AgeRange> {

    public Integer from;
    public Integer to;

    @Override
    public Optional<AgeRange> bind(String key, Map<String, String[]> data) {

        try {
            from = new Integer(data.get("from")[0]);
            to = new Integer(data.get("to")[0]);
            return Optional.of(this);

        } catch (Exception e) { // no parameter match return None
            return Optional.empty();
        }
    }

    @Override
    public String unbind(String key) {
        return new StringBuilder()
                .append("from=")
                .append(from)
                .append("&to=")
                .append(to)
                .toString();
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}