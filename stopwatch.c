/*************************************************************
* LCD Stopwatch
* 
*
*************************************************************/
																// Max Brewin
															
#include <avr/io.h>
#include <util/delay.h>
#include <string.h>
#include <stdlib.h>
#include <avr/interrupt.h>

void init_DDR()
{
	DDRB = 0x03;	// B0 register select  B1 Enabler
	DDRD = 0xF0;	// D7 - D4 command or data bytes
}

void init_ADC()
{
	ADMUX = 0x60;	// internal 5v, 8bit, input A0
	ADCSRA = 0x87;	// enable ADC module, prescaler of 128
	ADCSRA |= (1 << ADIE);	// enable ADC interrupts 
}

void init_TIMER()
{
	TCCR1B |=  (1 << WGM12); 					// Clear Timer Compare, 	
	OCR1A = 25000;								// Count To
	TCCR1B |= ((1 << CS11) | (1 << CS10));		// start timer, set prescaler
}	

// Resets clock to "00.0"
void reset(char time[], volatile int* msec, volatile int* sec_1, volatile int* sec_10,
volatile int* minute_1, volatile int* minute_10)
{
	char zero[] = "00:00.0";
	strcpy(time, zero);
	moveto(0x09);
	stringout(time);
	*msec = 0;
	*sec_1 = 0;
	*sec_10 = 0;
	*minute_1 = 0;
	*minute_10 = 0;	
}

// Stops clock
void stop_count(char time[])
{
	TCCR1B &= ~((1 << CS11) | (1 << CS10));		// disable counter interrupt
	moveto(0x09);
	stringout(time);							// displays stopped time
}

// Counts milliseconds
void count_msec(volatile int msec, char time[])
{
	char buffer[1];
	itoa(msec, buffer, 10);
	time[6] = buffer[0];				// msec slot is updated
}

// Counts seconds (1's place)
void count_sec1(volatile int sec_1, char time[])
{
	char buffer[1];
	itoa(sec_1, buffer, 10);
	time[4] = buffer[0];				// sec_1 slot is updated
}

// Counts seconds (10's place)
void count_sec10(volatile int sec_10, char time[])
{
	char buffer[1];
	itoa(sec_10, buffer, 10);
	time[3] = buffer[0];				// sec_10 slot is updated
}

// Counts minutes (1's place)
void count_minute1(volatile int minute_1, char time[])
{
	char buffer[1];
	itoa(minute_1, buffer, 10);
	time[1] = buffer[0];				// minute_1 slot is updated
}
	
// Counts minutes (10's place)
void count_minute10(volatile int minute_10, char time[])
{
	char buffer[1];
	itoa(minute_10, buffer, 10);
	time[0] = buffer[0];				// minute_10 slot is updated
}

void display_time(char time[], volatile int msec, volatile int sec_1, volatile int sec_10,
volatile int minute_1, volatile int minute_10)
{
    count_msec(msec, time);
    count_sec1(sec_1, time);
	count_sec10(sec_10, time);
	count_minute1(minute_1, time);
    count_minute10(minute_10, time);
	moveto(0x09);
	stringout(time);
}	

// Displays lapped time, but counter interrupt still enabled
void lapped(char time[])
{
	moveto(0x09);
	stringout(time);
}

// Clears display
void clear()
{
	moveto(0x00);
	stringout("                                ");
	moveto(0x00);
}

void init_lcd()
{
    _delay_ms(15);              // Delay at least 15ms

    writenibble(0x30);			// Use writenibble to send 0011
    _delay_ms(5);               // Delay at least 4msec

    writenibble(0x30);			// Use writenibble to send 0011
    _delay_us(120);             // Delay at least 100usec

    writenibble(0x30);			// Use writenibble to send 0011

    writenibble(0x20);			// Use writenibble to send 0010    .                                                           // Function Set: 4-bit interface
    _delay_ms(2);
    
    writecommand(0x28);         // Function Set: 4-bit interface, 2 lines

    writecommand(0x0f);         // Display and cursor on
}

void stringout(char *str)
{
	int x = *str;	// ascii conversion
	int count = 0;
	while (x != 0)
	{
		writedata(x);
    	count++;
    	x = *(str + count);
    }		
}

void moveto(unsigned char pos)
{
	pos = 0x80 + pos;
	writecommand(pos);
}

void writecommand(unsigned char x)
{
	PORTB = 0x00;
	
	writenibble(x);	// load first 4 bits
	x = (x << 4);	// puts next 4 bits in position
	writenibble(x);	// load next 4 bits
	
	_delay_ms(2);
}

void writedata(unsigned char x)
{
	PORTB = 0x01;
	
	writenibble(x);	// load first 4 bits
	x = (x << 4);	// puts next 4 bits in position
	writenibble(x);	// load next 4 bits
	
	_delay_ms(2);
}


void writenibble(unsigned char x)
{
	PORTD = x;	// load bits to send
	PORTB |= (1 << PB1);	// send bits
	_delay_us(100);
	PORTB &= ~(1 << PB1);
	_delay_us(100);

}

// Global variables
volatile int msec = 0;		
volatile int sec_1 = 0;
volatile int sec_10 = 0;
volatile int minute_1 = 0;
volatile int minute_10 = 0;
int state = 0;			// 0 = STOPPED 1 = STARTED 2 = LAPPED
volatile char time[] = "00:00.0";			
volatile unsigned char x = 255;			// will read ADCH register

int main(void) {

	init_DDR();
	init_ADC();
	init_lcd();		
	sei();						// enable interrupts
	init_TIMER(); 
	clear();

	ADCSRA |= (1 << ADSC);		// starts first ADC 
		
    while(1)
    {
    	// STOPPED state
    	if (state == 0 && (x >= 29 && x < 65)) 	// Stopped state and top button pressed
    	{
    		TIMSK1 |= (1 << OCIE1A);					// Enable Timer interrupt
    		_delay_us(500);
			state = 1;
    	}	
              
    	if (state == 0 && (x >= 65 && x < 100))	// Stopped state, bottom button is pressed
    	{
    		reset(time, &msec, &sec_1, &sec_10, &minute_1, &minute_10);
    	}
    		
    	// STARTED state
    	if (state == 1 && (x >= 205 && x < 256))
    	{
    		display_time(time, msec, sec_1, sec_10, minute_1, minute_10);
    	}
    	
    	if (state == 1 && (x >= 29 && x < 65))			// top button is pressed
    	{
    		stop_count(time);
    		_delay_us(500);
    		state = 0;
    		
    	}
    		
    	if (state == 1 && (x>= 65 && x < 100))			// bottom button is pressed
    	{
    		lapped(time);
    		_delay_us(500);
    		state = 2;
    	}
    	
    	// LAPPED state
    	if(state == 2 && ((x >= 29 && x < 65) || (x >= 100 && x < 150))) /* lapped state
    	and top or bottom button is pressed */
    	{
			_delay_us(500);
			state = 1;	
		}
    }
    
    return 0;   /* never reached */
}

ISR(TIMER1_COMPA_vect)		// 16 bit timer
{
	msec++;
	if (msec == 10)
	{
		sec_1++;
		msec = 0;
	}
	if (sec_1 == 10)
	{
		sec_10++;
		sec_1 = 0;
	}
	if (sec_10 == 6)
	{
		sec_10 = 0;
		minute_1++;
	}
	if (minute_1 == 10)
	{
		minute_1 = 0;
		minute_10++;
	}
	if (minute_10 == 6)
	{
		minute_10 = 0;
	}
	display_time(time, msec, sec_1, sec_10, minute_1, minute_10);
}		

ISR(ADC_vect)				// update ADC automatically
{
	x = ADCH;
	ADCSRA |= (1 << ADSC);
}

