#Git 기본 사용법

##`git init` : Repository 초기화

Git으로 버전관리를 시작하려면 Repository를 초기화 해야 한다.
`git init` 명령어로 초기화가 가능하다.

여기선 `git-tutorial`이란 폴더 생성한 후 초기화를 한다.
초기화가 되면 생성한 폴더 안에 `.git`이란 폴더가 생성된다.
`.git` 폴더를 이용해 앞으로 일어날 변경 이력 등을 관리할 수 있게 된다.
`.git` 폴더 내 내용을 `working tree`라고도 한다.

```bash
# 디렉토리 생성 후 이동
Jungui-MacBook-Pro:Git jung$ mkdir git-tutorial
Jungui-MacBook-Pro:Git jung$ cd git-tutorial/

# Repository 초기화
Jungui-MacBook-Pro:git-tutorial jung$ git init
Initialized empty Git repository in /Users/jung/data/study/Git/git-tutorial/.git/

# 확인
Jungui-MacBook-Pro:git-tutorial jung$ ls -al
total 0
drwxr-xr-x  3 jung  staff  102  1 14 11:41 .
drwxr-xr-x  6 jung  staff  204  1 14 11:41 ..
drwxr-xr-x  9 jung  staff  306  1 14 11:41 .git
```

##`git status` : Repository 상태 확인

`git status` 명령어는 Git 리파지토리의 상태를 표시한다.
다음과 같이 `nothing to commit~`이 나오면 Commit이 없었다는 뜻이다.

Commit이란 Working Tree에 있는 모든 파일의 특정 시점 상태를 기록하는 것이다.
즉, Commit이 없었다는 것은 현재 리파지토리에 어떠한 변화도 없었다는 것을 뜻한다.

```bash
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch master

Initial commit

nothing to commit (create/copy files and use "git add" to track)
```

Commit을 하기 위해 간단히 README.md 파일을 작성한 후 상태를 확인해 본다.
다음과 같이 README.md 파일이 `Untracked files`로 표시되는 것을 알 수 있다.

```bash
# README.md 파일 생성
Jungui-MacBook-Pro:git-tutorial jung$ touch README.md

# Repository 상태 확인
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch master

Initial commit

Untracked files:
  (use "git add <file>..." to include in what will be committed)

	README.md

nothing added to commit but untracked files present (use "git add" to track)
```

##`git add` : 스테이지 영역에 파일 추가

`git add` 명령어는 파일을 Git Repository에서 관리될 수 있도록 `스테이지 영역`에 파일을 등록하게 해준다.
`스테이지 영역`이란 Commit하기 전의 임시 영역이다.

이제 앞서 생성한 README.md 파일을 스테이지 영역에 등록하면, 다음과 같이 `Changes to be committed` 상태가 된다.

```bash
Jungui-MacBook-Pro:git-tutorial jung$ git add README.md 
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch master

Initial commit

Changes to be committed:
  (use "git rm --cached <file>..." to unstage)

	new file:   README.md
```

##`git commit` : Repository 변경 내용을 기록

`git commit` 명령어는 스테이지 영역에 기록된 파일들을 실제 Repository의 변경 내역에 반영한다.

Commit시 메시지를 기록할 수 있다.

* 한 줄 메시지 작성 : `git commit -m "Commit Message"`와 같이 `-m` 옵션을 사용한다.
* 상세한 메시지 작성 : `-m` 옵션을 사용하지 않고`git commit` 명령어만 입력한다. 그러면 에디터가 실행되면서 상세한 메시지를 작성할 수 있다.

Commit을 한 후 상태를 확인하면 다시 `nothing to commit~` 메시지가 출력되는 것을 확인할 수 있다.

```bash
Jungui-MacBook-Pro:git-tutorial jung$ git commit
[master (root-commit) a9acefb] Make README.md file.
 1 file changed, 0 insertions(+), 0 deletions(-)
 create mode 100644 README.md

Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch master
nothing to commit, working directory clean
```

Commit을 중지하고 싶을 때는 실행된 에디터에 아무것도 입력하지 않고 종료하면 Commit되지 않고 종료된다.

장문의 Commit 메시지는 다음과 같은 형태로 입력하면 좋다.

* 첫 번째 줄 : Commit으로 인한 변경 내용을 한 줄로 요약하여 작성
* 두 번째 줄 : 공백
* 세 번째 줄 이후 : 변경과 관련된 내용을 상세하게 기록


##`git log` : Commit 로그 확인

###기본적인 Commit 로그 확인
`git log` 명령어는 Repository에 Commit 된 로그를 확인할 수 있는 명령어다.
누가 언제 Commit, 변경, Merge를 했는지 알 수 있다.

```bash
Jungui-MacBook-Pro:git-tutorial jung$ git log
commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
Author: Jung Hyunsoo <eqelizer@naver.com>
Date:   Wed Jan 20 18:01:05 2016 +0900

    Make README.md file.
    
    It is made for GitHub test.
```

###Commit 메시지의 첫 번째 줄만 출력

상세한 메시지가 적힌 변경사항들의 로그를 모두 보면 다소 복잡해 보일 수 있다.
따라서 변경 내용을 한 줄로 요약한 첫 번째 줄만 보여주면 좀 더 효과적일 수 있다.
`git log` 명령어에 `--pretty=short` 옵션을 추가하면 다음과 같이 로그의 첫 번째 줄만 볼 수 있다.

```bash
Jungui-MacBook-Pro:git-tutorial jung$ git log --pretty=short
commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
Author: Jung Hyunsoo <eqelizer@naver.com>

    Make README.md file.
```

###특정 폴더 / 파일의 로그를 출력

`git log [ 파일명 | 폴더명 ]`과 같이 명령어를 입력하면 지정한 파일 또는 폴더의 로그만을 볼 수 있다.

```bash
Jungui-MacBook-Pro:git-tutorial jung$ git log README.md 
commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
Author: Jung Hyunsoo <eqelizer@naver.com>
Date:   Wed Jan 20 18:01:05 2016 +0900

    Make README.md file.
    
    It is made for GitHub test.
```

###파일의 변경된 내용 출력

Commit에서 변경된 내용을 함께 확인하려 할 경우는 `-p` 옵션을 함께 사용하면 된다.

```bash
# 모든 파일의 변경된 내용 출력
Jungui-MacBook-Pro:git-tutorial jung$ git log -p
commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
Author: Jung Hyunsoo <eqelizer@naver.com>
Date:   Wed Jan 20 18:01:05 2016 +0900

    Make README.md file.
    
    It is made for GitHub test.

diff --git a/README.md b/README.md
new file mode 100644
index 0000000..e69de29

# 지정한 파일의 변경된 내용만 출력
Jungui-MacBook-Pro:git-tutorial jung$ git log -p README.md 
commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
Author: Jung Hyunsoo <eqelizer@naver.com>
Date:   Wed Jan 20 18:01:05 2016 +0900

    Make README.md file.
    
    It is made for GitHub test.

diff --git a/README.md b/README.md
new file mode 100644
index 0000000..e69de29
```


##`git diff` : 변경 내역 확인

`git diff` 명령어는 working tree, 스테이지 영역, 최신 Commit 사이의 변경을 확인할 때 사용된다.

확인을 위해 앞서 생성한 README.md 파일의 내용을 수정한다.

```bash
# README.md에 내용 추가
Jungui-MacBook-Pro:git-tutorial jung$ echo "#Git 튜토리얼" > README.md 

# 추가한 내용 확인
Jungui-MacBook-Pro:git-tutorial jung$ cat README.md 
#Git 튜토리얼
```

###`Working Tree`와 `스테이지 영역`의 차이를 확인하는 방법

`git diff` 명령어를 실행하면 현재 Working Tree와 스테이지 영역의 차이를 확인할 수 있다.

* Working Tree 영역 : 앞에서 추가한 내용 (New)
* 스테이지 영역 : 추가 되기 전의 내용 (Old)

```bash
# Working Tree와 스테이지 영역 간의 Diff
Jungui-MacBook-Pro:git-tutorial jung$ git diff
diff --git a/README.md b/README.md
index e69de29..fb2ee47 100644
--- a/README.md
+++ b/README.md
@@ -0,0 +1 @@
+#Git 튜토리얼

# 현재 상태
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch master
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   README.md

no changes added to commit (use "git add" and/or "git commit -a")

```

###`Working Tree`에서 최근에 변경된 부분을 확인하는 방법

`git diff HEAD` 명령어를 사용하면 Working Tree에서 최근에 변경된 내용을 확인할 수 있다.
`git commit` 명령어를 실행하기 전에 `git diff HEAD` 명령어를 실행하는 버릇을 만들어 현재 Commit과 이전 Commit의 차이를 파악하도록 하는게 좋다.

```bash
# 변경된 내용(Working Tree 영역)을 스테이지 영역에 추가
Jungui-MacBook-Pro:git-tutorial jung$ git add README.md 

# 앞서 사용한 명령어. 아무런 결과물을 출력하지 않는다.
Jungui-MacBook-Pro:git-tutorial jung$ git diff

# Working Tree 영역 간의 Diff
Jungui-MacBook-Pro:git-tutorial jung$ git diff HEAD
diff --git a/README.md b/README.md
index e69de29..fb2ee47 100644
--- a/README.md
+++ b/README.md
@@ -0,0 +1 @@
+#Git 튜토리얼

# 현재 상태
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch master
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

	modified:   README.md

# 변경사항 Commit
Jungui-MacBook-Pro:git-tutorial jung$ git commit -m "Add Index"
[master 0f00862] Add Index
 1 file changed, 1 insertion(+)

# Commit 후 상태 확인
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch master
nothing to commit, working directory clean

# Commit 로그 확인
Jungui-MacBook-Pro:git-tutorial jung$ git log
commit 0f0086235272609af5e9fdd07a5ff5148265e103
Author: Jung Hyunsoo <eqelizer@naver.com>
Date:   Wed Jan 20 20:54:05 2016 +0900

    Add Index

commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
Author: Jung Hyunsoo <eqelizer@naver.com>
Date:   Wed Jan 20 18:01:05 2016 +0900

    Make README.md file.
    
    It is made for GitHub test.
```