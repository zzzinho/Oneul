package com.example.oneul.domain.post.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import com.example.oneul.domain.post.domain.Post;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class PostCommandJpaRepositoryImpl implements PostCommandRepository {
    private final EntityManager em;

    public PostCommandJpaRepositoryImpl(EntityManagerFactory entityManagerFactory){
        this.em = entityManagerFactory.createEntityManager();
    }

    @Override
    public Post save(Post post)   {
        em.persist(post);
        return post;
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Post update(Post post){
        return em.merge(post);
    }

    @Override
    public void deleteById(Long id){
        Post post = em.createQuery("select p from Post p where p.id = :id", Post.class)
                      .setParameter("id", id)
                      .getSingleResult();
        delete(post);
    }

    @Override
    public void delete(Post post){
        em.remove(post);
    }
}
