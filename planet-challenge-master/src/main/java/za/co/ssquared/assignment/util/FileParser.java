package za.co.ssquared.assignment.util;

import com.opencsv.CSVWriter;
import com.opencsv.CSVParser;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;

import za.co.ssquared.assignment.model.Planet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Utility class for reading/writing text files to beans and vice versa
 *
 */
public class FileParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileParser.class);
    
    /**
     * This class contains only static members and is a Singleton
     * Private constructor to prevent instantiation
     */
    private FileParser() {
        throw new IllegalStateException("Utility class");
      }
      
    /**
     * Generic Method for mapping column headers when reading a file
     * 
     * @param <T>      Describes the Type to be used to implement this method
     * @param type	   Class type for setting type of mapping strategy
     * @param headers  String array containing headers of the current file or bean
     * @return         A mapping strategy to be used for parsing a file
     */
    private static <T> ColumnPositionMappingStrategy<T> setMapping(Class<T> type, String[] headers) {
    	ColumnPositionMappingStrategy<T> mapStrategy = new ColumnPositionMappingStrategy<>();
    	mapStrategy.setType(type);
        mapStrategy.setColumnMapping(headers);      
        return mapStrategy;
    }
        
    /**
     * Generic Method for building an object that reads from files and creates beans
     * 
     * @param <T>          Describes the Type to be used to implement this method
     * @param csvtb        Builder object for which we will set parameters required to read from file to bean
     * @param mapStrategy  A mapping strategy to be used for parsing a file
     * @return             A valid object used to read from files to beans
     */
    private static <T> CsvToBean<T> setReader(CsvToBeanBuilder<T> csvtb, ColumnPositionMappingStrategy<T> mapStrategy){
    	return csvtb.withQuoteChar(CSVParser.DEFAULT_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator('|')
                    .withSkipLines(1)
                    .build();
    }

    /**
     * Generic Method used to read data from any csv file into a list of beans
     * 
     * @param <T>      Describes the Type to be used to implement this method
     * @param reader   Reader object containing the stream data from text file
     * @param type     Class type for setting type of mapping strategy
     * @param headers  String array containing headers of the current file or bean
     * @return         A list of beans created from data read from a file
     */
    public static <T> List<T> readText(BufferedReader reader, Class<T> type, String[] headers) {

    	List<T> beans = new ArrayList<>();
    	
        try {

        	ColumnPositionMappingStrategy<T> mapStrategy = setMapping(type,headers);        	    	
            CsvToBean<T> csvtb = setReader(new CsvToBeanBuilder<>(reader),mapStrategy);
            beans = csvtb.parse();           
            return beans; 
            
        } catch (Exception ex) {
            LOGGER.error("Error mapping Text to Bean", ex);
        }  
        
        return beans;
    }
    
    /**
     * Method to read from a list of spring beans into a csv format and send to client
     * 
     * @param writer   HttpResponse writer object for sending character text to client 
     * @param planets  List of beans to read data from
     * @param headers  String array containing headers of the current file or bean
     */
    public static void writePlanets(PrintWriter writer, List<Planet> planets, String[] headers) {
        for(Planet planet : planets) {
			writePlanet(writer, planet, headers);
		}
    }

    /**
     * Method used to read data from a spring bean into a csv format and send to client
     * Currently implemented for Planet type but can be made Generic
     * 
     * @param writer   HttpResponse writer object for sending character text to client 
     * @param planet   Bean to read data from
     * @param headers  String array containing headers of the current file or bean
     */
    public static void writePlanet(PrintWriter writer, Planet planet, String[] headers) {
       try {

    	   ColumnPositionMappingStrategy<Planet> mapStrategy = setMapping(Planet.class, headers);

            StatefulBeanToCsv<Planet> btcsv = new StatefulBeanToCsvBuilder<Planet>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mapStrategy)
                    .withSeparator('|')
                    .build();

            btcsv.write(planet);

        } catch (CsvException ex) {
            LOGGER.error("Error mapping Bean to CSV", ex);
        }
    }
}