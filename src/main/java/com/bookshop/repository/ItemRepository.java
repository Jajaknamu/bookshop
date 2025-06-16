package com.bookshop.repository;

import jakarta.persistence.EntityManager;
import com.bookshop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) { //id값이 없다는건 아예 새로 생성했다는 것을 의미함
                                    // 그래서 persist로 신규등록 하는거라 생각하면 됨
            em.persist(item);
        }else { //이미 db에 등록된 값이 있다는 거니깐 업데이트해준다는 의미로 해석할 것.
            em.merge(item); //업데이트 비슷
        }
    }

    //단건 조회
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    //여러개 조회하는건 jpql작성해야함
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
