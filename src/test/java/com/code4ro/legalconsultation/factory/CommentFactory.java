package com.code4ro.legalconsultation.factory;

import com.code4ro.legalconsultation.model.dto.CommentDetailDto;
import com.code4ro.legalconsultation.model.dto.CommentDto;
import com.code4ro.legalconsultation.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.model.persistence.Comment;
import com.code4ro.legalconsultation.model.persistence.DocumentNode;
import com.code4ro.legalconsultation.repository.CommentRepository;
import com.code4ro.legalconsultation.service.api.CommentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public final class CommentFactory {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    public CommentDto create() {
        final CommentDto commentDto = RandomObjectFiller.createAndFill(CommentDto.class);
        if (commentDto == null) throw new IllegalArgumentException("Failed to create the comment");
        commentDto.setLastEditDateTime(new Date());
        commentDto.setStatus(null);
        return commentDto;
    }

    public CommentDetailDto save(final UUID nodeId) {
        return commentService.create(nodeId, create());
    }

    public Comment createEntity() {
        final Comment comment = new Comment();
        comment.setOwner(RandomObjectFiller.createAndFillWithBaseEntity(ApplicationUser.class));
        comment.setText(RandomStringUtils.randomAlphanumeric(10));
        comment.setId(UUID.randomUUID());
        return comment;
    }

    public Comment createEntityOnNodeWithUser(DocumentNode node, ApplicationUser user) {
        Comment comment = createEntity();
        comment.setOwner(user);
        comment.setDocumentNode(node);
        comment.setLastEditDateTime(new Date());
        return commentRepository.save(comment);
    }
}
