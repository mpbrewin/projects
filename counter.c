/* A program that implements a counting system displayed on a seven segment display.
* One button makes the counter count up, the other makes it count down
*/

#include <avr/io.h>								// Max Brewin

#include <util/delay.h>

int main(void)
{		
	DDRD = 0b01111111; // PORT D set to outputs -- control segments
	
	DDRC = 0b00000000; // PORT C set to inputs -- connected to buttons
	
	PORTC = 0b00010100; // Pull up Resistors on C2 (count up) and C4 (count down)
	
	PORTD = 0b11111111; // start with segments off
	
	char a = 0b00000001;	// a = 0
	char b = 0b01001111;	// b = 1
	char c = 0b00010010;
	char d = 0b00000110;
	char e = 0b01001100;
	char f = 0b00100100;
	char g = 0b00100000;
	char h = 0b00001111;
	char i = 0b00000000;
	char j = 0b00001100;	// j = 9

	char number[] = {a, b, c, d, e, f, g, h, i, j};

	while(1)
	{
		char count = PINC;	// reads PINC's (the buttons') inputs -- C4 down C2 up
		
		count &= 0b00010100; // update variable corresponding to button pressed
	
		if (count == 0b00010000)	// if count up is pressed first, C2 is 0
		{
			int n = 0;	// start at 0
			while(1)
			{
				PORTD = number[n];		// first digit will be 0
				count = PINC & 0b00010100;	// update PINC to see if down is pressed
				_delay_ms(500);
				n++;					// increase counter
				if (n > 9)				// ensures counter stays in range
					n = 0;

				if (count == 0b00000100)	// if down is pressed
				{
					while(count != 0b00010000)	// as long as up button is not pressed
					{
						PORTD = number[n];		// display curent digit
						count = PINC & 0b00010100;	//update to see if up is pressed
						_delay_ms(500);
						n--;			// decrease counter
						if (n < 0)		// ensures counter stays in range
							n = 9;
					}
				}
					
			}							
		}
		
		if (count == 0b00000100)	// if count down is pressed first, C4 is 0
		{
			int n = 9;			// start at 9
			while (1)
			{
				PORTD = number[n];	// first digit will be 9
				count = PINC & 0b00010100;	//update PINC to see if up has been pressed
				_delay_ms(500);
				n--;				//decrease counter
				if (n < 0)			// ensures counter stays in range
					n = 9;
					
				if (count == 0b00010000)	// if up is pressed
				{
					while(count != 0b00000100)	// as long as down button is not pressed
					{
						PORTD = number[n];	// display current digit
						count = PINC & 0b00010100;	// update to see if down is pressed
						_delay_ms(500);
						n++;				//increase counter
						if (n > 9)			// ensures counter stays in range
							n = 0;
					}
				}
							
			}
		}
	}
}
	
	

	
