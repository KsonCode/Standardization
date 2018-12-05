package com.bwie.standardization.platform.entity;

import java.io.Serializable;

/**
 * Author:kson
 * E-mail:19655910@qq.com
 * Time:2018/04/27
 * Description:每个单元知识点类
 */
public class KnowledgeEntity implements Serializable {
    public String id;//主键
    public String title;//知识点名称
    public String definition;//知识点定义
    public String connotation;//知识点内涵
    public String extension;//知识点外延

}
