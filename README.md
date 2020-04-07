## Build 

~~~
 gradlew clean build unpack 
docker build --rm -t sbe .
~~~

## Run 

~~~
docker-compose up -d 
~~~

## Stop  

~~~
 docker-compose stop
~~~

## Rebuild Docker Container after Image Change

~~~
docker-compose up -d --no-deps --build sbe
~~~

## Publish to local maven repository:

~~~
gradlew clean build publishToMavenLocal
~~~
