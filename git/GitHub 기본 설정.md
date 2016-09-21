#GitHub 기본 설정


##SSH Key 생성
GitHub는 작성한 Repository에 대한 접근 인증을 SSH 공개키로 한다.

Shell에서 ssh-keygen을 이용해 Public Key와 Private Key를 생성한다.

```less
Jungui-MacBook-Pro:.ssh jung$ ssh-keygen -t rsa -C "eqelizer@naver.com"
Generating public/private rsa key pair.
Enter file in which to save the key (/Users/jung/.ssh/id_rsa): (엔터)
Enter passphrase (empty for no passphrase): (비밀번호 입력)
Enter same passphrase again: (비밀번호 재입력)
Your identification has been saved in /Users/jung/.ssh/id_rsa.
Your public key has been saved in /Users/jung/.ssh/id_rsa.pub.
The key fingerprint is:
(핑거프린트) eqelizer@naver.com
The key's randomart image is:
+---[RSA 2048]----+
|  o       . .    |
(생략)
```

이제 `~/.ssh/` 경로에 들어가면 `id_rsa`라는 Private Key와 `id_rsa.pub`라는 Public Key가 생성된다.

다음과 같이 Public Key를 확인한 후, 그 내용을 GitHub에 복사한다.
```less
Jungui-MacBook-Pro:.ssh jung$ cat ~/.ssh/id_rsa.pub 
ssh-rsa (공개키내용) eqelizer@naver.com
```

GitHub에 로그인하고 나서 [SSH Key Settings](https://github.com/settings/ssh)에 들어가 `Add SSH Key`를 클릭하여 Public Key를 추가한다. 그러면 등록한 이메일에 키 등록 완료 메일이 전송된다.

아래와 같이 SSH 통신을 통해 키 등록이 정상적으로 이뤄졌는지 확인할 수 있다.

```less
Jungui-MacBook-Pro:.ssh jung$ ssh -T git@github.com
Identity added: /Users/jung/.ssh/id_rsa ((null))
Hi hyunto! You've successfully authenticated, but GitHub does not provide shell access.
```


##GitHub 웹에서 리파지토리 생성

* Repository name
* Description
* Public과 Private
* Initialize this repository with a README
* Add .gitignore
* Add a license