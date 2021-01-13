package com.example.monolithic.jpainteractions;

import com.example.monolithic.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJPA extends JpaRepository<Post, Long> {
}
