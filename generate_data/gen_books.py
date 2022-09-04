import requests
import sys

def generate_title():
    return requests.post("https://randomall.ru/api/general/bookname").text[1:-1]

for i in range(1000):
    print(generate_title())
    print(i, file=sys.stderr)