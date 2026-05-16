For the IBM Bob Hackathon Project, I want to 

- Create a workflow framework, to let IBM Bob better handling the complexity of the migration task.
- This framework should cover all of common migration scenarios, such as
    - Cloud migration, migrate application running in legacy platform to cloud one. 
        - For example: migrate application running in IBM WebShpere to AWS Cloud
    - Framework migration, migrate the framework that application uses to another framework. 
        - For example: migrate application running in quarkus to spring boot
    - Programming Language migration, migrate the programming language used in a application to a difference one. 
        - For example: migrate application running in javascript/typscript to python
    - Version migration, upgrade the version of a technical that a application use to a newer one.
        - For example: migrate application running in Java 11 to Java 21
    - Dependency migration, migrate a dependency use in an application to a difference one.
        - For example: migrate the JDBC stack used by an application to use JPA + Hibernate ORM
    - Data migration, migrate the data from a database to a difference one, this can vary between SQL and non SQL database
        - For example: migrate the database used by an application from PostgresSQL to MongoDB

In order to that, I need to create these concrete artifacts to make the above idea works:

Reference: https://bob.ibm.com/docs/ide

- Create custom modes for IBM Bob, this is similar to Copilot Agent -> These modes capture the overall instructions like an AGENTS.md file
- Create an orchestator for the technical migration. This skill will orchestrate the execution of the modes in a sequence, phase by phase manner 
- Create custom skills for IBM Bob, this is similar to Anthropic Skills -> These skills go hand-in-hand with the modes, and specialized for each of the modes. 1 mode map to n skills specialized for the mode instruction.
- Create custom MCPS, tools for IBM Bob -> These tools and mcps are in combination with the mode for letting the IBM Bob select suitable tools, mcps during the execution each of the mode