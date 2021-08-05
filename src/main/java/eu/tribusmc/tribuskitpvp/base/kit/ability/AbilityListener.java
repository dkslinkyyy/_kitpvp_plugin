package eu.tribusmc.tribuskitpvp.base.kit.ability;

public enum AbilityListener {

    INTERACT,
    BUILD,
    BREAK,
    PROJECTILE_HIT,
    PROJECTILE_LAUNCH,
    FISHING_ROD,
    PLAYER_DAMAGE,
    FLIGHT_TOGGLE;




    public boolean isMatching(AbilityListener listener) {
        return AbilityListener.valueOf(listener.name()) != null;
    }

}
