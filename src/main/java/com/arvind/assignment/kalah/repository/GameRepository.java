package com.arvind.assignment.kalah.repository;

import com.arvind.assignment.kalah.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Author: Arvind Pandey On: 20/06/2020
 * Repository class to perform CRUD operation on game resource
 */
@Transactional
public interface GameRepository extends JpaRepository<Game, Integer> {
}
