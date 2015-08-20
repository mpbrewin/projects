/*************************************************************
* A program for an LCD counter. Can count up/down by ones and tens
* 
*
*************************************************************/
																// Max Brewin
															
#include <avr/io.h>
#include <util/delay.h>
#include <string.h>
#include <stdlib.h>

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

int main(void) {

	DDRD = 0b11110000;	// D7 - D4 command or data bytes
	DDRB = 0b00000011;	// B0 register select  B1 Enabler
	ADMUX = 0b01100000;		// internal 5v, 8bit, input A0
	ADCSRA = 0b10000111;	// prescaler of 128
	unsigned char x;			// will read ADCH register
	
	int var = 0;		// initialized at 0
	
    init_lcd();	
    
    while(1)
    {
    	ADCSRA |= (1 << ADSC);
    	while (ADCSRA & (1 << ADSC));	// conversion in progress
    	x = ADCH;
    	char digital_value[4];
    	char value[7];			// buffer array to display value on LCD
    	
    	moveto(0x00);
    	stringout("                               ");	// clears text
    	moveto(0x00);
    	
    	if (x >= 0 & x < 28)
    	{
    		var += 10;
    		itoa(var, value, 10);
    		stringout(value);
    	}
    	if (x >= 29 & x < 65)
    	{
    		var += 1;
    		itoa(var, value, 10);
    		stringout(value);
    	}                   
    	if (x >= 100 & x < 150)
    	{
    		var -= 10;
    		itoa(var, value, 10);
    		stringout(value);
    	}
    	if (x >= 65 & x < 100)
    	{
    		var -= 1;
    		itoa(var, value, 10);
    		stringout(value);    	}
    	if (x >= 169 & x < 205)
    	{
    	    var = 0;
    		itoa(var, value, 10);
    		stringout(value);
    	}
    	if (x >= 205 & x < 256)
    	{
    		itoa(var, value, 10);
    		stringout(value);
    	}
    	
    	/*
    	moveto(0x40);		// these lines are used to display the digitized value
    	itoa(x, digital_value, 10);
    	stringout(digital_value);
    	*/
    	_delay_ms(250);
    	
    }
    
    return 0;   /* never reached */
}
