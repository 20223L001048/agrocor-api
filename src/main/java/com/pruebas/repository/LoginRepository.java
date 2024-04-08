package com.pruebas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.model.Usuario;



public interface LoginRepository extends JpaRepository<Usuario, Long> {

}
