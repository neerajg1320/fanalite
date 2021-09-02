from flask import Flask
from regex_blueprint import rpapp


app = Flask(__name__)

app.register_blueprint(rpapp)


DEBUG = False


@app.route("/info")
def hello_world():
    return "PythonRegexProcessor: Flask Application"


