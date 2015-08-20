// A demo showing basic arduino button press
#include <avr/io.h>

int main(void)
{
  char button;

  DDRB |= (1 << DDB5); // LED on PB5

  PORTD |= (1 << PD7); // Turn pullup on PD7

  while(1)
    {
      button = PIND & (1 << PD7);

      if (button)
	PORTB &= ~(1 << PB5); //clear bit 5 to 0, turn on LED
      else
	PORTB |= (1 << PB5);	// set bit 5 to 1
    }
 
}
