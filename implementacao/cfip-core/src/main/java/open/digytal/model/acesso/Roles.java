package open.digytal.model.acesso;

public enum  Roles {
    ROLE_USER,
    ROLE_ADMIN;
	public static final String PRE_USER= "hasRole('USER')";
	public static final String PRE_ADMIN= "hasRole('ADMIN')";
	public static final String PRE_USER_ADMIN= "hasAnyRole('ADMIN','USER')";
}
