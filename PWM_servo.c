/* Controls a servo motor with PWM */

#include <avr/io.h>													// Max Brewin
#include <avr/interrupt.h>											
#include <string.h>
#include <stdio.h>

void init_DDR()
{
	DDRB = 0x04; 	// B2 PWM pulse output
	DDRC = 0x00;	// inputs
	PORTC = 0x30;	/* Turn on pullup resistors on A4 A5. These pins will read position
					of encoder */
}

// Allows for pin change interrupts
void init_PC()
{
	PCICR = (1 << PCIE1);
	PCMSK1 = ((1 << PC4) | (1 << PC5));
}

// Initialize timer1 for PWM
void init_PWM(void)
{
	TCCR1A |= ((1 << WGM10) | (1 << WGM11));		// Wave Generation Mode
	TCCR1B |= ((1 << WGM12) | (1 << WGM13));		// Wave Generation Mode
	
	TCCR1A |= (1 << COM1B1);	// B2 is now output line
	// for prescaler of 256
	OCR1A = 1250;			// pulse period/count value
	OCR1B = 94;				// 50% duty cycle between bounds
	
	TCCR1B |= (1 << CS12);// Prescaler = 256=> 20ms period
}
	
// Global variables


volatile int thresholdOLD = 94;		//initialized to a pulse duty cycle of 50%
volatile int thresholdNEW = 94;
volatile char stateOLD;				// Only concerned with A5 and A4
volatile char stateNEW;

int main(void) {

	init_DDR();
	init_PC();
	init_PWM();
	sei();
	
	stateOLD = PINC & 0x30;
	
	while(1){
	
		if (thresholdOLD != thresholdNEW)
		{
			// keep threshold within appropriate range
			if (thresholdNEW < 47)
			{
				thresholdNEW = 47;
			}
			if (thresholdNEW > 140)
			{
				thresholdNEW = 140;
			}
		
			// copy threshold value into OCR1B register
			OCR1B = thresholdNEW;
			
			thresholdOLD = thresholdNEW;
		}
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
			thresholdNEW = thresholdNEW + 2;
		}
		if (stateNEW == 0x20)
		{
			thresholdNEW = thresholdNEW - 2;
		}
	}
		
	if (stateOLD == 0x10)
	{
		if (stateNEW ==	0x30)
		{
			thresholdNEW = thresholdNEW + 2;
		}
		if (stateNEW == 0x00)
		{
			thresholdNEW = thresholdNEW - 2;
		}	
	}
		
	if (stateOLD == 0x30)
	{
		if (stateNEW == 0x20)
		{
			thresholdNEW = thresholdNEW + 2;
		}
		if (stateNEW == 0x10)
		{
			thresholdNEW = thresholdNEW - 2;
		}
	}
	
	if (stateOLD == 0x20)
	{
		if (stateNEW == 0x00)
		{
			thresholdNEW = thresholdNEW + 2;
		}
		if (stateNEW == 0x30)
		{
			thresholdNEW = thresholdNEW - 2;
		}
	}
	stateOLD = stateNEW;
}	
	
