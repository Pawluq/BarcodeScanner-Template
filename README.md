Not a full working product, but a base to continue implementation. 
The App reocgnizes ITF-Barcodes and displays the read Code. 
If you debug the code u will see, that it logs everytime a barcode from the barcodeRegistry is recognized.

The general App is divided in the MainActivity.kt for the init and logic and the ArduinoCommunication.java 
for the communication with the arduino.

The barcodeRegistry map keeps the list of barcodes on the shelf. 
Everytime a barcode is read by the API the processBarcode() is called.
It looks up in the registry if the barcode is existing on the shelf.
If the barcode is registered, the sendColorCode() is called. 
There you can call the ArduinoCommunication.java for the communication. 
How to call the ArduinoCommunication.java you can see in the „BarcodeGame“ sources. The sendColorCode() and the ArduinoCommunication is empty in this sample. For further implementation you must add these parts from and like in the project „BarcodeGame“.

The use of  the Barcode API refers to this tutorial: https://www.youtube.com/watch?v=KBlQt7IsvM4
