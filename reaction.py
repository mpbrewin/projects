# A two player game testing reactions. When the signal light lights up
# The players are to press a button. Whoever presses their button first gets
# a point, and lights up an LED. First to 3 wins!

import RPi.GPIO as GPIO
import time
import random
GPIO.setmode(GPIO.BOARD)    #pinouts refer to pin nunber
GPIO.setwarnings(False)

#assign GPIOs
signal = 10     # The LED light the players react to
led0 = 16       # Player 1 1 point
led1 = 18       # Player 1 2 points
led2 = 22       # Player 1 3 points
led3 = 24       # Player 2 1 point
led4 = 26       # Player 3 2 points
led5 = 32       # Player 3 3 points

p1_press = 12   # Player 1 button
p2_press = 8    # player 2 button

p1_wins = 0     # keeps track of p1 score
p2_wins = 0     # p2 score

ready = False   # Becomes true when signal is on. Then processes button press
game_over = False

GPIO.setup(p1_press, GPIO.IN, GPIO.PUD_UP)    #input, pullup resistor
GPIO.setup(p2_press, GPIO.IN, GPIO.PUD_UP)

# start with all LEDs off
GPIO.setup(signal, GPIO.OUT)    #outputs
GPIO.output(signal, 0)
GPIO.setup(led0,GPIO.OUT)               
GPIO.output(led0, 0)
GPIO.setup(led1,GPIO.OUT)
GPIO.output(led1, 0)
GPIO.setup(led2,GPIO.OUT)
GPIO.output(led2, 0)
GPIO.setup(led3,GPIO.OUT)
GPIO.output(led3, 0)
GPIO.setup(led4,GPIO.OUT)
GPIO.output(led4, 0)
GPIO.setup(led5,GPIO.OUT)
GPIO.output(led5, 0)

#function to handle the button press interrupts
def bp1(channel):
    global p1_wins      # have to define the variable as global within function
    global ready
    if ready:
        if p1_wins == 0:
            print("p1 won! 1 win")
            GPIO.output(led0, 1)
        elif p1_wins == 1:
            print("p1 won! 2 wins")
            GPIO.output(led1, 1)
        else:
            print("p1 won! 3 wins")
            GPIO.output(led2, 1)
        p1_wins+=1
        ready = False

def bp2(channel):
    global p2_wins
    global ready
    if ready:
        if p2_wins == 0:
            print("p2 won! 1 win")
            GPIO.output(led3, 1)
        elif p2_wins == 1:
            print("p2 won! 2 wins")
            GPIO.output(led4, 1)
        else:
            print("p2 won! 3 wins")
            GPIO.output(led5, 1)
        p2_wins+=1
        ready = False

#declare interrupt - pin, falling edge (pressed), function, debounce
GPIO.add_event_detect(p1_press, GPIO.FALLING, callback=bp1, bouncetime=200)
GPIO.add_event_detect(p2_press, GPIO.FALLING, callback=bp2, bouncetime=200)

time.sleep(2)   # so players can get ready

while game_over == False:

    time.sleep(random.uniform(1,6)) #game is idle while signal is off
    GPIO.output(signal,1)   #signal turns on after random time between 1 and 6 s
    ready = True
    while ready:
        GPIO.output(signal,1) #python requires code after while loop....
    GPIO.output(signal,0)   # turn LED off
    
    if p1_wins == 3:
        print("p1 wins! Game Over!")
        game_over = True
        for n in range(0,3):    # blink winners LEDs for cool effect
            time.sleep(1)
            GPIO.output(led0, 0)
            GPIO.output(led1, 0)
            GPIO.output(led2, 0)
            time.sleep(1)
            GPIO.output(led0, 1)
            GPIO.output(led1, 1)
            GPIO.output(led2, 1)
        GPIO.output(led0, 0)
        GPIO.output(led1, 0)
        GPIO.output(led2, 0)


    if p2_wins == 3:
        print("p2 wins! Game Over!")
        game_over = True
        for m in range(0,3):
            time.sleep(1)
            GPIO.output(led3, 0)
            GPIO.output(led4, 0)
            GPIO.output(led5, 0)
            time.sleep(1)
            GPIO.output(led3, 1)
            GPIO.output(led4, 1)
            GPIO.output(led5, 1)
        GPIO.output(led3, 0)
        GPIO.output(led4, 0)
        GPIO.output(led5, 0)
        
        

GPIO.cleanup()
    
