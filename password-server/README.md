# OAuth 2.0 - Resource Owner Password Credential

## Example
### Get Token
```
###
# Request Header
###
POST /oauth/token HTTP/1.1
Host: localhost:8443
Content-Type: application/x-www-form-urlencoded
Accept: application/json
Authorization: Basic Y2xpZW50YXBwOjEyMzQ1Ng==
cache-control: no-cache
Postman-Token: 71763900-9d21-41de-9263-1d5f03daf9d9
grant_type=passwordusername=jhyuntopassword=P%40ssw0rdscope=read_profileundefined=undefined

###
# Response Body
###
{
    "access_token": "41458f5b-fed0-4576-9dba-8320d584d62e",
    "token_type": "bearer",
    "expires_in": 42908,
    "scope": "read_profile"
}
```

### Get Profile
```
###
# Request Header
###
GET /api/profile HTTP/1.1
Host: localhost:8443
Authorization: Bearer 41458f5b-fed0-4576-9dba-8320d584d62e
cache-control: no-cache
Postman-Token: e9507af4-32e9-419c-87fc-57b5f2311d37

###
# Response Body
###
{
    "name": "jhyunto",
    "email": "jhyunto@mailinator.com"
}
```