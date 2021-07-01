# hl7-resource-generator-service
HL7 resource generator service transforms a given HL7 message to a bundle FHIR resource. The service uses [hl7v2-fhir-converter]( https://github.com/LinuxForHealth/hl7v2-fhir-converter) to generate different FHIR resources from HL7.

## Starting the service:
```
./gradlew clean build --refresh-dependencies
docker build . -t <<GROUP>>/hl7-resource-generator-service:0.1.0
docker run -p 8080:8080 <<GROUP>>/hl7-resource-generator-service:0.1.0
```

# REST API

The REST API for HL7 message transformation

## Transformation

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
### Sending HL7 message as plain text:
```
 curl -X POST -H "Content-Type: text/plain" --data "MSH|^~\\&|SE050|050|PACS|050|20120912011230||ADT^A01|102|T|2.6|||AL|NE
EVN||201209122222
PID|0010||PID1234^5^M11^A^MR^HOSP~1234568965^^^USA^SS||DOE^JOHN^A^||19800202|F||W|111 TEST_STREET_NAME^^TEST_CITY^NY^111-1111^USA||(905)111-1111|||S|ZZ|12^^^124|34-13-312||||TEST_BIRTH_PLACE
PV1|1|ff|yyy|EL|ABC||200^ATTEND_DOC_FAMILY_TEST^ATTEND_DOC_GIVEN_TEST|201^REFER_DOC_FAMILY_TEST^REFER_DOC_GIVEN_TEST|202^CONSULTING_DOC_FAMILY_TEST^CONSULTING_DOC_GIVEN_TEST|MED|||||B6|E|272^ADMITTING_DOC_FAMILY_TEST^ADMITTING_DOC_GIVEN_TEST||48390|||||||||||||||||||||||||201409122200|
OBX|1|TX|1234||ECHOCARDIOGRAPHIC REPORT||||||F|||||2740^TRDSE^Janetary~2913^MRTTE^Darren^F~3065^MGHOBT^Paul^J~4723^LOTHDEW^Robert^L|\r
AL1|1|DRUG|00000741^OXYCODONE||HYPOTENSION
AL1|2|DRUG|00001433^TRAMADOL||SEIZURES~VOMITING\r
PRB|AD|200603150625|aortic stenosis|53692||2||200603150625"  http://127.0.0.1:8080/hl7/transformation

```
