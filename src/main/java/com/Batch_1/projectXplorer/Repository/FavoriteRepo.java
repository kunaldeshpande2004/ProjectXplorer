package com.Batch_1.projectXplorer.Repository;

import com.Batch_1.projectXplorer.Entity.Favorites;
import com.Batch_1.projectXplorer.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorites, Integer> {

//    @Query("""
//        SELECT p FROM Project p
//        JOIN Favorite f ON p.pId = f.projectId
//        WHERE f.userId = :userId
//    """)
//    List<Project> findFavoriteProjectsByUserId(@Param("userId") Integer userId);

    @Query("""
    SELECT f.project FROM Favorites f
    WHERE f.user.userId = :userId
""")
    List<Project> findFavoriteProjectsByUserId(Integer userId);

    Optional<Favorites> findByUser_UserIdAndProject_PId(Integer userId, Integer projectId);

    void deleteByUser_UserId(Integer userId);
}