/* A program for an LCD thermostat */

#include <avr/io.h>
#include <avr/interrupt.h>
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#include "lcd.h"

void init_DDR()
{
	
	DDRB = 0x03;	// B0 register select  B1 Enabler
	DDRD = 0xF0;	// D7 - D4 command or data bytes ; D3 - D2 rotary encoder
	DDRC = 0x00;	// A2 Hi button, A3 Lo button ;
	
	PORTC = 0x0C;		// Pull up resistors for A3 and A2 (buttons)
	PORTD = 0x0C;		// Pull up resistors for D3 and D2 (rotary encoder)
}

// Allows for pin change interrupts
void init_PC()
{
	PCICR = (1 << PCIE1) | (1 << PCIE2);  // Allow pin change interrups on PORTC and PORTD
	
	PCMSK1 = ((1 << PC3) | (1 << PC2));	// Only A3 and A2 will generate interrupts
	PCMSK2 = ((1 << PD3) | (1 << PD2)); // Only D3 and D2 will generate interrupts
	
}


// Global variables
volatile char button;	// High or low determined by button press
volatile char setHIGH;		// 1 if setting high temp , 0 if setting low temp

volatile int countOLD = 0;
volatile int countNEW = 0;
volatile char stateOLD;
volatile char stateNEW;
char buffer[8];

int main(void)
{		
	init_DDR();
	init_lcd();
	sei();
	init_PC();
	clear();
	
	stateOLD = PIND & 0x0C;
	
	// screen 
	moveto(0x00);
	stringout("HIGH");
	moveto(0x40);
	stringout("LOW");
	
	while(1)
	{
		if (setHIGH == 0x01)
		{
			moveto(0x43);
			stringout(" ");
			moveto(0x04);
			stringout("*");
		}
		else if (setHIGH == 0x00)
		{
			moveto(0x04);
			stringout(" ");
			moveto(0x43);
			stringout("*");
		}
		else
		{
	//	while (setHIGH == 0x02)
		//{
			//if (countOLD != countNEW)
			//{
				/* puts decimal value into buffer of length 6 and prints to screen */
				sprintf(buffer, "%4d", countNEW);	
				moveto(0x09);
				stringout(buffer);
				countOLD = countNEW;
			//}
		//}
		}
		//moveto(0x10);	// move cursor off screen
	}
	
	return 0;
}

ISR(PCINT1_vect)	// pin change on c
{
/*********** DETECT BUTTON PRESSES ***********/
	
	button = PINC & 0x0C;
	
	// If high button is pressed, A2 will go to 0
	if (button == 0x08)
	{
		setHIGH = 0x01;
	}
	
	// if low button is pressed, A3 will got to 0
	if (button == 0x04)
	{
		setHIGH = 0x00;
	}
	
	// if no button is pressed, A3 and A2 are 1, go to neutral state
	if (button == 0x0C)
	{
		setHIGH = 0x02;		//neutral state
	}
}	

ISR(PCINT2_vect)	// pin change on d
{
/*********** ROTARY ENCODER **********/

	stateNEW = PIND & 0x0C;
	
	if (stateOLD == 0x00)
	{
		if (stateNEW == 0x04)
		{
			countNEW++;
		}
		else if (stateNEW == 0x08)
		{
			countNEW--;
		}
	}
		
	else if (stateOLD == 0x08)
	{
		if (stateNEW ==	0x00)
		{
			countNEW++;
		}
		else if (stateNEW == 0x0C)
		{
			countNEW--;
		}	
	}
		
	else if (stateOLD == 0x0C)
	{
		if (stateNEW == 0x08)
		{
			countNEW++;
		}
		else if (stateNEW == 0x04)
		{
			countNEW--;
		}
	}
	
	else if (stateOLD == 0x04)
	{
		if (stateNEW == 0x0C)
		{
			countNEW++;
		}
		else if (stateNEW == 0x00)
		{
			countNEW--;
		}
	}
	stateOLD = stateNEW;
}	
	

