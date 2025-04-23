#env bash

curl --data @update.json \
     --request PATCH \
     --header "Content-Type: application/json" \
     --verbose \
     --url 'http://localhost:8080/prediction/1'

curl --data @update.json \
     --request PATCH \
     --header "Content-Type: application/json" \
     --verbose \
     --url 'http://localhost:8080/prediction/2'
