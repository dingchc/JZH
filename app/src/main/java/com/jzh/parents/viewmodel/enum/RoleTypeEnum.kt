package com.jzh.parents.viewmodel.enum

/**
 * 角色枚举
 * @author ding
 * Created by Ding on 2018/9/12.
 */
enum class RoleTypeEnum(val value: Int) {

    // 身份 0 - 家长、1 - 爸爸、2 - 妈妈、3 - 老师

    /**
     * 身份：其他
     */
    ROLE_TYPE_OTHER(0),

    /**
     * 身份：爸爸
     */
    ROLE_TYPE_FATHER(1),

    /**
     * 身份：妈妈
     */
    ROLE_TYPE_MOTHER(2),

    /**
     * 身份：教师
     */
    ROLE_TYPE_TEACHER(3);

    /**
     * 获取角色名称
     */
    fun getRoleTypeName(): String {

        when (value) {

            ROLE_TYPE_TEACHER.value -> return "教师"

            ROLE_TYPE_MOTHER.value -> return "妈妈"

            ROLE_TYPE_FATHER.value -> return "爸爸"

            else -> return "其他"
        }
    }

}