FROM openjdk:11

ENV SBT_VERSION 1.8.2

RUN curl -L -o sbt-$SBT_VERSION.zip https://github.com/sbt/sbt/releases/download/v$SBT_VERSION/sbt-$SBT_VERSION.zip

RUN unzip sbt-$SBT_VERSION.zip -d ops

WORKDIR /database-interface

ADD . /database-interface

CMD /ops/sbt/bin/sbt run