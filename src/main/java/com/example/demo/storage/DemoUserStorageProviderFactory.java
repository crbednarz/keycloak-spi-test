package com.example.demo.storage;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class DemoUserStorageProviderFactory implements UserStorageProviderFactory<DemoUserStorageProvider> {

	@Override
	public DemoUserStorageProvider create(KeycloakSession session, ComponentModel model) {
		return new DemoUserStorageProvider(session, model);
	}

	@Override
	public String getId() {
		return "demo-user-storage";
	}
}
