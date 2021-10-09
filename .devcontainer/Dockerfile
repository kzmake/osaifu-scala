# See here for image contents: https://github.com/microsoft/vscode-dev-containers/tree/v0.194.3/containers/java/.devcontainer/base.Dockerfile

# [Choice] Java version: 11, 16
ARG VARIANT="11"
FROM mcr.microsoft.com/vscode/devcontainers/java:0-${VARIANT}

# [Option] Install Maven
ARG INSTALL_MAVEN="false"
ARG MAVEN_VERSION=""
# [Option] Install Gradle
ARG INSTALL_GRADLE="false"
ARG GRADLE_VERSION=""
RUN if [ "${INSTALL_MAVEN}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install maven \"${MAVEN_VERSION}\""; fi \
    && if [ "${INSTALL_GRADLE}" = "true" ]; then su vscode -c "umask 0002 && . /usr/local/sdkman/bin/sdkman-init.sh && sdk install gradle \"${GRADLE_VERSION}\""; fi

# [Choice] Node.js version: none, lts/*, 16, 14, 12, 10
ARG NODE_VERSION="lts/*"
RUN if [ "${NODE_VERSION}" != "none" ]; then su vscode -c "umask 0002 && . /usr/local/share/nvm/nvm.sh && nvm install ${NODE_VERSION} 2>&1"; fi

# [Choice] Golang version: none, 1.17.2
ARG GOLANG_VERSION="1.17.2"
ENV PATH $PATH:/usr/local/go/bin
RUN if [ "${GOLANG_VERSION}" != "none" ]; then curl -Lo golang.tar.gz https://golang.org/dl/go${GOLANG_VERSION}.linux-amd64.tar.gz \
    && tar -C /usr/local -xzf golang.tar.gz && rm golang.tar.gz; fi

# [Option] Install sbt
ARG INSTALL_SBT="true"
RUN if [ "${INSTALL_SBT}" = "true" ]; then \
    echo "deb https://repo.scala-sbt.org/scalasbt/debian all main" | tee /etc/apt/sources.list.d/sbt.list \
    && echo "deb https://repo.scala-sbt.org/scalasbt/debian /" | tee /etc/apt/sources.list.d/sbt_old.list \
    && curl -sL "https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x2EE0EA64E40A89B84B2DF73499E82A75642AC823" | apt-key add \
    && apt-get update && apt-get install -y sbt \
    && apt-get clean -y && rm -rf /var/lib/apt/lists/* ; fi

# [Optional] Uncomment this section to install additional OS packages.
RUN apt-get update && export DEBIAN_FRONTEND=noninteractive \
    && apt-get -y install --no-install-recommends make bash zsh fish

# [Optional] Uncomment this line to install global node packages.
RUN if [ "${NODE_VERSION}" != "none" ]; then su vscode -c "source /usr/local/share/nvm/nvm.sh && npm install -g yarn typescript fx husky prettier" 2>&1; fi

# [Optional] Uncomment this line to install global golang tools.
ENV PATH $PATH:/home/vscode/go/bin
RUN if [ "${GOLANG_VERSION}" != "none" ]; then su vscode -c "go install github.com/fullstorydev/grpcurl/cmd/grpcurl@latest" 2>&1; fi
