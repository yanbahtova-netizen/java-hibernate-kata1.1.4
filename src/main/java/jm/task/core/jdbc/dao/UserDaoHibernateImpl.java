package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        Transaction zalupa = null;
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR (50), " +
                "lastName VARCHAR (50), " +
                "age INT)";
        try (Session session = Util.getSession()) {
            zalupa = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            zalupa.commit();
        } catch (Exception e) {
            if(zalupa != null && zalupa.isActive()) {
                zalupa.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction zalupa = null;
        String sql = "DROP TABLE IF EXISTS users";
        try(Session session = Util.getSession()) {
            zalupa = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            zalupa.commit();
        } catch (Exception e) {
            if(zalupa != null && zalupa.isActive()) {
                zalupa.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction zalupa = null;
        try(Session session = Util.getSession()) {
            zalupa = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.persist(user);
            zalupa.commit();
        } catch (Exception e) {
            if(zalupa != null && zalupa.isActive()) {
                zalupa.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction zalupa = null;
        try(Session session = Util.getSession()) {
            zalupa = session.beginTransaction();
            session.createQuery("DELETE FROM User WHERE id = :id").setParameter("id", id).executeUpdate();
            zalupa.commit();
        } catch (Exception e) {
            if(zalupa != null && zalupa.isActive()) {
                zalupa.rollback();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = Util.getSession()) {
            List<User> users = session.createQuery("FROM User", User.class).list();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction zalupa = null;
        String sql = "DELETE FROM users";
        try(Session session = Util.getSession()) {
            zalupa = session.beginTransaction();
            session.createNativeQuery(sql).executeUpdate();
            zalupa.commit();
        } catch (Exception e) {
            if(zalupa != null && zalupa.isActive()) {
                zalupa.rollback();
            }
            throw new RuntimeException(e);
        }
    }
}
