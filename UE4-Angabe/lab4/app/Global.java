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

import data.JSONDataInserter;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;
import play.db.jpa.JPA;
import play.libs.F.Function0;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
		Logger.info("Start load from DBPedia");
		
		// Check if DBpedia is available
		if(!DBPediaService.isAvailable()) { 
			Logger.info("DBpedia is currently not available.");
			return; 
		}
		
		Resource city = DBPediaService.loadStatements(DBPedia.createResource("Vienna"));
		
		// build SPARQL-query
		SelectQueryBuilder query = DBPediaService.createQueryBuilder() .setLimit(5) // at most five statements 
				.addWhereClause(RDF.type, DBPediaOWL.Agent) 
				.addPredicateExistsClause(FOAF.name) 
				.addWhereClause(DBPediaOWL.deathPlace , city) 
				.addFilterClause(RDFS.label, Locale.GERMAN) 
				.addFilterClause(RDFS.label, Locale.ENGLISH);
		
		
		Model peopleDiedVienna = DBPediaService.loadStatements(query.toQueryString());
		
		List<String> EnglishPeopleNamesDiedVienna = DBPediaService.getResourceNames(peopleDiedVienna, Locale.ENGLISH);
		List<String> GermanPeopleNamesDiedVienna = DBPediaService.getResourceNames(peopleDiedVienna, Locale.GERMAN);

		query.removeWhereClause(DBPediaOWL.deathPlace, city); 
		query.addWhereClause(DBPediaOWL.birthPlace , city);
		query.setLimit(15);
		
		Model peopleBornVienna = DBPediaService.loadStatements(query.toQueryString());
		List<String> EnglishPeopleNamesBornVienna = DBPediaService.getResourceNames(peopleBornVienna, Locale.ENGLISH);
		List<String> GermanPeopleNamesBornVienna = DBPediaService.getResourceNames(peopleBornVienna, Locale.GERMAN);
	
		Category category= new Category();
		category.setNameDE("Personen");
		category.setNameEN("People");
		
		for(int i = 0; i < 5; i++){
			Question question = new Question();
			question.setMaxTime(new BigDecimal(30));
			question.setTextDE("Welche dieser Personen ist in Wien gestorben?");
			question.setTextEN("Which person died in Vienna?");
			
			Choice rightChoice = new Choice();
			rightChoice.setTextDE(GermanPeopleNamesDiedVienna.get(i));
			rightChoice.setTextEN(EnglishPeopleNamesDiedVienna.get(i));
			question.addRightChoice(rightChoice);
			
			for(int j = 0; j < 3; j++){
				Choice wrongChoice = new Choice();
				wrongChoice.setTextDE(GermanPeopleNamesBornVienna.get(3*i + j));
				wrongChoice.setTextEN(EnglishPeopleNamesBornVienna.get(3*i + j));
				question.addWrongChoice(wrongChoice);
			}
			
			category.addQuestion(question);
		}
		
		QuizDAO.INSTANCE.persist(category);
		Logger.info("End load from DBPedia");
	}

    public void onStop(Application app) {
        Logger.info("Application shutdown...");
    }

}