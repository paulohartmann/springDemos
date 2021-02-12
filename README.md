# Demos and Examples

Collection of projects that I used to test new features and to create some base code. With time I will push more projects to here.


## 1. Entityrelationship
![entities](https://github.com/paulohartmann/springDemos/blob/main/entityrelationship/entitierelationship.png)
- Spring Boot and JPA
- Example with @OneToMany and @ManyToOne (**dealing with Infinite Recursion**, see [1.1])
- Example of @Query
- Example of basic tests with jUnit

### 1.1. JsonManagedReference and JsonBackReference
Since Jackson 1.6 you can use two annotations to solve the infinite recursion problem without ignoring the getters/setters during serialization:  [`@JsonManagedReference`](https://fasterxml.github.io/jackson-annotations/javadoc/2.6/com/fasterxml/jackson/annotation/JsonManagedReference.html)  and  [`@JsonBackReference`](https://fasterxml.github.io/jackson-annotations/javadoc/2.6/com/fasterxml/jackson/annotation/JsonBackReference.html).

**Explanation**

For Jackson to work well, one of the two sides of the relationship should not be serialized, in order to avoid the infite loop that causes your stackoverflow error.

So, Jackson takes the forward part of the reference (your  `Set<BodyStat> bodyStats`  in Trainee class), and converts it in a json-like storage format; this is the so-called  **marshalling**  process. Then, Jackson looks for the back part of the reference (i.e.  `Trainee trainee`  in BodyStat class) and leaves it as it is, not serializing it. This part of the relationship will be re-constructed during the deserialization (**unmarshalling**) of the forward reference. ([From StackOverFlow](https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue))

## 2. spring-data-jpa-course

