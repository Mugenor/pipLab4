package entities;

import javax.persistence.*;

@Entity
@Table
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Double x;
    private Double y;
    private Double r;
    private Boolean isHitted;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private User user;

    public Point(){}
    public Point(Double x, Double y, Double r, User user){
        this.x = x.doubleValue();
        this.y = y.doubleValue();
        this.r = r.doubleValue();
        this.user = user;
        checkHitted();
    }
    public boolean checkHitted(){
        return (x>=0.0 && y>=0.0 && (x*x+y*y)<=(r*r/4)) ||
                (x>=0.0 && y<=0.0 && x<=r && y>=-r) ||
                (x<=0.0 && y>=0.0 && x<=(y+r/2));
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public Boolean getHitted() {
        return isHitted;
    }

    public void setHitted(Boolean hitted) {
        isHitted = hitted;
    }

    public Integer getId(){
        return id;
    }
}
