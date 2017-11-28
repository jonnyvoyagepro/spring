package customerreview;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import de.hybris.platform.customerreview.jalo.CustomerReview;
import de.hybris.platform.customerreview.jalo.CustomerReviewManager;
import de.hybris.platform.customerreview.jalo.SessionContext;
import de.hybris.platform.core.model.product.ProductModel;

@Component
public class CustomerReviewHandler {

	/*
	 * It's injected as defined in configuration file - see comments in CustomerReviewProcessor.java
	 * 
	 * */
	private String curses;
	
	private List<String> curseList;
	
	public String getCurses() {
		return curses;
	}

	public void setCurses(String curses) {
		this.curses = curses;
	}

	public List<String> getCurseList() {
		return curseList;
	}

	public void setCurseList(List<String> curseList) {
		this.curseList = curseList;
	}
	
	public boolean containsAKeyword(String theString, List<String> keywords){
		   for(String keyword : keywords){
		      if(theString.contains(keyword)){
		         return true;
		      }
		   }
		   return false; // Never found match.
	}
	
	public int getCountByRatingRange(ProductModel product, Double minRating, Double maxRating) {
		
		int totalNumber = 0;
		
		if(product!=null){
			SessionContext ctx = CustomerReviewManager.getInstance().getSession().getSessionContext();
	        List<CustomerReview> reviewList = CustomerReviewManager.getInstance().getAllReviews(ctx, product);

			int sizeReview = 0;
			CustomerReview review = null;
			 
           if (reviewList!=null && (sizeReview = reviewList.size())>0){
        	   
        	   for (int i=0;i<sizeReview;i++){
        		   review = (CustomerReview)reviewList.get(i);
        		   if(review.getRating() >= minRating && 
        				   review.getRating() <= maxRating){
        			   
        			   totalNumber++;// return this one
        		   }
        		   
        	   }      	   
           }	        
		}
		  
		 
		return totalNumber;

	}
	
	
	public void validateComment(String commentsToCheck) throws Exception {
		
	  if(this.curses!=null){// has curses list from configuration
		  
	     if(curseList==null){
	    	 
	    	 curseList=Arrays.asList(curses.split("\\s*,\\s*"));
	     }
		
		  if(commentsToCheck!=null && !commentsToCheck.trim().equals("")){// Not empty
			if(containsAKeyword(commentsToCheck, curseList)){
	  		  
				//Here is a simplified solution
				// 1. ResourceBundleMessageSource to get customized messages
				// 2. Create specific exception subclassing Exception class to categorize each scenario
	  		  throw new Exception("This Comment contains curses");
	  	    }
		  }
	  }	
	}
	
	
	public void validateRating(Double rating) throws Exception {
				  
		  if(rating<0){
			  
				//Here is a simplified solution
				// 1. ResourceBundleMessageSource to get customized messages
				// 2. Create specific exception subclassing Exception class to categorize each scenario
	  		  throw new Exception("Unqualified rating value");
		  }	
	}	
 
}
