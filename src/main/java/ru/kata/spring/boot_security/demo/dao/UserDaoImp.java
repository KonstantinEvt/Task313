package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.UserCrud;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(UserCrud userCrud) {
        entityManager.persist(userCrud);
    }

    @Override
    public void removeUser(Long id) {
        entityManager.remove(entityManager.find(UserCrud.class, id));
    }

    @Override
    public UserCrud getUser(Long id) {
        return entityManager.find(UserCrud.class, id);
    }

    @Override
    public List<UserCrud> getListUsers() {
        return entityManager.createQuery("from UserCrud", UserCrud.class).getResultList();
    }

    @Override
    public void updateUser(UserCrud userCrud, Long id) {
        UserCrud userOld = entityManager.find(UserCrud.class, id);
        userOld.setFirstName(userCrud.getFirstName());
        userOld.setLastName(userCrud.getLastName());
        userOld.setEmail(userCrud.getEmail());
        userOld.setUsername(userCrud.getUsername());
        userOld.setPassword(userCrud.getPassword());
        userOld.setRoles(userCrud.getRoles());
    }

    @Override
    public UserCrud findUserByUsername(String username) {
        try {
            return entityManager.createQuery("select u from UserCrud u where u.username= :username", UserCrud.class).setParameter("username", username).getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
}
