/*********************************************************************
*
*       This program make the LED attached to Port B, bit 5 flash
*       on and off at a 1 Hz rate.
*
*********************************************************************/

#include <avr/io.h>
#include <util/delay.h>

#define FLASH_DELAY 500

int main(void)
{
    DDRB |= (1 << DDB5);	/* Set PB5 for output */

    while(1) {
	PORTB |= (1 << PB5);	/* LED on */
	_delay_ms(FLASH_DELAY);
	PORTB &= ~(1 << PB5);	/* LED off */
	_delay_ms(FLASH_DELAY);
    }

    return 0;   /* never reached */
}
