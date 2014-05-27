package data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import models.Category;
import models.Choice;
import models.Question;
import models.QuizDAO;
import at.ac.tuwien.big.we14.lab4.dbpedia.api.DBPediaService;
import at.ac.tuwien.big.we14.lab4.dbpedia.api.SelectQueryBuilder;
import at.ac.tuwien.big.we14.lab4.dbpedia.vocabulary.DBPedia;
import at.ac.tuwien.big.we14.lab4.dbpedia.vocabulary.DBPediaOWL;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

public class DBPediaDataInserter {

	public static void insertData(){
		Resource vienna = DBPediaService.loadStatements(DBPedia.createResource("Vienna"));
		Resource berlin = DBPediaService.loadStatements(DBPedia.createResource("Berlin"));
		
		// build SPARQL-query
		SelectQueryBuilder query = DBPediaService.createQueryBuilder() .setLimit(5) // at most five statements 
				.addWhereClause(RDF.type, DBPediaOWL.Agent) 
				.addPredicateExistsClause(FOAF.name) 
				.addWhereClause(DBPediaOWL.deathPlace , vienna) 
				.addFilterClause(RDFS.label, Locale.GERMAN) 
				.addFilterClause(RDFS.label, Locale.ENGLISH);
		
		
		Model peopleDiedVienna = DBPediaService.loadStatements(query.toQueryString());
		
		List<String> EnglishPeopleNamesDiedVienna = DBPediaService.getResourceNames(peopleDiedVienna, Locale.ENGLISH);
		List<String> GermanPeopleNamesDiedVienna = DBPediaService.getResourceNames(peopleDiedVienna, Locale.GERMAN);

		query.removeWhereClause(DBPediaOWL.deathPlace, vienna); 
		query.addWhereClause(DBPediaOWL.deathPlace , berlin);
		query.setLimit(15);
		
		Model peopleDiedLondon = DBPediaService.loadStatements(query.toQueryString());
		List<String> englishPeopleNamesDiedBerlin = DBPediaService.getResourceNames(peopleDiedLondon, Locale.ENGLISH);
		List<String> germanPeopleNamesDiedBerlin = DBPediaService.getResourceNames(peopleDiedLondon, Locale.GERMAN);
	
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
				wrongChoice.setTextDE(germanPeopleNamesDiedBerlin.get(3*i + j));
				wrongChoice.setTextEN(englishPeopleNamesDiedBerlin.get(3*i + j));
				question.addWrongChoice(wrongChoice);
			}
			
			category.addQuestion(question);
		}
		
		QuizDAO.INSTANCE.persist(category);
	}
	
}
