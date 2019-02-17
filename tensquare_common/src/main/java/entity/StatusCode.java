package entity;

/**
 * 状态码实体类
 * @author LiangDong.
 */
public interface StatusCode {

    int OK = 20000; //成功
    int ERROR = 20001; //失败
    int LOGINERROR = 20002; //用户名或密码错误
    int ACCESSERROR = 20003; //权限不足
    int REMOTEERROR = 20004; //远程调用不足
    int REPERROR = 20005; //重复操作

}
