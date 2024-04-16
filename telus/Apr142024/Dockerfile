FROM openjdk:17-oracle as build
MAINTAINER victorofff@gmail.com

COPY . /usr/src/app/
WORKDIR /usr/src/app/
RUN ./mvnw clean install

# in theory there should be lightweight image to run only
FROM openjdk:17-alpine

RUN adduser --no-create-home -u 1000 -D appuser
RUN mkdir /app \
    && chown -R appuser /app
COPY --from=build /usr/src/app/target/app.jar /app/app.jar

COPY run.sh /app/
RUN chown appuser:appuser /app/run.sh \
  && chmod +x /app/run.sh

USER appuser
WORKDIR /app

ENTRYPOINT ["/app/run.sh"]
EXPOSE 8080

