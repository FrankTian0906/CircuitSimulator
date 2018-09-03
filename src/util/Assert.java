package util;

public class Assert {
	public static void check( boolean b, String message ) {
		if( !b ) throw new AssertionError( message ) ;
	}
	
	public static void check( boolean b ) {
		check( b, "Assertion failed" ) ;
	}
}
