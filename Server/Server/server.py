# https://blog.miguelgrinberg.com/post/designing-a-restful-api-with-python-and-flask

import random

from flask import Flask, jsonify, request, make_response, abort
from Server import login_handler

app = Flask(__name__)


####login_handler.load_users()


@app.errorhandler(400)
def bad_request(error):
    return make_response(jsonify({'error': 'bad request'}), 400)


@app.errorhandler(401)
def invalid(error):
    return make_response(jsonify({'error': 'invalid'}), 401)


shoplist = [
    {
        'name': 'Jellyfish',
        'image': 'jellyfish',
        'description': "It stings",
        'cost': 1,
        'itemId': 16,
        'keywords': 'animal:fish:pain'
    },
    {
        'name': 'Kitten',
        'image': 'kitten',
        'description': "It purrs",
        'cost': 1,
        'itemId': 17,
        'keywords': 'animal:mammal:pet'
    }
]


@app.route('/list', methods=['GET'])
def get_list():
    print("list")
    return jsonify({'list': shoplist})


@app.route('/buy', methods=['POST'])
def post_buy():
    print("buy")

    if not request.json:
        abort(400)

    if 'name' in request.json:
        print("buy: " + request.json.get('name', '?'))
    else:
        print("?")

    return jsonify({'spend': 0})


@app.route('/login', methods=['POST'])
def user_login():
    print("login")
    print(request.json)
    username = request.json.get('username')
    password = request.json.get('password')
    print(username + " " + password)
    if login_handler.login_check(username, password):
        return jsonify({"success": 1})
    else:
        return jsonify({"success": 0})


@app.route('/register', methods=['POST'])
def register_user():
    print("register")
    print(request.json)
    username = request.json.get('username')
    password = request.json.get('password')
    print(username + " " + password)
    if login_handler.register_user(username, password):
        return jsonify({"success": 1})
    else:
        return jsonify({"success": 0})


@app.route('/featured', methods=['POST'])
def get_featured():
    print("get featured")
    shop_size = int(request.json.get('size'))
    return jsonify({"item": random.randint(0, shop_size-1)})


app.run(port='5005', debug=True)
