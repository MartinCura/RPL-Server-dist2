# EXAMPLE USAGE:  docker run --rm rpl java

# Pull base image.
FROM  stackbrew/ubuntu:16.04
RUN apt-get update -y

# Install Java
RUN apt-get install -y software-properties-common
RUN add-apt-repository -y ppa:webupd8team/java
RUN echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections
RUN echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections
RUN apt-get update -y
RUN apt-get install -y oracle-java8-installer

# Install gcc
RUN apt-get install -y gcc

# Install python
RUN apt-get install -y python

# Add runner
ADD extras/ /root/extras/
ADD target/rpl-runner-0.0.1.jar /root/


# Execute
WORKDIR /root/
ENTRYPOINT ["java", "-jar", "rpl-runner-0.0.1.jar"]