import at.ac.tuwien.big.we14.lab4.dbpedia.api.DBPediaService;
import at.ac.tuwien.big.we14.lab4.dbpedia.api.SelectQueryBuilder;
import at.ac.tuwien.big.we14.lab4.dbpedia.vocabulary.DBPedia;
import at.ac.tuwien.big.we14.lab4.dbpedia.vocabulary.DBPediaOWL;

import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

import data.DBPediaDataInserter;
import data.JSONDataInserter;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.libs.F.Function0;
import twitter.TwitterClient;
import twitter.TwitterStatusMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import models.Category;
import models.Choice;
import models.Question;
import models.QuizDAO;

public class Global extends GlobalSettings {
	
	@play.db.jpa.Transactional
    public void onStart(Application app) {
       try {
		JPA.withTransaction(new Function0<Boolean>() {

			@Override
			public Boolean apply() throws Throwable {
				insertJSonData();
				insertDBPediaData();
				//For testing the client, enable this ;)
				//TwitterClient.share();
				return true;
			}
			   
			});
		} catch (Throwable e) {
			e.printStackTrace();
		}           
    }
	
	@play.db.jpa.Transactional
	public static void insertJSonData() throws IOException {
		File file = new File(Play.application().configuration().getString("questions.filePath"));
		InputSupplier<FileInputStream> inputStreamSupplier = 
				Files.newInputStreamSupplier(file);
		FileInputStream inputStream = inputStreamSupplier.getInput();
		JSONDataInserter.insertData(inputStream);
		Logger.info("Data from json file '" + file.getName() + "' inserted.");
	}
	
	@play.db.jpa.Transactional
	public static void insertDBPediaData() throws IOException {
		Logger.info("Start loading from DBPedia...");
		
		// Check if DBpedia is available
		if(!DBPediaService.isAvailable()) { 
			Logger.info("DBpedia is currently not available.");
			return; 
		}
		
		DBPediaDataInserter.insertData();
		Logger.info("DBPedia data inserted.");
	}

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}