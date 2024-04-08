package com.pruebas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.model.Producto;


public interface ProductoRepository  extends JpaRepository<Producto, Long>{

}
