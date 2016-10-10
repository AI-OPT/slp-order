tagversion=v1.0
git reset --hard origin/master 
git pull 
chmod a+x onekey-docker.sh 
gradle clean && gradle build -x test 
docker build -t 10.19.13.18:5000/slp-order:${tagversion} .   
docker push 10.19.13.18:5000/slp-order:${tagversion} 

docker rmi aioptapp/slp-order:${tagversion} 
docker tag 10.19.13.18:5000/slp-order:${tagversion} aioptapp/slp-order:${tagversion} 
docker login --username=aioptapp --password=aioptapp@123 --email=wuzhen3@asiainfo.com 
docker push aioptapp/slp-order:${tagversion} 