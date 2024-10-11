
import java.io.IOException;
import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;


public class TanitaDataModel {
	
        public String TestData ="";
        public TanitaDataModel()
        {
            DataDefinitionList = prepareDataDefinition();
        }
	
        public boolean validateData(String aData)
	{
		// validate header
		return aData.startsWith(getControlString());
	}
	
	private String getControlString()
	{
		 String str;
		 str = "{0,16,~0,1,~1,1,~2,1";
		 return str;
	}
	
	public String getSampleAdultData()
	{
		 String str;
		 str = ("{0,16,~0,1,~1,1,~2,1,MO,\"SC-330\",SN,\"00000001\",ID,\"0000000000\",Da,\"25/09/2014\",TI,\"08:20\",Bt,0,GE,1,AG,34,Hm,191.0,Pt,0.0,Wk,89.2,FW,18.8,fW,16.8,MW,72.4,mW,68.8,sW,13,bW,3.6,wW,48.0,ww,53.8,MI,24.5,Sw,80.3,OV,11.1,IF,6,rb,8874,rB,2121,rJ,13,rA,29,ZF,501.8,CS,D9}");
		 return str;
	}

	public String getSampleAthleteData()
	{
		 String str;
		 str = ("{0,16,~0,1,~1,1,~2,1,MO,\"SC-330\",SN,\"00000001\",ID,\"0000000000\",Da,\"25/09/2014\",TI,\"08:20\",Bt,0,GE,1,AG,34,Hm,191.0,Pt,0.0,Wk,89.2,FW,18.8,fW,16.8,MW,72.4,mW,68.8,sW,13,bW,3.6,wW,48.0,ww,53.8,MI,24.5,Sw,80.3,OV,11.1,IF,6,rb,8874,rB,2121,rJ,13,rA,29,ZF,501.8,CS,D9}");
		 return str;
	}
 	public String getSampleChildData()
	{
		 String str;
		 str = ("{0,16,~0,1,~1,1,~2,1,MO,\"SC-330\",SN,\"00000001\",ID,\"0000000000\",Da,\"25/09/2014\",TI,\"08:20\",Bt,0,GE,1,AG,34,Hm,191.0,Pt,0.0,Wk,89.2,FW,18.8,fW,16.8,MW,72.4,mW,68.8,sW,13,bW,3.6,wW,48.0,ww,53.8,MI,24.5,Sw,80.3,OV,11.1,IF,6,rb,8874,rB,2121,rJ,13,rA,29,ZF,501.8,CS,D9}");
		 return str;
	}       
	public class OneEntry
	{
		String Name;
		String ID;
		String Value;
		String Unit;
		String JavaScriptID;
                
		OneEntry(String aName, String aID, String aValue, String aUnit, String aJavaScriptID)
		{
			Name = aName;
			ID= aID;
			Value= aValue;
			Unit = aUnit;
                        JavaScriptID = aJavaScriptID;
		}
	}

	public String ArrayToString( List<OneEntry> datalist)
	{
		String Data = new String();
                Data = datalist.stream().map((item) -> item.ID + "," + item.Value + ",").reduce(Data, String::concat);
                Data +="\r\n";
		return Data.replace("\"\"\"","\"").replace("\"\"","\"");
	}
	
   public List<OneEntry> importFromFile( String aFile)
   {
	   List<OneEntry> datalist = new ArrayList<>();

	   try
	   {
		   FileReader fr = new FileReader(aFile);
		   BufferedReader br = new BufferedReader(fr);
		   String stringRead = br.readLine();
       
		   while( stringRead != null )
		   {
			   String[] elements = stringRead.split(";");

			   if(elements.length < 4) {
				   throw new RuntimeException("line too short"); //handle missing entries
			   }

			   String Name = elements[0];
			   String ID = elements[1];
			   String Value = elements[2];
			   String Unit = elements[3];
                           String JavaScriptID = elements[4];
                           
			   OneEntry temp = new OneEntry(Name, ID, Value, Unit, JavaScriptID);
			   datalist.add(temp);

			   // read the next line
			   stringRead = br.readLine();
		   }	
		   br.close( );
	   }
	   catch(IOException ioe)
	   {
		   System.out.println("Exception");
	   }

           datalist.stream().forEach((item) -> {
               Log.debug( item.Name + "," + item.ID + "," + item.Value + "," + item.Unit + ","+item.JavaScriptID);
            });
	   
	   return datalist;
  }
   
   public List<OneEntry> parseFromString( String aData)
   {
	   List<OneEntry> datalist = new ArrayList<>();
	   int startSegment = 0;
	   int endSegment;
   
	   endSegment = aData.indexOf(',');
	   
	   endSegment = aData.indexOf(",",endSegment + 1);
				   
	   String stringRead = aData.substring(startSegment, endSegment);
	   
	   while( stringRead != null )
	   {

			   String[] elements = stringRead.split(",");

			   if(elements.length < 2) {
				   throw new RuntimeException("line too short"); //handle missing entries
			   }
                           
                           OneEntry entry = getElementByID( elements[0]);
                           if (null != entry)
                           {
                                String Name = entry.Name;
                                String ID = elements[0];
                                String Value = elements[1];
                                String Unit = entry.Unit;
                                String JavaScriptID = entry.JavaScriptID;
                                OneEntry temp = new OneEntry(Name, ID, Value, Unit, JavaScriptID);
                                datalist.add(temp);
                           }
                           else
                               throw new RuntimeException("Element not found on list"); //handle missing entries
			   if( aData.length() <= endSegment + 1 ) break;

			   // read the next line
			   startSegment = endSegment + 1;
			   endSegment = aData.indexOf(",",startSegment);
			   if( aData.length() <= endSegment + 5) 
			   {
				   endSegment = aData.length() - 1;
			   }
			   else
			   {
				   endSegment = aData.indexOf(",",endSegment + 1);
			   }
			   stringRead = aData.substring(startSegment, endSegment);
		   }
	   
	   return datalist;
	   }

   private List<OneEntry> prepareDataDefinition()
   {
	   DataDefinitionList = new ArrayList<>();
		   String stringRead;
       
            for (String DataDefinition1 : DataDefinition) {
                stringRead = DataDefinition1;
                String[] elements = stringRead.split(";");
                if(elements.length < 5) {
                    throw new RuntimeException("line too short"); //handle missing entries
                }
                String Name = elements[0];
                String ID = elements[1];
                String Value = elements[2];
                String Unit = elements[3];
                String JavaStringID = elements[4];
                OneEntry temp = new OneEntry(Name, ID, Value, Unit, JavaStringID);
                DataDefinitionList.add(temp);
            }	

            DataDefinitionList.stream().forEach((item) -> {
                Log.debug( item.Name + "," + item.ID + "," + item.Value + "," + item.Unit + "," + item.JavaScriptID );
            });
	   
	   return DataDefinitionList;
  }
   
 private OneEntry getElementByID( String elementID)
 {
     if(null == DataDefinitionList)
     {
         Log.error( "Error: list not initialized");
     }
       for (OneEntry item : DataDefinitionList) {
            if( item.ID.equals(elementID)) return item;
	}
    Log.error( "Error: Item not found");
    return null;
 }
 
   List<OneEntry> DataDefinitionList;
   
   		private final String DataDefinition[] = 
        {
                "Control data;{0;16; ; ",
                "Control data;~0;1; ; ",
                "Control data;~1;1; ; ",
                "Control data;~2;1; ; ",
                "Model;MO;\"XXXXXX\"; ; ",
                "Serial No.;SN;\"XXXXXXXX\"; ; ",
                "ID number;ID;\"XXXXXXXXXX\"; ; ",
                "Date (dd/mm/yyyy);Da;\"dd/mm/yyyy\"; ;edit-field-fit-measurement-date-und-0-value-datepicker-popup-0",
                "Time;TI;hh:mm; ; ",
                "Body type;Bt;X; ;edit-field-fit-body-type-und",
                "Gender;GE;X; ; ",
                "Age;AG;XX; ; ",
                "Height;Hm;XXX.X;cm;edit-field-fit-body-height-und-0-value",
                "Clothes (tare);Pt;XX.X;kg; ",
                "Weight;Wk;XXX.X;kg;edit-field-fit-body-weight-und-0-value",
                "Body fat %;FW;XX.X;%;edit-field-fit-body-fat-percentage-und-0-value",
                "Fat mass;fW;XXX.X;kg;edit-field-fit-body-fat-mass-und-0-value",
                "Fat free mass;MW;XXX.X;kg;edit-field-fit-body-lean-mass-und-0-value",
                "Muscle mass;mW;XXX.X;kg;edit-field-fit-body-muscle-mass-und-0-value",
                "Muscle score;sW;XX; ; ",
                "Bone mass;bW;XXX.X;kg;edit-field-fit-body-bone-mass-und-0-value",
                "TBW;wW;XXX.X;kg;edit-field-fit-body-total-water-und-0-value",
                "TBW %;ww;XXX.X; ;edit-field-fit-body-water-percentage-und-0-value",
                "BMI;MI;XXX.X;kg;edit-field-fit-body-bmi-und-0-value",
                "Standard body weight;Sw;XXX.X;kg;edit-field-fit-target-weight-und-0-value",
                "Degree of obesity;OV;XX.XX;%;edit-field-fit-fat-rate-und-0-value",
                "Visceral fat rating;IF;XX; ;edit-field-fit-body-fat-intestinal-und-0-value",
                "BMR (kJ);rb;XXXXX;kJ; ",
                "BMR (kcal);rB;XXXXX;kcal;edit-field-fit-body-energy-needs-und-0-value",
                "BMR score;rJ;XX; ; ",
                "Metabolic age;rA;XX; ;edit-field-fit-body-metabolic-age-und-0-value",
                "Rohrer's index;RO;XXXX.X; ; ",
                "Target body fat %;gF;XX; ; ",
                "Predicted weight;gW;XXX.X; ; ",
                "Predicted fat mass;gf;XXX.X; ; ",
                "Fat to gain / lese;gt;XXXXX; ; ",
                "Impedance;ZF;XXXXX; ; ",
                "Checksum;CS;XX; ; "
                        };
   }
