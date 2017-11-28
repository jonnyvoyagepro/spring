 
 
  This is the instructions on how to use the new functionality
  
   1. Configurations
      A) Add the following bean definition into a configuration file (for example: customerreview-spring.xml)
      for container to pickup:
  
        <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
 	       <property name="location">
            		<value>settings.properties</value>
 	       </property>
        </bean>
   
	<bean id="customerReviewHandler" class="customerreview.CustomerReviewHandler">
	     	<property name="curses" value="${curse.text.list}" />
 	</bean>
 
     B) Add a properties file - settings.properties at src\main\resources\settings.properties
        containing key/value pairs including the curse word list(delimited by comma), for example:
        
        curse.text.list=curse1,curse2,curse3
  
   2. Inject CustomerReviewHandler & ProductService(this should already have configuration somewhere)
      @Autowired
      private CustomerReviewHandler reviewHandler; 
      
      @Autowired
      Private ProductService productService;
    
   3. When we need to get a product¡¯s total number of customer reviews 
      whose ratings are within a given range (inclusive), utilize CustomerReviewHandler to do the 
      counting:
      
      reviewHandler.getCountByRatingRange(product, minRating, maxRating);
      
   4. When we need to check curse from user comments, utilize CustomerReviewHandler to do checking:
         
      reviewHandler.validateComment(comment);
      
   5. If there's no exception raised from step 4, go ahead to create CustomerReview
   
 