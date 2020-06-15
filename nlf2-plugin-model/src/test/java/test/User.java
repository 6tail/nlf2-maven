package test;

import com.nlf.extend.model.Model;

import java.util.Date;

/**
 * 用户Model
 *
 * CREATE TABLE `test` (
 * `id` bigint(20) NOT NULL,
 * `name` varchar(20) NOT NULL,
 * `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 * `lover_name` varchar(20) DEFAULT NULL,
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class User extends Model<User> {

  private int id;
  private String name;
  private Date time;
  private String loverName;

  /**
   * 对应test表
   */
  public User() {
    super("test");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getLoverName() {
    return loverName;
  }

  public void setLoverName(String loverName) {
    this.loverName = loverName;
  }
}
