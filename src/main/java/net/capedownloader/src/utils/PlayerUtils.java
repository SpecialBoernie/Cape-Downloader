package net.capedownloader.src.utils;

import net.labymod.utils.UUIDFetcher;

import java.util.UUID;

public class PlayerUtils {

    public static UUID getUUID(String player) {
        return UUIDFetcher.getUUID(player);
    }

}
