# code-at-customer-sample-app-public

[![build-backend](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-backend.yaml/badge.svg)](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-backend.yaml) [![build-frontend](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-frontend.yaml/badge.svg)](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-frontend.yaml) [![build-download-adb-wallet](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-download-adb-wallet.yaml/badge.svg)](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-download-adb-wallet.yaml) [![build-kafka-connect](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-kafka-connect.yaml/badge.svg)](https://github.com/oracle-japan/code-at-customer-sample-app-public/actions/workflows/build-kafka-connect.yaml)

OCI 上で Cloud Native なアプリケーションの開発 ~ 監視までを確認できるサンプル実装です。

以下の観点のデモに使用することができます。

- Kubernetes(OKE) 上で稼働する Java アプリケーションの CI/CD
- Kubernetes(OKE) 上で稼働する Java アプリケーションの運用・監視
- Terraform/Resource Manager を用いた環境のプロビジョニング

## Structure overview

```bash
.
├── README.md
├── backend
│   ├── Dockerfile
│   ├── README.md
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── oracle
│       │   │           └── jp
│       │   │               └── backend
│       │   │                   ├── BackendApplication.java
│       │   │                   ├── controller
│       │   │                   │   └── ItemController.java
│       │   │                   ├── exception
│       │   │                   │   ├── ErrorResponse.java
│       │   │                   │   ├── ItemNotFoundException.java
│       │   │                   │   └── ItemNotFoundExceptionHandler.java
│       │   │                   ├── repository
│       │   │                   │   ├── Item.java
│       │   │                   │   └── ItemRepository.java
│       │   │                   └── service
│       │   │                       └── ItemService.java
│       │   └── resources
│       │       ├── application-local.yaml
│       │       └── application.yaml
│       └── test
│           └── java
│               └── com
│                   └── oracle
│                       └── jp
│                           └── backend
│                               └── BackendApplicationTests.java
├── build_spec.yaml
├── build_spec_iac.yaml
├── command_spec.yaml
├── download-adb-wallet
│   ├── Dockerfile
│   ├── download-adb-wallet.py
│   └── test.yaml
├── example-params
│   ├── create_apply_job.json
│   ├── create_github_access_token_provider.json
│   ├── create_plan_job.json
│   └── create_stack.json
├── frontend
│   ├── Dockerfile
│   ├── Dockerfile_helidon
│   ├── README.md
│   ├── front-helidon
│   │   ├── Dockerfile
│   │   ├── Dockerfile.jlink
│   │   ├── Dockerfile.native
│   │   ├── README.md
│   │   ├── pom.xml
│   │   └── src
│   │       ├── main
│   │       │   ├── java
│   │       │   │   └── com
│   │       │   │       └── example
│   │       │   │           └── demo
│   │       │   │               └── helidonfront
│   │       │   │                   ├── ItemsMockResource.java
│   │       │   │                   ├── ItemsResource.java
│   │       │   │                   └── package-info.java
│   │       │   └── resources
│   │       │       ├── META-INF
│   │       │       │   ├── beans.xml
│   │       │       │   ├── microprofile-config.properties
│   │       │       │   └── native-image
│   │       │       │       └── me.opc-helidon
│   │       │       │           └── front-helidon
│   │       │       │               └── native-image.properties
│   │       │       ├── application.yaml
│   │       │       └── logging.properties
│   │       └── test
│   │           └── resources
│   │               └── application.yaml
│   ├── front-spring
│   │   ├── mvnw
│   │   ├── mvnw.cmd
│   │   ├── pom.xml
│   │   └── src
│   │       ├── main
│   │       │   ├── java
│   │       │   │   └── com
│   │       │   │       └── example
│   │       │   │           └── demo
│   │       │   │               └── front
│   │       │   │                   ├── FrontSpringApplication.java
│   │       │   │                   ├── Item.java
│   │       │   │                   ├── ItemsController.java
│   │       │   │                   ├── MockController.java
│   │       │   │                   ├── MyExceptionHandler.java
│   │       │   │                   └── WebConfig.java
│   │       │   └── resources
│   │       │       └── application.properties
│   │       └── test
│   │           └── java
│   │               └── com
│   │                   └── example
│   │                       └── demo
│   │                           └── front
│   │                               └── FrontSpringApplicationTests.java
│   └── front-vue
│       ├── README.md
│       ├── babel.config.js
│       ├── jsconfig.json
│       ├── package-lock.json
│       ├── package.json
│       ├── public
│       │   ├── favicon.ico
│       │   └── index.html
│       ├── src
│       │   ├── App.vue
│       │   ├── assets
│       │   │   └── logo.png
│       │   ├── components
│       │   │   ├── AppHeader.vue
│       │   │   └── HelloWorld.vue
│       │   └── main.js
│       └── vue.config.js
├── kubernetes
│   ├── apps
│   │   ├── backend
│   │   │   └── backend.yaml
│   │   └── frontend
│   │       └── frontend.yaml
│   ├── cert-manager
│   │   ├── install.sh
│   │   ├── istio-system
│   │   │   ├── code-at-customer-ca-secret.yaml
│   │   │   ├── code-at-customer-certificate.yaml
│   │   │   └── code-at-customer-issuer.yaml
│   │   └── keycloak
│   │       ├── code-at-customer-certificate.yaml
│   │       ├── code-at-customer-issuer.yaml
│   │       ├── code-at-customer-le-issuer.yaml
│   │       ├── code-at-customer-le-staging-issuer.yaml
│   │       └── code-at-customer-tls-secret.yaml
│   ├── csi-driver
│   │   ├── install.sh
│   │   ├── secret-provider
│   │   │   ├── backend
│   │   │   │   ├── apm-secret-provider.yaml
│   │   │   │   ├── datasource-secret-provider.yaml
│   │   │   │   └── ocir-secret-provider.yaml
│   │   │   └── frontend
│   │   │       ├── apm-secret-provider.yaml
│   │   │       ├── oauth-secret-provider.yaml
│   │   │       └── ocir-secret-provider.yaml
│   │   └── values.oke.yaml
│   ├── ingress-nginx
│   │   ├── install.sh
│   │   └── values.yaml
│   ├── kafka-connect
│   │   ├── helm
│   │   │   ├── README.md
│   │   │   └── kafka-connect
│   │   │       ├── Chart.yaml
│   │   │       ├── templates
│   │   │       │   ├── deployment.yaml
│   │   │       │   ├── kafka-connect-secret-provider.yaml
│   │   │       │   └── ocir-secret-provider.yaml
│   │   │       └── values.yaml
│   │   └── image
│   │       └── Dockerfile
│   ├── keycloak
│   │   ├── keycloak-deployment.yaml
│   │   ├── keycloak-ingress.yaml
│   │   ├── kustomization.yaml
│   │   └── mysql-deployment.yaml
│   ├── monitoring
│   │   ├── addons
│   │   │   ├── grafana.yaml
│   │   │   ├── jaeger.yaml
│   │   │   ├── kiali.yaml
│   │   │   └── prometheus.yaml
│   │   ├── dashboard
│   │   │   └── oci-monitoring.json
│   │   └── monitoring-ingress.yaml
│   └── networking
│       ├── apps
│       │   ├── backend-dr.yaml
│       │   ├── backend-vs.yaml
│       │   ├── front-dr.yaml
│       │   ├── frontend-vs.yaml
│       │   └── gateway.yaml
│       ├── install.sh
│       └── keycloak
│           ├── gateway.yaml
│           ├── keycloak-dr.yaml
│           ├── keycloak-vs.yaml
│           ├── mysql-dr.yaml
│           └── mysql-vs.yaml
└── terraform
    ├── datasources.tf
    ├── locals.tf
    ├── main_api-gateway.tf
    ├── main_atp.tf
    ├── main_kube-config.tf
    ├── main_network.tf
    ├── main_oke.tf
    ├── provider.tf
    └── variables.tf
```

## Kubernetes(OKE) 上で稼働する Java アプリケーションの CI/CD

- GitHub Actions を用いたアプリケーションのビルド、GHCR(GitHub Container Registry)へのプッシュ
  - [.github/workflows](https://github.com/oracle-japan/code-at-customer-sample-app-public/tree/main/.github/workflows)
- OCI DevOps を用いた Kubernetes 基盤（OKE）へのアプリケーションのデプロイ
  - [build_spec.yaml](https://github.com/oracle-japan/code-at-customer-sample-app-public/blob/main/build_spec.yaml)
  - [kubernetes/apps/\*](https://github.com/oracle-japan/code-at-customer-sample-app-public/tree/main/kubernetes/apps)

## Kubernetes(OKE) 上で稼働する Java アプリケーションの運用・監視

- [OCI Metrics plugin for Grafana](https://github.com/oracle/oci-grafana-metrics) を用いたメトリクス監視
  - [kubernetes/monitoring/\*](https://github.com/oracle-japan/code-at-customer-sample-app-public/tree/main/kubernetes/monitoring)
- Cert Manager, OCI Certificates を用いた Kubernetes 基盤（OKE）でのプライベートな証明書運用
  - [kubernetes/cert-manager/\*](https://github.com/oracle-japan/code-at-customer-sample-app-public/tree/main/kubernetes/cert-manager)
- OCI Secrets Store CSI Driver Provider を用いた OCI Vault と Kubernetes - Secret の同期
  - [kubernetes/csi-driver/\*](https://github.com/oracle-japan/code-at-customer-sample-app-public/tree/main/kubernetes/csi-driver)
  - [kubernetes/apps/\*](https://github.com/oracle-japan/code-at-customer-sample-app-public/tree/main/kubernetes/apps)

## Terraform/Resource Manager を用いた環境のプロビジョニング

- [terraform/\*](https://github.com/oracle-japan/code-at-customer-sample-app-public/tree/main/terraform)
- [build_spec_iac.yaml](https://github.com/oracle-japan/code-at-customer-sample-app-public/blob/main/build_spec_iac.yaml)
- [command_spec.yaml](https://github.com/oracle-japan/code-at-customer-sample-app-public/blob/main/command_spec.yaml)
