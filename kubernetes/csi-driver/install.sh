#! /bin/bash

# FIXME: https://github.com/oracle-samples/oci-secrets-store-csi-driver-provider
helm install oci-provider oci-provider/oci-secrets-store-csi-driver-provider --namespace kube-system -f values.oke.yaml
