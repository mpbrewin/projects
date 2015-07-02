# A program demonstrating interrupts and debouncing

import RPi.GPIO as GPIO
GPIO.setmode(GPIO.BOARD)    #pinouts refer to pin nunber
GPIO.setwarnings(False)

#assign GPIOs
p1 = 10
led0 = 36
led1 = 38
led2 = 40

counter = 0 #counts button presses

GPIO.setup(p1, GPIO.IN, GPIO.PUD_UP)    #input, pullup resistor
GPIO.setup(led0,GPIO.OUT)               #outputs
GPIO.setup(led1,GPIO.OUT)
GPIO.setup(led2,GPIO.OUT)

#function to handle the interrupt, i.e. when button is pressed
def bp(channel):
    global counter      # have to define the variable as global within function
    if counter == 0:
        print("1 press")
    elif counter == 1:
        print("2 presses")
    else:
        print("3 presses")
    counter+=1
    print (counter)

#declare interrupt pin 10, falling edge (pressed), function, debounce
GPIO.add_event_detect(p1, GPIO.FALLING, callback=bp, bouncetime=200)

#this is constantly being executed
#when button is pressed, process will be interrupted
while True:
    if counter == 3:
        print("got to 3!")
        counter+=1

GPIO.cleanup()
    
