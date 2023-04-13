package com.oracle.jp.backend.repository;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ITEMS")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "ITEM_ID", nullable = false, updatable = false)
    private int id;

    @Basic(optional = false)
    @Column(name = "ITEM_TITLE")
    private String name;

    @Basic(optional = false)
    @Column(name = "ITEM_DESC")
    private String desc;

    @Basic(optional = false)
    @Column(name = "ITEM_POST_DATE")
    private String date;

    @Basic(optional = false)
    @Column(name = "ITEM_POSTED_BY")
    private String postedBy;

    @Basic(optional = false)
    @Column(name = "ITEM_BOUGHT_BY")
    private String boughtBy;

    @Basic(optional = false)
    @Column(name = "ITEM_PRICE")
    private String price;

    @Basic(optional = false)
    @Column(name = "ITEM_STATUS")
    private String status;
}
