# tanita - smartweight adapter to load data from Tannita device to the web page via web sockets

I. Structure
	Release
		Page - a page with a Java script that retrieves data from the application
		Servis - Application that downloads data from Tanita
		Simulator - tanity simulator
	Src - folder with sources and project in the netbeans environment - downloadable from https://netbeans.org/

II. How to run a test environment:
	0. Install jre http://www.oracle.com/technetwork/java/javase/downloads/jre7-downloads-1880261.html or https://www.java.com/pl/download/, make sure that the path to the java.exe command is added to the path variable
	1. Download and install the serial port emulator: http://sourceforge.net/projects/com0com/, add 1 port to COM20 and COM21 via the application or from the Com0Com console: "install PortName=COM22 PortName=COM23"
	2. Run the website in the browser (the website tries to connect to the application every 10 seconds)
	3. Enter the com port number into the application config: elease\servis\appProperties.conf
	3. Run the release\servis\runme.bat application (the application saves logs to the applicationLog.txt file and to the console)
	4. Run release\simulator\runme.bat simulator
	5. Select the com port in the simulator, select Adult from the test menu (all 3 are the same because I have no data) and press "Send", the data should appear on the page.


This is what the sample logs for the scenario above look like:

00:00  INFO: Connecting to com port:COM13
00:00  INFO: Port succesfully conected
00:02  INFO: Socket set
00:02  INFO: Websocket client connected, counter:0
00:11  INFO: Event received, data len:265
00:11  INFO: Message from tanita stored
00:11  INFO: Data from device:{0,16,~0,1,~1,1,~2,1,MO,"SC-330",SN,"00000001",ID,"0000000000",Da,"25/09/2014",TI,"08:20",Bt,0,GE,1,AG,34,Hm,191.0,Pt,0.0,Wk,89.2,FW,18.8,fW,16.8,MW,72.4,mW,68.8,sW,13,bW,3.6,wW,48.0,ww,53.8,MI,24.5,Sw,80.3,OV,11.1,IF,6,rb,8874,rB,2121,rJ,13,rA,29,ZF,501.8,CS,D9}00:11  INFO: Event received, data len:0
00:11  INFO: Entry send to browser:edit-field-fit-measurement-date-und-0-value-datepicker-popup-0;"25/09/2014"
00:11  INFO: Entry send to browser:edit-field-fit-body-type-und;0
00:11  INFO: Entry send to browser:edit-field-fit-body-height-und-0-value;191.0
00:11  INFO: Entry send to browser:edit-field-fit-body-weight-und-0-value;89.2
00:11  INFO: Entry send to browser:edit-field-fit-body-fat-percentage-und-0-value;18.8
00:11  INFO: Entry send to browser:edit-field-fit-body-fat-mass-und-0-value;16.8
00:11  INFO: Entry send to browser:edit-field-fit-body-lean-mass-und-0-value;72.4
00:11  INFO: Entry send to browser:edit-field-fit-body-muscle-mass-und-0-value;68.8
00:11  INFO: Entry send to browser:edit-field-fit-body-bone-mass-und-0-value;3.6
00:11  INFO: Entry send to browser:edit-field-fit-body-total-water-und-0-value;48.0
00:11  INFO: Entry send to browser:edit-field-fit-body-water-percentage-und-0-value;53.8
00:11  INFO: Entry send to browser:edit-field-fit-body-bmi-und-0-value;24.5
00:11  INFO: Entry send to browser:edit-field-fit-target-weight-und-0-value;80.3
00:11  INFO: Entry send to browser:edit-field-fit-fat-rate-und-0-value;11.1
00:11  INFO: Entry send to browser:edit­field­fit­body­fat­intestinal­und­0­value;6
00:11  INFO: Entry send to browser:edit­field­fit­body­energy­needs­und­0­value;8874
00:11  INFO: Entry send to browser:edit­field­fit­body­metabolic­age­und­0­value;29
00:11  INFO: Lock released, message send to websocket:{0,16,~0,1,~1,1,~2,1,MO,"SC-330",SN,"00000001",ID,"0000000000",Da,"25/09/2014",TI,"08:20",Bt,0,GE,1,AG,34,Hm,191.0,Pt,0.0,Wk,89.2,FW,18.8,fW,16.8,MW,72.4,mW,68.8,sW,13,bW,3.6,wW,48.0,ww,53.8,MI,24.5,Sw,80.3,OV,11.1,IF,6,rb,8874,rB,2121,rJ,13,rA,29,ZF,501.8,CS,D9}
