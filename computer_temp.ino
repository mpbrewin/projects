// Program to display internal temperature of a computer via LEDs
void setup()
{
  Serial.begin(9600);  // opens a serial port

  pinMode(3,OUTPUT);
  pinMode(10, OUTPUT);
  pinMode(11, OUTPUT);
  digitalWrite(3,0);    // start with LEDs off
  digitalWrite(10,0);
  digitalWrite(11,0);
  
}

//double base_temp = 21.0;

void loop()
{
   int sensor_val = analogRead(A0);    // TMP sensor
   double voltage = (sensor_val/1024.0)*5.0;
   double temp = (voltage - 0.5)*100;
   
   /*Serial.print("Sensor value: ");
   Serial.print(sensor_val);
   Serial.print('\n');
   Serial.print("Voltage: ");
   Serial.print(voltage);
   Serial.print('\n');*/
   Serial.print("Temp in C: ");
   Serial.print(temp);
   Serial.print('\n');
   
   if (temp > 24.0 && temp < 27.0)
   {
     digitalWrite(10,1);
     digitalWrite(11,0);
     digitalWrite(3,0);
   }
     
   else if (temp <= 24.0)
   {
     digitalWrite(3,1);
     digitalWrite(11,0);
     digitalWrite(10,0);
   }
   else if (temp > 27.0)
   {
     digitalWrite(11,1);
     digitalWrite(10,0);
     digitalWrite(3,0);
   }
      
}
