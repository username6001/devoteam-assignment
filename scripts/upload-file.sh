#!/bin/bash
set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]:-$0}" )" >/dev/null 2>&1 && pwd )"
export BASE_DIR="$(cd $SCRIPT_DIR/.. && pwd)"

echo $BASE_DIR/datasheet.csv
cat $BASE_DIR/datasheet.csv

curl --location 'http://localhost:8080/api/v1/barcodes/echo2' \
--form 'file=@"'"$BASE_DIR"'/datasheet.csv"'
