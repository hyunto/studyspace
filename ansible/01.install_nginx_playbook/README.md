# Install nginx via ansible playbook

```bash
$ pip3 install virtualenv
$ python3 -m virtualenv venv
$ source ./venv/bin/activate
$ pip install -r requirements.txt
$ vagrant up
$ ansible-playbook web-notls.yml
$ curl http://localhost:8080
```

```bash
$ openssl req -x509 -nodes -days 3650 -newkey rsa:2048 -subj /CN=localhost -keyout files/nginx.key -out files/nginx.crt
$ ansible-playbook web-tls.yml
```

```bash
$ curl https://raw.githubusercontent.com/ansible/ansible/devel/contrib/inventory/vagrant.py -ovagrant.py -s
$ chmod +x vagrant.py
$ pip3 install paramiko
$ python3 vagrant.py
```