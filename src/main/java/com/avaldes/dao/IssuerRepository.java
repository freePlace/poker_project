package com.avaldes.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.avaldes.model.Issuer;

@Repository
public class IssuerRepository {
	public static final String COLLECTION_NAME = "issuer";
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	public void addIssuer(Issuer issuer) {
        if (!mongoTemplate.collectionExists(Issuer.class)) {
            mongoTemplate.createCollection(Issuer.class);
        }       
        mongoTemplate.insert(issuer, COLLECTION_NAME);
    }
	
	public Issuer getIssuerByTicker(String ticker) {
	    return mongoTemplate.findOne(
	    		Query.query(Criteria.where("ticker").is(ticker)), Issuer.class, COLLECTION_NAME);
	}
	
	public List<Issuer> getAllIssuers() {
        return mongoTemplate.findAll(Issuer.class, COLLECTION_NAME);
    }
     
    public Issuer deleteIssuer(String ticker) {
    	Issuer issuer = mongoTemplate.findOne(
	    		Query.query(Criteria.where("ticker").is(ticker)), Issuer.class, COLLECTION_NAME);
        mongoTemplate.remove(issuer, COLLECTION_NAME);
        
        return issuer;
    }
     
    public Issuer updateIssuer(String ticker, Issuer issuer) {
    	Query query = new Query();
		query.addCriteria(Criteria.where("ticker").is(ticker));
 
		Update update = new Update();
		update.set("issuerName", issuer.getIssuerName());
		update.set("issuerType", issuer.getIssuerType());
		update.set("country", issuer.getCountry());
 
        mongoTemplate.updateFirst(query, update, Issuer.class);
        
        return issuer;
    }
}
