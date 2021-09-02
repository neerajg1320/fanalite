from flask import Flask, jsonify, request
from regex import check_compile_regex, regex_apply_on_text
from exceptions import InvalidParams
from debug_utils import debug_log
from jsonschema import validate

import json


app = Flask(__name__)

DEBUG = False


@app.route("/info")
def hello_world():
    return "PythonRegexProcessor: Flask Application"

regex_apply_schema = {
    'type': 'object',
    'properties': {
        'regex': { 'type': 'string' },
        'text': {'type': 'string'},
        'flags': { 'type': 'object' }
    },
    'required': ['regex', 'text']
}


def get_validated_json(request, schema):
    if request.content_type is None or not 'application/json' in request.content_type:
        raise InvalidParams("Error! json data missing")

    request_data = request.get_data().decode('utf-8')

    try:
        request_dict = json.loads(request_data, strict=False)
    except json.decoder.JSONDecodeError as e:
        raise InvalidParams(str(e))

    if request_dict is None:
        raise InvalidParams('Error! json body is missing')

    # This will raise ValidationError in case the request is different from schema
    validate(request_dict, schema)

    return request_dict


def create_response(result=None, error=None, status=None):
    if error is not None:
        response = (jsonify({ "status": "failed", "error": error }),
                    status if status is not None else 400)
    else:
        response = (jsonify({"status": "success", "result": result}),
                    status if status is not None else 200)

    debug_log("response:", result if error is None else error, active=False)
    return response


@app.route('/regex/validate', methods=['POST'])
def regex_isvalid_post():
    request_data = request.get_data().decode('utf-8')

    try:
        request_dict = json.loads(request_data, strict=False)
    except json.decoder.JSONDecodeError as e:
        raise InvalidParams(str(e))

    _, regex_error = check_compile_regex(request_dict["regex"])

    result = {
        'isValid': regex_error is None
    }
    if regex_error is not None:
        result.update({'regex_error': regex_error})

    return create_response(result=result)


@app.route('/regex/apply', methods=['POST'])
def regex_apply():
    request_dict = get_validated_json(request, regex_apply_schema)

    regex_str = request_dict["regex"]
    text = request_dict["text"]
    flags = request_dict.get("flags", None)

    if DEBUG:
        print("Regex Str:\n", regex_str)
        print("Text:\n", text)
        print("Flags:\n", flags)

    result = regex_apply_on_text(regex_str, text, flags=flags)

    return create_response(result=result, error=result["error"], status=200)
