package eu.the5zig.mod.util;

import com.mojang.authlib.GameProfile;

/**
 * A class that represents an entry of the network player list.
 */
public class NetworkPlayerInfo {


	private net.minecraft.client.network.NetworkPlayerInfo in;

	public NetworkPlayerInfo(net.minecraft.client.network.NetworkPlayerInfo in) {
		this.in = in;
	}

	/**
	 * @return the {@link GameProfile} of this player.
	 */
	public GameProfile getGameProfile() {
		return in.getGameProfile();
	}

	/**
	 * @return the display name of this player or {@code null}, if no special display name has been set.
	 */
	public String getDisplayName() {
		return in.getDisplayName().getFormattedText();
	}

	/**
	 * Sets a new display name for this player. If the unformatted string does not equal {@link GameProfile#getName()} of this player, a yellow star (*) will be added in front of the name.
	 * <br>
	 * Setting a display name will also override any scoreboard assigned team color.
	 *
	 * @param displayName the new display name for this player.
	 */
	public void setDisplayName(String displayName) {}

	/**
	 * @return the player response time to server in milliseconds.
	 */
	public int getPing() {
		return in.getResponseTime();
	}

}
