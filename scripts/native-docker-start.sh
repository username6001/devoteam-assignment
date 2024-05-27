#!/bin/bash
set -e

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIR=$(cd $SCRIPT_DIR/.. && pwd)

NETWORK=quarkus-network

cd $PROJECT_DIR
docker run -d -p 8080:8080 --network $NETWORK --name quarkus-react quarkus-react \
  -Dquarkus.datasource.db-kind=postgresql \
  -Dquarkus.datasource.username=postgres \
  -Dquarkus.datasource.password=pgpass \
  -Dquarkus.datasource.jdbc.url=jdbc:postgresql://postgresql:5432/postgres \
  -Dquarkus.hibernate-orm.database.generation=create
