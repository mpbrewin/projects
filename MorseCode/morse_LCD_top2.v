/****Max Brewin ***/

`timescale 1ns / 1ps
/** USAGE: bottom button to emit pulse, center button to emit done (got the desired letter), and
		up button is reset. NOTE though that reset does not clear the LCD, so what I do is press reset, and
		repeatedly press the done button as this writes a blank space to the positon, and then hit reset again
		once all of the letters have been erased **/


module morse_top(ClkPort,                                    // System Clock
        MemOE, MemWR, RamCS, FlashCS, QuadSpiFlashCS,
         BtnU, BtnD, BtnC,	             // the Left, Up, Right, Down, and Center buttons
		 LCD_data, LCD_e, LCD_rs, LCD_rw, LCD_bl,	// LCD
		  An0, An1, An2, An3,                         // 4 seven-LEDs
		  Ca, Cb, Cc, Cd, Ce, Cf, Cg, Dp,
		  Speaker);
		  
	input ClkPort;
	input    BtnU, BtnD, BtnC;
	// LCD outputs
	output [7:0] LCD_data;
	output LCD_e, LCD_rs, LCD_rw, LCD_bl;
	output   An0, An1, An2, An3;
	output   Ca, Cb, Cc, Cd, Ce, Cf, Cg, Dp;
	output MemOE, MemWR, RamCS, FlashCS, QuadSpiFlashCS;
	output Speaker;
	assign {MemOE, MemWR, RamCS, FlashCS, QuadSpiFlashCS} = 5'b11111;
	
	wire ClkPort;
	wire board_clk, sys_clk;
	wire[1:0] ssd_clk;
	reg[26:0] div_clk;
	wire Press, Done, Reset;	// inputs to core
	wire[7:0] code;
	wire[25:0] high_count;
	wire[2:0] signal_count;
	wire qDecode, qProcess, qPress, qIdle;	// outputs from core
	
	wire[31:0] LCD_WE;	// enables which position to write to on the lcd
	wire[7:0] char;
	
	reg [1:0]	SSD;
	wire [1:0]	SSD3, SSD2, SSD1, SSD0;
	reg [7:0]  SSD_CATHODES;
	
	wire		SCEN; // Single CEN (one clock-wide Single Clock Enable for single-stepping)
	
	//intermediate data wires carrying the 32 characters to the LCD module
	reg [7:0]  data1, data2, data3, data4, data5, data6, data7, data8,
				   data9, data10, data11, data12, data13, data14, data15, data16,
					data17, data18, data19, data20, data21, data22, data23, data24,
					data25, data26, data27, data28, data29, data30, data31, data32;	
				
	/* These are writing enables that correspond to each of the 32 available char slots.
		We enable it with a one-hot 32bit reg*/

	
	
	assign Press = BtnD;
	assign Done = BtnC;
	assign Reset = BtnU;
	
	BUFGP BUFGP1 (board_clk, ClkPort);
	
  always @(posedge board_clk, posedge Reset) 	
    begin							
        if (Reset)
		div_clk <= 0;
        else
		div_clk <= div_clk + 1'b1;
    end
	
	assign	sys_clk = board_clk;	// sys_clk runs @ full 100MHz
	assign Speaker = BtnD ? div_clk[17] : 0;
	
	assign ssd_clk = div_clk[19:18];

	assign SSD3 = code[7:6];	// display dot or dash to LCD
	assign SSD2 = code[5:4];
	assign SSD1 = code[3:2];
	assign SSD0 = code[1:0];
	
	assign An0	= !(~(ssd_clk[1]) && ~(ssd_clk[0]));  // when ssdscan_clk = 00
	assign An1	= !(~(ssd_clk[1]) &&  (ssd_clk[0]));  // when ssdscan_clk = 01
	assign An2	=  !((ssd_clk[1]) && ~(ssd_clk[0]));  // when ssdscan_clk = 10
	assign An3	=  !((ssd_clk[1]) &&  (ssd_clk[0]));  // when ssdscan_clk = 11
	
	always @ (ssd_clk, SSD0, SSD1, SSD2, SSD3)
	begin : SSD_SCAN_OUT
		case (ssd_clk) 
				  2'b00: SSD = SSD0;
				  2'b01: SSD = SSD1;
				  2'b10: SSD = SSD2;
				  2'b11: SSD = SSD3;
		endcase 
	end

	assign {Ca, Cb, Cc, Cd, Ce, Cf, Cg, Dp} = {SSD_CATHODES};
	
	always @(SSD)
	begin
		case(SSD)
		2'b00: SSD_CATHODES = 8'b11111111;
		2'b10: SSD_CATHODES = 8'b11111110;	// just dp
		2'b11: SSD_CATHODES = 8'b11111101;	// just a dash
		2'b01: SSD_CATHODES = 8'b01100001;	// this case shouldn't exist
											// and will show 'E' for error
		endcase
	end
	
	ee201_debouncer #(.N_dc(24)) ee201_debouncer 
        (.CLK(sys_clk), .RESET(Reset), .PB(BtnC), .DPB( ), .SCEN(SCEN), .MCEN( ), .CCEN( ));
	
	/****************INSANTIAITE UUT HERE **********************/
	morse(.Clk(sys_clk), .Reset(Reset), .Press(Press), .Done(Done), .code(code), .high_count(high_count), 
	.signal_count(signal_count), .qDecode(qDecode), .qProcess(qProcess), .qPress(qPress), 
	.qIdle(qIdle), .LCD_WE(LCD_WE), .char(char), .SCEN(SCEN));
	
//------------ LCD interface starts here -------------------

	// we only write to the LCD panel, so ..
	assign LCD_bl = 0;	

	
	
	//data assignment 
	
	/** DATA1 corresponds to the first lcd position slot, DATA2 the second, etc
		LCD_WE is a write enable,
		char is an 8bit value that stores the character (see .v file DECODE state)
		This code tries to implement cursor scrolling. If write enable, write the new char value
		else recycle the value.**/
		
		

	initial begin
	data1 <= " ";
	data2 <= " ";
	data3 <= " ";
	data4 <= " ";
	data5 <= " ";
	data6 <= " ";
	data7 <= " ";
	data8 <= " ";
	data9 <= " ";
	data10 <= " ";
	data11 <= " ";
	data12 <= " ";
	data13 <= " ";
	data14 <= " ";
	data15 <= " ";
	data16 <= " ";
	data17 <= " ";
	data18 <= " ";
	data19 <= " ";
	data20 <= " ";
	data21 <= " ";
	data22 <= " ";  
	data23 <= " ";  
	data24 <= " ";
	data25 <= " ";
	data26 <= " ";
	data27 <= " ";
	data28 <= " ";
	data29 <= " ";
	data30 <= " ";   
	data31 <= " "; 
	data32 <= " ";	
	
	end
	
	always @(posedge board_clk)
	begin
	 data1 <= LCD_WE[0] ? char : data1;	// if there is a 1 at lsb, write to first....
	 data2 <= LCD_WE[1] ? char : data2;
	 data3 <= LCD_WE[2] ? char : data3;
	 data4 <= LCD_WE[3] ? char : data4;
     data5 <= LCD_WE[4] ? char : data5;
	 data6 <= LCD_WE[5] ? char : data6;  
	 data7 <= LCD_WE[6] ? char : data7;  
	 data8 <= LCD_WE[7] ? char : data8;
	 data9 <= LCD_WE[8] ? char : data9;
	 data10 <= LCD_WE[9] ? char : data10;
	 data11 <= LCD_WE[10] ? char : data11;
	 data12 <= LCD_WE[11] ? char : data12;
	 data13 <= LCD_WE[12] ? char : data13;
	 data14 <= LCD_WE[13] ? char : data14;   
	 data15 <= LCD_WE[14] ? char : data15; 
	 data16 <= LCD_WE[15] ? char : data16;
	 data17 <= LCD_WE[16] ? char : data17;
	 data18 <= LCD_WE[17] ? char : data18;
	 data19 <= LCD_WE[18] ? char : data19;
	 data20 <= LCD_WE[19] ? char : data20;
	 data21 <= LCD_WE[20] ? char : data21;
	 data22 <= LCD_WE[21] ? char : data22;
	 data23 <= LCD_WE[22] ? char : data23; 
	 data24 <= LCD_WE[23] ? char : data24;
	 data25 <= LCD_WE[24] ? char : data25;
	 data26 <= LCD_WE[25] ? char : data26;
	 data27 <= LCD_WE[26] ? char : data27;
	 data28 <= LCD_WE[27] ? char : data28;
	 data29 <= LCD_WE[28] ? char : data29;
	 data30 <= LCD_WE[29] ? char : data30;  
	 data31 <= LCD_WE[30] ? char : data31;
	 data32 <= LCD_WE[31] ? char : data32;
	end
	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	//#########################################################################################################
	
	
	// Instantiate the Unit Under Test (UUT)
	lcd_core uut (
		.clk(board_clk), 
		.reset(Reset), 
		.lcd_data(LCD_data), 
		.lcd_e(LCD_e), 
		.lcd_rs(LCD_rs),
		.lcd_rw(LCD_rw),
		.data_f1(data1), .data_f2(data2), .data_f3(data3), .data_f4(data4), .data_f5(data5),.data_f6(data6), 
		.data_f7(data7), .data_f8(data8), .data_f9(data9), .data_f10(data10), .data_f11(data11), .data_f12(data12), 
		.data_f13(data13), .data_f14(data14), .data_f15(data15), .data_f16(data16),.data_s1(data17), .data_s2(data18), 
		.data_s3(data19), .data_s4(data20), .data_s5(data21),.data_s6(data22), .data_s7(data23), .data_s8(data24),
		.data_s9(data25), .data_s10(data26), .data_s11(data27), .data_s12(data28), .data_s13(data29), .data_s14(data30),
		.data_s15(data31), .data_s16(data32)
	);
	
	/** WE ARE NOT USING THIS **/
//function for binary to hexadecimal(character) conversion
function [7:0] bin2x;
 input [3:0] data;
  begin
	case (data)
	4'h0:	bin2x = "0";4'h1:	bin2x = "1";4'h2:	bin2x = "2";4'h3:	bin2x = "3";
	4'h4:	bin2x = "4";4'h5:	bin2x = "5";4'h6:	bin2x = "6";4'h7:	bin2x = "7";
	4'h8:	bin2x = "8";4'h9:	bin2x = "9";4'hA:	bin2x = "A";4'hB:	bin2x = "B";
	4'hC:	bin2x = "C";4'hD:	bin2x = "D";4'hE:	bin2x = "E";4'hF:	bin2x = "F";
	default:bin2x = "0";
	endcase
  end
  
endfunction
endmodule
	
	
	