package customerreview;


/*
 * 
 * This is a demo component showing how to use the new functionality
 * 
 *  1. Configurations
 *     A) Add the following bean definition into a configuration file (for example: customerreview-spring.xml)
 *     for container to pickup:
 * 
 *       <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
 *	       <property name="location">
 *           <value>settings.properties</value>
 *	       </property>
 *        </bean>
 *  
 *    <bean id="customerReviewHandler" class="customerreview.CustomerReviewHandler">
 *    	<property name="curses" value="${curse.text.list}" />
 *	  </bean>
 *
 *    B) Add a properties file - settings.properties at src\main\resources\settings.properties
 *       containing key/value pairs including the curse word list(delimited by comma), for example:
 *       
 *       curse.text.list=curse1,curse2,curse3
 * 
 *  2. Inject CustomerReviewHandler & ProductService(this should already have configuration somewhere)
 *     @Autowired
 *     private CustomerReviewHandler reviewHandler; 
 *     
 *     @Autowired
 *     Private ProductService productService;
 *   
 *  3. When we need to get a product¡¯s total number of customer reviews 
 *     whose ratings are within a given range (inclusive), utilize CustomerReviewHandler to do the 
 *     counting:
 *     
 *     reviewHandler.getCountByRatingRange(product, minRating, maxRating);
 *     
 *  4. When we need to check curse from user comments, utilize CustomerReviewHandler to do checking:
 *        
 *     reviewHandler.validateComment(comment);
 *     
 *  5. If there's no exception raised from step 4, go ahead to create CustomerReview
 *  
 * 
 * */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hybris.platform.product.ProductService;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.util.Config;

@Service
public class CustomerReviewProcessor {

	@Autowired
    private CustomerReviewHandler reviewHandler; 
	
	@Autowired
	Private ProductService productService; 

    public int countCustomerReview(String productName){
		/*
		 * Suppose the range has already been configured (for example:[2,4] ) by
		 * 
		 * Config.setParameter("customerreview.special.minimalrating", String.valueOf(2));
		 * Config.setParameter("customerreview.special.maximalrating", String.valueOf(4));
		 * 
		 * 
		 * */

    	Double minRating = Config.getParameter("customerreview.special.minimalrating");
    	Double maxRating = Config.getParameter("customerreview.special.maximalrating");
		ProductModel product = productService.getProduct(productName);
		
    	int count = 0;
    	if(product!=null){
    		count = reviewHandler.getCountByRatingRange(product, minRating, maxRating);
    	}    	
    	return count;
    }	
	
    public createCustomerReview(String comment, Double rating) {    	
    	
    	try {
			reviewHandler.validateComment(comment);
			reviewHandler.validateRating(rating);
			
			// add code here to create CustomerReview ...
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    }
	
}
