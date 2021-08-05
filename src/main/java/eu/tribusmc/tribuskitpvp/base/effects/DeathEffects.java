package eu.tribusmc.tribuskitpvp.base.effects;

import java.util.HashSet;
import java.util.Set;

public class DeathEffects {

    private static Set<DeathEffect> deathEffects = new HashSet<>();

    public static void load() {
        deathEffects.add(new BurnDeathEffect());
    }


    public static DeathEffect fetchMatching(String paramInternalName) {
        return deathEffects.stream().filter(deathEffect -> deathEffect.getName().equals(paramInternalName)).findFirst().orElse(null);
    }
}
