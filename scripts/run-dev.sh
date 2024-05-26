#!/bin/bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]:-$0}" )" >/dev/null 2>&1 && pwd )"
export BASE_DIR="$(cd $SCRIPT_DIR/.. && pwd)"

$BASE_DIR/mvnw quarkus:dev
