package eu.tribusmc.tribuskitpvp.placeholders;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class PlaceholderCollector {


    private HashMap<String, ArrayList<PlaceholderImpl>> collector;


    public PlaceholderCollector() {
        collector = new HashMap<>();
    }

    public void unRegister(String paramOwner) throws Exception {
        if (collector.get(paramOwner) != null)
            throw new Exception("Failed to unregister placeholder owner, the specified owner does not exist.");

        collector.remove(paramOwner);
    }


    public void register(String paramOwner) throws Exception {
        if (collector.get(paramOwner) != null)
            throw new Exception("Failed to register placeholder owner, the specified owner does already exist");

        collector.put(paramOwner, new ArrayList<>());
    }


    /*public PlaceholderImpl fetchSpecific(String paramPotentialPlaceholder) {
        PlaceholderImpl potential;


        collector.forEach((k,v) -> {
            potential = v.stream().findFirst().orElse(null);
        });
    }

     */

    @NotNull
    public ArrayList<PlaceholderImpl> fetch(String paramOwner) {
        return collector.get(paramOwner);
    }


    public ArrayList<PlaceholderImpl> getAll() {
        ArrayList<PlaceholderImpl> placeholders = new ArrayList<>();

        collector.forEach((k, v) -> {

            placeholders.addAll(v);
        });

        return placeholders;
    }

    public PlaceholderImpl fetchMatching(String paramPlaceholder) {
        return Objects.requireNonNull(getAll().stream().filter(placeholder -> placeholder.getPlaceholder().equals(paramPlaceholder)).findFirst().orElse(null));
    }


    public void addPlaceholder(String paramOwner, PlaceholderImpl[] placeholders) {
        for (PlaceholderImpl placeholder : placeholders) {
            collector.get(paramOwner).add(placeholder);
        }
    }

    public void addPlaceholder(String paramOwner, PlaceholderImpl paramPlaceholderImpl) {
        collector.get(paramOwner).add(paramPlaceholderImpl);
    }

    public void removePlaceholder(String paramOwner, PlaceholderImpl paramPlaceholderImpl) {
        collector.get(paramOwner).remove(paramPlaceholderImpl);
    }


    public boolean isCollected(String paramOwner) {
        return collector.get(paramOwner) != null;
    }


}
