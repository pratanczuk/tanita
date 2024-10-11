import jssc.SerialPortList;
import jssc.SerialPort;

public class SerialPortConfig
{
	String Port = "";
	int baudRate = SerialPort.BAUDRATE_9600;
	int dataBits = SerialPort.DATABITS_8;
	int stopBits = SerialPort.STOPBITS_1;
	int parity = SerialPort.PARITY_NONE;
	int flowControl = SerialPort.FLOWCONTROL_NONE;
}