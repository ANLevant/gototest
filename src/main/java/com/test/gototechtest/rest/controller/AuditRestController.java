package com.test.gototechtest.rest.controller;

import com.test.gototechtest.dto.AuditLogDTO;
import com.test.gototechtest.persistance.entities.Card;
import com.test.gototechtest.persistance.entities.Game;
import com.test.gototechtest.persistance.entities.Player;
import com.test.gototechtest.persistance.entities.Shoe;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("audit")
public class AuditRestController {

    @Autowired
    private EntityManagerFactory factory;

    @GetMapping
    public List getAuditLog() {
        AuditReader auditReader = AuditReaderFactory.get(factory.createEntityManager());

        List<AuditLogDTO> auditLogDTOList = new ArrayList<>();

        List shoeResults = auditReader.createQuery()
                .forRevisionsOfEntity(Shoe.class, false, false)
                .getResultList();

        List gameResults = auditReader.createQuery()
                .forRevisionsOfEntity(Game.class, false, false)
                .getResultList();

        List cardResults = auditReader.createQuery()
                .forRevisionsOfEntity(Card.class, false, false)
                .getResultList();

        List playerResults = auditReader.createQuery()
                .forRevisionsOfEntity(Player.class, false, false)
                .getResultList();

        for (Object genericShoeEnvers : shoeResults) {
            Shoe shoe = (Shoe) ((Object[]) genericShoeEnvers)[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) ((Object[]) genericShoeEnvers)[1];
            RevisionType revisionType = (RevisionType) ((Object[]) genericShoeEnvers)[2];

            AuditLogDTO auditLogDTO = new AuditLogDTO();

            auditLogDTO.setEntityName(shoe.getClass().getName());
            auditLogDTO.setRevisionType(revisionType.name());
            auditLogDTO.setRevisionDate(revisionEntity.getRevisionDate());

            auditLogDTOList.add(auditLogDTO);
        }

        for (Object genericGameEnvers : gameResults) {
            Game game = (Game) ((Object[]) genericGameEnvers)[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) ((Object[]) genericGameEnvers)[1];
            RevisionType revisionType = (RevisionType) ((Object[]) genericGameEnvers)[2];

            AuditLogDTO auditLogDTO = new AuditLogDTO();

            auditLogDTO.setEntityName(game.getClass().getName());
            auditLogDTO.setRevisionType(revisionType.name());
            auditLogDTO.setRevisionDate(revisionEntity.getRevisionDate());

            auditLogDTOList.add(auditLogDTO);
        }


        for (Object genericCardEnvers : cardResults) {
            Card card = (Card) ((Object[]) genericCardEnvers)[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) ((Object[]) genericCardEnvers)[1];
            RevisionType revisionType = (RevisionType) ((Object[]) genericCardEnvers)[2];

            AuditLogDTO auditLogDTO = new AuditLogDTO();

            auditLogDTO.setEntityName(card.getClass().getName());
            auditLogDTO.setRevisionType(revisionType.name());
            auditLogDTO.setRevisionDate(revisionEntity.getRevisionDate());

            auditLogDTOList.add(auditLogDTO);
        }

        for (Object genericPlayerEnvers : playerResults) {
            Player player = (Player) ((Object[]) genericPlayerEnvers)[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) ((Object[]) genericPlayerEnvers)[1];
            RevisionType revisionType = (RevisionType) ((Object[]) genericPlayerEnvers)[2];

            AuditLogDTO auditLogDTO = new AuditLogDTO();

            auditLogDTO.setEntityName(player.getClass().getName());
            auditLogDTO.setRevisionType(revisionType.name());
            auditLogDTO.setRevisionDate(revisionEntity.getRevisionDate());

            auditLogDTOList.add(auditLogDTO);
        }

        quickSort(auditLogDTOList, 0, auditLogDTOList.size() - 1);

        return auditLogDTOList;
    }


    private void quickSort(List<AuditLogDTO> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(List<AuditLogDTO> arr, int begin, int end) {
        Date pivot = arr.get(end).getRevisionDate();
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j).getRevisionDate().before(pivot)) {
                i++;

                AuditLogDTO swapTemp = arr.get(i);
                arr.set(i, arr.get(j));
                arr.set(j, swapTemp);
            }
        }

        AuditLogDTO swapTemp = arr.get(i + 1);
        arr.set(i + 1, arr.get(end));
        arr.set(end, swapTemp);

        return i + 1;
    }
}
