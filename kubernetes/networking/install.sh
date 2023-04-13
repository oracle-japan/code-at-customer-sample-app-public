#! /bin/bash

istioctl install \
    --set meshConfig.enableTracing=true \
    --set meshConfig.defaultConfig.tracing.zipkin.address=aaaadbxjrmg44aaaaaaaaab52a.apm-agt.ap-osaka-1.oci.oraclecloud.com:443
