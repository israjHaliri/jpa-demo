package id.or.greenlabs.demo.jpa.orm;

import id.or.greenlabs.demo.jpa.entity.Address;
import id.or.greenlabs.demo.jpa.entity.Student;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;

/**
 * @Author krissadewo
 * @Date 16/01/18
 **/
public class StudentBootStrapTest {

    private Logger log = Logger.getLogger(this.getClass().getName());
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistence-unit");
    private Student student;

    {
        student = new Student();

        Address address = new Address();
        address.setCity("Yogyakarta");
        address.setCountry("Indonesia");
        address.setStudent(student);

        student.setName("kris");
        student.getAddresses().add(address);
    }

    @Before
    public void init() {
        save();
    }

    //@Test
    private void save() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void update() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        student = em.getReference(Student.class, 1l);
        student.setName("test");

        //it's still managed object, no need call update query

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void updateUsingDetachEntity() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        //student = em.getReference(Student.class, 1l);
        student.setName("test");
        em.merge(student);

        //it's still managed object,no need call update query

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void find() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.find(Student.class, 1L);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void findWithLazyLoad() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        student = em.find(Student.class, 1L);
        log.info(student.getAddresses() + "");

        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void delete() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(student);
        em.remove(student);
        em.getTransaction().commit();
        em.close();
    }

    @Test
    public void verifyObjectState() {
        log.info(emf.createEntityManager().contains(student) + "");
    }


}
