#!/bin/bash

# [Setup: First time only]
# python -m venv ~/Projects/FanaLite/PythonRegexProcessor/venv3

# [Run]
source ~/Projects/FanaLite/PythonRegexProcessor/venv3/bin/activate
FLASK_APP=api FLASK_RUN_HOST=192.168.1.134 FLASK_RUN_PORT=8090 flask run
