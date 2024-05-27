# Assignment description
[assignment.md](assignment.md)

# Run in development mode with testcontainers
`./script/run-dev.sh`
OR
`mvnw quarkus:dev`

# Run in docker-compose
```commandline
cd docker-compose
docker-compose build
docker-compose up
```

## Stop docker-compose
```commandline
docker-compose down
```
OR
`CTRL-C`

# Run in docker
```commandline
./scripts/native-docker-build.sh
./scripts/postgres-start.sh
./scripts/native-docker-start.sh
```

## Stop docker
```commandline
./scripts/postgres-stop.sh
./scripts/native-docker-stop.sh
```

# API

## Upload CSV
```commandline
curl --location 'http://localhost:8080/api/v1/barcodes/upload-csv' \
--form 'file=@"datasheet.csv"'
```

## Get all barcodes
```commandline
curl --location 'http://localhost:8080/api/v1/barcodes/all'
```

## Get item by barcode
```commandline
curl --location 'http://localhost:8080/api/v1/barcodes/?barcode=310975'
```

## Delete item by barcode
```commandline
curl --location --request DELETE 'http://localhost:8080/api/v1/barcodes/?barcode=310975'
```

# TODO
- add data validation
- add more tests
- add more code comments
- add swagger
- add flyway for database migrations
- add jdk build & docker-compose-jdk.yaml
- move category to a separate entity/table
