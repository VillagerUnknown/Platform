package me.villagerunknown.platform.data;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.ProfileResult;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.ExtraCodecs;

public class ProfileResultData {
	
	public static final Codec<ProfileResult> PROFILE_RESULT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
			ExtraCodecs.AUTHLIB_GAME_PROFILE.fieldOf("profile").forGetter(ProfileResult::profile)
	).apply(instance, ProfileResultData::buildProfileResult));
	
	public static final Codec<ProfileResultData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			Codec.STRING.fieldOf("name").forGetter(player -> player.name),
			UUIDUtil.AUTHLIB_CODEC.fieldOf("uuid").forGetter(player -> player.uuid),
			PROFILE_RESULT_CODEC.fieldOf("profile").forGetter( p -> p.result )
	).apply(instance, ProfileResultData::new));
	
	public String name;
	
	public UUID uuid;
	
	public ProfileResult result;
	
	public ProfileResultData(String playerName, UUID playerUuid, ProfileResult profileResult) {
		name = playerName;
		uuid = playerUuid;
		result = profileResult;
	}
	
	public static ProfileResult buildProfileResult(GameProfile profile) {
		return new ProfileResult( profile );
	}
	
}