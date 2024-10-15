# Tanita SmartWeight Adapter

An application that interfaces with Tanita scales to load data onto a web page via WebSockets.

## Table of Contents

-   [Introduction](#introduction)
-   [Project Structure](#project-structure)
-   [Getting Started](#getting-started)
    -   [Prerequisites](#prerequisites)
    -   [Installation](#installation)
    -   [Running the Test Environment](#running-the-test-environment)
-   [Usage](#usage)
-   [UML Diagrams](#uml-diagrams)
    -   [System Architecture Diagram](#system-architecture-diagram)
    -   [Class Diagram](#class-diagram)
-   [License](#license)
-   [Contact](#contact)

## Introduction

The **Tanita SmartWeight Adapter** is designed to retrieve data from a Tanita device and display it on a web page in real-time using WebSockets. This enables seamless integration of weight measurement data into health and fitness applications.

## Project Structure

-   **release/**
    -   Contains the compiled application ready for deployment.
-   **src/**
    -   Source code of the project, compatible with NetBeans IDE.
-   **LICENSE**
    -   The MIT License governing the use of this software.
-   **README.md**
    -   Project documentation and instructions.
-   **manual.txt**
    -   Additional manual or instructions.

## Getting Started

### Prerequisites

-   **Java Runtime Environment (JRE) 7 or higher**
    
    -   Download from [Oracle](https://www.oracle.com/java/technologies/javase-jre7-downloads.html) or Java.com.
    -   Ensure the path to `java.exe` is added to your system's `PATH` variable.
-   **Serial Port Emulator**
    
    -   Download and install [com0com](http://sourceforge.net/projects/com0com/).
-   **NetBeans IDE (Optional, for source code access)**
    
    -   Download from [NetBeans](https://netbeans.org/).

### Installation

1.  **Install JRE**
    
    -   Follow the installer instructions to install Java on your system.
2.  **Install Serial Port Emulator**
    
    -   Install `com0com` to create virtual COM ports.
        
    -   Add a pair of virtual COM ports (e.g., COM22 and COM23) using the setup application or console command:
        
        shell
        
        Skopiuj kod
        
        `install PortName=COM22 PortName=COM23` 
        
3.  **Clone the Repository**
    
    -   Clone the project repository to your local machine.

### Running the Test Environment

1.  **Configure the Application**
    
    -   Edit `release/servis/appProperties.conf`.
    -   Set the `comPort` parameter to match the virtual COM port number (e.g., `COM22`).
2.  **Run the Web Page**
    
    -   Open the provided web page in your browser.
    -   The page attempts to connect to the application every 10 seconds.
3.  **Start the Application**
    
    -   Navigate to `release/servis/`.
    -   Run `runme.bat`.
    -   Logs are saved to `applicationLog.txt` and displayed in the console.
4.  **Start the Simulator**
    
    -   Navigate to `release/simulator/`.
    -   Run `runme.bat`.
    -   Select the COM port in the simulator.
    -   From the test menu, select "Adult" and click "Send".
    -   Data should now appear on the web page.

## Usage

-   **Data Retrieval**
    
    -   The application listens on the configured COM port for data from the Tanita device.
    -   Data is parsed and prepared for transmission.
-   **Data Transmission**
    
    -   Processed data is sent to the web page using WebSockets.
    -   Real-time updates are displayed to the user.
-   **Logging**
    
    -   All events and data transmissions are logged in `applicationLog.txt`.

## UML Diagrams

### System Architecture Diagram

plantuml

Skopiuj kod

`@startuml
actor User

rectangle "Tanita Device" as Tanita {
}

rectangle "Application" as App {
}

rectangle "Simulator" as Sim {
}

rectangle "Web Page" as Web {
}

Tanita --> App : Send data via COM port
Sim --> App : Simulate data input
App --> Web : Send data via WebSockets
Web --> User : Display data
@enduml` 

**Description:**

-   **Tanita Device**: The physical scale sending data.
-   **Simulator**: Simulates the Tanita device for testing.
-   **Application**: Receives data from the device or simulator, processes it, and sends it to the web page.
-   **Web Page**: Displays the data to the user in real-time.

### Class Diagram

plantuml

Skopiuj kod

`@startuml
class Application {
  - comPort: String
  - websocketServer: WebSocketServer
  - dataProcessor: DataProcessor
  + start()
  + stop()
}

class DataProcessor {
  + processData(data: String): ParsedData
}

class WebSocketServer {
  - clients: List<Client>
  + broadcast(data: ParsedData)
}

class Simulator {
  - comPort: String
  + sendData(data: String)
}

Application --> DataProcessor : uses
Application --> WebSocketServer : communicates with
Simulator --> Application : sends data to
@enduml` 

**Description:**

-   **Application**: Core component that manages data flow.
-   **DataProcessor**: Handles parsing and formatting of incoming data.
-   **WebSocketServer**: Manages WebSocket connections and data broadcasting.
-   **Simulator**: Test tool that emulates the Tanita device.

> **Note:** To visualize these diagrams, you can use [PlantUML](https://plantuml.com/) with the provided code snippets.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For questions or support, please contact the project maintainer

----------

# Sample Logs

Below is an example of what the logs look like when running the test environment:

00:00 INFO: Connecting to com port:COM13
00:00 INFO: Port successfully connected
00:02 INFO: Socket set
00:02 INFO: WebSocket client connected, counter:0
00:11 INFO: Event received, data len:265
00:11 INFO: Message from Tanita stored
00:11 INFO: Data from device:{0,16,~0,1,~1,1,~2,1,MO,"SC-330",SN,"00000001",ID,"0000000000",Da,"25/09/2014",TI,"08:20",Bt,0,GE,1,AG,34,Hm,191.0,Pt,0.0,Wk,89.2,FW,18.8,fW,16.8,MW,72.4,mW,68.8,sW,13,bW,3.6,wW,48.0,ww,53.8,MI,24.5,Sw,80.3,OV,11.1,IF,6,rb,8874,rB,2121,rJ,13,rA,29,ZF,501.8,CS,D9}
00:11 INFO: Event received, data len:0
00:11 INFO: Entry sent to browser: edit-field-fit-measurement-date-und-0-value-datepicker-popup-0;"25/09/2014"
00:11 INFO: Entry sent to browser: edit-field-fit-body-type-und;0
00:11 INFO: Entry sent to browser: edit-field-fit-body-height-und-0-value;191.0
00:11 INFO: Entry sent to browser: edit-field-fit-body-weight-und-0-value;89.2
00:11 INFO: Entry sent to browser: edit-field-fit-body-fat-percentage-und-0-value;18.8
00:11 INFO: Entry sent to browser: edit-field-fit-body-fat-mass-und-0-value;16.8
00:11 INFO: Entry sent to browser: edit-field-fit-body-lean-mass-und-0-value;72.4
00:11 INFO: Entry sent to browser: edit-field-fit-body-muscle-mass-und-0-value;68.8
00:11 INFO: Entry sent to browser: edit-field-fit-body-bone-mass-und-0-value;3.6
00:11 INFO: Entry sent to browser: edit-field-fit-body-total-water-und-0-value;48.0
00:11 INFO: Entry sent to browser: edit-field-fit-body-water-percentage-und-0-value;53.8
00:11 INFO: Entry sent to browser: edit-field-fit-body-bmi-und-0-value;24.5
00:11 INFO: Entry sent to browser: edit-field-fit-target-weight-und-0-value;80.3
00:11 INFO: Entry sent to browser: edit-field-fit-fat-rate-und-0-value;11.1
00:11 INFO: Entry sent to browser: edit-field-fit-body-fat-intestinal-und-0-value;6
00:11 INFO: Entry sent to browser: edit-field-fit-body-energy-needs-und-0-value;8874
00:11 INFO: Entry sent to browser: edit-field-fit-body-metabolic-age-und-0-value;29
00:11 INFO: Lock released, message sent to WebSocket: {0,16,~0,1,~1,1,~2,1,MO,"SC-330",SN,"00000001",ID,"0000000000",Da,"25/09/2014",TI,"08:20",Bt,0,GE,1,AG,34,Hm,191.0,Pt,0.0,Wk,89.2,FW,18.8,fW,16.8,MW,72.4,mW,68.8,sW,13,bW,3.6,wW,48.0,ww,53.8,MI,24.5,Sw,80.3,OV,11.1,IF,6,rb,8874,rB,2121,rJ,13,rA,29,ZF,501.8,CS,D9}

----------

**Note:** Ensure that the paths and configuration settings in the instructions match your actual setup. Adjust the COM port numbers and file paths as necessary.
