# BulkImporter
The Bulk-Importer should insert many Item entities into a database. Only one SQL-insert should be executed for one batch.

# Description - Implementation

There is a REST-assured test that creates 4 Items and calls the REST-Controller to import them into the H2-Database.

In the application.properties I configured that a batch size of 5 should be used.

```
hibernate.jdbc.batch_size=5
hibernate.order_inserts=true
hibernate.order_updates=true
```

I would expect that only one SQL insert is executed.
As described in [The best way to do batch processing with JPA and Hibernate](https://vladmihalcea.com/2017/04/25/the-best-way-to-do-batch-processing-with-jpa-and-hibernate/)
But 4 SQL inserts are executed.

During thr start of the Spring Boot application the Importer `@Component`  is executed and creates 3 Items. For every Item a SQL insert is executed. But it should only be one.

I tried another approach:
 ```
 @Component
 public class ImporterTransaction {

     private EntityManager entityManager;

     @Autowired
     public ImporterTransaction(EntityManagerFactory emf) {
         this.entityManager = emf.createEntityManager();
         importItems();
     }

     private void importItems() {

         int entityCount = 10;
         int batchSize = 5;
         EntityTransaction transaction = null;

         try {


             transaction = entityManager.getTransaction();
             transaction.begin();

             for (int i = 0; i < entityCount; ++i) {
                 if (i > 0 && i % batchSize == 0) {
                     entityManager.flush();
                     entityManager.clear();

                     transaction.commit();
                     transaction.begin();
                 }

                 Item item = new Item();
                 item.setId(UUID.randomUUID().toString());
                 item.setLocation("Board");
                 item.setDescription("Item "+i);

                 entityManager.persist(item);
             }

             transaction.commit();
         } catch (RuntimeException e) {
             if (transaction != null &&
                     transaction.isActive()) {
                 transaction.rollback();
             }
             throw e;
         } finally {
             if (entityManager != null) {
                 entityManager.close();
             }
         }
     }
 }

 ```

 Found here:
 [How do I save a rolled back record that generated a batch failure with JPA and Hibernate?](https://www.quora.com/How-do-I-save-a-rolled-back-record-that-generated-a-batch-failure-with-JPA-and-Hibernate/answer/Vlad-Mihalcea-1)

 But the result is the same. For every `save` or `persist` an insert is executed. But the batch import does not work.


 # Same Problem
 I found a thread with the same problem here:
 [How to enable batch inserts with Hibernate and Spring Boot](https://stackoverflow.com/questions/34228044/how-to-enable-batch-inserts-with-hibernate-and-spring-boot)
