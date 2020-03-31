#define HUMSens A0
int led = 13;

String inputString = "";
String Messaggio1= "ON1\n";
String Messaggio2= "OFF1\n";
String Messaggio3= "REFRESH\n";



void setup(){
  pinMode (led, OUTPUT);    
  Serial.begin(9600);
}
  

  

 void loop(){
  inputString.reserve(100);
  while (Serial.available())  {
    char c = (char)Serial.read();
    inputString = inputString + c;
   
     if (c == '\n') {
        //----------stringa su cui lavorare-----------
        //Serial.println(inputString);
        if (inputString.equals(Messaggio1)){
          digitalWrite(led,HIGH);
          inputString = "";
        }

         if (inputString.equals(Messaggio2)){
          digitalWrite(led,LOW);
          inputString = "";
        }

          if (inputString.equals(Messaggio3)){
           Serial.print("Umidità=");
          Serial.println(analogRead(HUMSens));
          inputString = "";
        }
     inputString = "";
    }
  }
 }
