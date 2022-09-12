# Keycloak SPI Demo

This repository contains a bare-bones example of using Keycloak to pass data from a custom UserStorageProvider to a ProtocolMapper.

## Getting started

Assuming Java, Maven, and Docker are available, `./run-in-docker.sh` can be used to build the project and run it in a Keycloak container.

Once initial setup is complete, the server can be accessed at `http://localhost:8080/` with credentials `admin`/`password`.

Additionally, configuration on the admin panel is required:
- Add `demo-user-storage` on the User Federation page.
- Add `DemoProtocolMapper` to desired client's mappers.
- Ensure `Demo Username Password Form` is used in place of `Username Password Form` for Authentication flow.

After configuration is complete, you should be able to sign in with `user`/`password` which will include mock backend service results in its access token.
