package org.to2mbn.akir.core.model;

import java.util.Map;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyEnumerated;
import org.hibernate.annotations.Type;

@Entity
public class GameCharacter {

	@Id
	@Column(length = 16)
	@Type(type = "uuid-binary")
	private UUID uuid;

	@Column(nullable = false, length = 16)
	@Type(type = "uuid-binary")
	private UUID ownerId;

	@Column(nullable = false, unique = true)
	private String name;

	@Enumerated(EnumType.STRING)
	private TextureModel model = TextureModel.STEVE;

	@ElementCollection
	@JoinTable
	@MapKeyEnumerated(EnumType.STRING)
	private Map<TextureType, String> textures;

	private long createTime;

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TextureModel getModel() {
		return model;
	}

	public void setModel(TextureModel model) {
		this.model = model;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public Map<TextureType, String> getTextures() {
		return textures;
	}

	public void setTextures(Map<TextureType, String> textures) {
		this.textures = textures;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
