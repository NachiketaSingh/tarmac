package tarmac.hcl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class getResources {
	private static Properties loc;
	private static FileInputStream file;
	private static XSSFWorkbook workbook ;
	private static XSSFSheet sheet ;
	private static int rowCount;
	
	public static void loadResources() {		
	try{
		
		File srcObjRepo = new File(System.getProperty("user.dir") +"\\resources\\Object Repository.properties");		
		FileInputStream fisObjRepo=new FileInputStream(srcObjRepo);
		 loc= new Properties();
		loc.load(fisObjRepo);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		}catch (NullPointerException e) {
			e.printStackTrace();
			}
	}

public static void loadExcel() {
	try {
	
	 file = new FileInputStream(new File(Constants.TARMAC_REPORT_PATH));
	 workbook = new XSSFWorkbook(file);
	 sheet = workbook.getSheetAt(0);
	 rowCount=sheet.getPhysicalNumberOfRows();
	 System.out.println("Excel loded");
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
		
	}
public static String getLocator(String ElementName) {
	return loc.getProperty(ElementName);
}

public static String getcelldata(int rowIndex, int colIndex ) {
	String tempData= "No value";
		 Row  row = sheet.getRow(rowIndex);
		  if (row != null) {
		    Cell cell = row.getCell(colIndex);
		    if (cell != null) {    	
				switch (cell.getCellType()) 				
				{
					case NUMERIC:
						tempData= String.format ("%.0f", cell.getNumericCellValue());
						break;
					case STRING:
						tempData= cell.getStringCellValue();
						break;
					default:
						tempData="Invalid data";
						break;
				}
		    }
		  }
		  return tempData;	
	}



public static List<String> getSAPIdwithAppName(String AppName) {
		
	List<String> arrEmp= new ArrayList<String>();
	   for (int i= 0 ; i<=rowCount; i++) {
		   String tempAppName= getcelldata(i,10);
		   String tempSAPId= getcelldata(i,4);
		   
		   if (tempAppName.equals(AppName)){			   
			   arrEmp.add(tempSAPId);	
			   
		   } 
		   
	   }
	
	  return arrEmp;
	  
}

public static void closeExcel() {
	try {
		workbook.close();
		file.close();
	} catch (IOException e) {
		System.out.println("There is some issue while closing the file");
		e.printStackTrace();
	}
}
}
