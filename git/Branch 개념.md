#Branch 생성 및 조작

##Branch 개념

브랜치는 각 작업을 병행하여 진행하려 할 때 사용한다.

`master`는 기본적으로 생성되는 브랜치다.
만약 기능 A를 추가하고 싶으면 `feature-A` 브랜치를 생성하고, 버그 B를 수정하고 싶으면 `fix-B` 브랜치를 생성하여 계속해서 개발한다.
이후 각각의 작업이 완료되면 `master` 브랜치와 `merge` 한다.
이를 통해 `master` 브랜치는 항상 배포 가능한 상태로 유지할 수 있다.

아래 그림의 왼쪽은 `브랜치로 나눠서 개발`하는 것을 보여주고, 오른쪽은 `feature-A 브랜치의 작업이 종료되어 merge 되는 과정`을 보여 준다.

```mermaid
graph BT;
	master-->feature-A;
    master-->fix-B;
    _-->master;

	'-->`
    `-->fix-B`
    `-->"
    "-->feature-A`
    feature-A`-->master`
    `-->master`
```

이러한 방법흔 GitHub을 이용한 기본적인 개발 방법이며 여러 사람과 효율적으로 함께 개발할 수 있게 해준다.


##`git branch` : 브랜치를 보는 방법

`git branch` 명령어는 브랜치 목록을 표시하고, 현재 어떤 브랜치를 사용하는지 확인할 수 있다.
브랜치 목록 중 `*`이 표시된 브랜치가 현재 사용중인 브랜치를 뜻한다.

```bash
Jungui-MacBook-Pro:git-tutorial jung$ git branch
* master
```
######참고로 `git branch` 명령어의 manual page를 보면 git branch를 `git-branch - List, Create, or Delete branches`라고 정의하고 있다. 즉, git branch를 가지고 git의 branch를 조정할 수 있다. `git branch --help` 명령어로 man page에 들어가 자세한 설명을 읽어보는 것도 좋다.


##`git checkout -b` : 브랜치를 만들고 이동하는 방법

###새로운 브랜치를 생성 후 이동

`git checkout -b` 명령어를 사용하여 새로운 브랜치를 ==작성==하고, ==이동==할 수 있다.

아래 실습의 과정 1, 2과정은 동일하게 작동한다.
feature-A라는 브랜치를 작성한 후, feature-A 브랜치로 이동하는 작업이다.

```bash
# === 과정 1 ===
# git checkout -b [브랜치 이름]
# 위 명령어를 통해 새로운 브랜치를 작성한 후, 작성한 브랜치로 이동한다.
# git branch 명령어 출력에 *이 표시된것이 현재 브랜치를 뜻한다.
Jungui-MacBook-Pro:git-tutorial jung$ git checkout -b feature-A
Switched to a new branch 'feature-A'

Jungui-MacBook-Pro:git-tutorial jung$ git branch
* feature-A
  master


# 브랜치 삭제
Jungui-MacBook-Pro:git-tutorial jung$ git checkout master
Switched to branch 'master'
Jungui-MacBook-Pro:git-tutorial jung$ git branch
  feature-A
* master
Jungui-MacBook-Pro:git-tutorial jung$ git branch -d feature-A
Deleted branch feature-A (was 0f00862).
Jungui-MacBook-Pro:git-tutorial jung$ git branch
* master


# === 과정 2 ===
# 아래 실습은 과정 1과 동일한 의미를 가진다
# git branch [브랜치 이름] 명령어로 새로운 브랜치를 작성한 후
# git checkout [브랜치 이름] 명령어로 해당 브랜치로 이동하였다.
Jungui-MacBook-Pro:git-tutorial jung$ git branch feature-A
Jungui-MacBook-Pro:git-tutorial jung$ git checkout feature-A
Switched to branch 'feature-A'
Jungui-MacBook-Pro:git-tutorial jung$ git branch
* feature-A
  master
```

###지정한 브랜치로 이동

앞서 *과정 2*에서도 볼 수 있듯이 `git checkout [브랜치 이름]` 명령어를 통해 원하는 브랜치로 이동할 수 있다.
또한 `git checkout -`명령어를 통해 바로 이전에 사용했던 브랜치로 이동할 수 있다.

```bash
# git checkout [브랜치 이름]
# 위 명령어를 사용하여 master 브랜치로 이동
Jungui-MacBook-Pro:git-tutorial jung$ git checkout master
Switched to branch 'master'
Jungui-MacBook-Pro:git-tutorial jung$ git branch
  feature-A
* master

# git checkout -
# 위 명령어를 사용하여 바로 이전의 브랜치로 이동
Jungui-MacBook-Pro:git-tutorial jung$ git checkout -
Switched to branch 'feature-A'
Jungui-MacBook-Pro:git-tutorial jung$ git branch
* feature-A
  master
```

######참고로 `git checkout` 명령어의 manual page에는 git checkout을 `git-checkout - Switch branches or restore working tree files`라고 정의하고 있다.

###작성한 브랜치와 master 브랜치와 비교

####순서 1> feature-A 브랜치의 README.md 파일의 내용을 변경한다.
```bash
#===========================================================
# 1. feature-A 브랜치로 이동한다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git checkout feature-A
Switched to branch 'feature-A'


#===========================================================
# 2. README.md 파일에 내용을 추가한다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ echo '' >> README.md 
Jungui-MacBook-Pro:git-tutorial jung$ echo '  - This is feature-A' >> README.md 
Jungui-MacBook-Pro:git-tutorial jung$ cat README.md 
#Git 튜토리얼

  - This is feature-A


#===========================================================
# 3. feature-A 브랜치의 상태를 확인한다.
#    현재 스테이지 영역으로 등록이 되어 있지 않음을 알 수 있다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch feature-A
Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)

	modified:   README.md

no changes added to commit (use "git add" and/or "git commit -a")


#===========================================================
# 4. git add 명령어을 사용하여 스테이지 영역으로 등록
#    등록 후 상태를 확인하면 스테이지 영역에 등록된 것을 확인할 수 있다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git add README.md 
Jungui-MacBook-Pro:git-tutorial jung$ git status
On branch feature-A
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

	modified:   README.md
    
#===========================================================
# 5. git commit 명령어로 변경사항을 리파지토리에 등록한다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git commit -m "Add feature-A in README.md"
[feature-A 910f00a] Add feature-A in README.md


#===========================================================
# 6. 로그를 확인하면 변경사항이 반영된 것을 알 수 있다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git log --pretty=short README.md 
commit 910f00ad43b171f50ed763585d58b19befd09834
Author: Jung Hyunsoo <eqelizer@naver.com>

    Add feature-A in README.md

commit 0f0086235272609af5e9fdd07a5ff5148265e103
Author: Jung Hyunsoo <eqelizer@naver.com>

    Add Index

commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
Author: Jung Hyunsoo <eqelizer@naver.com>

    Make README.md file.
```

####순서 2> master 브랜치의 REAMDE.md 확인

```bash
#===========================================================
# 1. master 브랜치로 이동
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git checkout master
Switched to branch 'master'
Jungui-MacBook-Pro:git-tutorial jung$ git branch
  feature-A
* master

#===========================================================
# 2. master 브랜치의 README.md 파일 확인
#    앞서 추가한 내용이 master 브랜치에서는 보이지 않는 것을 확인할 수 있다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ cat README.md 
#Git 튜토리얼
```



##토픽 브랜치 / 통합 브랜치

###토픽 브랜치
`토픽 브랜치`란 ==특정 주제만을 위해 개발이 이뤄지는 브랜치==를 뜻한다.
여기서 **특정 주제**란 **새로운 기능을 개발하거나 버그를 수정하는 등**의 의미를 지닌다.
토픽 브랜치에서 해당 주제에 대한 개발이 완료되면 다시 통합 브랜치와 합쳐준다.

이러한 방법을 통해 다른 개발자가 계속해서 토픽 브랜치를 만들어 안전하게 개발할 수 있게 된다.


###통합 브랜치
`통합 브랜치`란 ==토픽 브랜치를 나누고, 다시 통합하는 공간==을 뜻한다.
일반적으로 `master 브랜치`를 통합 브랜치로 사용한다.

즉, 토픽 브랜치에서 개발이 완료된 후 통합 브랜치로 다시 합쳐지게 된다.
그렇기 때문에 개발이 실제로 이뤄지는 브랜치는 통합 브랜치가 아닌 토픽 브랜치가 된다.



##`git merge` : 브랜치 합치기 (merge)

```bash
#===========================================================
# 1. master 브랜치로 이동한다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git checkout master
Switched to branch 'master'

#===========================================================
# 2. git merge --no-ff [merge 할 브랜치 이름]
#    명령어를 사용하여 merge 한다.
#    --no-ff 옵션의 의미는 git merge --help 를 통해 확인할 수 있다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git merge --no-ff feature-A
Merge made by the 'recursive' strategy.
 README.md | 2 ++
 1 file changed, 2 insertions(+)

#===========================================================
# 3. 이제 master 브랜치에서도 feature-A 브랜치에서 변경한 내용을
#    확인할 수 있다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git branch
  feature-A
* master
Jungui-MacBook-Pro:git-tutorial jung$ cat README.md 
#Git 튜토리얼

  - This is feature-A
```



##`git log --graph` : 브랜치의 변경 사항을 시각적으로 확인

`git log --graph` 명령어를 통해 브랜치의 생성, 삭제, merge 등을 시각적으로 확인할 수 있다.

```bash
# git log --graph 명령어로 확인
Jungui-MacBook-Pro:git-tutorial jung$ git log --graph
*   commit 1899dd6812cd92a02fc04e8baec5cf0aaaea7ffa
|\  Merge: 0f00862 910f00a
| | Author: Jung Hyunsoo <eqelizer@naver.com>
| | Date:   Thu Jan 21 21:54:06 2016 +0900
| | 
| |     Merge branch 'feature-A'
| |   
| * commit 910f00ad43b171f50ed763585d58b19befd09834
|/  Author: Jung Hyunsoo <eqelizer@naver.com>
|   Date:   Thu Jan 21 21:25:45 2016 +0900
|   
|       Add feature-A in README.md
|  
* commit 0f0086235272609af5e9fdd07a5ff5148265e103
| Author: Jung Hyunsoo <eqelizer@naver.com>
| Date:   Wed Jan 20 20:54:05 2016 +0900
| 
|     Add Index
|  
* commit a9acefb39ddb5bdf6a7fb0126d55d6648622a13d
  Author: Jung Hyunsoo <eqelizer@naver.com>
  Date:   Wed Jan 20 18:01:05 2016 +0900
  
      Make README.md file.
      
      It is made for GitHub test.
```