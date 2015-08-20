/* Type text into an LCD */
#include <avr/io.h>													// Max Brewin
#include <avr/interrupt.h>											// 
#include "lcd.h"
#include <string.h>
#include <stdio.h>

void init_DDR()
{
	DDRB = 0x03;	// B0 register select  B1 Enabler
	DDRC = 0x00;	// inputs
	PORTC = 0x30;	/* Turn on pullup resistors on A4 A5. These pins will read position
					of encoder */
	DDRD = 0xF0;	// D7 - D4 command or data bytes
}

// Allows for pin change interrupts
void init_PC()
{
	PCICR = (1 << PCIE1);
	PCMSK1 = ((1 << PC4) | (1 << PC5));
}

// Global variables
volatile int countOLD = 0;
volatile int countNEW = 0;
char buffer[8];
volatile char stateOLD;		// Only concerned with A5 and A4
volatile char stateNEW;

int main(void) {

	init_DDR();
	init_lcd();
	sei();
	init_PC();
	clear();
	
	stateOLD = PINC & 0x30;
	moveto(0x05);
	writedata(countNEW + '0');
	
	while(1){
		
		if (countOLD != countNEW) 	
		// Prevents writing to LCD unnecessarily
		{ 
			/* puts decimal value into buffer of length 6 and prints to screen */
			sprintf(buffer, "%6d", countNEW);	
			moveto(0x00);
			stringout(buffer);
			countOLD = countNEW;
		}
		moveto(0x10);					// Moves cursor off screen	
	}
	
	return 0;
}

ISR(PCINT1_vect)
{
	stateNEW = PINC & 0x30;
	
	if (stateOLD == 0x00)
	{
		if (stateNEW == 0x10)
		{
			countNEW++;
		}
		if (stateNEW == 0x20)
		{
			countNEW--;
		}
	}
		
	if (stateOLD == 0x10)
	{
		if (stateNEW ==	0x30)
		{
			countNEW++;
		}
		if (stateNEW == 0x00)
		{
			countNEW--;
		}	
	}
		
	if (stateOLD == 0x30)
	{
		if (stateNEW == 0x20)
		{
			countNEW++;
		}
		if (stateNEW == 0x10)
		{
			countNEW--;
		}
	}
	
	if (stateOLD == 0x20)
	{
		if (stateNEW == 0x00)
		{
			countNEW++;
		}
		if (stateNEW == 0x30)
		{
			countNEW--;
		}
	}
	stateOLD = stateNEW;
}	
	
