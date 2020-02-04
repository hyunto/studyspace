#Commit을 변경하는 조작


##`git reset` : 과거 상태로 복원

git은 ==분산형 버전 관리 시스템==이므로 과거 상태로 되돌리는 작업을 해도 다른 위치에 있는 리포지토리에 전혀 영향을 끼치지 않는다.
리포지토리의 HEAD, 스테이지, 현재의 Working Tree를 지정한 상태까지 복원하려면 `git reset --hard` 명령어를 사용한다.
```bash
#===========================================================
# 1. 롤백 전의 feature-A 브랜치의 README.md 파일 및 로그 확인
#===========================================================
$ cat README.md 
#Git 튜토리얼

  - This is feature-A

$ git log --graph
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

#===========================================================
# 2. feature-A 브랜치를 분기하기 이전의 상태로 복원
#    : git reset --hard 명령어 사용
#===========================================================
$ git reset --hard 0f0086235272609af5e9fdd07a5ff5148265e103
HEAD is now at 0f00862 Add Index

$ cat README.md 
#Git 튜토리얼

$ git log --graph
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
      

#===========================================================
# 3. fix-B 브랜치 작성
#===========================================================
$ git checkout -b fix-B
Switched to a new branch 'fix-B'

$ git branch
  feature-A
* fix-B
  master

#===========================================================
# 4. fix-B 브랜치의 README.md 파일 수정
#===========================================================
$ echo '' >> README.md 
$ echo '  - fix-B' >> README.md 
$ cat README.md 
#Git 튜토리얼

  - fix-B

#===========================================================
# 5. 수정된 내용을 Commit 후 로그 확인
#===========================================================
$ git add README.md 
$ git commit -m 'Fix B'
[fix-B 8826fc8] Fix B
 1 file changed, 2 insertions(+)

$ git log --graph
* commit 8826fc833a417624df5d23c27d560bd7139e9895
| Author: Jung Hyunsoo <eqelizer@naver.com>
| Date:   Thu Jan 28 09:30:40 2016 +0900
| 
|     Fix B
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

`git log` 명령어는 ==현재 브랜치의 로그만 확인==할 수 있다.
`git reflog` 명령어는 ==현재 리포지토리에서 수행된 모든 Commit 로그를 확인==할 수 있다.

따라서 특정 Commit 시점의 해시값를 찾을 때는 `git reflog` 명령어를 사용하는 것이 편하다.
Git의 `GC(Garbage Collection)`가 실행되어 이전의 상태를 제거하지 않았다면, 마치 타임머신처럼 언제든 로그에 나와 있는 변경 내역을 오고 갈 수 있다.
Git을 실수로 잘못 사용한 경우 `git reflog` 명령어로 해시값을 확인하여 원하는 상태로 복원할 수 있다.

과거 상태로 복원할 경우에는 `git reset --hard` 명령어를 사용한다.

```bash
#===========================================================
# 1. git reflog 명령어로 전체 Commit 로그를 확인한다.
#    위에서 4번째 로그(해시값 : 1899dd6)로 복원할 것이다.
#===========================================================
Jungui-MacBook-Pro:git-tutorial jung$ git reflog
-- git reset 을 한 이후 (below)
8826fc8 HEAD@{0}: commit: Fix B
0f00862 HEAD@{1}: checkout: moving from master to fix-B
0f00862 HEAD@{2}: reset: moving to 0f0086235272609af5e9fdd07a5ff5148265e103
-- git reset을 하기 전 (below)
1899dd6 HEAD@{3}: merge feature-A: Merge made by the 'recursive' strategy.
0f00862 HEAD@{4}: checkout: moving from feature-A to master
910f00a HEAD@{5}: checkout: moving from master to feature-A
0f00862 HEAD@{6}: checkout: moving from feature-A to master
910f00a HEAD@{7}: commit: Add feature-A in README.md
0f00862 HEAD@{8}: checkout: moving from master to feature-A
0f00862 HEAD@{9}: checkout: moving from feature-A to master
0f00862 HEAD@{10}: checkout: moving from feature-A to feature-A
0f00862 HEAD@{11}: checkout: moving from master to feature-A
0f00862 HEAD@{12}: checkout: moving from feature-A to master
0f00862 HEAD@{13}: checkout: moving from master to feature-A
0f00862 HEAD@{14}: checkout: moving from feature-A to master
0f00862 HEAD@{15}: checkout: moving from master to feature-A
0f00862 HEAD@{16}: commit: Add Index
a9acefb HEAD@{17}: commit (initial): Make README.md file.

#===========================================================
# 2. Master 브랜치로 이동한 후 원하는 시점으로 복원
#===========================================================
$ git checkout master
Switched to branch 'master'

$ git reset --hard 1899dd6
HEAD is now at 1899dd6 Merge branch 'feature-A'

#===========================================================
# 3. git log를 통해 master 브랜치의 로그를 확인
#===========================================================
$ git log --graph
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


##충돌 문제 해결

여러 사람과 협업을 하다보면 각 브랜치를 merge 할 때 충돌이 일어날 수 있다.
충돌을 해결하지 못하면 merge가 완료되지 않기 때문에 반드시 해결해야 한다.

충돌되는 부분은 보통 한쪽의 수정 내용을 완전히 삭제하거나 함께 공존게끔 한다.
이는 상황에 맞게 해결해야 한다.

```bash
#===========================================================
# 1. fix-B 브랜치 merge
#    이때 서로 충돌이 나서 merge가 fail 되었다.
#    출력 결과에 어떤 파일에서 충돌이 났는지 확인할 수 있다.
#===========================================================
$ git merge --no-ff fix-B
Auto-merging README.md
CONFLICT (content): Merge conflict in README.md
Automatic merge failed; fix conflicts and then commit the result.

#===========================================================
# 2. 충돌이 일어난 README.md 파일을 확인한다.
#===========================================================
$ cat README.md 
#Git 튜토리얼

<<<<<<< HEAD
  - This is feature-A
=======
  - fix-B
>>>>>>> fix-B

#===========================================================
# 3. README.md 파일을 열어 아래와 같이 수정한다.
#===========================================================
$ vim README.md 
$ cat README.md 
#Git 튜토리얼

  - This is feature-A
  - fix-B

#===========================================================
# 4. 충돌 문제를 해결한 후 Commit
#===========================================================
$ git branch
  feature-A
  fix-B
* master
$ git add README.md 
$ git commit -m "Fix confilct"
[master acf33d9] Fix confilct

#===========================================================
# 5. git log --graph 확인
#===========================================================
$ git log --graph
*   commit acf33d96b95e5c71308a8d79c2b18ebf870e50d5
|\  Merge: 1899dd6 8826fc8
| | Author: Jung Hyunsoo <eqelizer@naver.com>
| | Date:   Thu Jan 28 09:45:29 2016 +0900
| | 
| |     Fix confilct
| |   
| * commit 8826fc833a417624df5d23c27d560bd7139e9895
| | Author: Jung Hyunsoo <eqelizer@naver.com>
| | Date:   Thu Jan 28 09:30:40 2016 +0900
| | 
| |     Fix B
| |     
* |   commit 1899dd6812cd92a02fc04e8baec5cf0aaaea7ffa
|\ \  Merge: 0f00862 910f00a
| |/  Author: Jung Hyunsoo <eqelizer@naver.com>
|/|   Date:   Thu Jan 21 21:54:06 2016 +0900
| |   
| |       Merge branch 'feature-A'
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

##`git commit --amend` : commit 메시지 수정

바로 직전에 작성했던 Commit 메시지를 수정하려 할 경우 `git commit --amend` 명령어를 사용하면 된다.

```bash
#===========================================================
# 1. git commit --amend 를 입력하면 바로 에디터가 실행된다.
#    에디터 내용 수정 후 저장을 하면 아래와 같은 출력물이 나온다.
#===========================================================
$ git commit --amend
[master 4523a61] Merge branch 'fix-B'
 Date: Thu Jan 28 09:45:29 2016 +0900
 
#===========================================================
# 2. 에디터 내 내용 수정
#    "Fix conflict" 부분을 "Merge branch 'fix-B'"로 수정 후 종료
#===========================================================
  1 Fix conflict
  2 
  3 # Please enter the commit message for your changes. Lines starting
  4 # with '#' will be ignored, and an empty message aborts the commit.
  5 #
  6 # Date:      Thu Jan 28 09:45:29 2016 +0900
  7 #
  8 # On branch master
  9 # Changes to be committed:
 10 #   modified:   README.md
 
#===========================================================
# 3. git log --graph를 통해 변경된 Commit 메시지 내용 확인
#===========================================================
$ git log --graph
*   commit 4523a61583ed1626bd277c6ea01a11781d9c8e48
|\  Merge: 1899dd6 8826fc8
| | Author: Jung Hyunsoo <eqelizer@naver.com>
| | Date:   Thu Jan 28 09:45:29 2016 +0900
| | 
| |     Merge branch 'fix-B'
| |   
| * commit 8826fc833a417624df5d23c27d560bd7139e9895
| | Author: Jung Hyunsoo <eqelizer@naver.com>
| | Date:   Thu Jan 28 09:30:40 2016 +0900
| | 
| |     Fix B
| |     
* |   commit 1899dd6812cd92a02fc04e8baec5cf0aaaea7ffa
|\ \  Merge: 0f00862 910f00a
| |/  Author: Jung Hyunsoo <eqelizer@naver.com>
|/|   Date:   Thu Jan 21 21:54:06 2016 +0900
| |   
| |       Merge branch 'feature-A'
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

##`git rebase -i` : 변경 내역 조작

토픽 브랜치를 merge하기 전, 이미 commit 된 내용에 철자 오류 등이 있을 수 있다.
이런 경우에는 코드를 수정하고 Commit을 한 뒤, 바로 앞의 Commit에 합쳐버리면 된다.
철자 오류 같은 경우 그리 중요한 변경사항이 아니므로 뭉개버리는 것이다.

추가로 앞서 ==Commit==시 `git add` 명령어 실행 후 `git commit`을 순서대로 실행하였다.
짧은 변경일 경우 번거롭게 2번의 과정을 거치지 않고 `git commit -am` 명령어로 한번에 Commit 할 수 있다.

```bash
#===========================================================
# 1. feature-C 브랜치 생성
#===========================================================
$ git checkout -b feature-C
Switched to a new branch 'feature-C'

#===========================================================
# 2. feature-C 브랜치의 README.md 파일에 내용 추가 후 Commit
#===========================================================
$ echo '  - feature-C' >> README.md 
$ cat README.md 
#Git 튜토리얼

  - This is feature-A
  - fix-B
  - feature-C

$ git commit -am "Add feature-C"
[feature-C 508bb64] Add feature-C
 1 file changed, 1 insertion(+)

#===========================================================
# 3. 오타 수정
#    README.md 파일 내용을 아래와 같이 수정한다.
#    이전의 내용이 잘못된 것으로 가정한다.
#===========================================================
$ git diff
diff --git a/README.md b/README.md
index 9019c5e..f914b5b 100644
--- a/README.md
+++ b/README.md
@@ -2,4 +2,4 @@
 
   - This is feature-A
   - fix-B
-  - feature-C
+  - feature-C-modify

$ git commit -am "Fix typo"
[feature-C 9892c89] Fix typo
 1 file changed, 1 insertion(+), 1 deletion(-)

#===========================================================
# 4. 변경 내역 조작
#
#===========================================================

```