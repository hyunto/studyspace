# oauth2.0-cookbook

1. OAuth 2.0 기본
    - simplemvc
    - client-implicit
    - social-authcode
    - social-google1
    - social-linkd-in
2. OAuth 2.0 프로바이더 구현
    - auth-code-server : Authorization Code Grant Provider
    - implicit-server : Implicit Grant Provider
    - password-server : Resource Owner Password Credentials Grant Provider
    - client-credentials-server : Client Credentials Grant Provider
    - refresh-server : Refresh Token Grant Provider
    - rdbms-server : 클라이언트 정보와 토클을 MySQL에 저장
    - redis-server : 토큰을 Redis에 저장
    - oauth2provider : 클라이언트 등록을 할 수 있는 UI 추가
    - authorization-server, resource-server : 인가 서버와 리소스 서버 분리
3. OAuth 2.0 보호 API
    - client-authorization-code : Authorization Code Grant를 사용하는 클라이언트 구현