from flask import Flask
from flask_ask import Ask, statement, convert_errors
import RPi.GPIO as GPIO
import logging
import time

GPIO.setmode(GPIO.BCM)

app = Flask(__name__)
ask = Ask(app, '/')

logging.getLogger("flask_ask").setLevel(logging.DEBUG)

@ask.intent('GPIOControlIntent', mapping={'status': 'status', 'pin': 'pin'})
def gpio_control(status, pin):

    try:
        pinNum = int(pin)
    except Exception as e:
        return statement('Pin number not valid.')

    GPIO.setup(pinNum, GPIO.OUT)

    if status in ['on', 'high']:    GPIO.output(pinNum, GPIO.HIGH)
    if status in ['off', 'low']:    GPIO.output(pinNum, GPIO.LOW)

    return statement('Turning pin {} {}'.format(pin, status))

@ask.intent('PartyTime', mapping={})
def partyTime():

    GPIO.setup(5, GPIO.OUT)
    GPIO.setup(6, GPIO.OUT)
    GPIO.setup(13, GPIO.OUT)
    GPIO.setup(19, GPIO.OUT)
    GPIO.setup(26, GPIO.OUT)
    for i in range(20):
        for pinNum in [5,6,13,19,26]:
            GPIO.output(pinNum, GPIO.HIGH)
            time.sleep(.5)
            GPIO.output(pinNum, GPIO.LOW)
    return statement('Done')

app.run()
