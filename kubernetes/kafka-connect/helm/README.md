## 前提条件

OCI Secrets Store CSI Driver Provider がインストール済みであること
※ Secrets Sync を有効にする設定が必要  
https://github.com/oracle-samples/oci-secrets-store-csi-driver-provider  

OCI Vault に以下の名前の Secret があること
- dockerconfigjson - docker pull のための config
- kafka-connect-username - OCI Streaming のための username <tenant>/<user>
- kafka-connect-password - OCI Streaming のための password (user の API Key)

## インストール手順

1. kafka-connect/values.yaml の編集

2. helm によるインストール
    ```
    $ kubectl create namespace kafka-connect
    $ helm install -n kafka-connect --create-namespace kafka-connect kafka-connect/ (--values 別のvalues.yaml)  (--dry-run)
    $ helm list -n kafka-connect
    ```

## テスト

```
$ kubectl proxy &
$ curl -s http://127.0.0.1:8001/api/v1/namespaces/kafka-connect/services/kafka-connect:8083/proxy/ | jq .
{
  "version": "7.3.2-ccs",
  "commit": "853191ff421b2935dfa531545651ab667b809801",
  "kafka_cluster_id": "OSS"
}
```
