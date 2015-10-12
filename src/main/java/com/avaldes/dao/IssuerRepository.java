package com.avaldes.dao;

import java.util.List;

import com.avaldes.util.UtilMongoDB;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.avaldes.model.Issuer;

@Repository
public class IssuerRepository{
	public static final String COLLECTION_NAME = "issuer";

    ApplicationContext ctx = new AnnotationConfigApplicationContext(UtilMongoDB.class);
    MongoOperations mongoOperation = (MongoOperations)ctx.getBean("mongoTemplate");
	
	public void addIssuer(Issuer issuer) {
        if (!mongoOperation.collectionExists(Issuer.class)) {
            mongoOperation.createCollection(Issuer.class);
        }
        mongoOperation.insert(issuer, COLLECTION_NAME);
    }
	
	public Issuer getIssuerByTicker(String ticker) {
	    return mongoOperation.findOne(
	    		Query.query(Criteria.where("ticker").is(ticker)), Issuer.class, COLLECTION_NAME);
	}
	
	public List<Issuer> getAllIssuers() {
        return mongoOperation.findAll(Issuer.class, COLLECTION_NAME);
    }
     
    public Issuer deleteIssuer(String ticker) {
    	Issuer issuer = mongoOperation.findOne(
	    		Query.query(Criteria.where("ticker").is(ticker)), Issuer.class, COLLECTION_NAME);
        mongoOperation.remove(issuer, COLLECTION_NAME);
        
        return issuer;
    }
     
    public Issuer updateIssuer(String ticker, Issuer issuer) {
    	Query query = new Query();
		query.addCriteria(Criteria.where("ticker").is(ticker));
 
		Update update = new Update();
		update.set("issuerName", issuer.getIssuerName());
		update.set("issuerType", issuer.getIssuerType());
		update.set("country", issuer.getCountry());

        mongoOperation.updateFirst(query, update, Issuer.class);
        
        return issuer;
    }

}
