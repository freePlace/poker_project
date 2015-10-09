package com.avaldes.tutorial;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avaldes.dao.IssuerRepository;
import com.avaldes.model.Issuer;
import com.avaldes.rest.multipleIssuerResponse;
import com.avaldes.rest.restResponse;
import com.avaldes.rest.singleIssuerResponse;

/**
 * Handles requests for the application home page.
 */
@Controller
public class RestController {
	
	private static final Logger logger = LoggerFactory.getLogger(RestController.class);
	
	@Autowired
    private IssuerRepository issuerRepository;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Default Home REST page. The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "status";
	}
	
	@RequestMapping(value="/issuers", method=RequestMethod.GET)
	@ResponseBody
	public multipleIssuerResponse getAllIssuers() {
		logger.info("Inside getAllIssuers() method...");

		List<Issuer> allIssuers = issuerRepository.getAllIssuers();
		multipleIssuerResponse extResp = new multipleIssuerResponse(true, allIssuers);
		
		return extResp;
	}
	
	@RequestMapping(value="/issuer/{ticker}", method=RequestMethod.GET)
	@ResponseBody
	public singleIssuerResponse getIssuerByTicker(@PathVariable("ticker") String ticker) {
		Issuer myIssuer = issuerRepository.getIssuerByTicker(ticker);
		
		if (myIssuer != null) {
			logger.info("Inside getIssuerByTicker, returned: " + myIssuer.toString());
		} else {
			logger.info("Inside getIssuerByTicker, ticker: " + ticker + ", NOT FOUND!");
		}
		
		singleIssuerResponse extResp = new singleIssuerResponse(true, myIssuer);
		return extResp; 
	}

	@RequestMapping(value="/issuer/delete/{ticker}", method=RequestMethod.DELETE)
	@ResponseBody
	public restResponse deleteIssuerByTicker(@PathVariable("ticker") String ticker) {
		restResponse extResp;
		
		Issuer myIssuer = issuerRepository.deleteIssuer(ticker);
		
		if (myIssuer != null) {
			logger.info("Inside deleteIssuerByTicker, deleted: " + myIssuer.toString());
			extResp = new restResponse(true, "Successfully deleted Issuer: " + myIssuer.toString());
		} else {
			logger.info("Inside deleteIssuerByTicker, ticker: " + ticker + ", NOT FOUND!");
			extResp = new restResponse(false, "Failed to delete ticker: " + ticker);
		}
		
		return extResp;
	}
	
	@RequestMapping(value="/issuer/update/{ticker}", method=RequestMethod.PUT)
	@ResponseBody
	public restResponse updateIssuerByTicker(@PathVariable("ticker") String ticker, @ModelAttribute("issuer") Issuer issuer) {
		restResponse extResp;
		
		Issuer myIssuer = issuerRepository.updateIssuer(ticker, issuer);
		
		if (myIssuer != null) {
			logger.info("Inside updateIssuerByTicker, updated: " + myIssuer.toString());
			extResp = new restResponse(true, "Successfully updated Issuer: " + myIssuer.toString());
		} else {
			logger.info("Inside updateIssuerByTicker, ticker: " + ticker + ", NOT FOUND!");
			extResp = new restResponse(false, "Failed to update ticker: " + ticker);
		}
		
		return extResp;
	}

	@RequestMapping(value="/issuer/addIssuer", method=RequestMethod.POST)
	@ResponseBody
	public restResponse addIssuer(@ModelAttribute("issuer") Issuer issuer) {
		restResponse extResp;
		
		if (issuer.getTicker() != null && issuer.getTicker().length() > 0) {
			logger.info("Inside addIssuer, adding: " + issuer.toString());
			issuerRepository.addIssuer(issuer);
			extResp = new restResponse(true, "Successfully added Issuer: " + issuer.getTicker());
		} else {
			logger.info("Failed to insert...");
			extResp = new restResponse(false, "Failed to insert...");
		}
		
		return extResp;
	}	
}
