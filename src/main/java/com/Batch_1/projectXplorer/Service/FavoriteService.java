package com.Batch_1.projectXplorer.Service;

import com.Batch_1.projectXplorer.Entity.Project;
import com.Batch_1.projectXplorer.Entity.Users;
import com.Batch_1.projectXplorer.Repository.FavoriteRepo;
import com.Batch_1.projectXplorer.Repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepo favoriteRepo;

    @Autowired
    private UserRepository userRepo;

    public List<Project> getUserFavoriteProjects() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return favoriteRepo.findFavoriteProjectsByUserId(user.getUserId());
    }
}