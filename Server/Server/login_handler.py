
user_dict = {}

def load_users():
    with open("login.txt", "r") as file:
        line = file.readline()
        while line != "":
            line.split(":")
            user_dict[line[0]] = line[1]

def login_check(user, password):
    with open("login.txt", "r") as file:
        line = file.readline()
        while line != "":
            check_user = line.split(":")
            check_user[1] = check_user[1].rstrip("\n")
            if check_user[0] == user and check_user[1] == password:
                return True
            line = file.readline()
    return False

def register_user(user, password):
    if not login_check(user, password):
        with open("login.txt", "a") as file:
            file.write("\n")
            file.write(f"{user}:{password}")
        return True
    return False
