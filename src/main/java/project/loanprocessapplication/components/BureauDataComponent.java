package project.loanprocessapplication.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class BureauDataComponent {
	
	HashMap<Integer,String> csvFileAsMap;
	HashMap<String,Integer> columnNames;
	Properties modelProperties;
	Properties loanPurposeProperties;
	
	BureauDataComponent(){
		this.csvFileAsMap = new HashMap<Integer,String>();
		this.columnNames = new HashMap<String,Integer>();
		this.modelProperties = new Properties();
		this.loanPurposeProperties=new Properties();
		BufferedReader bufferedReader = null;
		FileReader modelPropertiesReader = null;
		FileReader loanPurposePropertiesReader=null;
		String line = "";
		String csvSplitBy = ",";
		
		try {
			bufferedReader = new BufferedReader(new FileReader( new File("BureauData.csv")));
			String topRow[] = bufferedReader.readLine().split(csvSplitBy);
			for(int column = 1; column < topRow.length; column++)
				columnNames.put(topRow[column],column-1);
			
			while((line = bufferedReader.readLine())!=null) {
				String[] keyValue = line.split(csvSplitBy, 2);
				this.csvFileAsMap.put(Integer.parseInt(keyValue[0]), keyValue[1]);
			}
			//File modelFile = new File("modelProperties.properties");
			
			File loanPurposeFile = new File("loanPurposeProperties.properties");
			
			//if(modelFile.exists())
			modelPropertiesReader = new FileReader(new File("modelProperties.properties"));
			if(loanPurposeFile.exists())
			loanPurposePropertiesReader = new FileReader(new File("loanPurposeProperties.properties"));
			if(modelPropertiesReader!=null)
				this.modelProperties.load(modelPropertiesReader);
			if(loanPurposePropertiesReader!=null)
				this.loanPurposeProperties.load(loanPurposePropertiesReader);
		}
		
		catch(FileNotFoundException exception) {
			exception.printStackTrace();
			System.out.println("Required files are BureauData.csv and modelProperties.properties");
		}
		catch(IOException exception) {
			exception.printStackTrace();
		}
		catch(NumberFormatException exception) {
			exception.printStackTrace();
		}
		
		finally {
			if(modelPropertiesReader!=null) {
				try {
					modelPropertiesReader.close();
				}
				catch(IOException exception) {
					exception.printStackTrace();
				}
			}
			if(bufferedReader!=null) {
				try {
					bufferedReader.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	public HashMap<Integer, String> getCsvFileAsMap() {
		return csvFileAsMap;
	}

	public HashMap<String,Integer> getColumnNames() {
		return columnNames;
	}

	public Properties getModelProperties() {
		return modelProperties;
	}

	public Properties getLoanPurposeProperties() {
		return loanPurposeProperties;
	}
	
}
