package it.polimi.ingsw.server;

import java.net.InetAddress;

/**
 * A class representing a user in the game
 *
 * @param nickname the nickname of this user.
 * @param address  The IP address of this user.
 */
public record User(String nickname, InetAddress address) {
}
