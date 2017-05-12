package org.to2mbn.akir.model;

public enum TextureModel {

	STEVE("default"),
	ALEX("slim");

	private String modelName;

	TextureModel(String modelName) {
		this.modelName = modelName;
	}

	public String getModelName() {
		return modelName;
	}

}
