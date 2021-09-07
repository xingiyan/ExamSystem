package com.lwx.object;

/**
 * @version 1.0
 * @author: xingyan
 * @date: 2021/6/4 14:41
 * @desc: 管理员对象存放个个管理员对应账号密码
 */

public class Admin {
    /**
     * 管理员昵称
     */
    private String name = null;
    /**
     * 管理员账号
     */
    private String account = null;
    /**
     * 管理员密码
     */
    private String password = null;

    public Admin(String name, String account, String password) {
        this.name = name;
        this.account = account;
        this.password = password;
    }

    public Admin() {
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
