package mb.mybatis.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author mabo
 * @Date 2022/8/22 20:09
 */
@Data
public class User {
//    ASSIGN_ID: 雪花算法
    //如果不设置类型值，默认则使用IdType.ASSIGN_ID策略
    @TableId(type = IdType.ASSIGN_ID)
    //auto策略需要本列有默认值
//    @TableId(type = IdType.AUTO)
    //ASSIGN_UUID需要本列类型是字符串
//    @TableId(type = IdType.ASSIGN_UUID)
    //INPUT需要本列不能为null，需手动输入
//    @TableId(type = IdType.INPUT)
//    @TableId(type = IdType.NONE)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //@TableField(fill = FieldFill.UPDATE)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @TableLogic
    private Integer deleted;
}