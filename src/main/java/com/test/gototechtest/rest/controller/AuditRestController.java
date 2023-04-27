package com.test.gototechtest.rest.controller;

import com.test.gototechtest.persistance.entities.Player;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("audit")
public class AuditRestController {

    @Autowired
    private EntityManagerFactory factory;

    @GetMapping
    public List getAuditLog() {
        AuditReader auditReader = AuditReaderFactory.get(factory.createEntityManager());

        List results = auditReader.createQuery()
                .forRevisionsOfEntity(Player.class, false)
                .getResultList();

        return results;
    }
}
