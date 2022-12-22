package dev.awesomebfm.reverselifesmp.models;

import java.util.UUID;

public class PlayerData {
    UUID uuid;
    int hearts;
    boolean deathBanned;

    public PlayerData(UUID uuid, int hearts, boolean deathBanned) {
        this.uuid = uuid;
        this.hearts = hearts;
        this.deathBanned = deathBanned;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public boolean isDeathBanned() {
        return deathBanned;
    }

    public void setDeathBanned(boolean deathBanned) {
        this.deathBanned = deathBanned;
    }
}
