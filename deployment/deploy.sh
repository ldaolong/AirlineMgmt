#!/usr/bin/env bash

echo "creating kube config"
echo "${KUBECONFIG_CONTENT}" | base64 -d > ${KUBECONFIG}

mkdir -p ./deployment/.generated
rm -rf deployment/.generated/*

echo "generating configmap from $ENV-configmap.yaml"
envsubst < ./deployment/config/$ENV-configmap.yaml  > "./deployment/.generated/config-map.yaml"

for f in ./deployment/tmpl/*.yaml
do
  envsubst < $f > "./deployment/.generated/$(basename $f)"
done

for f in deployment/.generated/*; do
    echo $f
    ## only for training
    cat $f
done

echo "set namespace context to $KUBE_NAMESPACE"
kubectl config set-context $(kubectl config current-context) --namespace=$KUBE_NAMESPACE

echo "deploying $APPLICATION_NAME resources..."
kubectl apply -f deployment/.generated/

echo "watching $APPLICATION_NAME rollout status..."
kubectl rollout status -n "$KUBE_NAMESPACE" -w "deployment/$APPLICATION_NAME"