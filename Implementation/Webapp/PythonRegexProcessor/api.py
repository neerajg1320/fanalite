from flask import Flask
from app.regex_blueprint import rbapp


app = Flask(__name__)

app.register_blueprint(rbapp)


DEBUG = False


@app.route("/info")
def hello_world():
    return "PythonRegexProcessor: Flask Application"


