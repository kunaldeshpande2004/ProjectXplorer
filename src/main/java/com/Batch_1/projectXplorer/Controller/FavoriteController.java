package com.Batch_1.projectXplorer.Controller;

import com.Batch_1.projectXplorer.Entity.Favorites;
import com.Batch_1.projectXplorer.Entity.Project;
import com.Batch_1.projectXplorer.Entity.Users;
import com.Batch_1.projectXplorer.Repository.FavoriteRepo;
import com.Batch_1.projectXplorer.Repository.ProjectRepo;
import com.Batch_1.projectXplorer.Repository.UserRepository;
import com.Batch_1.projectXplorer.Service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private FavoriteRepo favoriteRepo;

    @Autowired
    private ProjectRepo projectRepo;


    @GetMapping
    public List<Project> getFavorites() {
        return favoriteService.getUserFavoriteProjects();
    }

    @PostMapping("/toggle")
    public ResponseEntity<?> toggle(@RequestParam Integer projectId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = userRepo
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<Favorites> existing =
                favoriteRepo.findByUser_UserIdAndProject_PId(user.getUserId(), projectId);

        if (existing.isPresent()) {
            favoriteRepo.delete(existing.get());
        } else {
            Favorites fav = new Favorites();
            fav.setUser(user);

            Project project = projectRepo.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            fav.setProject(project);

            favoriteRepo.save(fav);
        }

        return ResponseEntity.ok("done");
    }

    @PostMapping("/clear")
    @Transactional
    public ResponseEntity<?> clearAllFavorites() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        favoriteRepo.deleteByUser_UserId(user.getUserId());

        return ResponseEntity.ok("All favorites cleared");
    }
}