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

@Entity
public class GameProfile {

	@Id
	private UUID uuid;

	@Column(nullable = false)
	private String ownerEmail;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TextureModel model = TextureModel.STEVE;

	@ElementCollection
	@JoinTable
	@MapKeyEnumerated(EnumType.STRING)
	private Map<TextureType, String> textures;

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

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public Map<TextureType, String> getTextures() {
		return textures;
	}

	public void setTextures(Map<TextureType, String> textures) {
		this.textures = textures;
	}

}
