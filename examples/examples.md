## Running the examples

```
% 
% pwd       
/home/arunp/My/code/great-prediction-service
% 
% cd examples                                          
% 
% ll
.rw-rw-r--   67 arunp 23 Apr 14:57 -- create-no-update.json
.rw-rw-r--   67 arunp 23 Apr 14:35 -- create.json
.rwxrwxr-x  302 arunp 23 Apr 16:18 -- create.sh
.rw-rw-r-- 6.2k arunp 23 Apr 16:17 -N examples.md
.rw-rw-r--   34 arunp 23 Apr 14:54 -- update.json
.rwxrwxr-x  340 arunp 23 Apr 16:18 -- update.sh
% 
% head *json
==> create.json <==
{
  "userId": 1,
  "matchId": 1,
  "predictedWinner": "Brentford"
}
==> create-no-update.json <==
{
  "userId": 1,
  "matchId": 2,
  "predictedWinner": "Brentford"
}
==> update.json <==
{
  "predictedWinner": "Chelsea"
}%                                                                                                                                                                       
% 
% 
% cat create.sh        
#env bash

curl --data @create.json \
     --header "Content-Type: application/json" \
     --verbose \
     --url 'http://localhost:8080/prediction'

curl --data @'create-no-update.json' \
     --header "Content-Type: application/json" \
     --verbose \
     --url 'http://localhost:8080/prediction'
% 
%             
% sh create.sh             
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> POST /prediction HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.12.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 63
> 
* upload completely sent off: 63 bytes
< HTTP/1.1 201 
< Location: http://localhost:8080/prediction/1
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 23 Apr 2025 15:30:34 GMT
< 
* Connection #0 to host localhost left intact
{"predictionId":1,"userId":1,"matchId":1,"predictedWinner":"Brentford"}* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> POST /prediction HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.12.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 63
> 
* upload completely sent off: 63 bytes
< HTTP/1.1 201 
< Location: http://localhost:8080/prediction/2
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 23 Apr 2025 15:30:34 GMT
< 
* Connection #0 to host localhost left intact
{"predictionId":2,"userId":1,"matchId":2,"predictedWinner":"Brentford"}%                                                                                                 
% 
% 
% curl -v http://localhost:8080/user/1/predictions | jq
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> GET /user/1/predictions HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.12.1
> Accept: */*
> 
* Request completely sent off
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 23 Apr 2025 15:30:51 GMT
< 
{ [151 bytes data]
100   145    0   145    0     0    997      0 --:--:-- --:--:-- --:--:--  1000
* Connection #0 to host localhost left intact
[
  {
    "predictionId": 1,
    "userId": 1,
    "matchId": 1,
    "predictedWinner": "Brentford"
  },
  {
    "predictionId": 2,
    "userId": 1,
    "matchId": 2,
    "predictedWinner": "Brentford"
  }
]
% 
% 
% cat update.sh 
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
% 
% 
% sh update.sh
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> PATCH /prediction/1 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.12.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 32
> 
* upload completely sent off: 32 bytes
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 23 Apr 2025 15:31:13 GMT
< 
* Connection #0 to host localhost left intact
{"predictionDto":{"predictionId":1,"userId":1,"matchId":1,"predictedWinner":"Chelsea"},"status":"SUCCESS"}* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
* using HTTP/1.x
> PATCH /prediction/2 HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.12.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 32
> 
* upload completely sent off: 32 bytes
< HTTP/1.1 422 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 23 Apr 2025 15:31:13 GMT
< 
* Connection #0 to host localhost left intact
{"predictionDto":{"predictionId":0,"userId":0,"matchId":0,"predictedWinner":"Chelsea"},"status":"NOT_UPDATED"}%                                                          
% 
```
