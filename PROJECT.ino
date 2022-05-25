#include <SPI.h> // SPI
#include <MFRC522.h> // RFID
#include <Servo.h> // 
#include <Keypad.h>

#define SS_PIN 10
#define RST_PIN 9
#define Password_Lenght 7 
char Data[Password_Lenght]; 
char Master[Password_Lenght] = "123456";
byte data_count = 0, master_count = 0;
bool Pass_is_good;
char customKey;
bool door = true;
 const byte ROWS = 4; 
const byte COLS = 3; 
 char keys[ROWS][COLS] = {
 {'1','2','3'},
 {'4','5','6'},
 {'7','8','9'},
 {'*','0','#'}
};
byte rowPins[ROWS] = {7,6 ,5,4};
byte colPins[COLS] = {3,2,A0};
Keypad customKeypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS);
    
// Déclaration 
MFRC522 rfid(SS_PIN, RST_PIN); 
int angle = 0;
// Tableau contentent l'ID
byte nuidPICC[4];
Servo myservo; // create servo object to control a servo
void setup() 
{ 
  // Init RS232
  Serial.begin(9600);

  // Init SPI bus
  SPI.begin(); 

  // Init MFRC522 
  rfid.PCD_Init(); 
  myservo.attach(8); 
  myservo.write(180);
  delay(1000);
   myservo.write(0);
}
 
void loop() 
{ 
  rfidFunc();
   if (door == 0)
  {
    customKey = customKeypad.getKey();
    

    if (customKey == '#')

    {
      
      ServoClose();
      door = 1;
      
    }
  }

  else Open();
}

void clearData()
{
  while (data_count != 0)
  { 
    Data[data_count--] = 0; 
  }
  return;

  
  
}




void rfidFunc(){
// Initialisé la boucle si aucun badge n'est présent 
  if ( !rfid.PICC_IsNewCardPresent())
    return;

  // Vérifier la présence d'un nouveau badge 
  if ( !rfid.PICC_ReadCardSerial())
    return;

  // Enregistrer l'ID du badge (4 octets) 
  for (byte i = 0; i < 4; i++) 
  {
    nuidPICC[i] = rfid.uid.uidByte[i];
  }
  
  // Affichage de l'ID 
  Serial.println("Un badge est détecté");
  Serial.println(" L'UID du tag est:");
  for (byte i = 0; i < 4; i++) 
  {
    Serial.print(nuidPICC[i], HEX);
    Serial.print(" ");
  }
  ServoOpen();
  door = 0;
 
  // Re-Init RFID
  rfid.PICC_HaltA(); // Halt PICC
  rfid.PCD_StopCrypto1(); // Stop encryption on PCD
}



void Open()
{ 
  customKey = customKeypad.getKey();
  Serial.println(customKey);
  Serial.print("data =");
  Serial.print(Data);
 if (customKey == '*')

    {  
      clearData();
    }
  
  if (customKey && customKey != '*') 
  {
    Data[data_count] = customKey; 
    data_count++; 
  }

  if (data_count == Password_Lenght - 1)
  {
    if (!strcmp(Data, Master))
    {
      ServoOpen();
      door = 0;
    }
    else
    {
      door = 1;
    }
    clearData();
  }
}
void ServoOpen()
{
  
    myservo.write(0);              
    delay(15);                       
  
}

void ServoClose()
{
 
    myservo.write(180);              
    delay(15);                       
  
}
