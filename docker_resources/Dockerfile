FROM mysql/mysql-server:5.6
COPY /bachelor_manager.sql /docker-entrypoint-initdb.d/test.sql

########################### DEPLOY ##########################
# - Docker build -t <name of image> .                       #
# Name of image MUST be lower case.                         #
# Make sure to build from GoogleFolder containing Dockerfile      #
# - Docker run -p host_port:8080 <name of image>            #
# Host port should be any open port on the host system,     #
# which will be your way to access the container.           #
# ###########################################################

#Start a MySQL Server Instance
# docker run --name my-container-name -e MYSQL_ROOT_PASSWORD=my-secret-pw -p 5050:3306 -d dbv12_img

#Connect to MySQL from an Application in another container.
# docker run --name app-container-name --link my-container-name:mysql -d app-that-uses-mysql

#Commandline execution inside the container
#docker exec -it my-container-name bash
#docker exec -it my-container-name mysql -uroot -p (mysql mode)

#docker run --name app-container-name --link my-container-name:mysql -d app-that-uses-mysql
