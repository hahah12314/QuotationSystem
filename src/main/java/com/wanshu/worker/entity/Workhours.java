package com.wanshu.worker.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工时表
 * </p>
 *
 * @author hehe
 * @since 2024-11-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("workhours")
public class Workhours implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 加工名称
     */
    private String processName;

    /**
     * 工人ID
     */
    private Integer workerId;

    /**
     * 费用每小时
     */
    private BigDecimal cost;
    /**
     * 工时
     */

    private Integer hour;


}
