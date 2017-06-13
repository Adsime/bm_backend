FROM payara/server-full
RUN apt-get update && apt-get install -y
RUN apt-get install maven -y
