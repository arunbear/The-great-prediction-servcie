#env bash

curl --data @create.json \
     --header "Content-Type: application/json" \
     --verbose \
     --url 'http://localhost:8080/prediction'

curl --data @'create-no-update.json' \
     --header "Content-Type: application/json" \
     --verbose \
     --url 'http://localhost:8080/prediction'
