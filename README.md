# Melita-E-Shop Spring Boot Micro-Service (Rest Frontend)

Run RabbitMQ on Docker using command:
* `docker run --rm -it -p 15672:15672 -p 5672:5672 rabbitmq:3-management`

Create IntelliJ Configuration to run `RestServer.java` using Environment > VM Options 
* `-Dspring.cloud.config.enabled=true`

The component in the following git repo,
* https://github.com/nzneil22/Melita-Config-Server

must already be running since it provides the cloud configuration for this component.

Components from the following repositories must also be running for the full workflow:
* https://github.com/nzneil22/Melita-Amqp-Server
* https://github.com/nzneil22/Melita-Message-Consumer

After all the components are correctly configured and functional, the user should then run the following post-man collection:
* https://www.getpostman.com/collections/bc09ef80dc90168b0a09
