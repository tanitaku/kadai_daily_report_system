package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "relations")
@NamedQueries({
    @NamedQuery(
            name = "getMyAllFollowers",
            query = "SELECT r FROM Relation AS r WHERE r.follower = :follower"
            ),
    @NamedQuery(
            name = "getFollow",
            query = "SELECT r.followered FROM Relation AS r WHERE r.follower = :follower"
            ),
@NamedQuery(
        name = "checkFollow",
        query = "SELECT r FROM Relation AS r WHERE r.follower = :follower AND r.followered = :followered"
        ),
@NamedQuery(
        name = "getAllFollowered",
        query = "SELECT r FROM Relation AS r ORDER BY r.id DESC"
        )

})

@Entity
public class Relation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Employee follower;

    @ManyToOne
    @JoinColumn(name = "followered_id", nullable = false)
    private Employee followered;

    @Column(name = "follow_flag", nullable = false)
    private Integer follow_flag;


    public Integer getFollow_flag() {
        return follow_flag;
    }

    public void setFollow_flag(Integer follow_flag) {
        this.follow_flag = follow_flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getFollower() {
        return follower;
    }

    public void setFollower(Employee follower) {
        this.follower = follower;
    }

    public Employee getFollowered() {
        return followered;
    }

    public void setFollowered(Employee followered) {
        this.followered = followered;
    }



}
