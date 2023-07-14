package top.openfbi.mdnote.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 借用申请审批表
 * </p>
 *
 * @author fx
 * @since 2019-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {


    /**
     * 用户ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户密码
     */
    @TableField("password")
    private String password;

}
