package com.code4ro.legalconsultation.model.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "document_nodes")
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentNode extends BaseEntity {

    @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "parent")
    @ToString.Exclude
    private DocumentNode parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @OrderBy("node_index ASC")
    private List<DocumentNode> children;

    @Column(name = "document_node_type")
    private DocumentNodeType documentNodeType;

    @Column(name = "title")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "node_index")
    private Integer index;

    @OneToMany(mappedBy = "documentNode", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("last_edit_date ASC")
    private List<Comment> comments;
}
