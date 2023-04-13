#!/bin/bash

helm -n ingress-nginx install ingress-nginx ingress-nginx/ingress-nginx --create-namespace -f values.yaml
