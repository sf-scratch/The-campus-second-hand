package com.lh.springboot03.repository;

import com.lh.springboot03.bean.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment,String> {
//    Page<Comment> findByParentId(String parentId, Pageable pageable);

    List<Comment> findByGoodsIdAndState(String goodsId, String state);

    List<Comment> findByGoodsIdAndParentIdAndState(String goodsId, String parentId, String state);

    Integer countByParentIdAndGoodsId(String parentId, String GoodsId);

    Integer countCommentByGoodsIdAndVisitedAndParentId(String goodsId, String visited, String parentId);

    List<Comment> findCommentByGoodsIdAndParentId(String goodsId, String parentId);

    List<Comment> findCommentsByGoodsId(String goodsId);

}
