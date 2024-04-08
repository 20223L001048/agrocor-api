package com.pruebas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.model.Empleado;



public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

}
