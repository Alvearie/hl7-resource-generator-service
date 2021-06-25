# HL7 Resource Generator Service Helm Chart

## Introduction

This [Helm](https://github.com/kubernetes/helm) chart installs an instance of the [Alvearie HL7 Resource Generator](https://github.com/Alvearie/hl7-resource-generator-service) service in a Kubernetes cluster.

## Pre-Requisites

- Kubernetes cluster 1.10+
- Helm 3.0.0+

## Installation

### Checkout the Code

Git clone this repository and `cd` into this directory.

```bash
git clone https://github.com/Alvearie/hl7-resource-generator-service.git
cd hl7-resource-generator-service/chart
```

### Install the Chart

Install the helm chart:

```bash
helm install <<RELEASE_NAME>> .
```

This will install the HL7 Resource Generator, but it will not be exposed outside the cluster. To add an ingress for the service, use:

```bash
helm install <<RELEASE_NAME>> . --set ingress.enabled=true  --set ingress.class=<<INGRESS_CLASS>> --set ingress.subdomain=<<INGRESS_SUBDOMAIN>>
```

where `<<INGRESS_CLASS>>` is the ingress class used by your cloud environment, and `<<INGRESS_SUBDOMAIN>>` is the configured subdomain you wish to use for your ingress.

### Using the Chart

See [HL7 Resource Generator](../README.md#rest-api) for information about calling the deployed API.

## Uninstallation

To uninstall/delete the deployment:

```bash
helm delete <<RELEASE_NAME>>
```

## Contributing

Feel free to contribute by making a [pull request](https://github.com/Alvearie/health-analytics/pull/new/master).

Please review the [Contributing Guide](https://github.com/Alvearie/health-analytics/blob/main/CONTRIBUTING.md) for information on how to get started contributing to the project.

## License
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) 
