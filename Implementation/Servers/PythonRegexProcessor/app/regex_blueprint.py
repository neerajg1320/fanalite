from flask import Blueprint, jsonify, request
from jsonschema import validate
from jsonschema.exceptions import ValidationError
import json

from app.plain.regex import check_compile_regex, regex_apply_on_text, regex_apply_on_text_brief
from app.plain.exceptions import InvalidParams
from app.plain.debug_utils import debug_log


rbapp = Blueprint('regex_blueprint', __name__, url_prefix='/regex')


regex_is_valid_schema = {
    'type': 'object',
    'properties': {
        'name': { 'type': 'string' },
        'regex': { 'type': 'string' },
        'flags': { 'type': 'object' }
    },
    'required': ['regex']
}

regex_apply_schema = {
    'type': 'object',
    'properties': {
        'regex': { 'type': 'string' },
        'text': {'type': 'string'},
        'flags': { 'type': 'object' }
    },
    'required': ['regex', 'text']
}

DEBUG = False


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
        response = (jsonify({ "status": "FAILED", "error": error }),
                    status if status is not None else 400)
    else:
        response = (jsonify({"status": "SUCCESS", "result": result}),
                    status if status is not None else 200)

    debug_log("response:", result if error is None else error, active=False)
    return response


@rbapp.errorhandler(InvalidParams)
def handle_invalid_params(error):
    return create_response(error=error.message)


@rbapp.errorhandler(ValidationError)
def handle_validation_error(error):
    return create_response(error=error.message)


@rbapp.route('/validate', methods=['POST'])
def regex_isvalid_post():
    request_dict = get_validated_json(request, regex_is_valid_schema)

    _, regex_error = check_compile_regex(request_dict["regex"])

    result = {
        'valid': regex_error is None
    }
    if regex_error is not None:
        result.update({'regexError': regex_error})

    return create_response(result=result)


@rbapp.route('/apply', methods=['POST'])
def regex_apply():
    request_dict = get_validated_json(request, regex_apply_schema)

    regex_str = request_dict["regex"]
    text = request_dict["text"]
    flags = request_dict.get("flags", None)

    if DEBUG:
        print("Regex Str:\n", regex_str)
        print("Text:\n", text)
        print("Flags:\n", flags)

    result = regex_apply_on_text_brief(regex_str, text, flags=flags)

    return create_response(result=result, error=result.get("error", None), status=200)
