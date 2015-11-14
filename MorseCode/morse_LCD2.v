`timescale 1ns / 1ps

/********* Morse Code Translator ************/
// Max Brewin 

module morse(Clk, Reset, Press, Done, code, high_count, signal_count, qDecode, qProcess,
	qPress, qIdle, LCD_WE, char, SCEN);

/* press refers to button that emits signal, done means we have the letter we want, and go to DECODE state */
input Clk, Reset, Press, Done, SCEN;
	
/** Outputs **/
output reg [7:0] code;	/* 8 bit number, 1 followed by 0 is a dot
					two 1s is a dash, two 0s in sequence
					signify end of letter */
output reg[26:0] high_count;	/* stores duration of button press, governed by clock,
							for 100MHZ, maxcount of 67108863 , .671 seconds
						*/
output reg [3:0] signal_count;	// the number of dots/dashes processed
											//if it goes to 4, we have processed the entire letter
											// and automatically go to DECODE state
reg wait_flag;	// basically implements debouncing
					// gives an extra clock to allow signal to stabilize
					
output reg [31:0] LCD_WE;	// lcd write enable, 32 bits, 1 for each position on LCD
output reg [7:0] char;	// register to store the letter
											
// states
output qDecode, qProcess, qPress, qIdle;
reg[3:0] state;
assign {qDecode, qProcess, qPress, qIdle} = state;


localparam
//states
DECODE = 4'b1000, PROCESS = 4'b0100, PRESS = 4'b0010, IDLE = 4'b0001,
// enables for writing to the 4 segments of 'code' where EN0 is the 2 msb
// without the enable we would either be writing to all of CODE at once
EN0 = 8'b11000000, EN1 = 8'b00110000, EN2 = 8'b00001100, EN3 = 8'b00000011,
DOT = 8'b10101010, DASH = 8'b11111111;


always @(posedge Clk, posedge Reset)
begin
	if (Reset)
	begin
		state <= IDLE;
		high_count <= 0;
		signal_count <= 0;
		code <= 8'b00000000;
		wait_flag <= 0;
		LCD_WE <= 1;
		char = " ";
	end
	else
	begin 
		case (state)
		IDLE:
		begin
			if (wait_flag)
				wait_flag <= 0;
			if (Press && !wait_flag)	// start counting PRESS
				state <=  PRESS;
			if (Done && !wait_flag && SCEN)	//go straight to DECODE state
			begin
				state <= DECODE;
				wait_flag <= 1;
			end
		end
		PRESS:
		begin
			if (!Press)
				state <= PROCESS;
			else
			begin
				if (high_count != 67108863)	// once # reaches this, recirculate
					high_count <= high_count + 1;
			end
				
		end
		PROCESS:
		/** 26 bit number allows for a max press of .671 seconds,
			anything between .005 seconds and .25 seconds will be considered a dot
			anything greater than .20 will be considered a dash
		**/
		begin
		/* state transition */
			if (signal_count == 3)
				state <= DECODE;
			else
				state <= IDLE;
		/* RTL */
			if (high_count > 500000 && high_count <= 20000000) // dot
			begin
				if (signal_count == 0)
					code <= code | (EN0 & DOT);	
				else if (signal_count == 1)
					code <= code | (EN1 & DOT);
				else if (signal_count == 2)
					code <= code | (EN2 & DOT);
				else
					code <= code | (EN3 & DOT);
					
				signal_count <= signal_count + 1;
			end
			else if (high_count > 20000000)    // dash
			begin
				if (signal_count == 0)
					code <= code | (EN0 & DASH);	// write to msb quadrant (2bits)
				else if (signal_count == 1)
					code <= code | (EN1 & DASH);
				else if (signal_count == 2)
					code <= code | (EN2 & DASH);
				else
					code <= code | (EN3 & DASH);	// lsb quadrant
				
				signal_count <= signal_count + 1;	//inc num of signals processed
			end
			wait_flag <= 1;
			high_count <= 0;
		end
		DECODE:
		begin
			state <= IDLE;
			/*clear vars and go back to idle*/
			signal_count <= 0;
			code <= 8'b00000000;
			LCD_WE <= LCD_WE << 1;	// increment to the next lcd position
		
			/** Look Up Table **/
			if (code == 8'b00000000)
			begin
				char = " ";
			end
			if (code == 8'b10110000)
			begin
				char = "a";
			end
			if (code == 8'b11101010)
			begin
				char = "b";
			end
			if (code == 8'b11101110)
			begin
				char = "c";
			end
			if (code == 8'b11101000)
			begin
				char = "d";
			end
			if (code == 8'b10000000)
			begin
				char = "e";
			end
			if (code == 8'b10101110)
			begin
				char = "f";
			end
			if (code == 8'b11111000)
			begin
				char = "g";
			end
			if (code == 8'b10101010)
			begin
				char = "h";
			end
			if (code == 8'b10100000)
			begin
				char = "i";
			end
			if (code == 8'b10111111)
			begin
				char = "j";
			end
			if (code == 8'b11101100)
			begin
				char = "k";
			end
			if (code == 8'b10111010)
			begin
				char = "l";
			end
			if (code == 8'b11110000)
			begin
				char = "m";
			end
			if (code == 8'b11100000)
			begin
				char = "n";
			end
			if (code == 8'b11111100)
			begin
				char = "o";
			end
			if (code == 8'b10111110)
			begin
				char = "p";
			end
			if (code == 8'b11111011)
			begin
				char = "q";
			end
			if (code == 8'b10111000)
			begin
				char = "r";
			end
			if (code == 8'b10101000)
			begin
				char = "s";
			end
			if (code == 8'b11000000)
			begin
				char = "t";
			end
			if (code == 8'b10101100)
			begin
				char = "u";
			end
			if (code == 8'b10101011)
			begin
				char = "v";
			end	
			if (code == 8'b10111100)
			begin
				char = "w";
			end	
			if (code == 8'b11101011)
			begin
				char = "x";
			end
			if (code == 8'b11101111)
			begin
				char = "y";
			end
			if (code == 8'b11111010)
			begin
				char = "z";
			end
		end
		endcase
	end
end
endmodule
	




	