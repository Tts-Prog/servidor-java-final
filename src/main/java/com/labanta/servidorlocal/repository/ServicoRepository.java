package com.labanta.servidorlocal.repository;

import com.labanta.servidorlocal.model.ServicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoModel, Long> {

    List<ServicoModel> findByEstaAtivoTrue();

    List<ServicoModel> findByPrecoLessThan(Double valorMaximo);

    List<ServicoModel> findByTituloContainingIgnoreCase(String termo);
}
