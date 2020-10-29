# hl7-resource-generator-service
HL7 resource generator service transforms a given HL7 message to a bundle FHIR resource. The service uses [hl7v2-fhir-converter]( https://github.com/LinuxForHealth/hl7v2-fhir-converter) to generate different FHIR resources from HL7.

## Starting the service:
```
./gradlew clean build --refresh-dependencies
./gradlew bootRun
```

# REST API

The REST API for HL7 message transformation

## Get list of Things

### Request

`POST /hl7/transformation`

    curl -X POST http://127.0.0.1:8080/hl7/transformation -F "file=@sample.hl7"

### Response
200 OK for successful transformation.
Response body : JSON
``` json
{
  "resourceType": "Bundle",
  "id": "cf56324b-e43a-4cc7-8768-eaba5eb61214",
  "meta": {
    "lastUpdated": "2020-10-24T15:59:07.418+08:00",
    "source": "Message: ADT_A01, Message Control Id: 102"
  },
  "type": "collection",
  "entry": [ {
    "resource": {
      "resourceType": "Patient",
      "id": "0f61c0c7-1d9e-46de-9bba-2ee908582804",
      "identifier": [ {
        "type": {
          "coding": [ {
            "system": "http://terminology.hl7.org/CodeSystem/v2-0203",
            "code": "MR",
            "display": "Medical record number"
          } ],
          "text": "MR"
        },
        "system": "A",
        "value": "PID1234"
      }, {
        "type": {
          "coding": [ {
            "system": "http://terminology.hl7.org/CodeSystem/v2-0203",
            "code": "SS",
            "display": "Social Security number"
          } ],
          "text": "SS"
        },
        "system": "USA",
        "value": "1234568965"
      } ],
      "name": [ {
        "family": "DOE",
        "given": [ "JOHN" ]
      } ],
      "gender": "female",
      "birthDate": "1980-02-02"
    }
  }]
  }
```

