import time
import json
import redis
import re
import psycopg2
from compreface import CompreFace
from compreface.service import VerificationService
DOMAIN: str = 'http://faceapi.logixpress.co'
PORT: str = '8000'
VERIFICATION_API_KEY: str = 'e9ef6910-abc5-48ab-bb43-0b48c59bb292'


r= redis.StrictRedis(host='localhost',port=6379,charset="utf-8", decode_responses=True)
p = r.pubsub()
p.psubscribe('*:*')
def verify(profile,attendance):
    compre_face: CompreFace = CompreFace(DOMAIN, PORT, {
        "limit": 0,
        "det_prob_threshold": 0.8,
        "face_plugins": "age,gender",
        "status": "true"
     })

    verify: VerificationService = compre_face.init_face_verification(VERIFICATION_API_KEY)
    image_path1: str = f'http://attendance.logixpress.co:9000/profiles/{profile}.jpg'
    image_path2: str = f'http://attendance.logixpress.co:9000/attendance/{attendance}.jpg'
    result=verify.verify(image_path1, image_path2)
    print(result["result"][0]["face_matches"][0]["similarity"])
    
def run_query(attendance_id):
    connection = psycopg2.connect(user="postgres",
                                  password="postgres",
                                  host="127.0.0.1",
                                  port="5432",
                                  database="attendance")
    cursor = connection.cursor()
    query = f"select employee_id from attendance where attendance_id={attendance_id}"
    cursor.execute(query)
    records = cursor.fetchall()
    employee_id=""
    for row in records:
        employee_id=row[0]
    return employee_id

def process(object_name):
    x = re.search("^attendance\/(.*)\.jpg$", object_name)
    if x:
        employee_id=run_query(x[1])
        attendance_id=x[1]
        verify(employee_id,attendance_id)
while True:
    message = p.get_message()
    if message:
        data=r.hgetall("bucket-events")
        print(data)
        if (data.keys()):
            process(list(data.keys())[0])
            keys=list(r.hgetall('bucket-events').keys())
            r.hdel('bucket-events', *keys)
    else:
        time.sleep(0.01)
